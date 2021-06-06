package carry.ilearn.service.utils;

/**
 * @author Carry
 * @since 2021/5/23
 */
public class ExamServiceUtil {
    
    private static final int CODE_LENGTH = 6;
    private static final char[][] CHAR_TABLE;
    
    static {
        CHAR_TABLE = new char[CODE_LENGTH][34];
        int i = 0;
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            if (ch == 'O') {
                continue;
            }
            CHAR_TABLE[0][i++] = ch;
        }
        for (char ch = '1'; ch <= '9'; ch++) {
            CHAR_TABLE[0][i++] = ch;
        }
        for (int j = 1; j < CODE_LENGTH; j++) {
            for (int k = 0; k < 34; k++) {
                CHAR_TABLE[j][k] = CHAR_TABLE[j - 1][((k - 5) + 34) % 34];
            }
        }
    }
    
    public static String base34(int n) {
        int[] codeArray = new int[CODE_LENGTH];
        int i = 0;
        while (n > 0) {
            int m = n % 34;
            codeArray[i++] = m;
            n = n / 34;
        }
        
        StringBuilder result = new StringBuilder();
        for (int j = CODE_LENGTH - 1; j >= 0; j--) {
            result.append(CHAR_TABLE[j][codeArray[j]]);
        }
        return result.toString();
    }
    
    private static void rotateLeft(char[] a, int step) {
        if (a == null || step < 0) {
            throw new IllegalArgumentException("step 不能小于 0");
        }
        if (step == 0) {
            return;
        }
        int moved = 0;
        int turn = 0;
        while (moved < a.length) {
            int anchor = turn;
            char initNum = a[anchor];
            int i = anchor;
            while (true) {
                moved++;
                final int next = (i + step) % a.length;
                if (next == anchor) {
                    a[i] = initNum;
                    break;
                }
                a[i] = a[next];
                i = next;
            }
            turn++;
        }
    }
    
    public static void main(String[] args) {
        int n = 1;
        System.out.println(base34(n));
    }
    
}
