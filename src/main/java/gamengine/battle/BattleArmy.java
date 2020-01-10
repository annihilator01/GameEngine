package gamengine.battle;

import gamengine.march.Army;
import gamengine.march.UnitStack;

import java.util.ArrayList;

public class BattleArmy {
    public static final int MAX_STACKS_NUM = 9;
    private ArrayList<BattleUnitStack> stacks;
    private final String playerName;
    private final int battleArmyIndex;
    private boolean hasGivenUp;
    private int waitAndDefenseActionsNumberInARow;

    public BattleArmy(Army army) {
        stacks = new ArrayList<>();

        for (UnitStack unitsStack : army.getArmy()) {
            stacks.add(new BattleUnitStack(unitsStack));
        }

        playerName = army.getPlayerName();
        battleArmyIndex = army.getArmyIndex();
        hasGivenUp = false;
        waitAndDefenseActionsNumberInARow = 0;
    }

    public BattleArmy(BattleArmy battleArmy) {
        this.stacks = battleArmy.stacks;
        this.playerName = battleArmy.getPlayerName();
        this.battleArmyIndex = battleArmy.getBattleArmyIndex();
        this.hasGivenUp = battleArmy.hasGivenUp;
        this.waitAndDefenseActionsNumberInARow = battleArmy.waitAndDefenseActionsNumberInARow;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getBattleArmyIndex() {
        return battleArmyIndex;
    }

    public int getWaitAndDefenseActionsNumberInARow() {
        return waitAndDefenseActionsNumberInARow;
    }
    public void waitAndDefenseActivated() {
        waitAndDefenseActionsNumberInARow++;
    }

    public void resetWaitAndDefenseActionsNumberInARow() {
        waitAndDefenseActionsNumberInARow = 0;
    }

    public BattleUnitStack getBattleStack(int i) {
        if (0 <= i && i < stacks.size()) {
            return stacks.get(i);
        } else {
            throw new IllegalArgumentException("\nValid stack index range: 0-" +
                    (stacks.size() - 1) + "\nYour invalid index: " + i);
        }
    }

    public ArrayList<BattleUnitStack> getBattleArmy() {
        return stacks;
    }

    public boolean hasGivenUp() {
        return hasGivenUp;
    }

    public void giveUp() {
        hasGivenUp = true;
    }

    public void addBattleUnitsStack(BattleUnitStack battleUnitsStack) {
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
