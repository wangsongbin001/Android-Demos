package com.wangsb.app.designmode.decorator;

public class RedGemDecorator implements IEquipDecorator {

    IEquipment iEquipment;

    public RedGemDecorator(IEquipment iEquipment){
        this.iEquipment = iEquipment;
    }

    @Override
    public int calculateAttack() {
        return iEquipment.calculateAttack() + 15;
    }

    @Override
    public String description() {
        return iEquipment.description() + " + 红宝石";
    }
}
