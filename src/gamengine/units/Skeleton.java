package gamengine.units;

import gamengine.Unit;
import gamengine.Range;

public class Skeleton extends Unit {
    public Skeleton() {
        super("Skeleton", 5, 1, 2,
                new Range(1, 1), 10);
    }
}
