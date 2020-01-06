package gamengine.march;

import gamengine.battle.BattleUnitStack;
import gamengine.unit.Unit;

public class UnitStack {
    public static final int MAX_UNITS_NUM = 999999;
    private final Unit unitClass;
    private final int unitsNumber;
    private final int armyIndex;

    public UnitStack(Unit unitClass, int unitsNumber, int armyIndex) {
        this.unitClass = unitClass;

        if (0 <= unitsNumber && unitsNumber <= MAX_UNITS_NUM) {
            this.unitsNumber = unitsNumber;
        } else {
            throw new IllegalArgumentException("\nValid number of units range: 0-" +
                    MAX_UNITS_NUM + "\nYour invalid number: " + unitsNumber);
        }

        if (1 <= armyIndex && armyIndex <= 2) {
            this.armyIndex = armyIndex;
        } else {
            throw new IllegalArgumentException("\nValid index of army range: 1-2" +
                    "\nYour invalid index: " + armyIndex);
        }
    }

    public UnitStack(BattleUnitStack battleUnitsStack) {
        if (!battleUnitsStack.isDead()) {
            this.unitClass = battleUnitsStack.getUnitClass();
            this.unitsNumber = battleUnitsStack.getUnitsNumber();
            this.armyIndex = battleUnitsStack.getArmyIndex();
        }

        throw new IllegalArgumentException("\nInvalid argument battleUnitsStack: stack dead");
    }

    public Unit getUnitClass() {
        return unitClass;
    }

    public int getUnitsNumber() {
        return unitsNumber;
    }

    public int getArmyIndex() {
        return armyIndex;
    }

    @Override
    public String toString() {
        return "BaseUnit Stack\n" +
                "\tUnit Class: " + unitClass.getType() + '\n' +
                "\tBaseUnit Number: " + unitsNumber + '\n' +
                "\tArmy Index: " + armyIndex + "\n\n";
    }
}
