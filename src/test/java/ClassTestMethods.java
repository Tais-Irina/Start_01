import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Требования к тестовому классу:
// Класс запускает три метода из задания к теме «Базовая Java»:
public class ClassTestMethods {

    //boolean isEven(int n) — запустить метод один раз со случайным числом от 1 до 100;
    //Случайная генерация данных для тестов (Random)
    @Test
    @Tag("Smoke")
    void testIsEvenOnce(){
        Random random = new Random();
        int randomValue = random.nextInt(0, 100);
        boolean actualResult = TestMain.isEven(randomValue);
        String str=  (actualResult) ? "четное":"нечетное";
        System.out.println("Random value " +randomValue +"  "+ str);
    }

    //String checkAccess(int age) — запустить метод 20 раз со случайными числами от 0 до 99;
    @RepeatedTest(20)
    @Tag("Smoke")
    void testCheckAccess(){
        Random random = new Random();
        int randomValue = random.nextInt(0, 99);
        String str = TestMain.checkAccess(randomValue);
        System.out.println("Result of method checkAccess() with age = "+ randomValue + " "+ str);
    }

    //String getGrade(int score) — запустить метод в параметризованных тестах
    // с массивом случайных чисел от 0 до 100.
    // массив случайных чисел генериться в отдельном методе
    @ParameterizedTest
    @MethodSource("getTestArrayData")
    @Tag("Smoke")
    void testGetGrade(int value){
        String str =  TestMain.getGrade(value);
        System.out.println("Result of method getGrade() with grade = " +value+ " " + str);
    }

    //метод возвращающий массив случайных чисел
    static List<Integer> getTestArrayData(){
        Random random = new Random();
        int randomLengthList = random.nextInt(2,7);
        List<Integer> list = new ArrayList<>();
        for (int i = 0 ; i < randomLengthList; i++ ){
            int randomValue = random.nextInt(0, 100);
            list.add(randomValue);
        }
        return list;
    }

    @BeforeAll
    static void init() {
        System.out.println("Начало");
    }

    @BeforeEach
    void setup(){
        System.out.println("========================Test method start");
    }

    @AfterEach
    void cleanup(){
        System.out.println("Test method end========================");
    }

    @AfterAll
    static void close(){
        System.out.println("Конец");
    }
}
