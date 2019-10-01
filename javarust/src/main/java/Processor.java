package javarust;

import com.sun.jna.Library;
import com.sun.jna.Native;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Processor {
    public interface CProcessor extends Library {
        CProcessor INSTANCE = (CProcessor) Native.loadLibrary("processor",CProcessor.class);

        int processInt(int value);
        float processFloat(float value);
        int vecLen(int[] value);
        int vecSumEven(int[] velues, int size);
    }

    public static void main(String[] args) {
        integer();
        doubles();
        vec();
        evenVec();
    }

    public static int processIntJava(int value) {
        return (int) Math.pow(value, 8) /5 /2 /5;
    }

    public static void integer() {
        long before_rust = System.currentTimeMillis();
        System.out.println("rust: 10 " + CProcessor.INSTANCE.processInt(10));
        long now_rust = System.currentTimeMillis();
        System.out.println("Seconds elapsed: " + (now_rust-before_rust)/1000F + "." );

        long before_java = System.currentTimeMillis();
        System.out.println("java: 10 " + processIntJava(10));
        long now_java = System.currentTimeMillis();
        System.out.println("Seconds elapsed: " + (now_java-before_java)/1000F + ".\n\n" );
    }

    public static float processFloatJava(float value) {
        return value * value * value * value * 5 / 304.5F;
    }

    public static void doubles() {
        long before_rust = System.currentTimeMillis();
        System.out.println("rust: 10 " + CProcessor.INSTANCE.processFloat(10));
        long now_rust = System.currentTimeMillis();
        System.out.println("Seconds elapsed: " + (now_rust-before_rust)/1000F + "." );

        long before_java = System.currentTimeMillis();
        System.out.println("java: 10 " + processFloatJava(10));
        long now_java = System.currentTimeMillis();
        System.out.println("Seconds elapsed: " + (now_java-before_java)/1000F + ".\n\n" );
    }

    public static int processVecLen(int[] values) {
        return values.length;
    }

    public static void vec() {
        int[] values = {1, 2, 3, 4, 5};
        long before_rust = System.currentTimeMillis();
        System.out.println("rust len: " + CProcessor.INSTANCE.vecLen(values));
        long now_rust = System.currentTimeMillis();
        System.out.println("Seconds elapsed: " + (now_rust-before_rust)/1000F + "." );

        long before_java = System.currentTimeMillis();
        System.out.println("java len: " + processVecLen(values));
        long now_java = System.currentTimeMillis();
        System.out.println("Seconds elapsed: " + (now_java-before_java)/1000F + ".\n\n" );

    }

    public static int processEvenVec(int[] values) {
        return Arrays.stream(values)
                .boxed()
                .collect(Collectors.toList())
                .stream()
                .filter(x -> x % 2 == 0)
                .reduce(0, (acc, x) -> acc + x);
    }

    public static void evenVec() {
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        long before_rust = System.currentTimeMillis();
        System.out.println("rust even: " + CProcessor.INSTANCE.vecSumEven(values, values.length));
        long now_rust = System.currentTimeMillis();
        System.out.println("Seconds elapsed: " + (now_rust-before_rust)/1000F + "." );

        long before_java = System.currentTimeMillis();
        System.out.println("java even: " + processEvenVec(values));
        long now_java = System.currentTimeMillis();
        System.out.println("Seconds elapsed: " + (now_java-before_java)/1000F + ".\n\n" );

    }
}
