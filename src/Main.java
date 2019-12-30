import gamengine.*;
import gamengine.march.*;
import gamengine.battle.*;
import gamengine.unit.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        UnitsLoader unitsLoader = new UnitsLoader();

        // units (begin)
        Unit angel = unitsLoader.createUnit("Angel");
        Unit devil = unitsLoader.createUnit("Devil");
        Unit flash = unitsLoader.createUnit("Flash");
        Unit rock = unitsLoader.createUnit("Rock");
        Unit furya = unitsLoader.createUnit("Furya");
        Unit arbalester = unitsLoader.createUnit("Arbalester");
        // units (end)

        // units stack (begin)
        UnitsStack angelStack = new UnitsStack(angel, 13, 1);
        UnitsStack devilStack = new UnitsStack(devil, 666, 1);
        UnitsStack flashStack = new UnitsStack(flash, 70, 1);
        UnitsStack arbalesterStack = new UnitsStack(arbalester, 60, 1);

        UnitsStack angelStack2 = new UnitsStack(angel, 11, 2);
        UnitsStack furyaStack = new UnitsStack(furya, 10, 2);
        UnitsStack rockStack = new UnitsStack(rock, 5, 2);
        // units stack (end)

        // army (begin)
        ArrayList<UnitsStack> stacks1 = new ArrayList<>();
        stacks1.add(angelStack);
        stacks1.add(devilStack);
        stacks1.add(flashStack);
        stacks1.add(arbalesterStack);

        ArrayList<UnitsStack> stacks2 = new ArrayList<>();
        stacks2.add(angelStack2);
        stacks2.add(furyaStack);
        stacks2.add(rockStack);

        Army army1 = new Army(stacks1);
        Army army2 = new Army(stacks2);
        // army (end)

        // Battle (begin)
        Battle battle = new Battle(army1, army2, "gyg", "gyg");
        System.out.println("#1 " + battle.getBattleArmy1());
        System.out.println("#2 " + battle.getBattleArmy2());

        System.out.println("Start Scale\n" + battle.initiativeScaleToString());
        battle.attack(battle.getCurrentMoveUnitsStack(), battle.getBattleArmy2().getBattleArmy().get(2), false);
        battle.wait(battle.getCurrentMoveUnitsStack());
        battle.defend(battle.getCurrentMoveUnitsStack());
        battle.useActiveSkill(battle.getCurrentMoveUnitsStack(), battle.getBattleArmy2().getBattleArmy().get(2));
//        battle.wait(battle.getCurrentMoveUnitsStack());
//        battle.wait(battle.getCurrentMoveUnitsStack());
//        battle.useActiveSkill(battle.getCurrentMoveUnitsStack(), battle.getBattleArmy1().getBattleArmy().get(2));
//        battle.defend(battle.getCurrentMoveUnitsStack());
//        battle.attack(battle.getCurrentMoveUnitsStack(), battle.getBattleArmy1().getBattleArmy().get(0), false);
//        battle.attack(battle.getCurrentMoveUnitsStack(), battle.getBattleArmy2().getBattleArmy().get(2), false);
        System.out.println("End Scale\n" + battle.initiativeScaleToString());

        System.out.println("#1 " + battle.getBattleArmy1());
        System.out.println("#2 " + battle.getBattleArmy2());
        // Battle (end)
    }
}