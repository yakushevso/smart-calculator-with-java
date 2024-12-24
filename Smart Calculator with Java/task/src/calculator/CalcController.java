package calculator;

import java.util.Arrays;

public class CalcController {
    private final CalcModel model;
    private final CalcView view;

    public CalcController(CalcModel model, CalcView view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        while (true) {
            String input = view.getInput();

            if (input.isEmpty()) {
                continue;
            }

            if ("/exit".equals(input)) {
                view.printOutput("Bye!");
                return;
            }

            if ("/help".equals(input)) {
                view.printOutput("The program calculates the sum of numbers");
                continue;
            }

            String[] parts = input.trim().split("\\s+");

            if (parts.length == 1) {
                view.printOutput(parts[0]);
            } else {
                try {
                    int[] nums = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
                    long sum = model.add(nums);
                    view.printOutput(String.valueOf(sum));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter parts or '/exit'.");
                }
            }
        }
    }
}
