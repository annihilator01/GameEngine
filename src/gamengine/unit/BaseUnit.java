package gamengine.unit;

import gamengine.Range;
import gamengine.skills.ActiveSkills;
import gamengine.skills.PassiveSkills;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;

public enum BaseUnit {
    ANGEL ("Angel", 180, 27, 27,
            new Range(45, 45), 11,
            null,
            ActiveSkills.PUNISHINGHIT, new Image("/gui/assets/angel.png")),

    ARBALESTER ("Arbalester", 10, 4, 4,
            new Range(2, 8), 8,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.SHOOTER,
                            PassiveSkills.CLEARSHOT
                    )),
            null, new Image("/gui/assets/arbalester.png")),

    BONEDRAGON ("Bone Dragon", 150, 27, 28,
            new Range(15, 30), 11,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.UNDEAD
                    )),
            ActiveSkills.CURSE, new Image("/gui/assets/bone_dragon.png")),

    CYCLOPS ("Cyclops", 85, 20, 15,
            new Range(18, 26), 10,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.SHOOTER
                    )),
            null, new Image("/gui/assets/cyclops.png")),

    DEVIL ("Devil", 166, 27, 25,
            new Range(36, 66), 11,
            null,
            ActiveSkills.WEAKENING, new Image("/gui/assets/devil.png")),

    FURYA ("Furya", 16, 5, 3,
            new Range(5, 7), 16,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.ENEMYNOTRESIST
                    )),
            null, new Image("/gui/assets/furya.png")),

    GRIFFIN ("Griffin", 30, 7, 5,
            new Range(5, 10), 15,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.ENDLESSRESISTANCE
                    )),
            null, new Image("/gui/assets/griffin.png")),

    HYDRA ("Hydra", 80, 15, 12,
            new Range(7, 14), 7,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.ATTACKALL,
                            PassiveSkills.ENEMYNOTRESIST
                    )),
            null, new Image("/gui/assets/hydra.png")),

    LICH ("Lich", 50, 15, 15,
            new Range(12, 17), 10,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.SHOOTER,
                            PassiveSkills.UNDEAD
                    )),
            ActiveSkills.RESURRECTION, new Image("/gui/assets/lich.png")),

    SHAMAN ("Shaman", 40, 12, 10,
            new Range(7, 12), 10.5,
            null,
            ActiveSkills.ACCELERATION, new Image("/gui/assets/shaman.png")),

    SKELETON ("Skeleton", 5, 1, 2,
            new Range(1, 1), 10,
            new ArrayList<>(
                    Arrays.asList(
                            PassiveSkills.UNDEAD
                    )),
            null, new Image("/gui/assets/skeleton.png"));

    private final Unit unit;

    BaseUnit(String type, int HP, int attack, int defense,
             Range damage, double initiative,
             ArrayList<PassiveSkills> passiveSkills,
             ActiveSkills activeSkill, Image icon) {
        unit = new Unit(type, HP, attack, defense, damage, initiative,
                        passiveSkills, activeSkill, icon);
    }

    public Unit getUnit() {
        return unit;
    }
}
