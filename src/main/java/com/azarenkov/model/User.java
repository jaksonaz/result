package com.azarenkov.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "user")
    private List<UserScore> userScoreList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<UserScore> getUserScoreList() {
        return userScoreList;
    }

    public void setUserScoreList(List<UserScore> userScoreList) {
        this.userScoreList = userScoreList;
    }
}
