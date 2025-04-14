package app.transport.message.storage;

import app.transport.message.AuthorizedMessage;

public class FileUploadRequest extends AuthorizedMessage {
    private  final String filename; // a/b/test.txt
    private final long size;

    public FileUploadRequest(String authToken, String filename, long size) {
        super(authToken);
        this.filename = filename;
        this.size = size;

//        Files.walk(Path.of(""))
//        .filter(Files::isRegularFile)
//                .count();
//        Files.copy(Path.of(""), Path.of(""));
//        new ProcessBuilder("cp", "", "").start();
    }

    public String getFilename() {
        return filename;
    }

    public long getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "FileUploadRequest{" +
                "filename='" + filename + '\'' +
                ", size=" + size +
                '}';
    }
}
