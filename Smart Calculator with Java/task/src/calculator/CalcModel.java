package calculator;

public class CalcModel {

    public long calc(String[] parts) {
        long currentValue = Long.parseLong(parts[0]);

        for (int i = 1; i < parts.length; i += 2) {
            String operator = parts[i];
            long nextValue = Long.parseLong(parts[i + 1]);
            currentValue = executeOperation(currentValue, operator, nextValue);
        }

        return currentValue;
    }

    private long executeOperation(long currentValue, String operator, long nextValue) {
        return switch (operator) {
            case "+" -> currentValue + nextValue;
            case "-" -> currentValue - nextValue;
            case "*" -> currentValue * nextValue;
            case "/" -> {
                if (nextValue == 0) {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                yield currentValue / nextValue;
            }
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }
}
