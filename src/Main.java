import gamengine.UnitsLoader;
import gamengine.battle.Battle;
import gamengine.battle.BattleArmy;
import gamengine.march.Army;
import gamengine.skills.ActiveSkills;
import gamengine.skills.PassiveSkills;
import gamengine.unit.Unit;
import gamengine.unit.BaseUnits;
import gamengine.march.UnitsStack;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Unit (begin)
        Unit testUnitAngel = BaseUnits.ANGEL.getUnit();
        Unit testUnitDevil = BaseUnits.DEVIL.getUnit();
        Unit testUnitLich = BaseUnits.LICH.getUnit();
        Unit testUnitGriffin = BaseUnits.GRIFFIN.getUnit();
        Unit testUnitFurya = BaseUnits.FURYA.getUnit();
        Unit testUnitArbalester = BaseUnits.ARBALESTER.getUnit();
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

        UnitsLoader ul = new UnitsLoader();
        for (Unit unit : ul.getAllUnits()) {
            System.out.println(unit);
        }
    }
}
