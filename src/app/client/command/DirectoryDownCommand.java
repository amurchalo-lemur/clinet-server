package app.client.command;

import app.IO;
import app.Settings;
import app.client.TokenHolder;
import app.transport.Transport;
import app.transport.message.storage.DirectoryDownladResponse;
import app.transport.message.storage.DirectoryDownloadRequest;

import java.io.IOException;
import java.nio.file.Files;

public class DirectoryDownCommand extends Command{
    public final TokenHolder tokenHolder;

    public DirectoryDownCommand(Transport transport, IO io, TokenHolder tokenHolder) {
        super(transport, io);
        this.tokenHolder = tokenHolder;
    }

    @Override
    protected void performConnected() throws IOException {
        io.print("enter directory (default '" + Settings.DEFAULT_FILENAME_TO_DOWNLOAD + "'): ");
        var directoryName = io.readln();
        if (directoryName.isBlank()) {
            directoryName = Settings.DEFAULT_FILENAME_TO_DOWNLOAD;
        }

        transport.send(new DirectoryDownloadRequest(tokenHolder.getToken(), directoryName));

        var response = expectMessage(DirectoryDownladResponse.class);

        var temp = Files.createTempFile("directory-storage_", "");
        try (var tempOutputStream = Files.newOutputStream(temp)) {
            transport.getInputStream().transferTo(tempOutputStream);
        }

        //new ProcessBuilder("/usr/bin/open", temp.toAbsolutePath().toString()).start();
    }
}
