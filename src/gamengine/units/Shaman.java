package gamengine.units;

import gamengine.Unit;
import gamengine.Range;
import gamengine.skills.ActiveSkills;

public class Shaman extends Unit {
    public Shaman() {
        super("Shaman", 40, 12, 10,
                new Range(7, 12), 10.5,
                null,
                ActiveSkills.ACCELERATION);
    }
}
