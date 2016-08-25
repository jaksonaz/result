package com.azarenkov.controller;

import com.azarenkov.dto.ResultScore;
import com.azarenkov.dto.ResultScoreWrapper;
import com.azarenkov.dto.Score;
import com.azarenkov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "user/score/day", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userScoreByDay(@RequestParam("id") long id,
                                            @RequestParam("date") String date) throws ParseException {
        if (userService.findById(id)) {
            Double scoreByUserIdPerDay = userService.retrieveScoreByUserIdPerDay(id, simpleDateFormat.parse(date));
            if (scoreByUserIdPerDay != null) {
                Score score = new Score();
                score.setScore(scoreByUserIdPerDay);
                return ResponseEntity.ok(score);
            }
        } else throw new UserNotFoundException(id);
        throw new NotFoundException();
    }

    @RequestMapping(value = "user/score/lastweek", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userScoreLastWeek(@RequestParam("id") long id) {
        if (userService.findById(id)) {
            Double scoreByUserIdPerDay = userService.retrieveScoreByUserIdLastWeek(id);
            if (scoreByUserIdPerDay != null) {
                Score score = new Score();
                score.setScore(scoreByUserIdPerDay);
                return ResponseEntity.ok(score);
            }
        } else throw new UserNotFoundException(id);
        throw new NotFoundException();
    }

    @RequestMapping(value = "/user/addscore", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public void userAddScore(@RequestParam("id") long id,
                             @RequestParam("date") String date,
                             @RequestParam("timezone") String timezome,
                             @RequestParam("score") double score) throws ParseException {

        if (userService.findById(id)) {
            userService.addScore(id, simpleDateFormat.parse(date), timezome, score);
        } else throw new UserNotFoundException(id);
    }

    @RequestMapping(value = "/user/getscore", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResultScoreWrapper usergetScore(@RequestParam("start") String start,
                                           @RequestParam("end") String end) throws ParseException {
        checkDates(start, end);
        List<ResultScore> listScore = new ArrayList<>();
        List<Object[]> objects = userService.totalUserScore(simpleDateFormat.parse(start), simpleDateFormat.parse(end));
        objects.forEach(e -> {
            listScore.add(new ResultScore((BigInteger) e[0], (double) e[1]));
        });
        return new ResultScoreWrapper(listScore);
    }


    @RequestMapping(value = "/user/getpositionbyid", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userGetPosition(@RequestParam("start") String start,
                                             @RequestParam("end") String end,
                                             @RequestParam("id") long id) throws ParseException {
        if (userService.findById(id) & checkDates(start, end)) {
            List<Object[]> objects = userService.totalUserScore(simpleDateFormat.parse(start), simpleDateFormat.parse(end));
            for (int i = 0; i < objects.size(); i++) {
                if (((BigInteger) objects.get(i)[0]).longValue() == (id)) {
                    return ResponseEntity.ok(i + 1);
                }
            }
        }
        return ResponseEntity.noContent().build();
    }


    private boolean checkDates(String start, String end) throws ParseException {
        Date startDate = simpleDateFormat.parse(start);
        Date endDate = simpleDateFormat.parse(end);

        if (startDate.compareTo(endDate) > 0) {
            throw new WrongDatePeriod(start, end);

        } else if (startDate.compareTo(endDate) == 0) {
            throw new SameDatePeriod(start, end);
        } else return true;

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(long id) {
            super("could not find user with id: " + id);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class NotFoundException extends RuntimeException {
        public NotFoundException() {
            super("could not find result : ");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    class WrongDatePeriod extends RuntimeException {
        public WrongDatePeriod(String start, String end) {
            super("Start date older then end date, Start date: " + start + " End date: " + end);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    class SameDatePeriod extends RuntimeException {
        public SameDatePeriod(String start, String end) {
            super("Start date equal end date, Start date: " + start + " End date: " + end);
        }
    }


}

