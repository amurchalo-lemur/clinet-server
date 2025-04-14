package app.client.command;

import app.IO;
import app.Settings;
import app.client.TokenHolder;
import app.transport.Transport;
import app.transport.message.storage.FileDownloadRequest;
import app.transport.message.storage.FileDownloadResponse;
import app.transport.message.storage.FileRemoveRequest;
import app.transport.message.storage.FileRemoveResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileRemoveCommand extends Command {
    public final TokenHolder tokenHolder;

    public FileRemoveCommand(Transport transport, IO io, TokenHolder tokenHolder) {
        super(transport, io);
        this.tokenHolder = tokenHolder;
    }

    @Override
    protected void performConnected() throws IOException {
        io.print("enter filename (default '" + Settings.DEFAULT_FILENAME_TO_REMOVE + "'): ");
        var filename = io.readln();
        if (filename.isBlank()) {
            filename = Settings.DEFAULT_FILENAME_TO_REMOVE;
        }

        transport.send(new FileRemoveRequest(tokenHolder.getToken(), filename));
        var response = expectMessage(FileRemoveResponse.class);
    }
}
