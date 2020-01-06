package gamengine.skills;

public enum PassiveSkills {
    SHOOTER ("Shooter"),
    CLEARSHOT ("Clearshot"),
    UNDEAD ("Undead"),
    ENEMYNOTRESIST ("No Enemy Resistance"),
    ATTACKALL ("Attack ALL"),
    ENDLESSRESISTANCE ("Endless Resistance");

    private final String passiveSkillName;

    PassiveSkills(String passiveSkillName) {
        this.passiveSkillName = passiveSkillName;
    }

    public String getPassiveSkillName() {
        return passiveSkillName;
    }
}
