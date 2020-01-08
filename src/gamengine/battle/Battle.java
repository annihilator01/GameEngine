package gamengine.battle;

import gamengine.*;
import gamengine.march.Army;
import gamengine.march.UnitStack;
import gamengine.skills.PassiveSkills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Battle {
    public static int MAX_WAIT_AND_DEFENSE_ACTIONS_IN_A_ROW = 5;
    public static int MAX_USE_OF_ACTIVE_SKILL = 3;

    private BattleArmy battleArmy1;
    private BattleArmy battleArmy2;
    private String namePlayer1;
    private String namePlayer2;
    private ArrayList<BattleUnitStack> initiativeScale;


    public Battle(Army army1, Army army2, String namePlayer1, String namePlayer2) {
        this.battleArmy1 = new BattleArmy(army1);
        this.battleArmy2 = new BattleArmy(army2);
        this.namePlayer1 = namePlayer1;
        this.namePlayer2 = namePlayer2;
        createInitiativeScale();
    }

    public BattleArmy getBattleArmy(BattleUnitStack battleUnitStack) {
        return (battleUnitStack.getArmyIndex() == 1) ? battleArmy1 : battleArmy2;
    }

    public BattleArmy getBattleArmy1() {
        return battleArmy1;
    }

    public BattleArmy getBattleArmy2() {
        return battleArmy2;
    }

    public ArrayList<BattleUnitStack> getAllBattleUnitStacks() {
        return new ArrayList<>(){{
            addAll(battleArmy1.getBattleArmy());
            addAll(battleArmy2.getBattleArmy());
        }};
    }

    public String getPlayerName1() {
        return namePlayer1;
    }

    public String getPlayerName2() {
        return namePlayer2;
    }

    public void createInitiativeScale() {
        initiativeScale = new ArrayList<>();

        getAllBattleUnitStacks().forEach(battleUnitStack -> {
            if (battleUnitStack.getUnitClass().getInitiative() + battleUnitStack.getInitiativeChange() < 0) {
                battleUnitStack.setInitiativeChange(-1 * (2 * battleUnitStack.getUnitClass().getInitiative() + battleUnitStack.getInitiativeChange()));
            }

            if (!battleUnitStack.isDead()) {
                initiativeScale.add(battleUnitStack);
            }
        });

        Collections.sort(initiativeScale);
    }

    public ArrayList<BattleUnitStack> getInitiativeScale() {
        return initiativeScale;
    }

    public String initiativeScaleToString() {
        String toString;
        toString = "Initiative Scale\n";

        for (int i = 0; i < initiativeScale.size(); i++) {
            toString += "#" + (i + 1) + ' ' + initiativeScale.get(i).toString();
        }

        return toString;
    }

    public Army convertToArmy(int armyIndex) {
        BattleArmy battleArmy;

        if (armyIndex == 1) {
            battleArmy = battleArmy1;
        } else if (armyIndex == 2) {
            battleArmy = battleArmy2;
        } else {
            throw new IllegalArgumentException("\nValid index of army range: 1-2" +
                "\nYour invalid index: " + armyIndex);
        }

        ArrayList<UnitStack> stacks = new ArrayList<>();
        for (BattleUnitStack battleUnitsStack : battleArmy.getBattleArmy()) {
            if (!battleUnitsStack.isDead()) {
                stacks.add(new UnitStack(battleUnitsStack));
            }
        }

        return new Army(stacks, battleArmy.getPlayerName(), armyIndex);

    }

    public BattleUnitStack getCurrentMoveUnitsStack() {
        if (initiativeScale.size() != 0) {
            return initiativeScale.get(0);
        }
        return null;
    }

    public void attack(BattleUnitStack actor, BattleUnitStack target) {
        attack(actor, target, false);
    }

    private void attack(BattleUnitStack actor, BattleUnitStack target, boolean isCounterAttack) {
        if (actor == null) {
            throw new IllegalArgumentException("\nInvalid argument actor: null");
        }

        // actor attack
        if (target == null) {
            if (actor.getUnitClass().getPassiveSkills().contains(PassiveSkills.ATTACKALL)) {
                ArrayList<BattleUnitStack> targetArmy;
                targetArmy = (actor.getArmyIndex() == 1) ? battleArmy2.getBattleArmy() : battleArmy1.getBattleArmy();

                for (BattleUnitStack targetStack : targetArmy) {
                    if (!targetStack.isDead()) {
                        attack(actor, targetStack, false);
                    }
                }

                initiativeScale.remove(0);
                return;
            } else {
                throw new IllegalArgumentException("\nInvalid argument target: null");
            }
        }

        if (actor.getArmyIndex() == target.getArmyIndex()) {
            throw new IllegalArgumentException("\nInvalid arguments: actor and target can't be in one army");
        }

        Range totalDamage;

        int actorUnitsNumber = actor.getUnitsNumber();
        double actorAttack = actor.getUnitClass().getAttack() + actor.getAttackChange();
        Range actorDamage = actor.getUnitClass().getDamage();

        int targetBattleUnitStackHP = target.getHP();

        double targetDefense;
        if (actor.getUnitClass().getPassiveSkills().contains(PassiveSkills.CLEARSHOT)) {
            targetDefense = 0;
        } else {
            targetDefense = target.getUnitClass().getDefense() + target.getDefenseChange();
        }

        if (actorAttack > targetDefense) {
            totalDamage = new Range(actorUnitsNumber * actorDamage.getMin() * (1 + 0.05 * (actorAttack - targetDefense)),
                                    actorUnitsNumber * actorDamage.getMax() * (1 + 0.05 * (actorAttack - targetDefense)));
        } else {
            totalDamage = new Range(actorUnitsNumber * actorDamage.getMin() / (1 + 0.05 * (targetDefense - actorAttack)),
                                    actorUnitsNumber * actorDamage.getMax() / (1 + 0.05 * (targetDefense - actorAttack)));
        }


        Random random = new Random();
        int finalDamage = random.nextInt((int)(totalDamage.getMax() - totalDamage.getMin()) + 1) +
                          (int)totalDamage.getMin();
        int newTargetBattleUnitStackHP = Math.max(targetBattleUnitStackHP - finalDamage, 0);

        target.setBattleUnitStackHP(newTargetBattleUnitStackHP);
        if (newTargetBattleUnitStackHP == 0) {
            initiativeScale.remove(target);
        }

        // target counter attack
        if (isCounterAttack ||
            actor.getUnitClass().getPassiveSkills().contains(PassiveSkills.SHOOTER) ||
            actor.getUnitClass().getPassiveSkills().contains(PassiveSkills.ENEMYNOTRESIST) ||
            target.getUnitClass().getPassiveSkills().contains(PassiveSkills.SHOOTER) ||
            target.hasResisted() || target.isDead()) {
            if (!actor.getUnitClass().getPassiveSkills().contains(PassiveSkills.ATTACKALL)) {
                initiativeScale.remove(0);
            }
            return;
        }

        attack(target, actor, true);

        if (!target.getUnitClass().getPassiveSkills().contains(PassiveSkills.ENDLESSRESISTANCE)) {
            target.resisted();
        }
    }

    public void useActiveSkill(BattleUnitStack actor, BattleUnitStack target) {
        if (actor == null) {
            throw new IllegalArgumentException("\nInvalid argument actor: null");
        }

        if (actor.getUnitClass().getActiveSkill() == null) {
            throw new IllegalArgumentException("\nInvalid argument actor: no active skill");
        }

        if (actor.getNumberActiveSkillUsed() == MAX_USE_OF_ACTIVE_SKILL) {
            throw new IllegalArgumentException("\nInvalid argument actor: used active skill");
        }

        if (target == null) {
            throw new IllegalArgumentException("\nInvalid argument actor: null");
        }

        if (target == actor) {
            throw new IllegalArgumentException("\nInvalid arguments: actor and target can't be the same heroes");
        }

        switch (actor.getUnitClass().getActiveSkill()) {
            case PUNISHINGHIT:
                target.setAttackChange(target.getAttackChange() + 12);
                break;
            case CURSE:
                if (target.getUnitClass().getAttack() + target.getAttackChange() - 12 > 0) {
                    target.setAttackChange(target.getAttackChange() - 12);
                } else {
                    target.setAttackChange(-1 * target.getUnitClass().getAttack());
                }
                break;
            case WEAKENING:
                if (target.getUnitClass().getDefense() + target.getDefenseChange() - 12 > 0) {
                    target.setDefenseChange(target.getDefenseChange() - 12);
                } else {
                    target.setDefenseChange(-1 * target.getUnitClass().getDefense());
                }
                break;
            case ACCELERATION:
                target.setInitiativeChange(target.getInitiativeChange() +
                                           0.4 * (target.getUnitClass().getInitiative() + target.getInitiativeChange()));
                break;
            case RESURRECTION:
                if (!target.getUnitClass().getPassiveSkills().contains(PassiveSkills.UNDEAD)) {
                    throw new IllegalArgumentException("\nInvalid argument target: not undead for resurrection");
                }

                if (target.isDead()) {
                    initiativeScale.add(target);
                    Collections.sort(initiativeScale);
                }

                int newTargetBattleUnitStackHP = target.getHP() + actor.getUnitsNumber() * 100;
                target.setBattleUnitStackHP(newTargetBattleUnitStackHP);
                break;
            default:
                throw new IllegalArgumentException("\nInvalid active skill: unknown");
        }

        actor.activeSkillActivated();
        initiativeScale.remove(0);
    }

    public void wait(BattleUnitStack actor) {
        if (actor == null) {
            throw new IllegalArgumentException("\nInvalid argument actor: null");
        }

        if (actor.getUnitClass().getInitiative() + actor.getInitiativeChange() > 0) {
            actor.setInitiativeChange(-1 * (2 * actor.getUnitClass().getInitiative() + actor.getInitiativeChange()));
        } else {
            actor.setInitiativeChange(-1 * (2 * actor.getUnitClass().getInitiative() + actor.getInitiativeChange()));
            initiativeScale.remove(0);
        }

        Collections.sort(initiativeScale);
    }

    public void defend(BattleUnitStack actor) {
        if (actor == null) {
            throw new IllegalArgumentException("\nInvalid argument actor: null");
        }

        initiativeScale.remove(0);
        actor.setDefenseChange(actor.getDefenseChange() +
                               0.3 * (actor.getUnitClass().getDefense() + actor.getDefenseChange()));
    }

    public void giveUp(BattleArmy battleArmy) {
        if (battleArmy == null) {
            throw new IllegalArgumentException("\nInvalid argument battleArmy: null");
        }

        battleArmy.giveUp();
    }

    public boolean isArmyDead(BattleArmy battleArmy) {
        for (BattleUnitStack battleUnitsStack : battleArmy.getBattleArmy()) {
            if (!battleUnitsStack.isDead()) {
                return false;
            }
        }

        return true;
    }

    public boolean isInitiativeScaleEmpty() {
        return initiativeScale.size() == 0;
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
