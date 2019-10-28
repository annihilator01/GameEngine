import gamengine.*;
import gamengine.units.*;

import java.util.ArrayList;

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
        UnitsStack testUnitsStackAngel = new UnitsStack(testUnitAngel, 13);
        UnitsStack testUnitsStackDevil = new UnitsStack(testUnitDevil, 666);
        UnitsStack testUnitsStackLich = new UnitsStack(testUnitLich, 1);
        UnitsStack testUnitsStackGriffin = new UnitsStack(testUnitGriffin, 5);
        UnitsStack testUnitsStackFurya = new UnitsStack(testUnitFurya, 10);
        UnitsStack testUnitsStackArbalester = new UnitsStack(testUnitArbalester, 60);
        // UnitsStack (end)

        // Army (begin)
        ArrayList<UnitsStack> stacks1 = new ArrayList<>();
        stacks1.add(testUnitsStackAngel);
        stacks1.add(testUnitsStackDevil);
        stacks1.add(testUnitsStackLich);
        stacks1.add(testUnitsStackGriffin);

        ArrayList<UnitsStack> stacks2 = new ArrayList<>();
        stacks2.add(testUnitsStackAngel);
        stacks2.add(testUnitsStackFurya);
        stacks2.add(testUnitsStackArbalester);

        Army army1 = new Army(stacks1);
        Army army2 = new Army(stacks2);
        // Army (end)

        // Battle Army (begin)
        BattleArmy battleArmy1 = new BattleArmy(army1);
        BattleArmy battleArmy2 = new BattleArmy(army2);

        System.out.print(battleArmy1);
        System.out.print(battleArmy2);
        // Battle Army (end)

        // Battle (begin)
        Battle battle = new Battle(battleArmy1, battleArmy2);
        battle.startBattle();
        System.out.print(battle.getWinner());
    }
}
