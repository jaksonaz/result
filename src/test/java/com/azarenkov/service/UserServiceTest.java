//package com.azarenkov.service;
//
//import com.azarenkov.config.EmbeddedDbJpaConfig;
//import com.azarenkov.model.User;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { EmbeddedDbJpaConfig.class})
//@Transactional
//public class UserServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    private User user;
//
//    @Before
//    public void setUp() {
//        user = createUser();
//    }
//
//    private User createUser() {
//        return userService.createUser();
//    }
//
//    @Test
//    public void addScore() throws Exception {
//        userService.addScore(user.getId(), "16-09-2016 19:45:03", "America/Montreal", 250);
//    }
//}
