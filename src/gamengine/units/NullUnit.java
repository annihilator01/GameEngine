package gamengine.units;

import gamengine.Unit;
import gamengine.Range;

public class NullUnit extends Unit {
    public NullUnit() {
        super("NullUnit", 0, 0, 0,
                new Range(0, 0), 0);
    }
}
