package gamengine;

import gamengine.units.NullUnit;

import java.util.Arrays;

public class Army {
    private final UnitsStack[] stacks;

    public Army(UnitsStack[] stacks) {
        if (stacks.length <= 6) {
            this.stacks = new UnitsStack[6];
            Arrays.fill(this.stacks, 0, 6,
                    new UnitsStack(new NullUnit(), 0));
            System.arraycopy(stacks, 0, this.stacks, 0, stacks.length);
        } else {
            throw new IllegalArgumentException("Illegal number of army stacks: " +
                stacks.length);
        }
    }

    public UnitsStack getStack(int i) {
        if (i < 6) {
        return stacks[i];
        } else {
            throw new IllegalArgumentException("Illegal number of stack: " + i);
        }
    }

    public UnitsStack[] getArmy() {
        return stacks;
    }

    public String about() {
        return "Army\n" +
                "\t\t\t  Class / Number\n" +
                "\tStack #1: " + stacks[0].getUnitClass().getType() + " / "
                + stacks[0].getUnitNumber() + '\n' +

                "\tStack #2: " + stacks[1].getUnitClass().getType() + " / "
                + stacks[1].getUnitNumber() + '\n' +

                "\tStack #3: " + stacks[2].getUnitClass().getType() + " / "
                + stacks[2].getUnitNumber() + '\n' +

                "\tStack #4: " + stacks[3].getUnitClass().getType() + " / "
                + stacks[3].getUnitNumber() + '\n' +

                "\tStack #5: " + stacks[4].getUnitClass().getType() + " / "
                + stacks[4].getUnitNumber() + '\n' +

                "\tStack #6: " + stacks[5].getUnitClass().getType() + " / "
                + stacks[5].getUnitNumber() + "\n\n";
    }
}
