package gamengine.units;

import gamengine.Unit;
import gamengine.Range;

public class Arbalester extends Unit {
    public Arbalester() {
        super("Arbalester", 10, 4, 4,
                new Range(2, 8), 8);
    }
}
