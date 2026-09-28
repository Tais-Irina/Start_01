import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ClassTestMethods {

    @Test
    public void testMethods(){
        System.out.println("Result of method isEven(101) is " + TestMain.isEven(101));
        System.out.println("Result of method isEven(204) is " + TestMain.isEven(204));

        System.out.println("Result of method checkAccess(18) is " + TestMain.checkAccess(18));
        System.out.println("Result of method checkAccess(21) is " + TestMain.checkAccess(21));
        System.out.println("Result of method checkAccess(5) is " + TestMain.checkAccess(5));

        System.out.println("Result of method isPositive(-3) is " + TestMain.isPositive(-3));
        System.out.println("Result of method isPositive(0) is " + TestMain.isPositive(0));
        System.out.println("Result of method isPositive(6) is " + TestMain.isPositive(6));

        System.out.println("Result of method getGrade(100) is " + TestMain.getGrade(100));
        System.out.println("Result of method getGrade(0) is " + TestMain.getGrade(0));
        System.out.println("Result of method getGrade(101) is " + TestMain.getGrade(101));

        System.out.println("Result of method blastOff(2) is "+ TestMain.blastOff(2));

        System.out.println("Result getEvenInRange(2 , 13) is: [" + TestMain.getEvenInRange(2, 13)+"]");

        String[] messages = {"flower","gBUG","bUg"};
        System.out.println("Result hasBug(messages) is: "+ TestMain.hasBug(messages));

        System.out.println("Result sumToN(4) is: "+ TestMain.sumToN(4));

        int[] arr = {1,5,3,8,21,4,3,15,3,2,8,0,5,34};
        System.out.println("Result findMax is: [" + TestMain.findMax(arr)+"]");

        String[] arrg = {"желтый","зеленый","синий","красный"};
        String[] rev_arr = new String[arrg.length];
        System.out.println("Result reverse is: ");
        rev_arr = TestMain.reverse(arrg);
        for ( int i =0; i < rev_arr.length; i++) {
            System.out.println(rev_arr[i]);
        }

        List<Integer> list = List.of(6,3,6,10);
        double av = TestMain.calcAverage(list);
        System.out.println("Result of method calcAverage(list) is " + av );

        List<String> colors = List.of("желтый","красный","синий","зеленый","синий");
        List<String> rl = new ArrayList<>( TestMain.removeSpecificName(colors, "синий"));
        System.out.println("Result of method removeSpecificName(colors, синий) is " + rl.get(0) +" "+ rl.get(1)+" " + rl.get(2) );
    }

}
