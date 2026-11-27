public class Main {
    public static void main(String[] args) {
        String filePath = "example.txt";
        String textToWrite = "Hello, World!\nThis is a test.";

        // Write to the file
        WriteToFile.write(filePath, textToWrite, 1000);

        // Read from the file
        ReadFromFile.read(filePath);
        FileProperties.displayFileProperties(filePath);

    }
}
