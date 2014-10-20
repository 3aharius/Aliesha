package by.aliesha.utils;

import java.io.InputStream;

public class FileUtils {
    
    public static InputStream getFileAsStreamFromClassPath(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }
    
    public static String getClassLoaderPath() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

}
