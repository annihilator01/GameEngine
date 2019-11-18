package gamengine.units;

import gamengine.Unit;
import gamengine.Range;
import gamengine.skills.ActiveSkills;
import gamengine.skills.PassiveSkills;

import java.util.ArrayList;
import java.util.Arrays;

public class Lich extends Unit {
    public Lich() {
        super("Lich", 50, 15, 15,
                new Range(12, 17), 10,
                new ArrayList<>(
                        Arrays.asList(
                                PassiveSkills.SHOOTER,
                                PassiveSkills.UNDEAD
                        )),
                ActiveSkills.RESURRECTION);
    }
}
