package calculator;

public class Main {

    public static void main(String[] args) {
        CalcModel calcModel = new CalcModel();
        CalcView calcView = new CalcView();
        CalcController calcController = new CalcController(calcModel, calcView);
        calcController.run();
    }
}
