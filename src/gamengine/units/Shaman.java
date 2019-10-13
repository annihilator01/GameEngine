package gamengine.units;

import gamengine.Unit;
import gamengine.Range;

public class Shaman extends Unit {
    public Shaman() {
        super("Shaman", 40, 12, 10,
                new Range(7, 12), 10.5);
    }
}
