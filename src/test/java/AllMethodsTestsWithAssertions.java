import com.google.common.math.Stats;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import org.junit.jupiter.params.provider.*;

// Д.з.№3
//добавить в автотесты разработанные в задаче 2 темы «Gradle и JUnit»
// информативные ассерты (заменить проверки через if на ассерты).
// При падении ассерты должны дать информацию, что ожидалось и что было получено в результате падения.
// Дополнить задачу запуска автотестов фильтрацией, или по аннотации @Tag, или по пакету.
// Запустить каждый тестовый метод не менее 10 раз.
public class AllMethodsTestsWithAssertions {

    //Тест1
    //Разработать метод, который возвращает true,если число чётное
    //boolean isEven(int n)
    @ParameterizedTest
    @Tag("HomeWork3_2")
    @MethodSource("randomPositiveData")
    void testIsEvenWithRandomData(int randomValue) {
        //ожидаемый результат
        boolean expectedResult = (randomValue % 2 == 0);
        //вывод сгенерированных данных в консоль
        System.out.println("Random value is " + randomValue + " expectedResult is " + expectedResult);
        boolean actualResult = TestMain.isEven(randomValue);

        Assertions.assertThat(actualResult)
                .as("Actual result " + actualResult +
                        " of method isEven for number " + randomValue
                        + " gives isn't correct result.\n Correct result is "
                        + expectedResult)
                .isEqualTo(expectedResult);
    }

        //возвращает массив случайных чисел в диапазоне  [start, end], число элементов массива N
        static int[] randomData(int start, int end, int N){
            Random random = new Random();
            int[] arrayRandomValues = new int[N];
            for (int i= 0; i< N; i++){
                arrayRandomValues[i] = random.nextInt(start,end);
            }
            return arrayRandomValues;
        }
        //массив из 5 положительных чисел в диапазоне от 0 до 100
        static int[] randomPositiveData(){
            return randomData(0,100, 5);
        }
        //массив из 7 целых чисел в диапазоне от -100 до 100
        static int[] randomIntData(){
            return randomData(-100,100,7);
        }

    //Тест 2
    //Разработать метод, который проверяет знак через тернарный оператор: возвращает true, если число
    //больше или равно 0, и false, если меньше
    //boolean isPositive(int n)
    @ParameterizedTest
    @Tag("HomeWork3_2")
    @MethodSource("randomIntData")
    void testIsPositiveWithRandomData(int randomValue) {
        boolean expectedResult = (randomValue >= 0);

        boolean actualResult = TestMain.isPositive(randomValue);

        Assertions.assertThat(actualResult)
                .as("Actual result " + actualResult +
                        " of method IsPositive for number " + randomValue
                        + " gives isn't correct result.\n Correct result is "
                        + expectedResult)
                .isEqualTo(expectedResult);
    }

    //Тест 3
    //Разработать метод, который возвращает Allowed, если age больше 18, иначе — Denied
    // String checkAccess(int age)
    @ParameterizedTest
    @Tag("HomeWork3_2")
    @CsvFileSource(resources = "AccessData.csv", numLinesToSkip = 1, delimiter = ';')
    void testCheckAccessWithRandomData(int randomValue, String expectedResult) {
        //вывод рандомных  данных из файла в консоль
        System.out.println("Random value is " + randomValue + " Random expectedResult is " + expectedResult);

        String actualResult = TestMain.checkAccess(randomValue);
        Assertions.assertThat(actualResult)
                .as("Actual result " + actualResult +
                        " of method CheckAccess for value " + randomValue
                        + " gives isn't correct result.\n Correct result is "
                        + expectedResult)
                .isEqualTo(expectedResult);
    }

