package com.wangsb.app.designmode.decorator;

public class RingEquipment implements IEquipment {

    @Override
    public int calculateAttack() {
        return 5;
    }

    @Override
    public String description() {
        return "圣战戒指";
    }
}
