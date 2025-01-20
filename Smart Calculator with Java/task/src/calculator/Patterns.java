package calculator;

public class Patterns {
    public static final String VALID_ASSIGNMENT = "^[a-zA-Z]+\\s*=\\s*(-?\\d+|[a-zA-Z]+)$";
    public static final String VALID_VARIABLE_NAME = "^[a-zA-Z]+\\s=.*";
    public static final String ASSIGNMENT_OPERATOR = "\\s*=\\s*";
    public static final String INVALID_OPERATORS = "[*/^]\\s[*/^]";
    public static final String LETTERS = "[a-zA-Z]+";
    public static final String DIGITS = "-?\\d+";
    public static final String VALID_EXPRESSION = "^([()]\\s)*(-?\\d+|[a-zA-Z]+)(\\s[()])*" +
            "(\\s[-+*/^]\\s([()]\\s)*(-?\\d+|[a-zA-Z]+)(\\s[()])*)*$";
}
