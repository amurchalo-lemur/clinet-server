package app.server.net;

import app.IO;
import app.Settings;
import app.server.ServerException;
import app.server.filestorage.FileStorageService;
import app.server.user.UserService;
import app.transport.Transport;
import app.transport.message.Message;
import app.transport.message.SuccessResponse;
import app.transport.message.storage.RegisterPasswordRequest;
import app.transport.message.storage.RegisterUsernameRequest;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RegisterHandler extends Handler {
    private final UserService userService;
    private final FileStorageService fileSystemService;

    public RegisterHandler(Transport transport, IO io, UserService userService, FileStorageService fileSystemService) {
        super(transport, io);
        this.userService = userService;
        this.fileSystemService = fileSystemService;
    }

    @Override
    public void handle(Message message) {
        var req = (RegisterUsernameRequest) message;

        var username = req.getUsername();
        io.debug("username - " + username);
        if (!fileSystemService.isUsernameValid(username)) {
            throw new ServerException("username is invalid, pattern: [a-zA-Z0-9_]+");
        }
        if (userService.userExists(username)) {
            throw new ServerException("user registered already");
        }
        try{
        //Thread.sleep(10000);
        }catch (Exception e){

        }

        transport.send(new SuccessResponse());

        var password = transport.receive(RegisterPasswordRequest.class).getPassword();
        io.debug("password - " + password);
        if (!userService.isPasswordValid(password)) {
            throw new ServerException("password is invalid");
        }
        Path path = Path.of(Settings.SERVER_FILE_STORAGE_BASE_PATH, username + '/');
        //filestorage
        try {
            Files.createDirectory(path);
            FileUtils.copyDirectory(new File( "C:/Users/imageuser/Desktop/reference/"), new File(path.toString()));
        }catch (IOException e){

        }


        userService.register(username, password);

        transport.send(new SuccessResponse());
        io.println("registered " + username + ":" + password);
    }
}
