package app.client.command;

import app.IO;
import app.client.TokenHolder;
import app.transport.Transport;
import app.transport.message.SuccessResponse;
import app.transport.message.storage.MakeDirectoryRequest;

import java.io.IOException;

public class MakeDirectoryCommand extends Command {
    public final TokenHolder tokenHolder;

    public MakeDirectoryCommand(Transport transport, IO io, TokenHolder tokenHolder) {
        super(transport, io);
        this.tokenHolder = tokenHolder;
    }

    @Override
    protected void performConnected() throws IOException {
        io.print("enter directoryName: ");
        var directoryName = io.readln();
        transport.send(new MakeDirectoryRequest(tokenHolder.getToken(), directoryName));
        expectMessage(SuccessResponse.class);
        io.println("directory is create successfully");
    }
}
