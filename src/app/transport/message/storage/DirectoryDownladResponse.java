package app.transport.message.storage;

import app.transport.message.Message;

public class DirectoryDownladResponse extends Message {
    private final long size;

    public DirectoryDownladResponse(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }
}
