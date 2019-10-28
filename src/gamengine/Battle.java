package gamengine;

import java.util.ArrayList;

public class Battle {
    private BattleArmy battleArmy1;
    private BattleArmy battleArmy2;


    public Battle(BattleArmy battleArmy1, BattleArmy battleArmy2) {
        this.battleArmy1 = battleArmy1;
        this.battleArmy2 = battleArmy2;
    }

    public void startBattle() {
        while (!isEnded()) {
            BattleUnitsStack battleUnitsStack = getCurrentMoveUnitsStack();
            ArrayList<Integer> moveActions = getCurrentMoveActions();
            Integer action = chooseAction(moveActions);
            makeMove(battleUnitsStack, action);
        }
    }

    private BattleUnitsStack getCurrentMoveUnitsStack() {
        return null;
    }

    private ArrayList<Integer> getCurrentMoveActions() {
        return null;
    }

    private Integer chooseAction(ArrayList<Integer> moveActions) {
        return null;
    }

    private void makeMove(BattleUnitsStack battleUnitsStack, Integer action) {

    }

    private boolean isArmyDead(BattleArmy battleArmy) {
        for (BattleUnitsStack battleUnitsStack : battleArmy.getBattleArmy()) {
            if (!battleUnitsStack.isDead()) {
                return false;
            }
        }

        return true;
    }

    private boolean isEnded() {
        return isArmyDead(battleArmy1) || isArmyDead(battleArmy2);
    }

    public BattleArmy getWinner() throws Exception {
        if (!isEnded()) {
            throw new Exception("No winner yet!");
        }

        if (isArmyDead(battleArmy1)) {
            return battleArmy2;
        }

        if (isArmyDead(battleArmy2)) {
            return battleArmy1;
        }

        return null;
    }
}
