package LambdasAndStreams;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers
            = Arrays.asList(11, 22, 33, 44,
                            55, 66, 77, 88,
                            99, 100);

        // Using lambda expression to print all elements
        numbers.forEach(num -> IO.println(num));

        numbers.stream()
                .map(num -> num * 2) // double each number
                .forEach(num -> IO.println(num)); // print each doubled number

        // print numbers that are even
        // and sum it
        IO.println(numbers.stream()
        .filter(num -> num % 2 == 0) // filter even numbers
        .mapToInt(num -> num) // convert to IntStream
        .sum()); // sum the even numbers
    }
}
