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

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class, GuildsRepository.class})
@ActiveProfiles("test")
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
        gb.setGuild_id(1);

        guildsRepository.putGuild(gb);

        List<GuildBean> gbList = guildsRepository.getAllChannels();

        System.out.println(gbList);

        for (GuildBean b : gbList) {
            System.out.println(b.getGuild());
            System.out.println(b.getPrefix());
            System.out.println(b.getChannelid());
        }
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