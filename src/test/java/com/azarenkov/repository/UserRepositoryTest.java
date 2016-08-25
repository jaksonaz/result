package com.azarenkov.repository;

import com.azarenkov.config.EmbeddedDbJpaConfig;
import com.azarenkov.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { EmbeddedDbJpaConfig.class})
@Transactional
public class UserRepositoryTest extends RepositoryUtilTest {

    @Test
    public void save() throws Exception {
        create();
        assertTrue(userRepository.findAll().iterator().hasNext());
    }

    @Test
    public void delete() throws Exception {
        User user = create();
        userRepository.delete(user.getId());
        assertFalse(userRepository.findAll().iterator().hasNext());
    }



}
