package calculator;

import java.util.*;
import java.util.regex.Pattern;

public class CalcController {
    private final CalcView view;
    private final CalcModel model;

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
                        view.printOutput(Messages.BYE);
                        return;
                    }
                    case "/help" -> {
                        view.printOutput(Messages.HELP);
                        continue;
                    }
                    default -> {
                        view.printOutput(Messages.UNKNOWN_COMMAND);
                        continue;
                    }
                }
            }

            String normalizedInput = normalizeInput(input);

            if (normalizedInput.contains("=")) {
                assignmentVariable(normalizedInput);
            } else {
                if (isValidExpression(normalizedInput)) {
                    String[] pairs = normalizedInput.split("\\s");

                    if (pairs.length == 1) {
                        if (pairs[0].matches(Patterns.LETTERS)) {
                            view.printOutput(Objects.requireNonNullElse(
                                    model.getValue(pairs[0]), Messages.UNKNOWN_VARIABLE));
                        } else {
                            view.printOutput(pairs[0]);
                        }
                    } else {
                        view.printOutput(model.calc(pairs));
                    }
                } else {
                    view.printOutput(Messages.INVALID_EXPRESSION);
                }
            }
        }
    }

    private void assignmentVariable(String input) {
        if (input.matches(Patterns.VALID_ASSIGNMENT)) {
            String[] pairs = input.split(Patterns.ASSIGNMENT_OPERATOR);

            if (pairs[1].matches(Patterns.LETTERS)) {
                if (model.getValue(pairs[1]) == null) {
                    view.printOutput(Messages.UNKNOWN_VARIABLE);
                } else {
                    model.setValue(pairs[0], model.getValue(pairs[1]));
                }
            } else {
                model.setValue(pairs[0], pairs[1]);
            }
        } else if (input.matches(Patterns.VALID_VARIABLE_NAME)) {
            view.printOutput(Messages.INVALID_ASSIGNMENT);
        } else {
            view.printOutput(Messages.INVALID_IDENTIFIER);
        }
    }

    private boolean isValidExpression(String input) {
        return isValidOperators(input)
                && isOperatorsAndOperandsBalanced(input)
                && isBracketsBalanced(input);
    }

    private boolean isValidOperators(String input) {
        return !Pattern.compile(Patterns.INVALID_OPERATORS).matcher(input).find();
    }

    private boolean isOperatorsAndOperandsBalanced(String input) {
        return input.matches(Patterns.VALID_EXPRESSION);
    }

    private boolean isBracketsBalanced(String input) {
        String[] parts = input.split("\\s");
        Deque<String> stack = new ArrayDeque<>();
        Map<String, String> brackets = new HashMap<>(Map.of(")", "("));

        for (String part : parts) {
            if (brackets.containsValue(part)) {
                stack.push(part);
            } else if (brackets.containsKey(part)) {
                if (stack.isEmpty() || !Objects.equals(brackets.get(part), stack.pop())) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    private String normalizeInput(String input) {
        Pattern pattern = Pattern.compile("(\\+\\+)|(--)");

        while (pattern.matcher(input).find()) {
            input = input.replaceAll("(\\+\\+)|(--)", "+")
                    .replaceAll("\\+-|-\\+", "-");
        }

        return input.replaceAll("(?<!\\d|[)])\\+(?=\\d)", "")
                .replaceAll("-(?!\\d)|(?<=\\d)-(?=\\d)", " - ")
                .replaceAll("\\+", " + ")
                .replaceAll("\\*", " * ")
                .replaceAll("/", " / ")
                .replaceAll("\\(", " ( ")
                .replaceAll("\\)", " ) ")
                .replaceAll("\\^", " ^ ")
                .replaceAll("\\s+", " ").trim()
                .replaceAll("(?=^[-+]\\s[(])|(?<=[(]\\s)(?=[-+]\\s[(])", "0 ");
    }
}
