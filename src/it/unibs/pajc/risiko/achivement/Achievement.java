package src.it.unibs.pajc.risiko.achivement;

import src.it.unibs.pajc.risiko.Player;

public class Achievement {

    private String description;
    private Condition condition;
    // private boolean achived;

    public Achievement(String description, Condition condition) {
        this.description = description;
        this.condition = condition;
        // this.achived = false;
    }

    public String getDescription() {
        return description;
    }

    public Condition getCondition() {
        return condition;
    }

    public boolean isAchived(Player player) {
        return condition.isAchived(player);
    }

    public String getName() {
        return description;
    }

}
