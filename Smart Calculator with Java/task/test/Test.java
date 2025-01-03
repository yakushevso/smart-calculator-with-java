import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Test extends StageTest<String> {
    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
                new TestCase<String>().setDynamicTesting(() -> {
                    TestedProgram main = new TestedProgram();

                    // The base test suit that checks if the program correctly responses to the commands and can stop
                    main.start();


                    // test of /help
                    String output = main.execute("/help").trim();
                    if (Array.getLength(output.split(" ")) < 3) {
                        return CheckResult.wrong("It seems like there was no any \"help\" message.");
                    }

                    // test of /exit
                    output = main.execute("/exit").trim().toLowerCase();
                    if (!output.startsWith("bye")) {
                        return CheckResult.wrong("Your program didn't print \"bye\" after entering \"/exit\".");
                    }

                    return new CheckResult(main.isFinished(), "Your program should exit after entering \"/exit\".");
                }),
                new TestCase<String>().setDynamicTesting(() -> {
                    TestedProgram main = new TestedProgram();

                    // The test suit that checks if the program functionality of the previous step works fine
                    main.start();

                    // testing sum of two positive numbers
                    String output = main.execute("123 321").trim();
                    if (!output.equals("444")) {
                        return CheckResult.wrong("The program cannot sum two positive numbers");
                    }

                    // testing sum of negative and positive number
                    output = main.execute("-456 390").trim();
                    if (!output.equals("-66")) {
                        return CheckResult.wrong("The program cannot sum negative and positive number");
                    }

                    // testing sum of positive and negative number
                    output = main.execute("264 -73").trim();
                    if (!output.equals("191")) {
                        return CheckResult.wrong("The program cannot sum positive and negative number");
                    }

                    // the sum of the numbers is zero
                    output = main.execute("2 -2").trim();
                    if (!output.equals("0")) {
                        return CheckResult.wrong("The problem when sum is equal to 0 has occurred");
                    }

                    // input one number
                    output = main.execute("99").trim();
                    if (!output.equals("99")) {
                        return CheckResult.wrong("The program printed not the same number that was entered.");
                    }

                    // input one negative number
                    output = main.execute("-211").trim();
                    if (!output.equals("-211")) {
                        return CheckResult.wrong("The program printed not the same number that was entered.");
                    }

                    // input empty string
                    output = main.execute("");
                    if (output.length() != 0) {
                        return CheckResult.wrong("Incorrect response to an empty string. " +
                                "The program should not print anything.");
                    }

                    // test of /exit
                    output = main.execute("/exit").trim().toLowerCase();
                    if (!output.startsWith("bye")) {
                        return CheckResult.wrong("Your program didn't print \"bye\" after entering \"/exit\".");
                    }

                    return new CheckResult(main.isFinished(), "Your program should exit after entering \"/exit\".");
                }),
                new TestCase<String>().setDynamicTesting(() -> {
                    TestedProgram main = new TestedProgram();

                    // The positive test suit that checks new features
                    main.start();

                    // sum of positive numbers
                    String output = main.execute("4 6 8").trim();
                    if (!output.equals("18")) {
                        return CheckResult.wrong("The program cannot sum more than two numbers.");
                    }

                    // sum mixed numbers
                    output = main.execute("2 -3 -4").trim();
                    if (!output.equals("-5")) {
                        return CheckResult.wrong("Incorrect sum of positive and negative numbers.");
                    }

                    // sum of negative numbers
                    output = main.execute("-8 -7 -1").trim();
                    if (!output.equals("-16")) {
                        return CheckResult.wrong("Incorrect sum of three negative numbers.");
                    }

                    // testing a big amount of numbers
                    output = main.execute("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1").trim(); // 20
                    if (!output.equals("20")){
                        return CheckResult.wrong("The program cannot process a large number of numbers.");
                    }

                    output = main.execute("10 20 30 40 50 -10 -20 -30 -40").trim();
                    if (!output.equals("50")) {
                        return CheckResult.wrong("The program cannot process a large number of numbers.");
                    }

                    // the sum of the numbers is zero
                    output = main.execute("3 -2 -1").trim();
                    if (!output.equals("0")) {
                        return CheckResult.wrong("The problem when sum is equal to 0 has occurred");
                    }

                    // test of /exit
                    output = main.execute("/exit").trim().toLowerCase();
                    if (!output.startsWith("bye")) {
                        return CheckResult.wrong("Your program didn't print \"bye\" after entering \"/exit\".");
                    }

                    return new CheckResult(main.isFinished(), "Your program should exit after entering \"/exit\".");
                })
        );
    }
}
