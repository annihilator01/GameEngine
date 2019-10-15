package gamengine;

public class UnitsStack {
    public final int MAX_UNITS_NUM = 999999;
    private final Unit unitClass;
    private final int unitsNumber;

    public UnitsStack(Unit unitClass, int unitsNumber) {
        this.unitClass = unitClass;

        if (0 <= unitsNumber && unitsNumber <= MAX_UNITS_NUM) {
            this.unitsNumber = unitsNumber;
        } else {
            throw new IllegalArgumentException("\nValid number of units range: 0-" +
                    MAX_UNITS_NUM + "\nYour invalid number: " + unitsNumber);
        }
    }

    public Unit getUnitClass() {
        return unitClass;
    }

    public int getUnitsNumber() {
        return unitsNumber;
    }

    @Override
    public String toString() {
        return "Units Stack\n" +
                "\tUnit Class: " + unitClass.getType() + '\n' +
                "\tUnits Number: " + unitsNumber + "\n\n";
    }
}
