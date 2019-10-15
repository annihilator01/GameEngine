package gamengine;

public class Army {
    public final int MAX_STACKS_NUM = 6;
    private final UnitsStack[] stacks;

    public Army(UnitsStack[] stacks) {
        if (stacks.length <= MAX_STACKS_NUM) {
            this.stacks = new UnitsStack[stacks.length];
            System.arraycopy(stacks, 0, this.stacks, 0, stacks.length);
        } else {
            throw new IllegalArgumentException("\nValid number of stacks range: 0-" +
                    MAX_STACKS_NUM + "\nYour invalid number: " + stacks.length);
        }
    }

    public UnitsStack getStack(int i) {
        if (0 <= i && i < stacks.length) {
            return stacks[i];
        } else {
            throw new IllegalArgumentException("\nValid stack index range: 0-" +
                    (stacks.length - 1) + "\nYour invalid index: " + i);
        }
    }

    public UnitsStack[] getArmy() {
        return stacks;
    }

    @Override
    public String toString() {
        String toString;
        toString = "Army\n\t\t\t  Class / Number\n";

        for (int i = 0; i < stacks.length; ++i) {
            toString += "\tStack #" + (i + 1) + ": " + stacks[i].getUnitClass().getType() + " / " +
                    stacks[i].getUnitsNumber() + '\n';
        }

        toString += '\n';
        return toString;
    }
}
