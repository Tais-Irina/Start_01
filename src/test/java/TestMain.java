import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

///
public class TestMain {

    //Разработать метод, который возвращает true,если число чётное
    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    //Разработать метод, который проверяет знак через тернарный оператор: возвращает true, если число
    //больше или равно 0, и false, если меньше
    public static boolean isPositive(int n) {
        return (n >= 0) ? true : false;
    }

    //Разработать метод, который возвращает Allowed, если age больше 18, иначе — Denied
    public static String checkAccess(int age) {
        return (age > 18) ? "Allowed" : "Denied";
    }

    //Разработать метод, который преобразует баллы (0–100) в символ оценки:
    //+ 0–20: E     //+ 21–40: D     //+ 41–60: C     //+ 61–80: B     //+ 81–100: A
    public static String getGrade(int score) {
        String grade;
        grade = (score >= 0) && (score <= 20) ? "E" :
                (score >= 21) && (score <= 40) ? "D" :
                        (score >= 41) && (score <= 60) ? "C" :
                                (score >= 61) && (score <= 80) ? "B" :
                                        (score >= 81) && (score <= 100) ? "A" :
                                                "Error";
        return grade;
    }

    //Разработать метод, который принимает стартовое
    //число (например, 5) и возвращает строку со всеми
    //числами до 1 и словом «Поехали!» в конце
    //(например, «5 4 3 2 1 Поехали!»)
    public static String blastOff(int start) {
        String str = "";
        for (int i = start; i > 0; i--) {
            str = str + i + " ";
        }
        return !(str == "") ? str + "Поехали!" : "";
    }

    //Разработать метод, который принимает массив
    //строк и возвращает true, если хотя бы одна строка
    //равна Bug
    public static boolean hasBug(String[] messages) {
        boolean res = false;
        for (int i = 0; i < messages.length; i++) {
            if (!messages[i].equalsIgnoreCase("bug")) {
                continue;
            } else {
                return true;
            }
        }
        return res;
    }

    //Разработать метод, который возвращает сумму всех целых чисел от 1 до n
    public static int sumToN(int n) {
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            sum = sum + i;
        }
        return sum;
    }

    //Разработать метод, который принимает границы диапазона и возвращает строку,
    // состоящую только из чётных чисел внутри этого промежутка
    public static String getEvenInRange(int start, int end

    ) {
        String str = "";
        //проверить, если start>end - выйти и вернуть пустую строку
        if (start > end) {
            return str;
        }
        //проверить, если start = end - проверить четноть, если четное то добавить и выйти, если не четное выйти
        if (start == end) {
            if (start % 2 == 0) {
                str = str + start;
            }
            return str;
        } else {
            //если интервал задан верно, проверит каждый элемент на четность,
            int element = start;
            while (element <= end) {
                if (element % 2 == 0) { //если элемент четный, добавить в строку
                    str = str + element;
                    element = element + 2;
                    //если следующий четный элемент входит в диапазон, то
                    // добавить перед ним пробел
                    if ((element <= end)) {
                        str = str + " ";
                    }
                }else {//если элемент не четный,
                    element++;
                }
            }
            return str;
        }
    }


    //Разработать метод, который находит и возвращает
    //самое большое число в переданном массиве
    public static int findMax(int[] arr)
    {
        int max=0;
        if (arr.length !=0) max = arr[0];
        for (int i=1;i<arr.length;i++){
            if (arr[i]>max){
                max=arr[i];
            }
        }
        return max;
    }
    //Разработать метод, возвращающий новый массив,
    // в котором элементы исходного массива расположены в обратном порядке
    public static String[] reverse(String[] arr)
    {
        int len = arr.length;
        String [] rev = new String[len];
        for (int i=0; i<len; i++)
        {
            rev[i] = arr[len-i-1];
        }
        return rev;
    }


    //Разработать метод, который вычисляет и возвращает
    //среднее арифметическое всех чисел в списке
    public static double calcAverage(List<Integer> list)
    {
        double aver =0;
        for (int l : list){
            aver = aver + l;
        }
        aver = aver/ (list.toArray().length);
        return aver;
    }



    //Разработать метод, принимающий список и имя, которое нужно исключить.
    // Возвращает новый список, не содержащий указанного имени
    public static List<String> removeSpecificName(List<String> list, String nameToRemove)
    {
        List<String>   removelist = new ArrayList<>(list);
        boolean b = true;
        while (b) {
            b = removelist.remove(nameToRemove);
        }
        return removelist;
    }

    @Test
    void test() {
        List<String> list = List.of("желтый","красный","синий","зеленый","синий");
        List<String> rl = new ArrayList<>( removeSpecificName(list, "синий"));
        System.out.println("Result of method removeSpecificName(list) is " + rl.get(0) +" "+ rl.get(1)+" " + rl.get(2) );
        System.out.println("getEvenInRange(int start, int end " + getEvenInRange(1,7));
    }
}

