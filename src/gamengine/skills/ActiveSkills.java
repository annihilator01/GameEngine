package gamengine.skills;

public enum ActiveSkills {
    PUNISHINGHIT ("Punishing Hit"),
    CURSE ("Curse"),
    WEAKENING ("Weakening"),
    ACCELERATION ("Acceleration"),
    RESURRECTION ("Resurrection");

    private final String activeSkillName;

    ActiveSkills(String activeSkillName) {
        this.activeSkillName = activeSkillName;
    }

    public String getActiveSkillName() {
        return activeSkillName;
    }
}
