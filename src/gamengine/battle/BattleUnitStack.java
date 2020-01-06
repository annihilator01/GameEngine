package gamengine.battle;

import gamengine.unit.Unit;
import gamengine.march.UnitStack;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class BattleUnitStack implements Comparable<BattleUnitStack> {
    private final Unit unitClass;
    private final int initialUnitsNumber;
    private int unitsNumber;
    private int armyIndex;
    private double initiativeChange;
    private double attackChange;
    private double defenseChange;
    private boolean hasResisted;
    private boolean hasUsedActiveSkill;
    private HBox battleHBox;

    public BattleUnitStack(UnitStack unitsStack) {
        this.unitClass = unitsStack.getUnitClass();
        this.initialUnitsNumber = unitsStack.getUnitsNumber();
        this.unitsNumber = unitsStack.getUnitsNumber();
        this.armyIndex = unitsStack.getArmyIndex();

        hasResisted = false;
        hasUsedActiveSkill = false;
        initiativeChange = 0;
        attackChange = 0;
        defenseChange = 0;
        battleHBox = null;
    }

    public BattleUnitStack(BattleUnitStack battleUnitsStack) {
        this.unitClass = new Unit(battleUnitsStack.unitClass);
        this.initialUnitsNumber = battleUnitsStack.unitsNumber;
        this.unitsNumber = battleUnitsStack.unitsNumber;
        this.armyIndex = battleUnitsStack.armyIndex;
        this.initiativeChange = battleUnitsStack.initiativeChange;
        this.attackChange = battleUnitsStack.attackChange;
        this.defenseChange = battleUnitsStack.defenseChange;
        this.hasResisted = battleUnitsStack.hasResisted;
        this.hasUsedActiveSkill = battleUnitsStack.hasUsedActiveSkill;
        this.battleHBox = battleUnitsStack.battleHBox;
    }

    public Unit getUnitClass() {
        return new Unit(unitClass);
    }

    public int getInitialUnitsNumber() {
        return initialUnitsNumber;
    }

    public int getUnitsNumber() {
        return unitsNumber;
    }

    public int getArmyIndex() {
        return armyIndex;
    }

    public double getInitiativeChange() {
        return initiativeChange;
    }

    public double getAttackChange() {
        return attackChange;
    }

    public double getDefenseChange() {
        return defenseChange;
    }

    public HBox getBattleHBox() {
        return battleHBox;
    }

    /* for tableView in BattleSceneController */
    public double getInitiative() {
        return initiativeChange + unitClass.getInitiative();
    }

    public String getType() {
        return unitClass.getType();
    }

    public String getActiveSkill() {
        return unitClass.getActiveSkill() != null ?
               unitClass.getActiveSkill().getActiveSkillName() :
               "None";
    }
    /*----------------------------------------*/

    public boolean hasResisted() {
        return hasResisted;
    }

    public boolean hasUsedActiveSkill() {
        return hasUsedActiveSkill;
    }

    public boolean isDead() {
        return unitsNumber == 0;
    }

    public void setInitiativeChange(double change) {
        initiativeChange = change;
    }

    public void setAttackChange(double change) {
        attackChange = change;
    }

    public void setDefenseChange(double change) {
        defenseChange = change;
    }

    public void setBattleHBox(HBox newHbox) {
        battleHBox = newHbox;
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

    public void resisted() {
        hasResisted = true;
    }

    public void usedActiveSkill() {
        hasUsedActiveSkill = true;
    }

    @Override
    public String toString() {
        return "Battle BaseUnit Stack\n" +
                "\tUnit Class: " + unitClass.getType() + '\n' +
                "\tUnits Number: " + unitsNumber + '\n' +
                "\tArmy Index: " + armyIndex + "\n\n";
    }

    @Override
    public int compareTo(BattleUnitStack other) {
        double thisInitiative = this.unitClass.getInitiative() + this.initiativeChange;
        double otherInitiative = other.unitClass.getInitiative() + other.initiativeChange;
        if (thisInitiative > otherInitiative) {
            return -1;
        } else if (thisInitiative == otherInitiative) {
            return 0;
        }
        return 1;
    }
}
