package gamengine;

import gamengine.skills.ActiveSkills;
import gamengine.skills.PassiveSkills;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Battle {
    private BattleArmy battleArmy1;
    private BattleArmy battleArmy2;
    private ArrayList<BattleUnitsStack> initiativeScale;


    public Battle(Army army1, Army army2) {
        this.battleArmy1 = new BattleArmy(army1);
        this.battleArmy2 = new BattleArmy(army2);
        createInitiativeScale();
    }

    public void createInitiativeScale() {
        initiativeScale = new ArrayList<>();
        initiativeScale.addAll(battleArmy1.getBattleArmy());
        initiativeScale.addAll(battleArmy2.getBattleArmy());
        Collections.sort(initiativeScale);
    }

    public BattleUnitsStack getCurrentMoveUnitsStack() {
        if (initiativeScale.size() != 0) {
            return new BattleUnitsStack(initiativeScale.get(0));
        }
        return null;
    }

    public void attack(BattleUnitsStack actor, BattleUnitsStack target) {
        if (actor == null) {
            throw new IllegalArgumentException("\nInvalid argument actor: null");
        }

        if (target == null) {
            if (actor.getUnitClass().getPassiveSkils().contains(PassiveSkills.ATTACKALL) &&
                actor.getUnitClass().getPassiveSkils().contains(PassiveSkills.ENEMYNORESIST)) {
                ArrayList<BattleUnitsStack> targetArmy;
                targetArmy = (actor.getArmyIndex() == 1) ? battleArmy2.getBattleArmy() : battleArmy1.getBattleArmy();

                for (BattleUnitsStack  targetStack : targetArmy) {
                    if (!targetStack.isDead()) {
                        attack(actor, targetStack);
                    }
                }

                return;
            } else {
                throw new IllegalArgumentException("\nInvalid argument target: null");
            }
        }

        Range totalDamage;

        int actorUnitsNumber = actor.getUnitsNumber();
        double actorAttack = actor.getUnitClass().getAttack() + actor.getAttackChange();
        Range actorDamage = actor.getUnitClass().getDamage();

        int targetUnitsNumber = target.getUnitsNumber();
        int targetUnitHP = target.getUnitClass().getHP();
        double targetDefense = target.getUnitClass().getDefense() + target.getDefenseChange();

        if (actorAttack > targetDefense) {
            totalDamage = new Range(actorUnitsNumber * actorDamage.min * (1 * 0.5 *(actorAttack - targetDefense)),
                                    actorUnitsNumber * actorDamage.max * (1 * 0.5 *(actorAttack - targetDefense)));
        } else {
            totalDamage = new Range(actorUnitsNumber * actorDamage.min / (1 * 0.5 *(targetDefense - actorAttack)),
                                    actorUnitsNumber * actorDamage.max / (1 * 0.5 *(targetDefense - actorAttack)));
        }


        Random random = new Random();
        int finalDamage = random.nextInt((int)(totalDamage.max - totalDamage.min) + 1) +
                          (int)totalDamage.min;
        int newTargetUnitsNumber = Math.max(
                (int)Math.ceil((targetUnitsNumber * targetUnitHP - finalDamage) / (double)targetUnitsNumber),
                0);

        target.setUnitsNumber(newTargetUnitsNumber);

        // TODO: counter attack
    }

    public void useActiveSkill(BattleUnitsStack actor, BattleUnitsStack target) {
        // TODO: using of active skills
    }

    public void wait(BattleUnitsStack actor) {
        if (actor == null) {
            throw new IllegalArgumentException("\nInvalid argument actor: null");
        }

        actor.negateInitiative();
        initiativeScale.remove(0);
        initiativeScale.add(actor);
        Collections.sort(initiativeScale);
    }

    public void defend(BattleUnitsStack actor) {
        if (actor == null) {
            throw new IllegalArgumentException("\nInvalid argument actor: null");
        }

        initiativeScale.remove(0);
        actor.setDefenseChange(0.3 * actor.getUnitClass().getDefense());
    }

    public void giveUp(BattleArmy battleArmy) {
        if (battleArmy == null) {
            throw new IllegalArgumentException("\nInvalid argument battleArmy: null");
        }

        battleArmy.giveUp();
    }

    public boolean isArmyDead(BattleArmy battleArmy) {
        for (BattleUnitsStack battleUnitsStack : battleArmy.getBattleArmy()) {
            if (!battleUnitsStack.isDead()) {
                return false;
            }
        }

        return true;
    }

    public boolean isEnded() {
        return isArmyDead(battleArmy1) || isArmyDead(battleArmy2) ||
                battleArmy1.hasGivenUp() || battleArmy2.hasGivenUp();
    }

    public BattleArmy getWinner() throws Exception {
        if (!isEnded()) {
            throw new Exception("No winner yet!");
        }

        if (isArmyDead(battleArmy1) || battleArmy1.hasGivenUp()) {
            return battleArmy2;
        }

        if (isArmyDead(battleArmy2) || battleArmy2.hasGivenUp()) {
            return battleArmy1;
        }

        return null;
    }
}
