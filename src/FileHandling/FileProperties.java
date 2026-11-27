import java.io.File;

public class FileProperties {
    public static void displayFileProperties(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("File Name: " + file.getName());
            System.out.println("Absolute Path: " + file.getAbsolutePath());
            System.out.println("Writable: " + file.canWrite());
            System.out.println("Readable: " + file.canRead());
            System.out.println("File Size in bytes: " + file.length());
        } else {
            System.out.println("The file does not exist.");
        }
    }
}
