import gamengine.*;
import gamengine.units.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) throws Exception {
        // Unit (begin)
        Unit testUnitAngel = new Angel();
        Unit testUnitDevil = new Devil();
        Unit testUnitLich = new Lich();
        Unit testUnitGriffin = new Griffin();
        Unit testUnitFurya = new Furya();
        Unit testUnitArbalester = new Arbalester();
        // Unit (end)

        // UnitsStack (begin)
        UnitsStack testUnitsStackAngel1 = new UnitsStack(testUnitAngel, 13, 1);
        UnitsStack testUnitsStackDevil = new UnitsStack(testUnitDevil, 666, 1);
        UnitsStack testUnitsStackLich = new UnitsStack(testUnitLich, 1, 1);
        UnitsStack testUnitsStackGriffin = new UnitsStack(testUnitGriffin, 5, 1);

        UnitsStack testUnitsStackAngel2 = new UnitsStack(testUnitAngel, 13, 2);
        UnitsStack testUnitsStackFurya = new UnitsStack(testUnitFurya, 10, 2);
        UnitsStack testUnitsStackArbalester = new UnitsStack(testUnitArbalester, 60, 2);
        // UnitsStack (end)

        // Army (begin)
        ArrayList<UnitsStack> stacks1 = new ArrayList<>();
        stacks1.add(testUnitsStackAngel1);
        stacks1.add(testUnitsStackDevil);
        stacks1.add(testUnitsStackLich);
        stacks1.add(testUnitsStackGriffin);

        ArrayList<UnitsStack> stacks2 = new ArrayList<>();
        stacks2.add(testUnitsStackAngel2);
        stacks2.add(testUnitsStackFurya);
        stacks2.add(testUnitsStackArbalester);

        Army army1 = new Army(stacks1);
        Army army2 = new Army(stacks2);
        // Army (end)

        // Battle Army (begin)
        BattleArmy battleArmy1 = new BattleArmy(army1);
        BattleArmy battleArmy2 = new BattleArmy(army2);
        // Battle Army (end)

        // Battle (begin)
        Battle battle = new Battle(army1, army2);
        System.out.println("#1 " + battle.getBattleArmy1());
        System.out.println("#2 " + battle.getBattleArmy2());

        System.out.println("Start Scale\n" + battle.initiativeScaleToString());
        battle.wait(battle.getCurrentMoveUnitsStack());
        battle.wait(battle.getCurrentMoveUnitsStack());
        battle.useActiveSkill(battle.getCurrentMoveUnitsStack(), battle.getBattleArmy1().getBattleArmy().get(2));
        battle.defend(battle.getCurrentMoveUnitsStack());
        battle.attack(battle.getCurrentMoveUnitsStack(), battle.getBattleArmy1().getBattleArmy().get(0), false);
        battle.attack(battle.getCurrentMoveUnitsStack(), battle.getBattleArmy2().getBattleArmy().get(2), false);
        System.out.println("End Scale\n" + battle.initiativeScaleToString());

        System.out.println("#1 " + battle.getBattleArmy1());
        System.out.println("#2 " + battle.getBattleArmy2());
        // Battle (end)
    }
}
