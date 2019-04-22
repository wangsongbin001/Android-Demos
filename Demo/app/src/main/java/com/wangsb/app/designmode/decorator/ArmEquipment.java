package com.wangsb.app.designmode.decorator;

public class ArmEquipment implements IEquipment {

    @Override
    public int calculateAttack() {
        return 20;
    }

    @Override
    public String description() {
        return "屠龙刀";
    }
}
