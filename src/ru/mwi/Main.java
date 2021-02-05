package ru.mwi;

//import java.util.Arrays;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
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
        System.out.print("Введите данные, например: 1+2 или 1+2-45. ps: формула может быть бесконечна ;-): ");

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
        // exit if error in numbers
        if (nums.length < 2) {
            System.out.println("Ошибка при вводе чисел");
            System.exit(255);
        }

//        System.out.println(Arrays.deepToString(nums));

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
