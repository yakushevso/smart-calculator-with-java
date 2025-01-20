package calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class CalcModel {
    private final Map<String, String> variables = new HashMap<>();

    public String calc(String[] pairs) {
        Deque<String> postfixExpression = infixToPostfix(pairs);
        Deque<String> res = new ArrayDeque<>();

        while (!postfixExpression.isEmpty()) {
            String peek = postfixExpression.peek();

            if (peek.matches(Patterns.DIGITS)) {
                res.push(postfixExpression.pop());
            } else if (peek.matches(Patterns.LETTERS)) {
                res.push(getValue(postfixExpression.pop()));
            } else {
                res.push(executeOperation(postfixExpression.pop(), res));
            }
        }

        return res.pop();
    }

    private String executeOperation(String operator, Deque<String> values) {
        int a = Integer.parseInt(values.pop());
        int b = Integer.parseInt(values.pop());

        return switch (operator) {
            case "+" -> String.valueOf(b + a);
            case "-" -> String.valueOf(b - a);
            case "*" -> String.valueOf(b * a);
            case "/" -> {
                if (a == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield String.valueOf(b / a);
            }
            case "^" -> String.valueOf((int) Math.pow(b, a));
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };
    }

    private Deque<String> infixToPostfix(String[] input) {
        Deque<String> res = new ArrayDeque<>();
        Deque<String> stack = new ArrayDeque<>();
        Map<String, Integer> operators = Map.of(
                "(", 0, ")", 0,
                "+", 1, "-", 1,
                "*", 2, "/", 2,
                "^", 3
        );

        for (String part : input) {
            if ("(".equals(part)) {
                stack.push("(");
            } else if (")".equals(part)) {
                while (!stack.isEmpty() && !"(".equals(stack.peek())) {
                    res.offer(stack.pop());
                }
                stack.pop();
            } else if (operators.containsKey(part)) {
                while (!stack.isEmpty() && operators.get(part) <= operators.get(stack.peek())) {
                    res.offer(stack.pop());
                }
                stack.push(part);
            } else {
                res.offer(part);
            }
        }

        while (!stack.isEmpty()) {
            res.offer(stack.pop());
        }

        return res;
    }

    public String getValue(String key) {
        if (this.variables.containsKey(key)) {
            return variables.get(key);
        } else {
            return null;
        }
    }

    public void setValue(String key, String value) {
        this.variables.put(key, value);
    }
}
