public class ExternalAI {
    String goal;
    String[] allExercises = { "squats", "deadlifts", "bench press", "pull-ups", "lunges", "planks", "burpees",
            "jumping jacks", };
    String[] muscles = { "legs", "back", "chest", "arms", "core" };
    String[] difficultyLevels = { "beginner", "intermediate", "advanced" };
    String[] issues = { "knee", "back", "shoulder", "arm", "core" };
    String[][] restrictions = {
            { "squats", "lunges" },
            { "deadlifts", "pull-ups" },
            { "bench press" },
            { "pull-ups", "burpees" },
            { "planks", "burpees" }
    };

    public ExternalAI(String goal) {
        this.goal = goal;
    }

    public String[] getExercises(String[] userissues) {
        boolean[] selectedExercises = new boolean[allExercises.length];

        for (int i = 0; i < userissues.length; i++) {
            for (int j = 0; j < issues.length; j++) {
                if (userissues[i].equalsIgnoreCase(issues[j])) {
                    for (int k = 0; k < restrictions[j].length; k++) {
                        for (int l = 0; l < allExercises.length; l++) {
                            if (restrictions[j][k].equalsIgnoreCase(allExercises[l])) {
                                selectedExercises[l] = true;
                            }
                        }
                    }
                }
            }
        }

        int allowedCount = 0;
        for (int i = 0; i < selectedExercises.length; i++) {
            if (!selectedExercises[i]) {
                allowedCount++;
            }
        }

        String[] result = new String[allowedCount];
        int index = 0;

        for (int i = 0; i < selectedExercises.length; i++) {
            if (!selectedExercises[i]) {
                result[index++] = allExercises[i];
            }
        }

        return result;
    }
}
