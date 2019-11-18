package gamengine;

public class UnitsStack {
    public static final int MAX_UNITS_NUM = 999999;
    private final Unit unitClass;
    private final int unitsNumber;
    private final int armyIndex;

    public UnitsStack(Unit unitClass, int unitsNumber, int armyIndex) {
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
        return "Units Stack\n" +
                "\tUnit Class: " + unitClass.getType() + '\n' +
                "\tUnits Number: " + unitsNumber + "\n\n";
    }
}
