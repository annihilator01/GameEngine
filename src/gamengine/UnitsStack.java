package gamengine;

public class UnitsStack {
    private final Unit unitClass;
    private final int unitNumber;

    public UnitsStack(Unit unitClass, int unitNumber) {
        this.unitClass = unitClass;
        this.unitNumber = Math.min(unitNumber, 999999);
    }

    public Unit getUnitClass() {
        return unitClass;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public String about() {
        return "Units Stack\n" +
                "\tUnit Class: " + unitClass.getType() + '\n' +
                "\tUnit Number: " + unitNumber + "\n\n";
    }
}