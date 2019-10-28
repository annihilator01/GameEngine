package gamengine;

public class BattleUnitsStack {
    private Unit unitClass;
    private final int initialUnitsNumber;
    private int unitsNumber;
    private boolean isDead;

    public BattleUnitsStack(UnitsStack unitsStack) {
        this.unitClass = unitsStack.getUnitClass();
        this.initialUnitsNumber = unitsStack.getUnitsNumber();
        this.unitsNumber = unitsStack.getUnitsNumber();
        this.isDead = false;
    }

    public Unit getUnitClass() {
        return new Unit(unitClass);
    }

    public int getUnitsNumber() {
        return unitsNumber;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setUnitsNumber(int unitsNumber) {
        isDead = unitsNumber == 0;

        if (0 <= unitsNumber && unitsNumber <= initialUnitsNumber) {
            this.unitsNumber = unitsNumber;
        } else if (unitsNumber > initialUnitsNumber) {
            this.unitsNumber = initialUnitsNumber;
        } else {
            throw new IllegalArgumentException("\nNumber of units can't be negative!" +
                    "\nYour invalid number: " + unitsNumber);
        }
    }

    @Override
    public String toString() {
        return "Battle Units Stack\n" +
                "\tUnit Class: " + unitClass.getType() + '\n' +
                "\tUnits Number: " + unitsNumber + "\n\n";
    }
}
