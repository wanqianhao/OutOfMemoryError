package outofmemory.stack;

/**
 * 虚拟机栈和本地方法栈溢出
 * 1）如果线程请求的栈深度大于虚拟机所允许的最大深度，将抛出StackOverflowError异常。
 * 2）如果虚拟机的栈内存允许动态扩展，当扩展栈容量无法申请到足够的内存时，将抛出 OutOfMemoryError异常。
 *  HOTSPOT虚拟机对于栈是不会自动扩展的 所以我们只需要实验第一种情况即可
 *
 *  使用-Xss将栈的容量变小  -Xss128k
 */
public class JVMStackAndLocatStackOut {

    //第一种情况  我们将 栈空间减小
    private int stackLength=1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }
    /**
     * 第一种情况的保存信息
     * stack length 1617
     * Exception in thread "main" java.lang.StackOverflowError
     */

    //第二种我们不操作栈空间大小  但是我们知道 虚拟机栈主要存放方法的局部变量，是用了一张局部变量表来存储的  所以我们增大方法的局部变量，让局部变量表超过上限
    /**
     * 第二种情况的报错
     * stack length 7051
     * Exception in thread "main" java.lang.StackOverflowError
     * 	at outofmemory.stack.JVMStackAndLocatStackOut.test(JVMStackAndLocatStackOut.java:52)
     */
    private  int stackLen = 0;
    //递归定义N多变量 占用局部变量表
    public  void test() {

        long unused1, unused2, unused3, unused4, unused5,
                unused6, unused7, unused8, unused9, unused10,
                unused11, unused12, unused13, unused14, unused15,
                unused16, unused17, unused18, unused19, unused20,
                unused21, unused22, unused23, unused24, unused25,
                unused26, unused27, unused28, unused29, unused30,
                unused31, unused32, unused33, unused34, unused35,
                unused36, unused37, unused38, unused39, unused40,
                unused41, unused42, unused43, unused44, unused45,
                unused46, unused47, unused48, unused49, unused50,
                unused51, unused52, unused53, unused54, unused55,
                unused56, unused57, unused58, unused59, unused60,
                unused61, unused62, unused63, unused64, unused65,
                unused66, unused67, unused68, unused69, unused70,
                unused71, unused72, unused73, unused74, unused75,
                unused76, unused77, unused78, unused79, unused80,
                unused81, unused82, unused83, unused84, unused85,
                unused86, unused87, unused88, unused89, unused90,
                unused91, unused92, unused93, unused94, unused95,
                unused96, unused97, unused98, unused99, unused100;
        stackLen ++;
        test();
        unused1 = unused2 = unused3 = unused4 = unused5 =
                unused6 = unused7 = unused8 = unused9 = unused10 =
                        unused11 = unused12 = unused13 = unused14 = unused15 =
                                unused16 = unused17 = unused18 = unused19 = unused20 =
                                        unused21 = unused22 = unused23 = unused24 = unused25 =unused26 = unused27 = unused28 = unused29 = unused30 =
                                                unused31 = unused32 = unused33 = unused34 = unused35 =
                                                        unused36 = unused37 = unused38 = unused39 = unused40 =
                                                                unused41 = unused42 = unused43 = unused44 = unused45 =
                                                                        unused46 = unused47 = unused48 = unused49 = unused50 =
                                                                                unused51 = unused52 = unused53 = unused54 = unused55 =
                                                                                        unused56 = unused57 = unused58 = unused59 = unused60 =
                                                                                                unused61 = unused62 = unused63 = unused64 = unused65 =
                                                                                                        unused66 = unused67 = unused68 = unused69 = unused70 =
                                                                                                                unused71 = unused72 = unused73 = unused74 = unused75 =
                                                                                                                        unused76 = unused77 = unused78 = unused79 = unused80 =
                                                                                                                                unused81 = unused82 = unused83 = unused84 = unused85 =
                                                                                                                                        unused86 = unused87 = unused88 = unused89 = unused90 =
                                                                                                                                                unused91 = unused92 = unused93 = unused94 = unused95 =
                                                                                                                                                        unused96 = unused97 = unused98 = unused99 = unused100 = 0;
    }

    public static void main(String[] args) {
        JVMStackAndLocatStackOut jvm=new JVMStackAndLocatStackOut();
        try{
            //不断的递归增大 stackLength的大小
            jvm.test();
        }catch (Throwable e){
            //我们看看当递归到了多少 会造成栈溢出
            System.out.println("stack length"+jvm.stackLen);
            throw e;
        }
    }

}
