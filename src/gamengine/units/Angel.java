package gamengine.units;

import gamengine.Unit;
import gamengine.Range;

public class Angel extends Unit {
    public Angel() {
        super("Angel", 180, 27, 27,
                new Range(45, 45), 11);
    }
}
