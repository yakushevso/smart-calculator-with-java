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

            if (input.startsWith("/")) {
                switch (input) {
                    case "/exit" -> {
                        view.printOutput("Bye!");
                        return;
                    }
                    case "/help" -> {
                        view.printOutput("The program performs addition and subtraction of numbers");
                        continue;
                    }
                    default -> {
                        view.printOutput("Unknown command");
                        continue;
                    }
                }
            }

            String formattedInput = simplifyOperators(input);

            if (isValidInput(formattedInput)) {
                String[] parts = formattedInput.split("\\s+");

                if (parts.length == 1) {
                    view.printOutput(parts[0]);
                } else {
                    long result = model.calc(parts);
                    view.printOutput(String.valueOf(result));
                }
            } else {
                view.printOutput("Invalid expression");
            }
        }
    }

    private boolean isValidInput(String input) {
        return input.matches("^(-?\\d+)(\\s[-+*/]\\s-?\\d+)*$");
    }

    private static String simplifyOperators(String input) {
        return input.replaceAll("--", "+")
                .replaceAll("\\+{2,}", "+")
                .replaceAll("-{2,}", "-")
                .replaceAll("\\+-", "-")
                .replaceAll("-\\+", "-")
                .replaceAll("\\+(?=\\d)", "");
    }
}
