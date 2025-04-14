package app.server.net;

import app.IO;
import app.Settings;
import app.server.ServerException;
import app.server.filestorage.FileStorageService;
import app.server.session.Session;
import app.server.session.SessionService;
import app.server.session.Token;
import app.transport.Transport;
import app.transport.message.Message;
import app.transport.message.storage.FileUploadRequest;
import app.transport.message.storage.FileUploadResponse;
import app.transport.message.storage.FileUploadRewriteConfirmation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUploadHandler extends Handler {
    private final FileStorageService fileSystemService;
    private final SessionService sessionService;

    public FileUploadHandler(Transport transport, IO io, FileStorageService fileStorageService, SessionService sessionService) {
        super(transport, io);
        this.fileSystemService = fileStorageService;
        this.sessionService = sessionService;
    }

    @Override
    public void handle(Message message) {

        System.out.println(sessionService.getMap());

        var req = (FileUploadRequest) message;

        System.out.println(message);

        var username = sessionService.get(Token.fromText(req.getAuthToken())).getString(Session.USERNAME);

        Path path = Path.of(Settings.SERVER_FILE_STORAGE_BASE_PATH, username, req.getFilename() );
        Path directory = path.getParent();

        if(req.getSize() >= Settings.MAX_FILE_SIZE) {
            throw new ServerException("file is too big");
        }
        try {
            Files.createDirectories(directory);
        }catch(IOException e){

        }

        var fileExists = fileSystemService.fileExists(username, req.getFilename());
        System.out.println("-------->" + path);
        transport.send(new FileUploadResponse(fileExists));
        if (fileExists) {
            transport.receive(FileUploadRewriteConfirmation.class);
        }


        try (var fileOutputStream = fileSystemService.getFileOutputStream(username, req.getFilename())) {
            var transpotInputStream = transport.getInputStream();
            var transferred = transpotInputStream.transferTo(fileOutputStream);
            fileOutputStream.flush();
            assert transferred == req.getSize();
        } catch (Exception e) {
            throw new ServerException(e);
        }

        //io.println("file '" + filename + "' uploaded by user '" + username + "'");
    }
}
