import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String[] names = {"Alice", "Bob", "Charlie", "David", "Eve"};

        Arrays.asList(names)
            .stream()
            .map(String::toUpperCase)
            .forEach(System.out::println);

        Arrays.asList(names)
            .stream()
            .filter(name -> name.length() > 3)
            .forEach((s) -> System.out.println(s));
    }
}
