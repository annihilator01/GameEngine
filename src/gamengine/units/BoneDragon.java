package gamengine.units;

import gamengine.Unit;
import gamengine.Range;

public class BoneDragon extends Unit {
    public BoneDragon() {
        super("BoneDragon", 150, 27, 28,
                new Range(15, 30), 11);
    }
}
