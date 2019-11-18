package gamengine;

import java.util.ArrayList;

import gamengine.skills.ActiveSkills;
import gamengine.skills.PassiveSkills;


public class Unit {
    private final String type;
    private final int HP;
    private final int attack;
    private final int defense;
    private final Range damage;
    private final double initiative;
    private final ArrayList<PassiveSkills> passiveSkills;
    private final ActiveSkills activeSkill;

    protected Unit(String type, int HP, int attack, int defense,
                   Range damage, double initiative,
                   ArrayList<PassiveSkills> passiveSkills,
                   ActiveSkills activeSkill) {
        this.type = type;
        this.HP = HP;
        this.attack = attack;
        this.defense = defense;
        this.damage = new Range(damage.min, damage.max);
        this.initiative = initiative;
        this.passiveSkills = (passiveSkills != null) ? new ArrayList<>(passiveSkills) : null;
        this.activeSkill = activeSkill;
    }

    public Unit(Unit unit) {
        this.type = unit.type;
        this.HP = unit.HP;
        this.attack = unit.attack;
        this.defense = unit.defense;
        this.damage = new Range(unit.damage.min, unit.damage.max);
        this.initiative = unit.initiative;
        this.passiveSkills = new ArrayList<>(unit.passiveSkills);
        this.activeSkill =unit.activeSkill;
    }

    public String getType() {
        return type;
    }

    public int getHP() {
        return HP;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public Range getDamage() {
        return damage;
    }

    public double getInitiative() {
        return initiative;
    }

    public ArrayList<PassiveSkills> getPassiveSkils() {
        return new ArrayList<>(passiveSkills);
    }

    public ActiveSkills getActiveSkill() {
        return activeSkill;
    }

    @Override
    public String toString() {
        return "Unit\n" +
                "\tType: " + type + '\n' +
                "\tHP: " + HP + '\n' +
                "\tAttack: " + attack + '\n' +
                "\tDefense: " + defense + '\n' +
                "\tDamage: " + damage.min + "-" + damage.max + '\n' +
                "\tInitiative: " + initiative + '\n' +
                "\tPassive Skills:" + passiveSkills.toString() + '\n' +
                "\tActive Skill:" + activeSkill.toString() + "\n\n";
    }
}
