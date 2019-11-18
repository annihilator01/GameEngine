package gamengine.units;

import gamengine.Unit;
import gamengine.Range;
import gamengine.skills.PassiveSkills;

import java.util.ArrayList;
import java.util.Arrays;

public class Furya extends Unit {
    public Furya() {
        super("Furya", 16, 5, 3,
                new Range(5, 7), 16,
                new ArrayList<>(
                        Arrays.asList(
                                PassiveSkills.ENEMYNORESIST
                        )),
                null);
    }
}
