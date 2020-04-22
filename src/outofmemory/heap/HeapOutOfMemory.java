package outofmemory.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * JAVA堆溢出
 */
public class HeapOutOfMemory {
    static class OOMObject{}

    public static void main(String[] args) {
        List<OOMObject> list=new ArrayList<>();

        while (true)
            list.add(new OOMObject());
    }
}
