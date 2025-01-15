package calculator;

import java.util.HashMap;
import java.util.Map;

public class CalcController {
    private final CalcView view;
    private final CalcModel model;
    private final Map<String, String> archive;
    private static final String EXIT = "/exit";
    private static final String HELP = "/help";

    public CalcController(CalcModel model, CalcView view) {
        this.model = model;
        this.view = view;
        this.archive = new HashMap<>();
    }

    public void run() {
        while (true) {
            String input = view.getInput();

            if (input.isEmpty()) {
                continue;
            }

            if (input.startsWith("/")) {
                switch (input) {
                    case EXIT -> {
                        view.printOutput("Bye!");
                        return;
                    }
                    case HELP -> {
                        view.printOutput("The program performs addition and subtraction of numbers " +
                                "with the ability to save variables");
                        continue;
                    }
                    default -> {
                        view.printOutput("Unknown command");
                        continue;
                    }
                }
            }

            String normalizedInput = normalizeOperators(input);

            if (normalizedInput.contains("=")) {
                assignment(normalizedInput);
            } else {
                calculation(normalizedInput);
            }
        }
    }

    private void assignment(String input) {
        if (input.matches("^[a-zA-Z]+\\s*=\\s*(\\d+|[a-zA-Z]+)$")) {
            String[] pairs = input.split("\\s*=\\s*");
            String value = pairs[1].matches("\\d+") ? pairs[1] : resolveValue(pairs[1]);

            if (value != null) {
                archive.put(pairs[0], value);
            }
        } else if (input.matches("^[a-zA-Z]+\\s*=.*")) {
            view.printOutput("Invalid assignment");
        } else {
            view.printOutput("Invalid identifier");
        }
    }

    private void calculation(String input) {
        if (input.matches("^(-?\\d+|[a-zA-Z]+)(\\s[-+*/]\\s(-?\\d+|[a-zA-Z]+))*$")) {
            String[] parts = input.split("\\s+");

            for (int i = 0; i < parts.length; i++) {
                if (parts[i].matches("[a-zA-Z]+")) {
                    String resolvedValue = resolveValue(parts[i]);
                    if (resolvedValue != null) {
                        parts[i] = resolvedValue;
                    } else {
                        return;
                    }
                }
            }

            if (parts.length == 1) {
                view.printOutput(parts[0]);
            } else {
                view.printOutput(String.valueOf(model.calc(parts)));
            }
        } else {
            view.printOutput("Invalid expression");
        }
    }

    private String resolveValue(String key) {
        if (archive.containsKey(key)) {
            return archive.get(key);
        } else {
            view.printOutput("Unknown variable");
            return null;
        }
    }

    private static String normalizeOperators(String input) {
        return input.replaceAll("--", "+")
                .replaceAll("\\+{2,}", "+")
                .replaceAll("-{2,}", "-")
                .replaceAll("\\+-", "-")
                .replaceAll("-\\+", "-")
                .replaceAll("\\+(?=\\d)", "")
                .replaceAll("={2,}", "=");
    }
}
