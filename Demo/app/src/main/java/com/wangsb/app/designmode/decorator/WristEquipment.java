package com.wangsb.app.designmode.decorator;

public class WristEquipment implements IEquipment {

    @Override
    public int calculateAttack() {
        return 5;
    }

    @Override
    public String description() {
        return "圣战护腕";
    }
}
