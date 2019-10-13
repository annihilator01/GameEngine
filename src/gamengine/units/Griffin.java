package gamengine.units;

import gamengine.Unit;
import gamengine.Range;

public class Griffin extends Unit {
    public Griffin() {
        super("Griffin", 30, 7, 5,
                new Range(5, 10), 15);
    }
}
