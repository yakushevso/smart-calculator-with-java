package calculator;

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
                view.printOutput("The program performs addition and subtraction of numbers");
                continue;
            }

            String[] parts = simplifyOperators(input).split("\\s+");

            if (parts.length == 1) {
                view.printOutput(parts[0]);
            } else {
                try {
                    long res = model.calc(parts);
                    view.printOutput(String.valueOf(res));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter parts or '/exit'.");
                }
            }
        }
    }

    private static String simplifyOperators(String input) {
        return input.replaceAll("--", "+")
                .replaceAll("\\+{2,}", "+")
                .replaceAll("-{2,}", "-")
                .replaceAll("\\+-", "-")
                .replaceAll("-\\+", "-");
    }
}