        //Тест 4 - запустим 5 раз
        //Разработать метод, который преобразует баллы (0–100) в символ оценки:
        //+ 0–20: E     //+ 21–40: D     //+ 41–60: C     //+ 61–80: B     //+ 81–100: A
        //String getGrade(int score)
        @RepeatedTest(5)
        @Tag("HomeWork3_2")
        void testGetGradeWithRandomData(){
            //сгенерируем тестовые случайные данные внутри тестового метода
            Random random = new Random();
            int randomValue = random.nextInt(0, 100);
            int randomIndex = random.nextInt(2);
            String expectedResult;
            expectedResult = (randomValue >= 0) && (randomValue <= 20) ? "E" :
                    (randomValue >= 21) && (randomValue <= 40) ? "D" :
                            (randomValue >= 41) && (randomValue <= 60) ? "C" :
                                    (randomValue >= 61) && (randomValue <= 80) ? "B" :
                                            (randomValue >= 81) && (randomValue <= 100) ? "A" :
                                                    "Error";
            //вывод сгенерированных данных в консоль
            System.out.println("Random value is "+randomValue+ " Random expectedResult is " + expectedResult);

            String actualResult = TestMain.getGrade(randomValue);
            Assertions.assertThat(actualResult)
                    .as("Actual result " + actualResult +
                            " of method GetGrade for value " + randomValue
                            + " gives isn't correct result.\n Correct result is "
                            + expectedResult)
                    .isEqualTo(expectedResult);
        }

        //Тест 5
        //Разработать метод, который принимает стартовое
        //число (например, 5) и возвращает строку со всеми
        //числами до 1 и словом «Поехали!» в конце
        //(например, «5 4 3 2 1 Поехали!»)
        //String blastOff(int start)
        @Test
        @Tag("HomeWork3_2")
        void testВlastOffWithRandomData(){
            //сгенерируем тестовые случайные данные внутри тестового метода
            Random random = new Random();
            int randomValue = random.nextInt(0, 20);
            String expectedResult = "";
            for (int i = randomValue; i > 0; i--) {
                expectedResult = expectedResult + i + " ";
            }
            if (!expectedResult.equals("")) {
                expectedResult=expectedResult + "Поехали!" ;}

            //вывод сгенерированных данных в консоль
            System.out.println("Random value is "+randomValue+ " Random expectedResult is " + expectedResult);

            // проверка на случайно-сгенерированных данных
            if ((expectedResult).equals(TestMain.blastOff(randomValue)) ){
                System.out.println("TEST PASSED!");
            } else{System.out.println("TEST FAILED!");}

            String actualResult = TestMain.blastOff(randomValue);
            Assertions.assertThat(actualResult)
                    .as("Actual result " + actualResult +
                            " of method blastOff for value " + randomValue
                            + " gives isn't correct result.\n Correct result is "
                            + expectedResult)
                    .isEqualTo(expectedResult);
        }

        //Тест 6
        //Разработать метод, который принимает массив
        //строк и возвращает true, если хотя бы одна строка
        //равна Bug
        //boolean hasBug(String[] messages)
        @RepeatedTest(3)
        @Tag("HomeWork3_2")
        void testHasBugWithRandomData() {
            Map<String [], Boolean> randomMap = createRandomStringArrayMap();
            Boolean actualResult = false;
            Boolean expectedResult = false;
            for (Map.Entry<String[], Boolean> entry: randomMap.entrySet()) {
                String[] randomString = entry.getKey();
                //контройльный вывод рандомного массива строк
                for (int i = 0; i < randomString.length; i++) {
                    System.out.println(randomString[i]);
                }
                actualResult = TestMain.hasBug(randomString);
                expectedResult = entry.getValue();
                System.out.println(expectedResult);

                Assertions.assertThat(actualResult)
                        .as("Actual result " + actualResult +
                                " of method HasBug for value " + randomString
                                + " gives isn't correct result.\n Correct result is "
                                + expectedResult)
                        .isEqualTo(expectedResult);
            }
        }


        //метод возвращает карту из массива строк и есть ли в ней bug
        static Map<String [], Boolean> createRandomStringArrayMap()
        {
            Random random = new Random();
            //вероятные значения bug в массиве
            String[] bugs = {"bug","Bug","BUg","bUg","BUG","buG"};
            Integer randomIndex = random.nextInt(0,bugs.length);

            //длина рандомного массива
            Integer randomArrayLength = random.nextInt(1,10);

            boolean useLetters = true;
            boolean useNumbers = false;

            //Создание массива строк
            String[] ramdomStringArray = new String[randomArrayLength];
            //бедет ли баг?
            Boolean bugIsPresent = random.nextBoolean();
            //позиция bug в массиве
            Integer randomBugIndex = random.nextInt(0,randomArrayLength);
            for (int i=0; i<randomArrayLength; i++){
                if ((i == randomBugIndex)&&(bugIsPresent)){
                    ramdomStringArray[i] = bugs[randomIndex];
                    continue;
                }
                //случайные строки
                Integer randomWordLength = random.nextInt(2,6);
                String generatedString = RandomStringUtils.random(randomWordLength, useLetters, useNumbers);
                ramdomStringArray[i] = generatedString;
            }

            Map<String [], Boolean> randMap = new HashMap<>();
            randMap.put(ramdomStringArray, bugIsPresent);
            return randMap;
        }

