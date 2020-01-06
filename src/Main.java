import gamengine.*;
import gamengine.march.*;
import gamengine.battle.*;
import gamengine.unit.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        /*UnitLoader unitsLoader = new UnitLoader();

        // units (begin)
        Unit angel = unitsLoader.createUnit("Angel");
        Unit devil = unitsLoader.createUnit("Devil");
        Unit flash = unitsLoader.createUnit("Flash");
        Unit rock = unitsLoader.createUnit("Rock");
        Unit furya = unitsLoader.createUnit("Furya");
        Unit arbalester = unitsLoader.createUnit("Arbalester");
        // units (end)

        // units stack (begin)
        UnitStack angelStack = new UnitStack(angel, 13, 1);
        UnitStack devilStack = new UnitStack(devil, 666, 1);
        UnitStack flashStack = new UnitStack(flash, 70, 1);
        UnitStack arbalesterStack = new UnitStack(arbalester, 60, 1);

        UnitStack angelStack2 = new UnitStack(angel, 11, 2);
        UnitStack furyaStack = new UnitStack(furya, 10, 2);
        UnitStack rockStack = new UnitStack(rock, 5, 2);
        // units stack (end)

        // army (begin)
        ArrayList<UnitStack> stacks1 = new ArrayList<>();
        stacks1.add(angelStack);
        stacks1.add(devilStack);
        stacks1.add(flashStack);
        stacks1.add(arbalesterStack);

        ArrayList<UnitStack> stacks2 = new ArrayList<>();
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
        // Battle (end)*/
    }
}