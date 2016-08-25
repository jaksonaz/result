package com.azarenkov.controller;

import com.azarenkov.ResultAppApplication;
import com.azarenkov.config.EmbeddedDbJpaConfig;
import com.azarenkov.service.UserService;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ResultAppApplication.class, EmbeddedDbJpaConfig.class})
public class UserControllerTest {

    private static final double SCORE = 250D;
    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        // Here we manually injecting mocked implementation of the service into the controller
        manuallyInject("userService", userController, userService);
        //full control over the instantiation and initialization of controllers and their dependencies
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    public void testUserScoreByDay() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String source = "2016-08-24 22:21:21";
        Date date = simpleDateFormat.parse(source);
        when(userService.retrieveScoreByUserIdPerDay(1L , date)).thenReturn(250D);

        String response =
                mockMvc
                        .perform(get("/user/{id}/score/day/{date}", 1L, source).contentType(contentType))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse().getContentAsString();
        assertNotNull(response);


        double score = JsonPath.read(response, "$.score");
        assertEquals(SCORE, score, 0);
    }

    @Test
    public void testUserScoreBetween() throws Exception {
        //TODO: for two dates
    }

    protected void manuallyInject(String fieldName, Object instance, Object targetObject)
            throws NoSuchFieldException, IllegalAccessException {
        Field contentServiceField = instance.getClass().getDeclaredField(fieldName);
        contentServiceField.setAccessible(true);
        contentServiceField.set(instance, targetObject);
    }
}
