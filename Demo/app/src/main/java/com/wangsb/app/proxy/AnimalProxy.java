package com.wangsb.app.proxy;

import android.util.Log;

/**
 * Created by xiaosongshu on 2019/3/24.
 */

public class AnimalProxy implements Fly, Run {

    Animal animal;

    public AnimalProxy(Animal animal){
        this.animal = animal;
    }

    @Override
    public void fly() {
        Log.i(Animal.TAG, "AnimalProxy before fly");
        this.animal.fly();
        Log.i(Animal.TAG, "AnimalProxy behind fly");
    }

    @Override
    public void run() {
        Log.i(Animal.TAG, "AnimalProxy before run");
       this.animal.run();
        Log.i(Animal.TAG, "AnimalProxy behind run");
    }
}
