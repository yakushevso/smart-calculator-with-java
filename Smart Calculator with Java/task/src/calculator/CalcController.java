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

            if ("/exit".equals(input)) {
                view.printOutput("Bye!");
                return;
            }

            String[] parts = input.trim().split("\\s+");

            if (parts.length == 1) {
                view.printOutput(parts[0]);
            } else {
                try {
                    int first = Integer.parseInt(parts[0]);
                    int second = Integer.parseInt(parts[1]);
                    int result = model.add(first, second);
                    view.printOutput(String.valueOf(result));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter parts or '/exit'.");
                }
            }
        }
    }


}
