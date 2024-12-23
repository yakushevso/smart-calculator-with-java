package calculator;

import java.util.Scanner;

public class CalcView {
    private final Scanner scanner = new Scanner(System.in);

    public String getInput() {
        return scanner.nextLine().trim();
    }

    public void printOutput(String output) {
        System.out.println(output);
    }
}
