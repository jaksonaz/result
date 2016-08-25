package com.azarenkov.dto;


import java.util.List;

public class ResultScoreWrapper {
    private List<ResultScore> scoreList;

    public ResultScoreWrapper(List<ResultScore> scoreList) {
        this.scoreList = scoreList;
    }

    public List<ResultScore> getScoreList() {
        return scoreList;
    }
}
