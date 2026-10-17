import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;


import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// Домешнее задание №3 (часть 1)
//написать 4 автотеста, использующих в работе ассерты.
// Методы, которые будут проверяться автотестами, можно написать самостоятельно
// или взять из предыдущих заданий
//возвращает булево значение;
//должен возвращать список;
//должен вернуть неверное значение (ассерт должен падать).
//    При падении ассерты должны дать информацию,
//    что ожидалось и что было получено в результате падения.
public class TestWithAssertions {

    //Автотест №1 проверяет метод возвращающий список и на одной из итераций проверок
    //assert падает
    @ParameterizedTest
    @Tag("HomeWork3")
    @CsvFileSource(resources = "NamesData.csv", numLinesToSkip = 1, delimiter = ';')
    void testSecondRemoveSpecificName(String name, String names, String expNames) {
        //список имен в котором удалять
        List<String> namesList = Arrays.asList(names.split(", "));

        //ожидаемый результат из файла
        List<String> expectedResult = Arrays.asList(expNames.split(", "));

        //фактический результ работы метода removeSpecificName
        List<String> actualResult = TestMain.removeSpecificName(namesList, name);

        //!!!!!!В файле специально данные, чтобы тест падал!!!!!!!!!
        Assertions.assertThat(actualResult)
                .as("Actual List of names " + actualResult
                        + "\n is not equal Expected List of names "
                        + expectedResult + "\n after name " + name + " was removed! from "
                        + names)
                .isEqualTo(expectedResult);
    }

    //Автотест №2 проверяет метод, возвращающий булево значение
    @Test
    @Tag("HomeWork3")
    void testSecondIsPositive() {
        Random random = new Random();
        int n = random.nextInt(-10, 10);
        boolean actualResult = TestMain.isPositive(n);
        boolean expectedResult = (n >= 0);
        Assertions.assertThat(actualResult)
                .as("Actual result " + actualResult +
                        " of method isPositive for number " + n
                        + " gives isn't correct result.\n Correct result is "
                        + expectedResult)
                .isEqualTo(expectedResult);
    }

    //Метод возвращает Allowed если Admin и Prohibited если Guest или другие
    public static String userAccess(AccessRole role) {
        switch (role) {
            case Admin -> {
                return "Allowed";
            }
            case Guest -> {
                return "Prohibited";
            }
        }
        return "Prohibited";
    }

    //Автотест №3 Проверяет работу метода userAccess
    @ParameterizedTest
    @EnumSource(value = AccessRole.class)
    @Tag("HomeWork3")
    void testUserAccess(AccessRole role) {
        String expectedResult = (role.toString().equals("Admin")) ? "Allowed" : "Prohibited";

        String actualResult = userAccess(role);
        Assertions.assertThat(actualResult)
                .as("Actual result " + actualResult +
                        " of method userAccess for role " + role
                        + " gives isn't correct result.\n Correct result is "
                        + expectedResult)
                .isEqualTo(expectedResult);
    }

    //Автотест №4  проверяет работу метода isEven
    //тест намеренно написан так, чтобы он падал!!!!!
    @Test
    @Tag("HomeWork3")
    void testSecondIsEven(){
        Random random = new Random();
        int n = random.nextInt(0, 100);
        boolean actualResult = TestMain.isEven(n);
        boolean expectedResult = (n % 2 != 0);
        Assertions.assertThat(actualResult)
                .as("Actual result " + actualResult +
                        " of method isEven for number " + n
                        + " gives isn't correct result.\n Correct result is "
                        + expectedResult)
                .isEqualTo(expectedResult);
    }

}
