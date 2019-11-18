package gamengine.units;

import gamengine.Unit;
import gamengine.Range;
import gamengine.skills.PassiveSkills;

import java.util.ArrayList;
import java.util.Arrays;

public class Arbalester extends Unit {
    public Arbalester() {
        super("Arbalester", 10, 4, 4,
                new Range(2, 8), 8,
                new ArrayList<>(
                        Arrays.asList(
                                PassiveSkills.SHOOTER,
                                PassiveSkills.CLEARSHOT
                        )),
                null);
    }
}
