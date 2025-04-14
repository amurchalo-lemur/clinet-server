package app.transport.message.storage;

import app.transport.message.AuthorizedMessage;

public class FileRemoveRequest extends AuthorizedMessage {
    private final String filename;

    public FileRemoveRequest(String authToken, String filename) {
        super(authToken);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
