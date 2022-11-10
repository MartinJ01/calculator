import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static String input1, input2, operation;
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        System.out.println(calc(input));
    }

    public static String calc(String input) throws Exception {
        String[] inputStr = input.split(" ");
        if (inputStr.length != 3) {
            throw new Exception("Incorrect input");
        }
        input1 = inputStr[0];
        operation = inputStr[1];
        input2 = inputStr[2];

        if (isCorrectIntegerInput()) {
            if (Integer.parseInt(input1) > 10 || Integer.parseInt(input2) > 10) {
                throw new Exception("Invalid input");
            }
            return Integer.toString(calculate(Integer.parseInt(input1), operation, Integer.parseInt(input2)));
        }
        if (isCorrectRomanInput()) {
            if (rom2dec(input1) < rom2dec(input2) && operation == "-" || rom2dec(input2) > 10 || rom2dec(input1) > 10) {
                throw new Exception("Invalid input");
            }
            return dec2rom(calculate(rom2dec(input1), operation, rom2dec(input2)));
        }
        throw new Exception("Invalid input");
    }

    private static Integer calculate(int input1, String operation, int input2) throws Exception {
        switch (operation) {
            case ("+"):
                return input1 + input2;
            case ("-"):
                return input1 - input2;
            case ("/"):
                return input1 / input2;
            case ("*"):
                return input1 * input2;
        }
        throw new Exception("Incorrect operation");
    }

    private static Boolean isCorrectRomanInput() {
        String romPattern = "^M{0,3}?(CM|CD|D?C{0,3})?(XC|XL|L?X{0,3})?(IX|IV|V?I{0,3})?$";
        Pattern pattern = Pattern.compile(romPattern);
        Matcher match1 = pattern.matcher(input1),
                match2 = pattern.matcher(input2);
        return match1.find() && match2.find();
    }

    private static Boolean isCorrectIntegerInput() throws Exception {
        String intPattern = "\\d+";
        Pattern pattern = Pattern.compile(intPattern);
        Matcher match1 = pattern.matcher(input1),
                match2 = pattern.matcher(input2);
        if (match1.find() && match2.find()) {
            if (Integer.parseInt(input1) > 10 || Integer.parseInt(input2) > 10) {
                throw new Exception("Incorrect input");
            }
            return true;
        }
        return false;
    }

    private static String dec2rom(int input) {
        String output = "";
        if (input / 100 > 0) {
            int IntM = input / 100;
            while (IntM > 0) {
                output += "C";
                IntM -= 1;
            }
            input %= 100;
        }
        if (input / 10 > 0) {
            int IntM = input / 10;
            if (IntM == 9) { output += "XC"; IntM -= 9; }
            if (IntM == 4) { output += "XL"; IntM -= 4; }
            if (IntM >= 5) { output += "L"; IntM -= 5; }
            while (IntM > 0) { output += "X"; IntM -= 1; }
            input %= 10;
        }
        if (input > 0) {
            int IntM = input;
            if (IntM == 9) { output += "IX"; IntM -= 9; }
            if (IntM == 4) { output += "IV"; IntM -= 4; }
            if (IntM >= 5) { output += "V"; IntM -= 5; }
            while (IntM > 0) { output += "I"; IntM -= 1; }
        }
        return output;
    }

    private static int rom2dec(String input) {
        int result = 0;
        String[] array = input.split("");
        Map<String, Integer> dictionary= new HashMap<>() {
            {
                put("C", 100);
                put("L", 50);
                put("X", 10);
                put("V", 5);
                put("I", 1);
            }
        };
        int iterator = 1;
        while (iterator < input.length()) {
            if (dictionary.get(array[iterator - 1]) < dictionary.get(array[iterator])) {
                result -= dictionary.get(array[iterator - 1]);
            }
            else {
                result += dictionary.get(array[iterator - 1]);
            }
            iterator++;
        }
        result += dictionary.get(array[input.length() - 1]);
        return result;
    }
}
