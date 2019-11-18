package gamengine.units;

import gamengine.Unit;
import gamengine.Range;
import gamengine.skills.ActiveSkills;

import java.util.ArrayList;
import java.util.Arrays;

public class Angel extends Unit {
    public Angel() {
        super("Angel", 180, 27, 27,
                new Range(45, 45), 11,
                null,
                ActiveSkills.PUNISHINGHIT);
    }
}
