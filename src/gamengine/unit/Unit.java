package gamengine.unit;

import java.util.ArrayList;

import gamengine.Range;
import gamengine.skills.ActiveSkills;
import gamengine.skills.PassiveSkills;
import org.json.JSONException;
import org.json.JSONObject;


public class Unit {
    private final String type;
    private final int HP;
    private final int attack;
    private final int defense;
    private final Range damage;
    private final double initiative;
    private final ArrayList<PassiveSkills> passiveSkills;
    private final ActiveSkills activeSkill;

    Unit(String type, int HP, int attack, int defense,
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

    public Unit(String unitStringJSON) {
        JSONObject unitJSON = new JSONObject(unitStringJSON);

        this.type = unitJSON.getString("type");
        this.HP = Integer.parseInt(unitJSON.getString("HP"));
        this.attack = Integer.parseInt(unitJSON.getString("attack"));
        this.defense = Integer.parseInt(unitJSON.getString("defense"));
        this.initiative = Double.parseDouble(unitJSON.getString("initiative"));
        this.activeSkill = ActiveSkills.valueOf(unitJSON.getString("activeSkill"));

        this.damage = new Range(Double.parseDouble(
                unitJSON.getJSONObject("damage").getString("min")),
                Double.parseDouble(
                        unitJSON.getJSONObject("damage").getString("max")));

        ArrayList<PassiveSkills> tmpPassiveSkills;
        try {
            tmpPassiveSkills = new ArrayList<>();

            for (Object passiveSkill : unitJSON.getJSONArray("passiveSkills")) {
                tmpPassiveSkills.add(PassiveSkills.valueOf((String)passiveSkill));
            }

            if (((tmpPassiveSkills.contains(PassiveSkills.SHOOTER) ||
                (tmpPassiveSkills.contains(PassiveSkills.ENEMYNOTRESIST))) &&
                 tmpPassiveSkills.contains(PassiveSkills.ENDLESSRESISTANCE))
                 ||
                (tmpPassiveSkills.contains(PassiveSkills.ATTACKALL) &&
              !(tmpPassiveSkills.contains(PassiveSkills.SHOOTER) ||
                tmpPassiveSkills.contains(PassiveSkills.ENEMYNOTRESIST)))) {
                throw new IllegalArgumentException("\nInvalid passive skills combination: " +
                                                   this.type);
            }
        } catch (JSONException e) {
            tmpPassiveSkills = null;
        }

        this.passiveSkills = tmpPassiveSkills;
    }

    public Unit(Unit unit) {
        this.type = unit.type;
        this.HP = unit.HP;
        this.attack = unit.attack;
        this.defense = unit.defense;
        this.damage = new Range(unit.damage.min, unit.damage.max);
        this.initiative = unit.initiative;
        this.passiveSkills = (unit.passiveSkills != null) ? new ArrayList<>(unit.passiveSkills) : null;
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

    public ArrayList<PassiveSkills> getPassiveSkills() {
        return (passiveSkills != null) ? new ArrayList<>(passiveSkills) : new ArrayList<>();
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
                "\tPassive Skills: " + ((passiveSkills != null ) ? passiveSkills.toString() : "[null]") + '\n' +
                "\tActive Skill: " + ((activeSkill != null ) ? activeSkill.toString() : "null") + "\n\n";
    }
}
