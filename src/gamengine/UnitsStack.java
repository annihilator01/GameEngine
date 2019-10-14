package gamengine;

public class UnitsStack {
    private final Unit unitClass;
    private final int unitNumber;

    public UnitsStack(Unit unitClass, int unitNumber) {
        this.unitClass = unitClass;

        if (0 <= unitNumber && unitNumber <= 999999) {
            this.unitNumber = unitNumber;
        } else {
            throw new IllegalArgumentException("Illegal number of units: " + unitNumber);
        }
    }

    public Unit getUnitClass() {
        return unitClass;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    @Override
    public String toString() {
        return "Units Stack\n" +
                "\tUnit Class: " + unitClass.getType() + '\n' +
                "\tUnit Number: " + unitNumber + "\n\n";
    }
}