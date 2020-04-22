package outofmemory.locat;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 本机直接内存溢出
 * 通过-Xmx20M -XX:MaxDirectMemorySize=10M 设置内存大小
 *
 * 看我们首先设置直接内存为10M   然后我们通过反射获取Unsafe实列注意通过反射获取此实例，我们并没有去new这个对象，也就是它还没有分配内存
 * 然后我们通过手动的方式为其分配内存， 一直分配内存当操作系统无法分配内存时就会提示异常
 */
public class DirectMemoryOOM {

    private static final int _1MB=1024*1024;


    public static void main(String[] args) throws IllegalAccessException {
        Field unsageField= Unsafe.class.getDeclaredFields()[0];
        unsageField.setAccessible(true);

        Unsafe unsafe=(Unsafe)unsageField.get(null);

        while (true){
            unsafe.allocateMemory(_1MB);
        }
    }

    /**
     * 由直接内存导致的内存溢出，一个明显的特征是在 Heap DUMP文件中不会看见有什么明显的异常情况，如果发现内存溢出之后产生的DUMP文件很小，而程序中又直接或件借使用了NIO就需要考虑直接内存溢出了
     * Exception in thread "main" java.lang.OutOfMemoryError
     * at sun.misc.Unsafe.allocateMemory(Native Method)
     * at org.fenixsoft.oom.DMOOM.main(DMOOM.java:20
     */
}
