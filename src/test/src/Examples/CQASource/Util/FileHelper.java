package Util;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class FileHelper {

    public static List<String> findFilesInDirectory(String path) {

        File dir = new File(path);
        List<String> paths = new LinkedList<>();
        File[] files = dir.listFiles(File::isFile);

        if(files != null) {
            for (File file : files) {
                paths.add(file.getPath());
            }
        }

        return paths;
    }

    public static String getWorkingDir() {
        return System.getProperty("user.dir");
    }

    public static String getFileNameFromPath(String dir) {
        return Paths.get(dir).getFileName().toString();
    }

    public static String getFileNameFromPath(String dir, boolean keepExtension) {
        String fileName = getFileNameFromPath(dir);
        if(!keepExtension) {
            fileName = FilenameUtils.removeExtension(fileName);
        }

        return fileName;
    }


    public static String loadTextFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void saveTextToFile(String path, String text) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));
            writer.write(text);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLocalFilePath(String localFileName) {
        return getWorkingDir().concat(localFileName);
    }

    public static String appendPath(String path, String subpath) {
        return path + '/' + subpath;
    }

    public static boolean checkAndCreateDirectory(String path) {
        boolean success = true;

        if(!isDirectoryPathValid(path)) {
            File dir = new File(path);
            success = dir.mkdirs();
        }
        return success;
    }

    public static boolean isDirectoryPathValid(String path) {
        boolean isValid;

        try {
            File file = new File(path);
            isValid = file.isDirectory();

        } catch (Exception e) {
            isValid = false;
        }

        return isValid;
    }

}
