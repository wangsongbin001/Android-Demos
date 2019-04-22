package com.wangsb.app.designmode.decorator;

import com.wangsb.app.designmode.decorator.IEquipDecorator;
import com.wangsb.app.designmode.decorator.IEquipment;

public class YellowGemDecorator implements IEquipDecorator {

    IEquipment iEquipment;

    public YellowGemDecorator(IEquipment iEquipment){
        this.iEquipment = iEquipment;
    }

    @Override
    public int calculateAttack() {
        return iEquipment.calculateAttack() + 10;
    }

    @Override
    public String description() {
        return iEquipment.description() + " + 黄宝石";
    }
}
