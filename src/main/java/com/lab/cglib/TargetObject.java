package com.lab.cglib;

/**
 *
 * @author youyinnn
 *
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

    public String c() {
        return "aaaa";
    }
}
