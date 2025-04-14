package app.transport.message.storage;

import app.transport.message.AuthorizedMessage;

public class DirectoryDownloadRequest extends AuthorizedMessage {
    private final String directory;

    public DirectoryDownloadRequest(String authToken, String directory) {
        super(authToken);
        this.directory = directory;
    }

    public String getFilename() {
        return directory;
    }
}
