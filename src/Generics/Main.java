package Generics;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Create a BaseError object with String data
        BaseError<String> stringError = new BaseError<>("Some data", "An error occurred", 400);
        System.out.println("String Error Data: " + stringError.getData());
        System.out.println("String Error Message: " + stringError.getMessage());
        System.out.println("String Error HTTP Status Code: " + stringError.getHttpStatusCode());

        // Create a BaseError object with Integer data
        BaseError<Integer> integerError = new BaseError<>(123, "Another error occurred", 500);
        System.out.println("Integer Error Data: " + integerError.getData());
        System.out.println("Integer Error Message: " + integerError.getMessage());
        System.out.println("Integer Error HTTP Status Code: " + integerError.getHttpStatusCode());

        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        BaseError<ArrayList<Integer>> listError = new BaseError<>(numbers, "List error occurred", 404);
        System.out.println("List Error Data: " + listError.getData());
        System.out.println("List Error Message: " + listError.getMessage());
        System.out.println("List Error HTTP Status Code: " + listError.getHttpStatusCode());

    }
}
