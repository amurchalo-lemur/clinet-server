package app.server.net;

import app.IO;
import app.server.ServerException;
import app.server.filestorage.FileStorageService;
import app.server.session.Session;
import app.server.session.SessionService;
import app.server.session.Token;
import app.transport.Transport;
import app.transport.TransportException;
import app.transport.message.Message;
import app.transport.message.storage.*;

public class FileRemoveHandler extends Handler {
    private final FileStorageService fileSystemService;
    private final SessionService sessionService;

    public FileRemoveHandler(Transport transport, IO io, FileStorageService fileStorageService, SessionService sessionService) {
        super(transport, io);
        this.fileSystemService = fileStorageService;
        this.sessionService = sessionService;
    }

    @Override
    public void handle(Message message) {
        var req = (FileRemoveRequest) message;
        var username = sessionService.get(Token.fromText(req.getAuthToken())).getString(Session.USERNAME);

        var filename = req.getFilename();
        if (!fileSystemService.fileExists(username, filename)) {
            throw new TransportException("no file '%s'".formatted(filename));
        } else {
            fileSystemService.delete(username, filename);
            transport.send(new FileRemoveResponse());
        }

        io.println("file '" + filename + "' uploaded by user '" + username + "'");
    }
}
