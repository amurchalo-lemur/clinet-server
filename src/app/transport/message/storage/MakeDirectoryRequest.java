package app.transport.message.storage;

import app.transport.message.AuthorizedMessage;

public class MakeDirectoryRequest extends AuthorizedMessage {
    private final String directoryName;

    public MakeDirectoryRequest(String authToken, String directoryName) {
        super(authToken);
        this.directoryName = directoryName;
    }

    public String getDirectoryName() {
        return directoryName;
    }

}
