import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;


public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int testNumber = sc.nextInt();
        sc.close();

        switch (testNumber) {
            case 0:
                System.out.println("Ok! Let's start!");
                break;
            case 1:
                GenericPackage genericPackage1 = new GenericPackage("111", 2.5, "Laptop");
                GenericPackage genericPackage2 = new GenericPackage("113", 30.25, "Monitor");

                genericPackage1.setCourierName("FanCourier");

                genericPackage1.printObjectFields();
                System.out.println(genericPackage1.getCourierName() + " - " + genericPackage1.getPackageName());

                genericPackage1.setCourierName("Sameday");

                System.out.println(genericPackage2.getCourierName());
                System.out.println(genericPackage1.getCourierName());

                try {
                    Arrays.asList(GenericPackage.class.getDeclaredFields()).stream()
                            .filter(f -> f.getName().equalsIgnoreCase("uniqueID"))
                            .map(f -> Modifier.isFinal(f.getModifiers())).forEach(System.out::println);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                GenericPackage genericPackage12 = new GenericPackage("111", 2.5, "Laptop");
                genericPackage12.setCourierName("DHL");
                genericPackage12.printObjectFields();
                genericPackage12.addItem(2.5);
                genericPackage12.setCourierName("FanCourier");
                genericPackage12.printObjectFields();
                break;

            case 3:
                GenericPackage genericPackage13 = new GenericPackage("111", 2.5, "Laptop");
                genericPackage13.setCourierName("DHL");
                genericPackage13.addItem(10.75);
                System.out.println(genericPackage13.computeDetails());
                genericPackage13.printObjectFields();
                break;
            case 4:
                Arrays.asList(
                                new GenericPackage("1234512347", 2.0, "Monitor"),
                                new GenericPackage("10000303000", 3.7, "Monitor"),
                                new GenericPackage("1181000000", 3.7, "Monitor"),
                                new GenericPackage("118100*000", 3.7, "Monitor"),
                                new GenericPackage("1234512345", 3.7, "Monitor"),
                                new GenericPackage("1234512347", 3.7, "Monitor")).stream()
                        .map(a -> a.computeDetails() + " ->\t"+ a.checkID()).forEach(System.out::println);
                break;
            default:
                break;

        }
    }
}