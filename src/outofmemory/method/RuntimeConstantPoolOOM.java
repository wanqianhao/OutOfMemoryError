package outofmemory.method;

import java.util.HashSet;
import java.util.Set;

/**
 * 方法区和运行时常量池溢出
 * 运行时常量池属于方法区中的：方法区存放已被虚拟机加载的类型信息，常量，静态变量等
 *
 * 对于方法区的异常测试  可以通过创建大量的类去填充方法区  因为我们知道方法区会保存类名和访问修饰符等类型信息
 *
 * -XX:PermSize=6M -XX:MaxPermSize=6M 通过这两个参数设置上限 并允许自动扩展
 *
 */
public class RuntimeConstantPoolOOM {

    private void JDK6vsJDK7(){
        //看一下下列这段代码 如果他们是在JDK6上运行为两个false 如果他们在JDK7及以上 结果为 一个true 一个false
        //我们首先明确一点  两个字符串进行==比较   是比较的对象内存地址
        //然后在明确一点  intern()方法  为首次遇到原则   当执行此方法中会前往常量池中 查找具有此字符串的对象如果有就返回 没有就创建
        //首先讲JDK6  对于JDK6而言  他的字符串常量池都存放在永久代中，所以执行intern方法就回去永久代区域中找此字符串常量池 而StringBuilder存放在java堆区域中因为他是个对象嘛，
        //所以JDK6中 字符串常量池跟对象都不在一个区域  那么==比较肯定为false
        //对于JDK7而言 他的字符串常量池存放在堆中  所以执行intern方法 会前往堆内存中查找 查找出来的对象是同一个 所以==比较为true  但是为什么java这个字符串对象不行。
        //是因为这个字符串已经存放在字符串常量池中了，是在加载sun.misc.Version类时填充到字符串常量池中的，所以执行intern会返回之前保存的字符串对象，这样比较就是两个对象了
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);

    }

    /**
     * String::intern 方法是一个本地方法，它的作用是如果字符串常量池中已经包含一个等于此String对象的字符串，则返回代表池中此字符串对象的引用也就是相同的字符串使用同一个对象
     * 但是如果没有的话 则会将此Stirng对象包含的字符串添加到常量池中
     */
    public static void main(String[] args) {
        Set<String> set=new HashSet<>();

        short i=0;

        //我们通过 String::intern() 一直向常量池中存放  使得常量池超过上限
        while (true){
            set.add(String.valueOf(i++).intern());
        }

    }
    /**
     * Exception in thread "main" java.lang.OutOfMemoryError: PermGen space
     * at java.lang.String.intern(Native Method)
     * at org.fenixsoft.oom.RuntimeConstantPoolOOM.main(RuntimeConstantPoolOOM.java: 18)
     */
}
