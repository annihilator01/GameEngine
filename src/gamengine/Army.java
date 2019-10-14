package gamengine;

public class Army {
    private final UnitsStack[] stacks;

    public Army(UnitsStack[] stacks) {
        if (stacks.length <= 6) {
            this.stacks = new UnitsStack[stacks.length];
            System.arraycopy(stacks, 0, this.stacks, 0, stacks.length);
        } else {
            throw new IllegalArgumentException("Illegal number of army stacks: " +
                stacks.length);
        }
    }

    public UnitsStack getStack(int i) {
        if (i < stacks.length) {
        return stacks[i];
        } else {
            throw new IllegalArgumentException("Illegal number of stack: " + i);
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
                    stacks[i].getUnitNumber() + '\n';
        }

        toString += '\n';
        return toString;
    }
}
