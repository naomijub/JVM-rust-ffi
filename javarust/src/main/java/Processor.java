package javarust;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;



import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Processor {
    public interface RustProcessor extends Library {
        class VALUE extends Structure {
            public int a;
            public int b;
            public int c;

            @Override
            protected List getFieldOrder() {
                return Arrays.asList(new String[]{"a", "b", "c"});
            }
        }

        RustProcessor INSTANCE = (RustProcessor) Native.loadLibrary("processor", RustProcessor.class);

        int processInt(int value);
        float processFloat(float value);
        int vecLen(int[] value);
        int vecSumEven(int[] values, int size);
        float vecMean(VALUE[] values, int size);
    }

    public static void main(String[] args) {
        integer();
        doubles();
        vec();
        evenVec();
        meanVec();
    }

    public static int processIntJava(int value) {
        return (int) Math.pow(value, 8) /5 /2 /5;
    }

    public static void integer() {
        long before_rust = System.currentTimeMillis();
        System.out.println("rust: 10 " + RustProcessor.INSTANCE.processInt(10));
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
        System.out.println("rust: 10 " + RustProcessor.INSTANCE.processFloat(10));
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
        System.out.println("rust len: " + RustProcessor.INSTANCE.vecLen(values));
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
        System.out.println("rust even: " + RustProcessor.INSTANCE.vecSumEven(values, values.length));
        long now_rust = System.currentTimeMillis();
        System.out.println("Seconds elapsed: " + (now_rust-before_rust)/1000F + "." );

        long before_java = System.currentTimeMillis();
        System.out.println("java even: " + processEvenVec(values));
        long now_java = System.currentTimeMillis();
        System.out.println("Seconds elapsed: " + (now_java-before_java)/1000F + ".\n\n" );

    }

    public static float processMeanVec(RustProcessor.VALUE[] values) {
        return Arrays.stream(values)
                .collect(Collectors.toList())
                .stream()
                .map(x -> (x.a + x.b + x.c) / 3)
                .reduce(0, (acc, x) -> acc + x) / values.length;
    }

    public static void meanVec() {
        RustProcessor.VALUE value = new RustProcessor.VALUE();
        RustProcessor.VALUE[] values = (RustProcessor.VALUE[]) value.toArray(15);
        values[0].a = 1; values[0].b = 2; values[0].c = 3;
        values[1].a = 6; values[1].b = 5; values[1].c = 4;
        values[2].a = 7; values[2].b = 10; values[2].c = 14;
        values[3].a = 8; values[3].b = 15; values[3].c = 24;
        values[4].a = 99; values[4].b = 55; values[4].c = 34;
        values[5].a = 11; values[5].b = 22; values[5].c = 53;
        values[6].a = 16; values[6].b = 25; values[6].c = 54;
        values[7].a = 17; values[7].b = 210; values[7].c = 514;
        values[8].a = 18; values[8].b = 215; values[8].c = 254;
        values[9].a = 199; values[9].b = 255; values[9].c = 534;
        values[10].a = 21; values[10].b = 42; values[10].c = 73;
        values[11].a = 36; values[11].b = 45; values[11].c = 74;
        values[12].a = 37; values[12].b = 410; values[12].c = 714;
        values[13].a = 38; values[13].b = 415; values[13].c = 724;
        values[14].a = 399; values[14].b = 455; values[14].c = 734;


        long before_rust = System.currentTimeMillis();
        System.out.println("rust mean: " + RustProcessor.INSTANCE.vecMean(values, values.length));
        long now_rust = System.currentTimeMillis();
        System.out.println("Seconds mean: " + (now_rust-before_rust)/1000F + "." );

        long before_java = System.currentTimeMillis();
        System.out.println("java even: " + processMeanVec(values));
        long now_java = System.currentTimeMillis();
        System.out.println("Seconds elapsed: " + (now_java-before_java)/1000F + ".\n\n" );
    }
}
