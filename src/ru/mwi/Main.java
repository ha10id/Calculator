package ru.mwi;

//import java.util.Arrays;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Main {
    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }
    public static String arabicToRoman(double number) {

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
    public static int convertRoman(String roman_numeral) {
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
        return res;
    }
    // emulate push for string array
    private static String[] pushS(String[] array, String push) {
        String[] longer = new String[array.length + 1];
        System.arraycopy(array, 0, longer, 0, array.length);
        longer[array.length] = push;
        return longer;
    }

    // emulate push for double array
    private static int[] pushD(int[] array, int push) {
        int[] longer = new int[array.length + 1];
        System.arraycopy(array, 0, longer, 0, array.length);
        longer[array.length] = push;
        return longer;
    }

    public static void main(String[] args) {
        int[] nums = {};
        String[] ops = {};
        int ans = 0;
        boolean isArab = false;
        boolean isRoman = false;

        Scanner reader = new Scanner(System.in);
        System.out.print("Введите данные, например: 1+2 или I+V-X. ps: формула может быть бесконечна ;-): ");

        String Name = reader.nextLine();
        // remove spaces
        Name = Name.replaceAll("\\s", "");
        // get arab numbers
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(Name);
        // fill array with arab nums
        while (matcher.find()) {
            int num = Integer.parseInt(matcher.group());
            if (num > 10 || num < 1) {
                System.out.println("Ошибка при вводе арабских чисел");
                System.exit(255);
            }
            nums = pushD(nums, num);
            isArab = true;
        }
        // get roman numbers
        pattern = Pattern.compile("[IVXLCDM]+");
        matcher = pattern.matcher(Name);
        // fill array with roman nums
        while (matcher.find()) {
            int num = convertRoman(matcher.group());
            if (num > 10 || num < 1 || isArab) {
                System.out.println("Ошибка при вводе римских чисел");
                System.exit(255);
            }
            nums = pushD(nums, num);
            isRoman = true;
        }
        if (isArab && isRoman) {
            System.out.println("Ошибка смешивания арабских и римских чисел");
            System.exit(255);
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
        if (isArab) {
            System.out.printf("\nРезультат: %s\n", ans);
        } else {
            String res = arabicToRoman(ans);
            System.out.printf("\nРезультат: %s\n", res);

        }
    }
}
