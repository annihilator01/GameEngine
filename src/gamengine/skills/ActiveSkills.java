package gamengine.skills;

public enum ActiveSkills {
    PUNISHINGHIT ("Punishing Hit", "increases target attack by 12 points"),
    CURSE ("Curse", "decreases target attack by 12 points (attack can't turn less than 0)"),
    WEAKENING ("Weakening", "decreases target defense by 12 points (attack can't turn less than 0)"),
    ACCELERATION ("Acceleration", "increases target initiative by 40%"),
    RESURRECTION ("Resurrection", "resurrects 100 target (only with passive skill Undead!) health points\nfor every unit that conjures (resurrection can't restore more hp than it was in the beginning of the battle)");

    private final String title;
    private final String description;

    ActiveSkills(String title, String description) {
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