    //Тест 7
    //Разработать метод, который возвращает сумму всех целых чисел от 1 до n
    //int sumToN(int n)
    @RepeatedTest(4)
    @Tag("HomeWork3_2")
    void testSumToNWithRandomData() {
        //сгенерируем тестовые случайные данные внутри тестового метода
        Random random = new Random();
        int randomValue = random.nextInt(0, 10);
        int expectedResult = randomValue * (randomValue + 1) / 2;
        //вывод сгенерированных данных в консоль
        System.out.println("Random value is " + randomValue + " Random expectedResult is " + expectedResult);

        int actualResult = TestMain.sumToN(randomValue);
        Assertions.assertThat(actualResult)
                .as("Actual result " + actualResult +
                        " of method SumToN for value " + randomValue
                        + " gives isn't correct result.\n Correct result is "
                        + expectedResult)
                .isEqualTo(expectedResult);

    }

        //Тест 8
        //Разработать метод, который принимает границы диапазона и возвращает строку,
        // состоящую только из чётных чисел внутри этого промежутка
        //String getEvenInRange(int start, int end)
        @ParameterizedTest
        @Tag("HomeWork3_2")
        @CsvFileSource(resources = "RangeData.csv", numLinesToSkip = 1, delimiter = ';')
        void testGetEvenInRange(int start, int end, String expectedResult){
            System.out.println("Start = "+ start+ " Ent = "+end);
            System.out.println("ExpectedResult " + expectedResult);
            String actualResult = TestMain.getEvenInRange(start, end);
            System.out.println("ActualResult: "+ actualResult);

            // проверка на случайно-сгенерированных данных
            Assertions.assertThat(actualResult)
                    .as("Actual result " + actualResult +
                            " of method GetEvenInRange for value start = " + start
                            + " value end= " + end
                            + " gives isn't correct result.\n Correct result is "
                            + expectedResult)
                    .isEqualTo(expectedResult);
        }


        //Тест 9
        //Разработать метод, который находит и возвращает
        //самое большое число в переданном массиве
        // int findM  ax(int[] arr)
        @RepeatedTest(5)
        @Tag("HomeWork3_2")
        void testFindMax(){
            int [] strArr = randomIntData();
            //Вывод рандомных данных
            for (int i = 0; i<strArr.length; i++) {
                System.out.print(" "+ strArr[i]);
            }
            System.out.println();

            int expectedResult=0;
            if (strArr.length !=0) expectedResult = strArr[0];
            for (int i=1;i<strArr.length;i++){
                if (strArr[i]>expectedResult){
                    expectedResult=strArr[i];
                }
            }

            int actualResult = TestMain.findMax(strArr);
            System.out.println("Max: " + actualResult);

            List<Integer> list = new ArrayList<>();
            for (int number : strArr) {
                list.add(number);
            }
            // проверка на случайно-сгенерированных данных
            Assertions.assertThat(actualResult)
                    .as("Actual result " + actualResult +
                            " of method FindMax for value " + list
                            + " gives isn't correct result.\n Correct result is "
                            + expectedResult)
                    .isEqualTo(expectedResult);
        }


        //Тест 10
        //Разработать метод, возвращающий новый массив,
        // в котором элементы исходного массива расположены в обратном порядке
        //String[] reverse(String[] arr)
        @Test
        @Tag("HomeWork3_2")
        void testReverse(){
            //получить массив случайных строк
            String [] arrString = createRandomStringArray();
            List<String> originalArr = Arrays.asList(arrString);
            System.out.println("Исходный массив строк: " + originalArr);

            int len = arrString.length;
            //rev_expected - инвертировать массив
            String [] rev_expected = new String[len];
            for (int i=0; i<len; i++)
            {
                rev_expected[i] = arrString[len-i-1];
            }
            List<String> expectedResult = Arrays.asList(rev_expected);
            //метод инвертирующий строку
            String [] rev_actual = TestMain.reverse(arrString);
            List<String> actualResult = Arrays.asList(rev_actual);

            System.out.println("Перевернутый методом reverse массив строк: "+ actualResult);

            // проверка на случайно-сгенерированных данных
            Assertions.assertThat(actualResult)
                    .as("Actual result " + actualResult +
                            " of method Reverse"
                            + " gives isn't correct result.\n Correct result is "
                            + expectedResult)
                    .isEqualTo(expectedResult);
        }

