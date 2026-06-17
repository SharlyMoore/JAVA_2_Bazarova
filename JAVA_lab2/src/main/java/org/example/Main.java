package org.example;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("Program is running");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the line:");
        String input = scanner.nextLine();

        String reversed = StringUtils.reverse(input);

        logger.info("Source string: {}", input);
        logger.info("Reversed string: {}", reversed);
        logger.info("The program is completed");
    }
}