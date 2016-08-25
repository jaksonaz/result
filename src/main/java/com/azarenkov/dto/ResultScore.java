package com.azarenkov.dto;

import java.math.BigInteger;

public class ResultScore {
   private double score;
    private BigInteger userId;


    public ResultScore( BigInteger userId,double score) {
        this.score = score;
        this.userId = userId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }
}