        //метод случайным образом генерящий массив строк
        static String[]createRandomStringArray(){
            Random random = new Random();

            //длина рандомного массива
            Integer randomArrayLength = random.nextInt(2,5);

            boolean useLetters = true;
            boolean useNumbers = false;

            //Создание массива строк
            String[] ramdomStringArray = new String[randomArrayLength];
            for (int i=0; i<randomArrayLength; i++){
                //случайные строки
                Integer randomWordLength = random.nextInt(3,8);
                String generatedString = RandomStringUtils.random(randomWordLength, useLetters, useNumbers);
                ramdomStringArray[i] = generatedString;
            }
            return ramdomStringArray;
        }

        //Тест 11
        //Разработать метод, который вычисляет и возвращает
        //среднее арифметическое всех чисел в списке
        //double calcAverage(List<Integer> list)
        @Test
        @Tag("HomeWork3_2")
        void testCalcAverage(){
            List<Integer> list = randomList();
            System.out.println("list " + list);
            double expectedResult = Stats.meanOf(list);
            System.out.println("expectedResult " + expectedResult);
            double actualResult = TestMain.calcAverage(list);
            System.out.println("actualResult " + actualResult);


            // проверка на случайно-сгенерированных данных
            Assertions.assertThat(actualResult)
                    .as("Actual result " + actualResult +
                            " of method CalcAverage"
                            + " gives isn't correct result.\n Correct result is "
                            + expectedResult)
                    .isEqualTo(expectedResult);
        }

        //возвращает массив случайных чисел в диапазоне  [0, 1000], число элементов массива N
        static List<Integer> randomList(){
            Random random = new Random();
            int N = random.nextInt(2,10);
            List<Integer> listRandomValues= new ArrayList<>();
            for (int i= 0; i< N; i++){
                listRandomValues.add(random.nextInt(0,1000));
            }
            return listRandomValues;
        }

        //Тест 12
        //Разработать метод, принимающий список и имя, которое нужно исключить.
        // Возвращает новый список, не содержащий указанного имени
        //List<String> removeSpecificName(List<String> list, String nameToRemove)
        @Test
        @Tag("HomeWork3_2")
        void testRemoveSpecificName()
        {
            //список строк
            List<String> list = createRandomStringList();
            System.out.println("List " + list );
            int length = list.size();
            Random random = new Random();
            int index = random.nextInt(0,length-1);
            String nameToRemove =  list.get(index);
            System.out.println("Index: "+ index+ " nameToRemove " + nameToRemove);
            List<String>   expectedResult = new ArrayList<>(list);
            boolean b = true;
            while (b) {
                b = expectedResult.remove(nameToRemove);
            }
            System.out.println("removelistExpected " + expectedResult);
            List<String> actualResult = new ArrayList<>(list);
            actualResult = TestMain.removeSpecificName(list,nameToRemove);
            System.out.println("removelistActual " + actualResult);


            // проверка на случайно-сгенерированных данных
            // проверка на случайно-сгенерированных данных
            Assertions.assertThat(actualResult)
                    .as("Actual result " + actualResult +
                            " of method RemoveSpecificName"
                            + " gives isn't correct result.\n Correct result is "
                            + expectedResult)
                    .isEqualTo(expectedResult);
        }

        //метод случайным образом генерящий список строк
        static List<String>createRandomStringList(){
            Random random = new Random();

            //длина рандомного массива
            Integer randomArrayLength = random.nextInt(1,10);

            boolean useLetters = true;
            boolean useNumbers = false;

            //Создание массива строк
            List<String> list = new ArrayList<>();
            for (int i=0; i<randomArrayLength; i++){
                //случайные строки
                Integer randomWordLength = random.nextInt(3,8);
                String generatedString = RandomStringUtils.random(randomWordLength, useLetters, useNumbers);
                list.add(generatedString);
            }
            return list;
        }

}
