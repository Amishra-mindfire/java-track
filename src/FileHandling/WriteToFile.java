import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {
    
    public static void write(String filePath, String text, int count)
    {
        File file = new File(filePath);
        if(!file.exists()) {
            try {
                boolean isCreated = file.createNewFile();
                if(!isCreated) System.out.println("File already exists.");
                else System.out.println("File created successfully.");
                FileWriter writer = new FileWriter(file, true);
                for(int i = 0; i < count; i++) {
                    writer.write(text + "\n");
                }
                writer.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file.");
                e.printStackTrace();
            }
        }
    }
}
