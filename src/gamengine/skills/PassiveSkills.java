package gamengine.skills;

public enum PassiveSkills {
    SHOOTER ("Shooter", "target doesn't launch a counterattack, so doesn't actor"),
    CLEARSHOT ("Clearshot", "ignores target defense"),
    UNDEAD ("Undead", "this creature can be resurrected"),
    ENEMYNOTRESIST ("No Enemy Resistance", "target doesn't launch a counterattack"),
    ATTACKALL ("Attack ALL", "attack all unit stacks of enemy's army"),
    ENDLESSRESISTANCE ("Endless Resistance", "this creature always counterattack after being attacked");

    private final String title;
    private final String description;

    PassiveSkills(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
