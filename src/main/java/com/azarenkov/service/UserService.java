package com.azarenkov.service;

import com.azarenkov.model.User;
import com.azarenkov.model.UserScore;
import com.azarenkov.repository.UserRepository;
import com.azarenkov.repository.UserScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class UserService {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserScoreRepository userScoreRepository;


//    public User createUser() {
//        return userRepository.save(new User());
//    }

    public void deleteUser(long id) {
        userRepository.delete(id);
    }

    public void addScore(long userId, Date date, String timezome, double score) throws ParseException {
        UserScore userScore = new UserScore();
        userScore.setDate(date);
        userScore.setScore(score);
        userScore.setTimeZone(timezome);
        User user = userRepository.findOne(userId);
        userScore.setUser(user);
        userScoreRepository.save(userScore);
    }

    public Double retrieveScoreByUserIdPerDay(long userId, Date date) {
        //today
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);

        // next day
        Calendar end = Calendar.getInstance();
        end.setTime(date);
        end.add(Calendar.DAY_OF_MONTH, 1);

        return retrieveScoreByUserIdBetween(userId, start.getTime(), end.getTime());
    }

    public Double retrieveScoreByUserIdBetween(long userId, Date start, Date end) {
        return userScoreRepository.sumScoreByUserIdPerBetween(userId, start, end);
    }
    public Double retrieveScoreByUserIdLastWeek(long userId) {
        Calendar end = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.add(Calendar.DATE, -7);

        return userScoreRepository.sumScoreByUserIdPerBetween(userId, start.getTime(), end.getTime());
    }

    public List<Object[]> totalUserScore(Date start, Date end) {
        return userScoreRepository.totalUserScore(start, end);
    }

    public boolean findById(long id){
      return   userRepository.exists(id);
    }


}
