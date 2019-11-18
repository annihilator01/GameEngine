package gamengine.units;

import gamengine.Unit;
import gamengine.Range;
import gamengine.skills.PassiveSkills;

import java.util.ArrayList;
import java.util.Arrays;

public class Hydra extends Unit {
    public Hydra() {
        super("Hydra", 80, 15, 12,
                new Range(7, 14), 7,
                new ArrayList<>(
                        Arrays.asList(
                                PassiveSkills.ATTACKALL,
                                PassiveSkills.ENEMYNORESIST
                        )),
                null);
    }
}
