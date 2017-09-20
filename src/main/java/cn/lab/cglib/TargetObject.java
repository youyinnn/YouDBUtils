package cn.lab.cglib;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/12
 */
public class TargetObject {

    public String say(String str){
        System.out.println("targetMethod call");
        return "Target";
    }

    public void a(){
        System.out.println("method a");
    }

    public void b(){
        System.out.println("method b");
    }

    public Object c(){
        return "method c";
    }

    @Override
    public String toString() {
        return "target object";
    }
}
