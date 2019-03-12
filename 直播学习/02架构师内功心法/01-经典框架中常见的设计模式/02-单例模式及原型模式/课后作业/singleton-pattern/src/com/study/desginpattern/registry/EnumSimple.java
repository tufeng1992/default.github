package com.study.desginpattern.registry;

/**
 * 通过枚举实现的饿汉式单例模式
 * 通过下面反编译后的代码可以看到，
 * 枚举类保证的单例实际为枚举在初始化时将对象放入一个数组中存储起来，
 * 而后获取的实例也实际为数组中已经实例化好的对象
 */
public enum EnumSimple {

    KEY1(new Pojo1());

    EnumSimple(Object value) {
    }


    /**
     * 定义一个简单的对象来配合测试注册式单例
     */
    private static class Pojo1 {

        private Pojo1(){

        }
    }
}
//枚举反编译代码
//package com.study.desginpattern.registry;
//
//
//public final class EnumSimple extends Enum
//{
//    private static class Pojo1
//    {
//
//        private Pojo1()
//        {
//        }
//
//    }
//
//
//    public static EnumSimple[] values()
//    {
//        return (EnumSimple[])$VALUES.clone();
//    }
//
//    public static EnumSimple valueOf(String name)
//    {
//        return (EnumSimple)Enum.valueOf(com/study/desginpattern/registry/EnumSimple, name);
//    }
//
//    private EnumSimple(String s, int i, Object value)
//    {
//        super(s, i);
//    }
//
//    public static final EnumSimple KEY1;
//    private static final EnumSimple $VALUES[];
//
//    static
//    {
//        KEY1 = new EnumSimple("KEY1", 0, new Pojo1());
//        $VALUES = (new EnumSimple[] {
//                KEY1
//        });
//    }
//}

