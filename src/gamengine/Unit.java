package gamengine;

public abstract class Unit {
    private final String type;
    private final int HP;
    private final int attack;
    private final int defense;
    private final Range damage;
    private final double initiative;

    protected Unit(String type, int HP, int attack, int defense,
                   Range damage, double initiative) {
        this.type = type;
        this.HP = HP;
        this.attack = attack;
        this.defense = defense;
        this.damage = damage;
        this.initiative = initiative;
    }

    public String getType() {
        return type;
    }

    public int getHP() {
        return HP;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public Range getDamage() {
        return damage;
    }

    public double getInitiative() {
        return initiative;
    }

    public String about() {
        return "Unit\n" +
                "\tType: " + type + '\n' +
                "\tHP: " + HP + '\n' +
                "\tAttack: " + attack + '\n' +
                "\tDefense: " + defense + '\n' +
                "\tDamage: " + damage.min + "-" + damage.max + '\n' +
                "\tInitiative: " + initiative + "\n\n";
    }
}
