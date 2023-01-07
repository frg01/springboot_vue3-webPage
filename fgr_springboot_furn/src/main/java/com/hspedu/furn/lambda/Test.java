package com.hspedu.furn.lambda;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class Test {
    public static void main(String[] args) {
        //传统的方式实现接口FgrFunction 可以使用匿名内部类
//        FgrFunction<Desk,String> hf = new FgrFunction<Desk, String>() {
//            @Override
//            public String apply(Desk desk) {
//                return "HELLO,desk";
//            }
//        };
//        String apply = hf.apply(new Desk());
//        System.out.println("apply" + apply);
        FgrFunction<Desk,String> hf2 = Desk::getName;
        String apply = hf2.apply(new Desk());
        System.out.println("apply2" + apply);
    }
}

//定义函数式接口:有且只有一个抽象方法的接口
//可以使用@FunctionInterface 来标识一个函数式接口
//FgrFunction是函数式接口（自定义接口）
@FunctionalInterface
interface  FgrFunction<T,R>{
    R apply(T t);//抽象方法：根据类型T的参数，获取类型R的结果

    //对于函数式接口，可以有多个默认实现方法
//    default public void ok(){
//        System.out.println("ok");
//    }
}

class Desk{
    private String name = "fgr";

    public String getName(){
        return name;
    }
}
