package gamengine.battle;

import gamengine.unit.Unit;
import gamengine.march.UnitStack;
import javafx.scene.layout.HBox;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.Formatter;

public class BattleUnitStack implements Comparable<BattleUnitStack> {
    private final Unit unitClass;
    private final int initialUnitsNumber;
    private int armyIndex;
    private double initiativeChange;
    private double attackChange;
    private double defenseChange;
    private boolean hasResisted;
    private HBox battleHBox;
    private int HP;
    private int numberActiveSkillUsed;
    public BattleUnitStack(UnitStack unitsStack) {
        this.unitClass = unitsStack.getUnitClass();
        this.initialUnitsNumber = unitsStack.getUnitsNumber();
        this.armyIndex = unitsStack.getArmyIndex();

        hasResisted = false;
        initiativeChange = 0;
        attackChange = 0;
        defenseChange = 0;
        battleHBox = null;
        HP = initialUnitsNumber * unitClass.getHP();
        numberActiveSkillUsed = 0;
    }

    public BattleUnitStack(BattleUnitStack battleUnitsStack) {
        this.unitClass = new Unit(battleUnitsStack.unitClass);
        this.initialUnitsNumber = battleUnitsStack.initialUnitsNumber;
        this.armyIndex = battleUnitsStack.armyIndex;
        this.initiativeChange = battleUnitsStack.initiativeChange;
        this.attackChange = battleUnitsStack.attackChange;
        this.defenseChange = battleUnitsStack.defenseChange;
        this.hasResisted = battleUnitsStack.hasResisted;
        this.battleHBox = battleUnitsStack.battleHBox;
        this.HP = battleUnitsStack.HP;
        this.numberActiveSkillUsed = battleUnitsStack.numberActiveSkillUsed;
    }

    public Unit getUnitClass() {
        return new Unit(unitClass);
    }

    public int getInitialUnitsNumber() {
        return initialUnitsNumber;
    }

    public int getUnitsNumber() {
        return (int) Math.ceil((double) HP / unitClass.getHP());
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

    public int getHP() {
        return HP;
    }

    public int getNumberActiveSkillUsed() {
        return numberActiveSkillUsed;
    }

    public void activeSkillActivated() {
        numberActiveSkillUsed++;
    }

    /* for tableView in BattleSceneController */
    public Double getInitiative() {
        return initiativeChange + unitClass.getInitiative();
    }

    public String getType() {
        return unitClass.getType();
    }

    public String getActiveSkill() {
        return unitClass.getActiveSkill() != null ?
               unitClass.getActiveSkill().getTitle() :
               "None";
    }
    /*----------------------------------------*/

    public boolean hasResisted() {
        return hasResisted;
    }

    public boolean isDead() {
        return HP == 0;
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

    public void setBattleUnitStackHP(int HP) {
        if (0 <= HP && HP <= initialUnitsNumber * unitClass.getHP()) {
            this.HP = HP;
        } else if (HP > initialUnitsNumber * unitClass.getHP()) {
            this.HP = initialUnitsNumber * unitClass.getHP();
        } else {
            throw new IllegalArgumentException("\nHP of battle unit stack can't be negative!" +
                    "\nYour invalid number: " + HP);
        }
    }

    public void resisted() {
        hasResisted = true;
    }

    @Override
    public String toString() {
        return "Battle Unit Stack\n" +
                "\tUnit Class: " + unitClass.getType() + '\n' +
                "\tOverall HP: " + HP + '\n' +
                "\tOne unit HP: " + unitClass.getHP() + '\n' +
                "\tAttack: " + (unitClass.getAttack() + attackChange) + '\n' +
                "\tDamage: " + unitClass.getDamage().getMin() + "-" + unitClass.getDamage().getMax() + '\n' +
                "\tDefense: " + (unitClass.getDefense() + defenseChange) + '\n' +
                "\tInitiative: " + getInitiative() + '\n' +
                "\tPassive Skills: " + ((!unitClass.getPassiveSkills().isEmpty()) ? unitClass.getPassiveSkills().toString() : "None") + '\n' +
                "\tActive Skill: " + ((unitClass.getActiveSkill() != null ) ? unitClass.getActiveSkill().getTitle() : "None");
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
