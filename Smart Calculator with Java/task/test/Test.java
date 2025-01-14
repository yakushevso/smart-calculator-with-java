import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.SimpleTestCase;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Arrays;
import java.util.List;

public class Test extends StageTest<String> {

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
                new TestCase<String>().setDynamicTesting(() -> {
                    TestedProgram main = new TestedProgram();

                    // tests of functionality of previous steps
                    main.start();

                    // test of /help
                    String output = main.execute("/help").trim();
                    if (output.length() < 4) {
                        return CheckResult.wrong("It seems like there was no any \"help\" message.");
                    }

                    // input empty string
                    output = main.execute("");
                    if (output.length() != 0) {
                        return CheckResult.wrong("Incorrect response to an empty string. " +
                                "The program should not print anything.");
                    }

                    // sum of positive numbers
                    output = main.execute("5 + 1 + 4").trim();
                    if (!output.equals("10")) {
                        return CheckResult.wrong("The program cannot sum more than two numbers.");
                    }

                    // sum of numbers with different signs & negative answer
                    output = main.execute("23 - 17 - 13").trim();
                    if (!output.equals("-7")) {
                        return CheckResult.wrong("Incorrect sum of positive and negative numbers.");
                    }

                    // testing a big amount of numbers
                    output = main.execute("33 + 20 + 11 + 49 - 32 - 9 + 1 - 80 + 4").trim();
                    if (!output.equals("-3")) {
                        return CheckResult.wrong("The program cannot process a large number of numbers.");
                    }

                    // the sum of the numbers is zero
                    output = main.execute("11 - 7 - 4").trim();
                    if (!output.equals("0")) {
                        return CheckResult.wrong("The problem when sum is equal to 0 has occurred.");
                    }

//                    // test of multiple operations
//                    output = main.execute("5 --- 2 ++++++ 4 -- 2 ---- 1").trim();
//                    if (!output.equals("10")) {
//                        return CheckResult.wrong("The program cannot process multiple operations with several operators.");
//                    }

                    // test of a nonexistent command
                    output = main.execute("/start").trim().toLowerCase();
                    if (!output.startsWith("unknown")) {
                        return CheckResult.wrong("The program should print \"Unknown command\" " +
                                "when a nonexistent command is entered.");
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

                    // testing different assignments
                    main.start();

                    // testing basic assignment
                    String output = main.execute("n = 5");
                    if (output.length() != 0) {
                        return CheckResult.wrong("Unexpected reaction after assignment." +
                                "The program should not print anything in this case.");
                    }

                    // assignment without spaces
                    output = main.execute("m=2");
                    if (output.length() != 0) {
                        return CheckResult.wrong("Unexpected reaction after assignment." +
                                "The program should not print anything in this case.");
                    }

                    // assignment with extra spaces
                    output = main.execute("a    =  7");
                    if (output.length() != 0) {
                        return CheckResult.wrong("Unexpected reaction after assignment." +
                                "The program should not print anything in this case.");
                    }

                    // assign the value of another variable
                    output = main.execute("c=  a ");
                    if (output.length() != 0) {
                        return CheckResult.wrong("Unexpected reaction after assignment." +
                                "The program should not print anything in this case.");
                    }

                    // test printing values of the variables
                    output = main.execute("a").trim();
                    if (!output.equals("7")) {
                        return CheckResult.wrong("The variable stores not a correct value.");
                    }

                    output = main.execute("c").trim();
                    if (!output.equals("7")) {
                        return CheckResult.wrong("The variable stores not a correct value." +
                                "May be the program could not assign the value of one variable to another one.");
                    }

                    // trying to assign a new variable after printing
                    output = main.execute("test = 0");
                    if (output.length() != 0) {
                        return CheckResult.wrong("Unexpected reaction after assignment." +
                                "The program should not print anything in this case.");
                    }

                    // check if assignment was successful
                    output = main.execute("test").trim();
                    if (!output.equals("0")) {
                        return CheckResult.wrong("The variable stores not a correct value.");
                    }

                    // trying to reassign
                    output = main.execute("test = 1");
                    if (output.length() != 0) {
                        return CheckResult.wrong("Unexpected reaction after assignment." +
                                "The program should not print anything in this case.");
                    }

                    // trying to reassign with the value of another variable
                    output = main.execute("a = test");
                    if (output.length() != 0) {
                        return CheckResult.wrong("Unexpected reaction after assignment." +
                                "The program should not print anything in this case.");
                    }

                    output = main.execute("a").trim();
                    if (!output.equals("1")) {
                        return CheckResult.wrong("The program could not reassign variable.");
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

                    // testing operations with variables
                    main.start();

                    // created some variables
                    String output = main.execute("a = 9\nb=2\nc = 1").trim();
                    if (output.length() != 0) {
                        return CheckResult.wrong("Unexpected reaction after assignment." +
                                "The program should not print anything in this case.");
                    }

                    // testing simple sum
                    output = main.execute("a + b").trim();
                    if (!output.equals("11")) {
                        return CheckResult.wrong("The program cannot perform operations with variables. " +
                                "For example, addition operation.");
                    }

                    // subtraction testing
                    output = main.execute("b - a").trim();
                    if (!output.equals("-7")) {
                        return CheckResult.wrong("The program cannot perform operations with variables. " +
                                "For example, subtraction operation.");
                    }

                    // adding a negative number
                    output = main.execute("b + c").trim();
                    if (!output.equals("3")) {
                        return CheckResult.wrong("The program cannot perform operations with variables. " +
                                "For example, addition operation.");
                    }

                    // subtraction of negative number
                    output = main.execute("b - c").trim();
                    if (!output.equals("1")) {
                        return CheckResult.wrong("The program cannot perform operations with variables. " +
                                "For example, subtraction operation.");
                    }

                    // testing multiple operations
                    output = main.execute("a -- b - c + 3 --- a ++ 1").trim();
                    if (!output.equals("5")) {
                        return CheckResult.wrong("The program cannot perform several operations in one line.");
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

                    // a set of negative tests
                    main.start();

                    // testing invalid variable name
                    String output = main.execute("var1 = 1").trim().toLowerCase();
                    if (!output.startsWith("invalid")) {
                        return CheckResult.wrong("The name of variable should contain only Latin letters.");
                    }
                    output = main.execute("кириллица = 123").trim().toLowerCase();
                    if (!output.startsWith("invalid")) {
                        return CheckResult.wrong("The name of variable should contain only Latin letters.");
                    }

                    // testing invalid value
                    output = main.execute("var = 2a").trim().toLowerCase();
                    if (!output.startsWith("invalid")) {
                        return CheckResult.wrong("The value can be an integer number or a value of another variable.");
                    }

                    // testing multiple equalization
                    output = main.execute("c = 7 - 1 = 5").trim().toLowerCase();
                    if (!output.startsWith("invalid")) {
                        return CheckResult.wrong("The program could not handle a invalid assignment.");
                    }

                    // testing assignment by unassigned variable
                    output = main.execute("variable = f").trim().toLowerCase();
                    if (!(output.startsWith("unknown") || output.startsWith("invalid"))) {
                        return CheckResult.wrong("The program should not allow an assignment by unassigned variable.");
                    }

                    // checking case sensitivity
                    main.execute("variable = 777");
                    output = main.execute("Variable").trim().toLowerCase();
                    if (!output.startsWith("unknown")) {
                        return CheckResult.wrong("The program should be case sensitive.");
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
