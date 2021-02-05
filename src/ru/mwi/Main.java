package ru.mwi;

//import java.util.Arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static double convertRoman(String roman_numeral) {
        Map<Character, Integer> roman_char_dict = new HashMap<Character, Integer>();
        roman_char_dict.put('I', 1);
        roman_char_dict.put('V', 5);
        roman_char_dict.put('X', 10);
        roman_char_dict.put('L', 50);
        roman_char_dict.put('C', 100);
        roman_char_dict.put('D', 500);
        roman_char_dict.put('M', 1000);
        int res = 0;
        for (int i = 0; i < roman_numeral.length(); i += 1) {
            if (i == 0 || roman_char_dict.get(roman_numeral.charAt(i)) <= roman_char_dict.get(roman_numeral.charAt(i - 1)))
                res += roman_char_dict.get(roman_numeral.charAt(i));
            else
                res += roman_char_dict.get(roman_numeral.charAt(i)) - 2 * roman_char_dict.get(roman_numeral.charAt(i - 1));
        }
        double dres = res;
        return dres;
    }
    // emulate push for string array
    private static String[] pushS(String[] array, String push) {
        String[] longer = new String[array.length + 1];
        System.arraycopy(array, 0, longer, 0, array.length);
        longer[array.length] = push;
        return longer;
    }

    // emulate push for double array
    private static double[] pushD(double[] array, double push) {
        double[] longer = new double[array.length + 1];
        System.arraycopy(array, 0, longer, 0, array.length);
        longer[array.length] = push;
        return longer;
    }

    public static void main(String[] args) {
        double[] nums = {};
        String[] ops = {};
        double ans = 0;
        Scanner reader = new Scanner(System.in);
        System.out.print("Введите данные, например: 1+2 или I+V-X. ps: формула может быть бесконечна ;-): ");

        String Name = reader.nextLine();
        // remove spaces
        Name = Name.replaceAll("\\s", "");
        // get numbers
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(Name);
        // fill array nums
        while (matcher.find()) {
            nums = pushD(nums, Double.parseDouble(matcher.group()));
        }
        pattern = Pattern.compile("[IVXLCDM]+");
        matcher = pattern.matcher(Name);
        // fill array nums
        while (matcher.find()) {
            nums = pushD(nums, convertRoman(matcher.group()));
        }
        // exit if error in numbers
        if (nums.length < 2) {
            System.out.println("Ошибка при вводе чисел");
            System.exit(255);
        }

        // get operators
        String[] s3 = Name.split("\\w+");
        for (int a = 0; a < s3.length; a++) {
            if (s3[a] != "" && s3[a].length() == 1) {
                ops = pushS(ops, s3[a]);
            }
        }
        // exit if error in operators
        if (ops.length < 1 || ops.length != (nums.length - 1)) {
            System.out.println("Ошибка! при вводе арифметического оператора");
            System.exit(255);
        }

//        System.out.println(Arrays.deepToString(ops));

        // calculate answer
        for (int j = 0; j < ops.length; j++) {
            if (j == 0) {
                ans = nums[j];
            }
            switch (ops[j]) {
                case "+":
                    ans = ans + nums[j + 1];
                    break;
                case "-":
                    ans = ans - nums[j + 1];
                    break;
                case "*":
                    ans = ans * nums[j + 1];
                    break;
                case "/":
                    ans = ans / nums[j + 1];
                    break;
                default:
                    System.out.printf("Ошибка! Введите правильный оператор");
                    return;
            }
        }
        System.out.printf("\nРезультат: %s\n", ans);
    }
}
