package gamengine.units;

import gamengine.Unit;
import gamengine.Range;

public class Hydra extends Unit {
    public Hydra() {
        super("Hydra", 80, 15, 12,
                new Range(7, 14), 7);
    }
}
