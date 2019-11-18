package gamengine.units;

import gamengine.Unit;
import gamengine.Range;
import gamengine.skills.PassiveSkills;

import java.util.ArrayList;
import java.util.Arrays;

public class Cyclops extends Unit {
    public Cyclops() {
        super("Cyclops", 85, 20, 15,
                new Range(18, 26), 10,
                new ArrayList<>(
                        Arrays.asList(
                                PassiveSkills.SHOOTER
                        )),
                null);
    }
}
