package gamengine;

import java.util.ArrayList;

public class BattleArmy {
    public static final int MAX_STACKS_NUM = 9;
    private ArrayList<BattleUnitsStack> stacks;

    public BattleArmy(Army army) {
        stacks = new ArrayList<>();

        for (UnitsStack unitsStack : army.getArmy()) {
            stacks.add(new BattleUnitsStack(unitsStack));
        }
    }

    public BattleArmy(BattleArmy battleArmy) {
        this.stacks = battleArmy.stacks;
    }

    public BattleUnitsStack getBattleStack(int i) {
        if (0 <= i && i < stacks.size()) {
            return stacks.get(i);
        } else {
            throw new IllegalArgumentException("\nValid stack index range: 0-" +
                    (stacks.size() - 1) + "\nYour invalid index: " + i);
        }
    }

    public ArrayList<BattleUnitsStack> getBattleArmy() {
        return new ArrayList<>(stacks);
    }

    public void addBattleUnitsStack(BattleUnitsStack battleUnitsStack) {
        if (stacks.size() + 1 > MAX_STACKS_NUM) {
            throw new IllegalArgumentException("\nNot enough space in the army!");
        }

        stacks.add(battleUnitsStack);
    }

    public void removeBattleUnitsStack(int i) {
        if (i < 0 || i>= stacks.size()) {
            throw new IllegalArgumentException("\nValid stack index range: 0-" +
                    (stacks.size() - 1) + "\nYour invalid index: " + i);
        }

        stacks.remove(i);
    }

    @Override
    public String toString() {
        String toString;
        toString = "Battle Army\n\t\t\t  Class / Number\n";

        for (int i = 0; i < stacks.size(); ++i) {
            toString += "\tStack #" + (i + 1) + ": " + stacks.get(i).getUnitClass().getType() + " / " +
                    stacks.get(i).getUnitsNumber() + '\n';
        }

        toString += '\n';
        return toString;
    }
}
