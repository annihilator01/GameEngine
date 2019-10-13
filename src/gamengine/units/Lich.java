package gamengine.units;

import gamengine.Unit;
import gamengine.Range;

public class Lich extends Unit {
    public Lich() {
        super("Lich", 50, 15, 15,
                new Range(12, 17), 10);
    }
}
