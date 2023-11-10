package org.example.ui;

import java.util.Arrays;
import java.util.Scanner;

public class TypeManager {

    public static <T> T getType(Class<T> clazz) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease choose " + clazz.getSimpleName() + ": ");

        Arrays.stream(clazz.getEnumConstants())
                .forEach(System.out::println);

        String chosenType = scanner.nextLine();

        return Arrays.stream(clazz.getEnumConstants())
                .filter(type -> chosenType.equalsIgnoreCase(type.toString()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Incorrect type!"));
    }
}
