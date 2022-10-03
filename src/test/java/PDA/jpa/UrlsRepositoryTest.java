package PDA.jpa;

import java.util.List;

import PDA.beans.UrlBean;
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
@ContextConfiguration(classes = {TestConfig.class, UrlsRepository.class})
@ActiveProfiles("test")
@Transactional
public class UrlsRepositoryTest {

    @Autowired
    UrlsRepository urlsRepository;

    @Test
    public void getAllUrlsTest() {
        UrlBean ub1 = new UrlBean();
        ub1.setUrl("url1");
        ub1.setGuild("guild1");
        ub1.setUrl_id(1);

        UrlBean ub2 = new UrlBean();
        ub2.setUrl("url2");
        ub2.setGuild("guild2");
        ub1.setUrl_id(2);

        UrlBean ub3 = new UrlBean();
        ub3.setUrl("url3");
        ub3.setGuild("guild3");
        ub1.setUrl_id(3);

        urlsRepository.putUrl(ub1);
        urlsRepository.putUrl(ub2);
        urlsRepository.putUrl(ub3);

        List<UrlBean> ubList = urlsRepository.getAllUrls();

        assertEquals(3, ubList.size());
        assertEquals("url1", ubList.get(0).getUrl());
        assertEquals("guild1", ubList.get(0).getGuild());

        assertEquals("url2", ubList.get(1).getUrl());
        assertEquals("guild2", ubList.get(1).getGuild());

        assertEquals("url3", ubList.get(2).getUrl());
        assertEquals("guild3", ubList.get(2).getGuild());

        // should be sorted in ascending order by url_id
        assertTrue(ubList.get(0).getUrl_id() < ubList.get(1).getUrl_id());
        assertTrue(ubList.get(1).getUrl_id() < ubList.get(2).getUrl_id());
    }

    @Test
    public void getGuildUrlsTest() {
        UrlBean ub1 = new UrlBean();
        ub1.setUrl("url1");
        ub1.setGuild("guild");
        ub1.setUrl_id(1);

        UrlBean ub2 = new UrlBean();
        ub2.setUrl("url2");
        ub2.setGuild("guild");
        ub1.setUrl_id(2);

        UrlBean ub3 = new UrlBean();
        ub3.setUrl("url3");
        ub3.setGuild("notguild");
        ub1.setUrl_id(3);

        urlsRepository.putUrl(ub1);
        urlsRepository.putUrl(ub2);
        urlsRepository.putUrl(ub3);

        List<UrlBean> ubList = urlsRepository.getGuildUrls("guild");

        assertEquals(2, ubList.size());
        assertEquals("url1", ubList.get(0).getUrl());
        assertEquals("guild", ubList.get(0).getGuild());

        assertEquals("url2", ubList.get(1).getUrl());
        assertEquals("guild", ubList.get(1).getGuild());

        assertTrue(ubList.get(0).getUrl_id() < ubList.get(1).getUrl_id());
    }

    @Test
    public void removeUrlTest() {
        UrlBean ub1 = new UrlBean();
        ub1.setUrl("url1");
        ub1.setGuild("guild");
        ub1.setUrl_id(1);

        urlsRepository.putUrl(ub1);

        List<UrlBean> ubList = urlsRepository.getGuildUrls("guild");
        assertEquals(1, ubList.size());

        urlsRepository.removeUrl("guild", "url1");

        ubList = urlsRepository.getGuildUrls("guild");
        assertTrue(ubList.isEmpty());
    }

    @Test
    public void removeGuildUrlsTest() {
        UrlBean ub1 = new UrlBean();
        ub1.setUrl("url1");
        ub1.setGuild("guild");
        ub1.setUrl_id(1);

        UrlBean ub2 = new UrlBean();
        ub2.setUrl("url2");
        ub2.setGuild("guild");
        ub1.setUrl_id(2);

        UrlBean ub3 = new UrlBean();
        ub3.setUrl("url3");
        ub3.setGuild("notguild");
        ub1.setUrl_id(3);

        urlsRepository.putUrl(ub1);
        urlsRepository.putUrl(ub2);
        urlsRepository.putUrl(ub3);

        List<UrlBean> ubList = urlsRepository.getAllUrls();
        assertEquals(3, ubList.size());

        urlsRepository.removeGuildUrls("guild");
        ubList = urlsRepository.getAllUrls();

        assertEquals(1, ubList.size());
        assertEquals("url3", ubList.get(0).getUrl());
        assertEquals("notguild", ubList.get(0).getGuild());
    }

}
