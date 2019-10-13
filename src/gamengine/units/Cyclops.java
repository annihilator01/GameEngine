package gamengine.units;

import gamengine.Unit;
import gamengine.Range;

public class Cyclops extends Unit {
    public Cyclops() {
        super("Cyclops", 85, 20, 15,
                new Range(18, 26), 10);
    }
}
