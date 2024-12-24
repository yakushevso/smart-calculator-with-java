package calculator;

public class CalcModel {

    public long add(int[] nums) {
        long res = 0;

        for (int i : nums) {
            res += i;
        }
        return res;
    }
}
