package gamengine.unit;

import java.io.File;
import java.util.ArrayList;

import gamengine.Range;
import gamengine.UnitLoader;
import gamengine.skills.ActiveSkills;
import gamengine.skills.PassiveSkills;
import javafx.scene.image.Image;
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
    private final Image icon;

    Unit(String type, int HP, int attack, int defense,
                   Range damage, double initiative,
                   ArrayList<PassiveSkills> passiveSkills,
                   ActiveSkills activeSkill, Image icon) {
        this.type = type;
        this.HP = HP;
        this.attack = attack;
        this.defense = defense;
        this.damage = new Range(damage.min, damage.max);
        this.initiative = initiative;

        this.passiveSkills = (passiveSkills != null) ? new ArrayList<PassiveSkills>(passiveSkills){
            @Override
            public String toString() {
                StringBuilder allPassiveSkills = new StringBuilder();
                for (int i = 0; i < passiveSkills.size(); i++) {
                    allPassiveSkills.append(passiveSkills.get(i).getPassiveSkillName());
                    allPassiveSkills.append((i != passiveSkills.size() - 1) ? ", " : "");
                }
                return allPassiveSkills.toString();
            }
        } : null;

        this.activeSkill = activeSkill;
        this.icon = icon;
    }

    public Unit(String unitStringJSON) {
        JSONObject unitJSON = new JSONObject(unitStringJSON);

        this.type = unitJSON.getString("type");
        this.HP = Integer.parseInt(unitJSON.getString("HP"));
        this.attack = Integer.parseInt(unitJSON.getString("attack"));
        this.defense = Integer.parseInt(unitJSON.getString("defense"));
        this.initiative = Double.parseDouble(unitJSON.getString("initiative"));

        // active skill
        ActiveSkills tmpActiveSkill;
        try {
            tmpActiveSkill = ActiveSkills.valueOf(unitJSON.getString("activeSkill"));
        } catch (Exception e) {
            tmpActiveSkill = null;
        }
        this.activeSkill = tmpActiveSkill;

        // damage
        this.damage = new Range(Double.parseDouble(
                unitJSON.getJSONObject("damage").getString("min")),
                Double.parseDouble(
                        unitJSON.getJSONObject("damage").getString("max")));

        // passive skills
        ArrayList<PassiveSkills> tmpPassiveSkills;
        try {
            tmpPassiveSkills = new ArrayList<>();

            for (Object passiveSkill : unitJSON.getJSONArray("passiveSkills")) {
                tmpPassiveSkills.add(PassiveSkills.valueOf((String) passiveSkill));
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

        ArrayList<PassiveSkills> finalTmpPassiveSkills = tmpPassiveSkills;
        this.passiveSkills = (tmpPassiveSkills != null) ? new ArrayList<PassiveSkills>(finalTmpPassiveSkills) {
            @Override
            public String toString() {
                StringBuilder allPassiveSkills = new StringBuilder();
                for (int i = 0; i < finalTmpPassiveSkills.size(); i++) {
                    allPassiveSkills.append(finalTmpPassiveSkills.get(i).getPassiveSkillName());
                    allPassiveSkills.append((i != finalTmpPassiveSkills.size() - 1) ? ", " : "");
                }
                return allPassiveSkills.toString();
            }
        } : null;

        // icon
        String iconName  = unitJSON.getString("icon");
        String pathToIconFolder = System.getProperty("user.dir") + "/mods";
        File iconFile = new File(pathToIconFolder +  "/" + iconName);

        this.icon = (!iconName.equals("null") && iconFile.exists()) ?
                     new Image("file:///" + iconFile.getPath()) :
                     new Image("/gui/assets/default.png");
    }

    public Unit(Unit unit) {
        this.type = unit.type;
        this.HP = unit.HP;
        this.attack = unit.attack;
        this.defense = unit.defense;
        this.damage = new Range(unit.damage.min, unit.damage.max);
        this.initiative = unit.initiative;

        this.passiveSkills = (unit.passiveSkills != null) ? new ArrayList<PassiveSkills>(unit.passiveSkills){
            @Override
            public String toString() {
                StringBuilder allPassiveSkills = new StringBuilder();
                for (int i = 0; i < unit.passiveSkills.size(); i++) {
                    allPassiveSkills.append(unit.passiveSkills.get(i).getPassiveSkillName());
                    allPassiveSkills.append((i != unit.passiveSkills.size() - 1) ? ", " : "");
                }
                return allPassiveSkills.toString();
            }
        } : null;

        this.activeSkill = unit.activeSkill;
        this.icon = unit.icon;
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

    public Image getIcon() {
        return icon;
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
                "\tPassive Skills: " + ((passiveSkills != null ) ? passiveSkills.toString() : "None") + '\n' +
                "\tActive Skill: " + ((activeSkill != null ) ? activeSkill.getActiveSkillName() : "None") + "\n\n";
    }
}
