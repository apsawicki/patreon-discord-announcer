package PDA.jpa;

import PDA.beans.GuildBean;
import PDA.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {GuildsRepository.class, TestConfig.class})
public class GuildsRepositoryTest {

    @Autowired
    GuildsRepository guildsRepository;

    @Test
    @Transactional
    public void getAllChannels() {
        GuildBean gb = new GuildBean();
        gb.setGuild("guild");
        gb.setPrefix("prefix");
        gb.setChannelid("channel");

        guildsRepository.putGuild(gb);
    }

    @Test
    public void getChannel() {
    }

    @Test
    public void putGuild() {
    }

    @Test
    public void updateChannel() {
    }

    @Test
    public void removeGuild() {
    }

    @Test
    public void updatePrefix() {
    }
}