package com.wangsb.app.designmode.decorator;

public class BlueGemDecorator implements IEquipDecorator{

    IEquipment iEquipment;

    public BlueGemDecorator(IEquipment iEquipment){
        this.iEquipment = iEquipment;
    }

    @Override
    public int calculateAttack() {
        return iEquipment.calculateAttack() + 5;
    }

    @Override
    public String description() {
        return iEquipment.description() + "+ 蓝宝石";
    }
}
