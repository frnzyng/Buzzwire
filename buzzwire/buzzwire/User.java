package com.example.buzzwire;

public class User {
    static User user;
    String scoreEasy;
    String scoreMedium;
    String scoreHard;

    public static User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public String getScoreEasy() {
        return scoreEasy;
    }

    public void setScoreEasy(String scoreEasy) {
        this.scoreEasy = scoreEasy;
    }

    public String getScoreMedium() {
        return scoreMedium;
    }

    public void setScoreMedium(String scoreMedium) {
        this.scoreMedium = scoreMedium;
    }

    public String getScoreHard() {
        return scoreHard;
    }

    public void setScoreHard(String scoreHard) {
        this.scoreHard = scoreHard;
    }
}
