package outofmemory.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * JAVA堆溢出  通常情况下就是对象创建过多超过堆内存大小
 * 添加如下虚拟机参数 -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError 将堆内存变为20MB并且不可扩展
 * -XX:+HeapDumpOnOutOfMemoryError可以让虚拟机在出现内存溢出时DUMP出当前的内存堆转储快照以便事后分析
 */
public class HeapOutOfMemory {
    static class OOMObject{}

    public static void main(String[] args) {
        List<OOMObject> list=new ArrayList<>();

        while (true)
            list.add(new OOMObject());
    }
    /**
     * 执行之后报的错误
     * java.lang.OutOfMemoryError: Java heap space
     * Dumping heap to java_pid8000.hprof ...
     * Heap dump file created [28176747 bytes in 0.123 secs]
     * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
     * 虚拟机在错误信息中为我们提示了 这是堆溢出错误  Java heap space
     * 出现了堆错误信息 我们可以使用工具分析 DUMP出的内存快照进行分析
     * 一般分为两种情况  内存泄漏：通过调用链找到引起内存泄露的对象是通过怎样的引用路径，与那些GC Roots相关联才导致垃圾收集器无法回收他们
     * 内存溢出：如果不是内存泄漏那么就代表当前内存中所有对象都是需要存活的，不是可以抛弃的，那么可以尝试增大堆内存
     */
}
