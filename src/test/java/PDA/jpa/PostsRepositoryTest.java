package PDA.jpa;

import java.util.List;

import PDA.beans.PostBean;
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
@ContextConfiguration(classes = {TestConfig.class, PostsRepository.class})
@ActiveProfiles("test")
@Transactional
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @Test
    public void getPostTest() {
        PostBean pb = new PostBean();
        pb.setUrl("url");
        pb.setGuild("guild");
        pb.setContent("content");
        pb.setPost_id(1);

        postsRepository.putPost(pb);

        PostBean pbRet = postsRepository.getPost("guild", "url");
        assertEquals("guild", pbRet.getGuild());
        assertEquals("url", pbRet.getUrl());
        assertEquals("content", pbRet.getContent());
    }

    @Test
    public void getAllPostsTest() {
        PostBean pb1 = new PostBean();
        pb1.setUrl("url");
        pb1.setGuild("guild");
        pb1.setContent("content");
        pb1.setPost_id(1);

        PostBean pb2 = new PostBean();
        pb2.setUrl("url");
        pb2.setGuild("guild1");
        pb2.setContent("content");
        pb2.setPost_id(2);

        PostBean pb3 = new PostBean();
        pb3.setUrl("url");
        pb3.setGuild("guild2");
        pb3.setContent("content");
        pb3.setPost_id(3);

        postsRepository.putPost(pb1);
        postsRepository.putPost(pb2);
        postsRepository.putPost(pb3);

        List<PostBean> pbList = postsRepository.getAllPosts();

        assertEquals(3, pbList.size());
        assertTrue(pbList.get(0).getPost_id() < pbList.get(1).getPost_id());
        assertTrue(pbList.get(1).getPost_id() < pbList.get(2).getPost_id());
    }

    @Test
    public void getGuildPostsTest() {
        PostBean pb1 = new PostBean();
        pb1.setUrl("url");
        pb1.setGuild("guild");
        pb1.setContent("content");
        pb1.setPost_id(1);

        PostBean pb2 = new PostBean();
        pb2.setUrl("url");
        pb2.setGuild("guild");
        pb2.setContent("content");
        pb2.setPost_id(2);

        PostBean pb3 = new PostBean();
        pb3.setUrl("url");
        pb3.setGuild("guild2");
        pb3.setContent("content");
        pb3.setPost_id(3);

        postsRepository.putPost(pb1);
        postsRepository.putPost(pb2);
        postsRepository.putPost(pb3);

        List<PostBean> pbList = postsRepository.getGuildPosts("guild");

        assertEquals(2,  pbList.size());
        assertEquals("guild", pbList.get(0).getGuild());
        assertTrue(pbList.get(0).getPost_id() < pbList.get(1).getPost_id());
    }

    @Test
    public void removePostTest() {
        PostBean pb1 = new PostBean();
        pb1.setUrl("url");
        pb1.setGuild("guild");
        pb1.setContent("content");
        pb1.setPost_id(1);

        postsRepository.putPost(pb1);

        List<PostBean> pbList = postsRepository.getAllPosts();
        assertEquals(1, pbList.size());

        postsRepository.removePost("guild", "url");

        pbList = postsRepository.getAllPosts();
        assertTrue(pbList.isEmpty());
    }

    @Test
    public void removeGuildPostsTest() {
        PostBean pb1 = new PostBean();
        pb1.setUrl("url");
        pb1.setGuild("guild");
        pb1.setContent("content");
        pb1.setPost_id(1);

        PostBean pb2 = new PostBean();
        pb2.setUrl("url");
        pb2.setGuild("guild");
        pb2.setContent("content");
        pb2.setPost_id(2);

        PostBean pb3 = new PostBean();
        pb3.setUrl("url");
        pb3.setGuild("guild2");
        pb3.setContent("content");
        pb3.setPost_id(3);

        postsRepository.putPost(pb1);
        postsRepository.putPost(pb2);
        postsRepository.putPost(pb3);

        List<PostBean> pbList = postsRepository.getAllPosts();
        assertEquals(3,  pbList.size());

        postsRepository.removeGuildPosts("guild");

        pbList = postsRepository.getAllPosts();
        assertEquals(1,  pbList.size());
    }
}
