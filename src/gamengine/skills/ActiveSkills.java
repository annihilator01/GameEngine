package gamengine.skills;

public enum ActiveSkills {
    PUNISHINGHIT("Punishing Hit", "increases target attack by 12 points",
                 battleUnitStack -> battleUnitStack.getUnitClass().getAttack() + battleUnitStack.getAttackChange(),
            "/gui/assets/attack.png", true),

    CURSE ("Curse", "decreases target attack by 12 points (attack can't turn less than 0)",
           battleUnitStack -> battleUnitStack.getUnitClass().getAttack() + battleUnitStack.getAttackChange(),
            "/gui/assets/attack.png", false),

    WEAKENING ("Weakening", "decreases target defense by 12 points (attack can't turn less than 0)",
               battleUnitStack -> battleUnitStack.getUnitClass().getDefense() + battleUnitStack.getDefenseChange(),
            "/gui/assets/defense.png", false),

    ACCELERATION ("Acceleration", "increases target initiative by 40%",
                  battleUnitStack -> battleUnitStack.getUnitClass().getInitiative() + battleUnitStack.getInitiativeChange(),
            "/gui/assets/initiative.png", true),

    RESURRECTION ("Resurrection", "resurrects 100 target (only with passive skill Undead!) health points\nfor every unit that conjures (resurrection can't restore more hp than it was in the beginning of the battle)",
                  battleUnitStack -> battleUnitStack.getHP(),
            "/gui/assets/heart.png", true);

    private final String title;
    private final String description;
    private final ChangeParameter changeParameter;
    private final String imageURL;
    private final boolean isPositive;

    ActiveSkills(String title, String description, ChangeParameter changeParameter,
                 String imageURL, boolean isPositive) {
        this.title = title;
        this.description = description;
        this.changeParameter = changeParameter;
        this.imageURL = imageURL;
        this.isPositive = isPositive;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ChangeParameter getChangeParameter() {
        return changeParameter;
    }

    public String getImageURL() {
        return imageURL;
    }

    public boolean isPositive() {
        return isPositive;
    }
}
