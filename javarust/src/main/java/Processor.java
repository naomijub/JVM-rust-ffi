package javarust;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class Processor {
    public interface CProcessor extends Library {
        CProcessor INSTANCE = (CProcessor) Native.loadLibrary("processor",CProcessor.class);

        int processInt(int value);
        float processFloat(float value);
        int[] processVecInt(int[] value, int size);
    }

    public static void main(String[] args) {
        integer();
        doubles();
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
}
