package jnr;
import jnr.ffi.LibraryLoader;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;

import java.util.Arrays;
import java.util.stream.Collectors;

public class main {
    public static class VALUE extends Struct {
        public final int32_t a = new int32_t();
        public final int32_t b = new int32_t();
        public final int32_t c = new int32_t();

        public VALUE(Runtime runtime) {
            super(runtime);
        }
    }
    public interface LibC {
        int puts(String s, int size);
        float vecMean(VALUE[] values, int size);

    }

    public static void main(String[] args) {

        LibC libc = LibraryLoader.create(LibC.class).load("hellow");
        String word = "Hello, World";
        VALUE[] values = createValuesVec();

        libc.puts(word, word.length());

        long before_rust = System.currentTimeMillis();
        System.out.println(libc.vecMean(values, values.length));
        long now_rust = System.currentTimeMillis();
        System.out.println("Seconds mean: " + (now_rust-before_rust)/1000F + "." );

        long before_java = System.currentTimeMillis();
        System.out.println("java even: " + processMeanVec(values));
        long now_java = System.currentTimeMillis();
        System.out.println("Seconds elapsed: " + (now_java-before_java)/1000F + ".\n\n" );
    }

    public static  float processMeanVec(VALUE[] values) {
        return Arrays.stream(values)
                .collect(Collectors.toList())
                .stream()
                .map(x -> (x.a.intValue() + x.b.intValue() + x.c.intValue()) / 3)
                .reduce(0, (acc, x) -> acc + x) / values.length;
    }

    public static VALUE[] createValuesVec() {
        Runtime runtime = Runtime.getSystemRuntime();
        VALUE[] values = Struct.arrayOf(runtime, VALUE.class, 5);
        values[0].a.set(1); values[0].b.set(2); values[0].c.set(3);
        values[1].a.set(11); values[1].b.set(21); values[1].c.set(32);
        values[2].a.set(13); values[2].b.set(31); values[2].c.set(33);
        values[3].a.set(16); values[3].b.set(22); values[3].c.set(2);
        values[4].a.set(21); values[4].b.set(26); values[4].c.set(42);

        return values;
    }
}
