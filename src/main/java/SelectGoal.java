public class SelectGoal {
    BodyAnalyzer ba;
    String goal;

    public SelectGoal(BodyAnalyzer ba) {
        this.ba = ba;
    }

    public void chooseGoal() {
        if (ba.bodystatus.equals("Underweight")) {
            goal = "bulk";
        } else if (ba.bodystatus.equals("Normal weight")) {
            goal = "maintain weight";
        } else {
            goal = "cut";
        }
    }

    public String getGoal() {
        return goal;
    }
}
