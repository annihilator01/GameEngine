package gamengine.units;

import gamengine.Unit;
import gamengine.Range;
import gamengine.skills.PassiveSkills;

import java.util.ArrayList;
import java.util.Arrays;

public class Skeleton extends Unit {
    public Skeleton() {
        super("Skeleton", 5, 1, 2,
                new Range(1, 1), 10,
                new ArrayList<>(
                        Arrays.asList(
                                PassiveSkills.UNDEAD
                        )),
                null);
    }
}
