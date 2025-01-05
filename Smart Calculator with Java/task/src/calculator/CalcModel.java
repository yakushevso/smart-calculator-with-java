package calculator;

public class CalcModel {

    public long calc(String[] parts) {
        long res = Long.parseLong(parts[0]);

        for (int i = 1; i < parts.length; i += 2) {
            String operator = parts[i];
            int nextNum = Integer.parseInt(parts[i + 1]);

            switch (operator) {
                case "+" -> res += nextNum;
                case "-" -> res -= nextNum;
                default -> {
                    throw new IllegalArgumentException("Unknown operator: " + operator);
                }
            }
        }

        return res;
    }
}
