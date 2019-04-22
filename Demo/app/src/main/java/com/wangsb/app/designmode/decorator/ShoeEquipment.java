package com.wangsb.app.designmode.decorator;

public class ShoeEquipment implements IEquipment {

    @Override
    public int calculateAttack() {
        return 5;
    }

    @Override
    public String description() {
        return "圣战靴子";
    }
}
