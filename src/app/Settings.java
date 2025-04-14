package app;

public interface Settings {
    String HOST = "localhost";
    int PORT = 5566;
    boolean DEBUG_ON = false;
    String SERVER_FILE_STORAGE_BASE_PATH = "C:/Users/imageuser/Desktop/serever_sorsce/";
    String DEFAULT_FILENAME_TO_UPLOAD = "C:/Users/imageuser/Desktop/14.png";
    String DEFAULT_FILENAME_TO_DOWNLOAD = "earth.png";
    String DEFAULT_FILENAME_TO_REMOVE = "earth.png";
    long MAX_FILE_SIZE = 5 * 1024 * 1024 * 1024L;

}
