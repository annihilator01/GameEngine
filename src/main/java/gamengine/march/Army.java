package gamengine.march;

import java.util.ArrayList;

public class Army {
    public static final int MAX_STACKS_NUM = 6;
    private ArrayList<UnitStack> stacks;
    private final String playerName;
    private final int armyIndex;

    public Army(ArrayList<UnitStack> stacks, String playerName, int armyIndex) {
        if (stacks.size() <= MAX_STACKS_NUM) {
            this.stacks = new ArrayList<>(stacks);
            this.playerName = playerName;
            this.armyIndex = armyIndex;
        } else {
            throw new IllegalArgumentException("\nValid number of stacks range: 0-" +
                    MAX_STACKS_NUM + "\nYour invalid number: " + stacks.size());
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getArmyIndex() {
        return armyIndex;
    }

    public UnitStack getStack(int i) {
        if (0 <= i && i < stacks.size()) {
            return stacks.get(i);
        } else {
            throw new IllegalArgumentException("\nValid stack index range: 0-" +
                    (stacks.size() - 1) + "\nYour invalid index: " + i);
        }
    }

    public ArrayList<UnitStack> getArmy() {
        return new ArrayList<>(stacks);
    }

    public void addUnitsStack(UnitStack unitsStack) {
        if (stacks.size() + 1 > MAX_STACKS_NUM) {
            throw new IllegalArgumentException("\nNot enough space in the army!");
        }

        stacks.add(unitsStack);
    }

    public void removeUnitsStack(int i) {
        if (i < 0 || i>= stacks.size()) {
            throw new IllegalArgumentException("\nValid stack index range: 0-" +
                    (stacks.size() - 1) + "\nYour invalid index: " + i);
        }

        stacks.remove(i);
    }

    @Override
    public String toString() {
        String toString;
        toString = "Army\n\t\t\t  Class / Number\n";

        for (int i = 0; i < stacks.size(); ++i) {
            toString += "\tStack #" + (i + 1) + ": " + stacks.get(i).getUnitClass().getType() + " / " +
                    stacks.get(i).getUnitsNumber() + '\n';
        }

        toString += '\n';
        return toString;
    }
}
