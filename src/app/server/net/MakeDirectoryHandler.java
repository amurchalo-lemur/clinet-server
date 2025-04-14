package app.server.net;

import app.IO;
import app.Settings;
import app.server.ServerException;
import app.server.filestorage.FileStorageService;
import app.server.session.SessionService;
import app.transport.Transport;
import app.transport.message.Message;
import app.transport.message.SuccessResponse;
import app.transport.message.storage.MakeDirectoryRequest;

import java.io.File;

public class MakeDirectoryHandler extends Handler{
    private final FileStorageService fileSystemService;
    private final SessionService sessionService;

    public MakeDirectoryHandler(Transport transport, IO io, FileStorageService fileStorageService, SessionService sessionService) {
        super(transport, io);
        this.fileSystemService = fileStorageService;
        this.sessionService = sessionService;
    }

    @Override
    public void handle(Message message) {
        var req = (MakeDirectoryRequest) message;

        File directory = new File(Settings.SERVER_FILE_STORAGE_BASE_PATH + req.getDirectoryName());

        if(!directory.exists()){
            directory.mkdir();
        }else{
            throw new ServerException("directory exist!");
        }
        transport.send(new SuccessResponse());
//        var directoryname = req.getDirectoryName();
//        io.debug("username - " + username);
//        if (!fileSystemService.isUsernameValid(username)) {
//            throw new ServerException("username is invalid, pattern: [a-zA-Z0-9_]+");
//        }
//        if (userService.userExists(username)) {
//            throw new ServerException("user registered already");
//        }
//        transport.send(new SuccessResponse());
//
//        var password = transport.receive(RegisterPasswordRequest.class).getPassword();
//        io.debug("password - " + password);
//        if (!userService.isPasswordValid(password)) {
//            throw new ServerException("password is invalid");
//        }
//
//        new File(Settings.SERVER_FILE_STORAGE_BASE_PATH + username).mkdirs();
//
//
//        userService.register(username, password);
//        transport.send(new SuccessResponse());
//        io.println("registered " + username + ":" + password);
    }
}
