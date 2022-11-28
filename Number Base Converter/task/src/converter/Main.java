package converter;

import java.math.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        do {
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit)");
            Scanner scanner = new Scanner(System.in);
            Scanner scan = new Scanner(System.in);
            String baseInput = scanner.nextLine();
            if (baseInput.equals("/exit")) {
                System.exit(0);
            }
            String[] split = baseInput.split("\\s+");
            int sourceBase = Integer.parseInt(split[0]);
            int targetBase = Integer.parseInt(split[1]);
            do {
                System.out.printf("Enter number in base %s to convert to base %d (To go back type /back)", sourceBase, targetBase);
                String inputNumber = scan.nextLine();
                if (inputNumber.equals("/back")) {
                    break;
                }

                int numberDigits = 6;
                int index = inputNumber.indexOf('.');
                //assert index != -1;
                int l = inputNumber.length() - index - 1;
                assert l >= 1;
                int firstIndex = index + 1;
                int hexDigits[] = new int[l];
                StringBuilder decimal = new StringBuilder((index == 0 || index == -1) ? String.valueOf(/*Integer.parseInt*/new BigInteger(inputNumber, sourceBase)) : String.valueOf(new BigInteger(inputNumber.substring(0, index), sourceBase)));
                if (!(index == -1)) {
                    decimal.append('.');
                    for (int i = 0; i < l; i++) {
                        hexDigits[i] = Integer.parseInt(inputNumber.substring(i + firstIndex, i + firstIndex + 1), sourceBase);
                    }
                }
                while (numberDigits != 0 && l != 0) {
                    int carry = 0;
                    boolean allZeroes = true;
                    for (int i = l - 1; i >= 0; i--) {
                        int value = hexDigits[i] * 10 + carry;
                        if (value == 0 && allZeroes) {
                            l = i;
                        } else {
                            allZeroes = false;
                            carry = (value / sourceBase);
                            hexDigits[i] = value % sourceBase;
                        }
                    }
                    numberDigits--;
                    if (carry != 0 || (numberDigits != 0 && l != 0))
                        decimal.append("0123456789".charAt(carry));
                }

                int n;
                if (inputNumber.contains(".")) {
                    n = 5;
                } else {
                    n = 0;
                }
                BigDecimal bd = new BigDecimal(decimal.toString());
                BigDecimal mult = new BigDecimal(targetBase).pow(n);
                bd = bd.multiply(mult);
                BigInteger bi = bd.toBigInteger();
                StringBuilder str = new StringBuilder(bi.toString(targetBase));
                while (str.length() < n + 1) {  // +1 for leading zero
                    str.insert(0, "0");
                }
                if (inputNumber.contains(".")) {
                    str.insert(str.length() - n, ".");
                }
                System.out.println("Conversion result: " + str);

            } while (true);
        } while (true);
    }
}
