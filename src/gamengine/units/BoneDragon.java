package gamengine.units;

import gamengine.Unit;
import gamengine.Range;
import gamengine.skills.ActiveSkills;
import gamengine.skills.PassiveSkills;

import java.util.ArrayList;
import java.util.Arrays;

public class BoneDragon extends Unit {
    public BoneDragon() {
        super("BoneDragon", 150, 27, 28,
                new Range(15, 30), 11,
                new ArrayList<>(
                        Arrays.asList(
                                PassiveSkills.UNDEAD
                        )),
                ActiveSkills.CURSE);
    }
}
