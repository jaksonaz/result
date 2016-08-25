package com.azarenkov.repository;

import com.azarenkov.config.EmbeddedDbJpaConfig;
import com.azarenkov.model.User;
import com.azarenkov.model.UserScore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmbeddedDbJpaConfig.class})
@Transactional
public class UserScoreRepositoryTest extends RepositoryUtilTest {

    @Autowired
    private UserScoreRepository userScoreRepository;



    @Test
    public void save() throws Exception {
        User user = create();
        UserScore userScore = new UserScore();
        userScore.setUser(user);
        userScoreRepository.save(userScore);
        assertTrue(userScoreRepository.findAll().iterator().hasNext());
    }

    @Test
    public void delete() throws Exception {
        User user = create();
        UserScore userScore = new UserScore();
        userScore.setUser(user);
        userScoreRepository.save(userScore);
        userScoreRepository.delete(userScore);
        assertFalse(userScoreRepository.findAll().iterator().hasNext());
    }

    @Test
    public void sumScoreByUserId() throws Exception {

        User user = create();
        UserScore userScore = new UserScore();
        userScore.setUser(user);
        userScore.setScore(200);
        userScore.setId(1);
        userScore.setDate(new Date());
        userScoreRepository.save(userScore);
        UserScore userScoreUpdated = new UserScore();
        userScoreUpdated.setDate(new Date());
        userScoreUpdated.setUser(user);
        userScoreUpdated.setScore(200);
        userScoreRepository.save(userScoreUpdated);
        assertEquals(400, userScoreRepository.sumScoreByUserId(user.getId()),0);

    }

    @Test
    public void sumScoreByUserIdPerDay() throws Exception {
    Calendar currentDay = Calendar.getInstance();
    Calendar otherCurrentDay = Calendar.getInstance();
    Calendar anotherDay = Calendar.getInstance();

    anotherDay.add(Calendar.DATE,5);

        User user = create();
        UserScore userScore = new UserScore();
        userScore.setDate(currentDay.getTime());
        userScore.setId(1);
        userScore.setScore(100);
        userScore.setUser(user);
        userScoreRepository.save(userScore);

        UserScore userScoreUpdated = new UserScore();
        userScoreUpdated.setId(2);
        userScoreUpdated.setDate(otherCurrentDay.getTime());
        userScoreUpdated.setScore(100);
        userScoreUpdated.setUser(user);
        userScoreRepository.save(userScoreUpdated);

        UserScore userScoreUpdatedTwice = new UserScore();
        userScoreUpdatedTwice.setId(3);
        userScoreUpdatedTwice.setDate(anotherDay.getTime());
        userScoreUpdatedTwice.setScore(100);
        userScoreUpdatedTwice.setUser(user);
        userScoreRepository.save(userScoreUpdatedTwice);
         //today
        Calendar start = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);

        // next day
        Calendar end = new GregorianCalendar();
        end.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(200, userScoreRepository.sumScoreByUserIdPerBetween(user.getId(), start.getTime(), end.getTime()), 0);

    }

    @Test
    public void retrieveScoreByUserIdLastWeek() {
        Calendar end = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        Calendar outOfLastweek = Calendar.getInstance();
        start.add(Calendar.DATE, -7);
        outOfLastweek.add(Calendar.DATE, -10);

        User user = create();
        UserScore userScore = new UserScore();
        userScore.setDate(start.getTime());
        userScore.setId(1);
        userScore.setScore(100);
        userScore.setUser(user);
        userScoreRepository.save(userScore);

        UserScore userScoreUpdated = new UserScore();
        userScoreUpdated.setId(2);
        userScoreUpdated.setDate(end.getTime());
        userScoreUpdated.setScore(100);
        userScoreUpdated.setUser(user);
        userScoreRepository.save(userScoreUpdated);

        UserScore userScoreUpdatedTwice = new UserScore();
        userScoreUpdatedTwice.setId(3);
        userScoreUpdatedTwice.setDate(outOfLastweek.getTime());
        userScoreUpdatedTwice.setScore(100);
        userScoreUpdatedTwice.setUser(user);
        userScoreRepository.save(userScoreUpdatedTwice);


        assertEquals(200, userScoreRepository.sumScoreByUserIdPerBetween(user.getId(), start.getTime(), end.getTime()), 0);

    }

    @Test
    public void totalUserScore(){
        Calendar end = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        Calendar outOfLastweek = Calendar.getInstance();
        start.add(Calendar.DATE, -7);
        outOfLastweek.add(Calendar.DATE, -10);

        User user = create();
        UserScore userScore = new UserScore();
        userScore.setDate(start.getTime());
        userScore.setId(1);
        userScore.setScore(100);
        userScore.setUser(user);
        userScoreRepository.save(userScore);

        UserScore userScoreUpdated = new UserScore();
        userScoreUpdated.setId(2);
        userScoreUpdated.setDate(end.getTime());
        userScoreUpdated.setScore(100);
        userScoreUpdated.setUser(user);
        userScoreRepository.save(userScoreUpdated);

        UserScore userScoreUpdatedTwice = new UserScore();
        userScoreUpdatedTwice.setId(3);
        userScoreUpdatedTwice.setDate(outOfLastweek.getTime());
        userScoreUpdatedTwice.setScore(100);
        userScoreUpdatedTwice.setUser(user);
        userScoreRepository.save(userScoreUpdatedTwice);
        assertEquals(300,userScoreRepository.totalUserScore(start.getTime(),outOfLastweek.getTime()));


    }

}
