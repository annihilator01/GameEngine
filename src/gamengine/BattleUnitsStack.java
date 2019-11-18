package gamengine;

public class BattleUnitsStack implements Comparable<BattleUnitsStack> {
    private final Unit unitClass;
    private final int initialUnitsNumber;
    private int unitsNumber;
    private int armyIndex;
    private double initiative;
    private double attackChange;
    private double defenseChange;
    private boolean hasUsedActiveSkill;

    public BattleUnitsStack(UnitsStack unitsStack) {
        this.unitClass = unitsStack.getUnitClass();
        this.initialUnitsNumber = unitsStack.getUnitsNumber();
        this.unitsNumber = unitsStack.getUnitsNumber();
        this.armyIndex = unitsStack.getArmyIndex();
        this.initiative = unitsStack.getUnitClass().getInitiative();

        hasUsedActiveSkill = false;
        attackChange = 0;
        defenseChange = 0;
    }

    public BattleUnitsStack(BattleUnitsStack battleUnitsStack) {
        this.unitClass = new Unit(battleUnitsStack.unitClass);
        this.initialUnitsNumber = battleUnitsStack.unitsNumber;
        this.unitsNumber = battleUnitsStack.unitsNumber;
        this.armyIndex = battleUnitsStack.armyIndex;
        this.initiative = battleUnitsStack.initiative;
        this.attackChange = battleUnitsStack.attackChange;
        this.defenseChange = battleUnitsStack.defenseChange;
        this.hasUsedActiveSkill = battleUnitsStack.hasUsedActiveSkill;
    }

    public Unit getUnitClass() {
        return new Unit(unitClass);
    }

    public int getUnitsNumber() {
        return unitsNumber;
    }

    public int getArmyIndex() {
        return armyIndex;
    }

    public double getInitiative() {
        return initiative;
    }

    public double getAttackChange() {
        return attackChange;
    }

    public double getDefenseChange() {
        return defenseChange;
    }

    public boolean hasUsedActiveSkill() {
        return hasUsedActiveSkill;
    }

    public boolean isDead() {
        return unitsNumber == 0;
    }

    public void negateInitiative() {
        if (initiative > 0){
            initiative *= -1;
        }
    }

    public void setAttackChange(double change) {
        attackChange = change;
    }

    public void setDefenseChange(double change) {
        defenseChange = change;
    }

    public void setUnitsNumber(int unitsNumber) {
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

    @Override
    public int compareTo(BattleUnitsStack other) {
        if (this.getInitiative() > other.getInitiative()) {
            return 1;
        } else if (this.getInitiative() == other.getInitiative()) {
            return 0;
        }
        return -1;
    }
}
