package PDA.jpa;

import java.util.List;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class, GuildsRepository.class})
@ActiveProfiles("test")
@Transactional
public class GuildsRepositoryTest {

    @Autowired
    GuildsRepository guildsRepository;

    @Test
    public void getAllGuildsTest() {
        GuildBean gb = new GuildBean();
        gb.setGuild("guild");
        gb.setPrefix("prefix");
        gb.setChannelid("channel");
        gb.setGuild_id(1);

        guildsRepository.putGuild(gb);

        List<GuildBean> gbList = guildsRepository.getAllGuilds();
        assertEquals(1, gbList.size());
        assertEquals("guild", gbList.get(0).getGuild());
        assertEquals("prefix", gbList.get(0).getPrefix());
        assertEquals("channel", gbList.get(0).getChannelid());
    }

    @Test
    public void getGuildTest() {
        GuildBean gb = new GuildBean();
        gb.setGuild("guild");
        gb.setPrefix("prefix");
        gb.setChannelid("channel");
        gb.setGuild_id(1);

        guildsRepository.putGuild(gb);

        GuildBean gbReturn = guildsRepository.getGuild("guild");

        assertEquals(gb.getGuild(), gbReturn.getGuild());
        assertEquals(gb.getPrefix(), gbReturn.getPrefix());
        assertEquals(gb.getChannelid(), gbReturn.getChannelid());
    }

    @Test
    public void updateChannelTest() {
        GuildBean gb = new GuildBean();
        gb.setGuild("guild");
        gb.setPrefix("prefix");
        gb.setChannelid("channel");
        gb.setGuild_id(1);

        guildsRepository.putGuild(gb);

        gb.setChannelid("newchannel");
        guildsRepository.updateChannel(gb);

        GuildBean gbReturn = guildsRepository.getGuild("guild");

        assertEquals("newchannel", gbReturn.getChannelid());
    }

    @Test
    public void updatePrefixTest() {
        GuildBean gb = new GuildBean();
        gb.setGuild("guild");
        gb.setPrefix("prefix");
        gb.setChannelid("channel");
        gb.setGuild_id(1);

        guildsRepository.putGuild(gb);

        gb.setPrefix("newprefix");
        guildsRepository.updatePrefix(gb);

        GuildBean gbReturn = guildsRepository.getGuild("guild");

        assertEquals("newprefix", gbReturn.getPrefix());
    }

    @Test
    public void removeGuildTest() {
        GuildBean gb = new GuildBean();
        gb.setGuild("guild");
        gb.setPrefix("prefix");
        gb.setChannelid("channel");
        gb.setGuild_id(1);

        guildsRepository.putGuild(gb);

        assertEquals(1, guildsRepository.getAllGuilds().size());

        guildsRepository.removeGuild("guild");

        assertTrue(guildsRepository.getAllGuilds().isEmpty());
    }
}