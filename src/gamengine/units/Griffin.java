package gamengine.units;

import gamengine.Unit;
import gamengine.Range;
import gamengine.skills.PassiveSkills;

import java.util.ArrayList;
import java.util.Arrays;

public class Griffin extends Unit {
    public Griffin() {
        super("Griffin", 30, 7, 5,
                new Range(5, 10), 15,
                new ArrayList<>(
                        Arrays.asList(
                                PassiveSkills.ENDLESSRESISTANCE
                        )),
                null);
    }
}
