package gamengine.unit;

import gamengine.Range;
import gamengine.skills.ActiveSkills;
import gamengine.skills.PassiveSkills;

import java.util.ArrayList;
import java.util.Arrays;

public enum BaseUnits {
    ANGEL ("Angel", 180, 27, 27,
            new Range(45, 45), 11,
            null,
            ActiveSkills.PUNISHINGHIT),

    ARBALESTER ("Arbalester", 10, 4, 4,
            new Range(2, 8), 8,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.SHOOTER,
                            PassiveSkills.CLEARSHOT
                    )),
            null),

    BONEDRAGON ("BoneDragon", 150, 27, 28,
            new Range(15, 30), 11,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.UNDEAD
                    )),
            ActiveSkills.CURSE),

    CYCLOPS ("Cyclops", 85, 20, 15,
            new Range(18, 26), 10,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.SHOOTER
                    )),
            null),

    DEVIL ("Devil", 166, 27, 25,
            new Range(36, 66), 11,
            null,
            ActiveSkills.WEAKENING),

    FURYA ("Furya", 16, 5, 3,
            new Range(5, 7), 16,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.ENEMYNOTRESIST
                    )),
            null),

    GRIFFIN ("Griffin", 30, 7, 5,
            new Range(5, 10), 15,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.ENDLESSRESISTANCE
                    )),
            null),

    HYDRA ("Hydra", 80, 15, 12,
            new Range(7, 14), 7,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.ATTACKALL,
                            PassiveSkills.ENEMYNOTRESIST
                    )),
            null),

    LICH ("Lich", 50, 15, 15,
            new Range(12, 17), 10,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.SHOOTER,
                            PassiveSkills.UNDEAD
                    )),
            ActiveSkills.RESURRECTION),

    SHAMAN ("Shaman", 40, 12, 10,
            new Range(7, 12), 10.5,
            null,
            ActiveSkills.ACCELERATION),

    SKELETON ("Skeleton", 5, 1, 2,
            new Range(1, 1), 10,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.UNDEAD
                    )),
            null);

    private final Unit unit;

    BaseUnits(String type, int HP, int attack, int defense,
              Range damage, double initiative,
              ArrayList<PassiveSkills> passiveSkills,
              ActiveSkills activeSkill) {
        unit = new Unit(type, HP, attack, defense, damage, initiative,
                        passiveSkills, activeSkill);
    }

    public Unit getUnit() {
        return unit;
    }
}
