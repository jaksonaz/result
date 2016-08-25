package com.azarenkov.repository;

import com.azarenkov.model.UserScore;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;


public interface UserScoreRepository extends CrudRepository<UserScore, Long> {
    @Query(value = "SELECT SUM(score)" +
            " FROM user_score" +
            " where user_id = ?1 " +
            "GROUP BY user_id", nativeQuery = true)
    Double sumScoreByUserId(long id);

    @Query(value = "SELECT SUM(score) "
            + "FROM user_score "
            + "where user_id = ?1 "
            + "AND date BETWEEN ?2 AND ?3 "
            + "GROUP BY user_id", nativeQuery = true)
    Double sumScoreByUserIdPerBetween(long id, Date start, Date end);

    @Query(value = "SELECT user_id, " +
            "SUM(score) FROM user_score " +
            "WHERE date BETWEEN ?1 AND ?2 " +
            "GROUP BY user_id " +
            "ORDER BY SUM(score) DESC ",nativeQuery = true)
    List<Object[]> totalUserScore(Date start, Date end);

}
