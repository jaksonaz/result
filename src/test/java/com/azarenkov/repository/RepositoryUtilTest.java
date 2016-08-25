package com.azarenkov.repository;


import com.azarenkov.config.EmbeddedDbJpaConfig;
import com.azarenkov.model.User;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { EmbeddedDbJpaConfig.class})
@Transactional
public abstract class RepositoryUtilTest {

    @Autowired
    protected UserRepository userRepository;

    public User create()  {
        User user = new User();
        user.setId(1L);
        return userRepository.save(user);
    }
}
