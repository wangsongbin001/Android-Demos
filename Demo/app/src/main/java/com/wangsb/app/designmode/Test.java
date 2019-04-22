package com.wangsb.app.designmode;

import com.wangsb.app.designmode.decorator.ArmEquipment;
import com.wangsb.app.designmode.decorator.BlueGemDecorator;
import com.wangsb.app.designmode.decorator.IEquipment;
import com.wangsb.app.designmode.decorator.RedGemDecorator;
import com.wangsb.app.designmode.decorator.ShoeEquipment;
import com.wangsb.app.designmode.decorator.YellowGemDecorator;

public class Test{

    public static void main(String[] args){
        // 一个镶嵌2颗红宝石，1颗蓝宝石的靴子
        System.out.println(" 一个镶嵌2颗红宝石，1颗蓝宝石的靴子");
        IEquipment equip = new RedGemDecorator(new RedGemDecorator(new BlueGemDecorator(new ShoeEquipment())));
        System.out.println("攻击力  : " + equip.calculateAttack());
        System.out.println("描述 :" + equip.description());
        System.out.println("-------");
        // 一个镶嵌1颗红宝石，1颗蓝宝石的武器
        System.out.println(" 一个镶嵌1颗红宝石，1颗蓝宝石,1颗黄宝石的武器");
        equip = new RedGemDecorator(new BlueGemDecorator(new YellowGemDecorator(new ArmEquipment())));
        System.out.println("攻击力  : " + equip.calculateAttack());
        System.out.println("描述 :" + equip.description());
        System.out.println("-------");
    }
}
