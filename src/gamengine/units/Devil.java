package gamengine.units;

import gamengine.Unit;
import gamengine.Range;
import gamengine.skills.ActiveSkills;

import java.util.ArrayList;
import java.util.Arrays;

public class Devil extends Unit {
    public Devil() {
        super("Devil", 166, 27, 25,
                new Range(36, 66), 11,
                null,
                ActiveSkills.WEAKENING);
    }
}
