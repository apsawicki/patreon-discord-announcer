package PDA.jpa;

import PDA.beans.PostBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PostsRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public PostBean getPost(String guild, String url) {
        String sql = "select * from posts where guild = :guild and url = :url";

        Query q = em.createNativeQuery(sql, PostBean.class);
        q.setParameter("guild", guild);
        q.setParameter("url", url);

        List<PostBean> pbList = q.getResultList();

        if (pbList.isEmpty()) {
            return new PostBean();
        }
        // TODO: if no posts, then return empty postbean
        return (PostBean) q.getResultList().get(0);
    }

    @Transactional(readOnly = true)
    public List<PostBean> getAllPosts() { // good
        String sql = "select * from posts";

        Query q = em.createNativeQuery(sql, PostBean.class);

        return q.getResultList();
    }

    @Transactional(readOnly = true)
    public List<PostBean> getGuildPosts(String guild) { // good
        String sql = "select * from posts where guild = :guild";

        Query q = em.createNativeQuery(sql, PostBean.class);
        q.setParameter("guild", guild);

        return q.getResultList();
    }

    @Transactional
    public void putPost(PostBean pb) { // good
        String sql = "insert into posts (published, title, url, content, isprivate, guild) values (:published, :title, :url, :content, :isprivate, :guild)";

        Query q = em.createNativeQuery(sql, PostBean.class);
        q.setParameter("published", pb.getPublishDate());
        q.setParameter("title", pb.getTitle());
        q.setParameter("url", pb.getUrl());
        q.setParameter("content", pb.getContent());
        q.setParameter("isprivate", pb.isPrivate());
        q.setParameter("guild", pb.getGuild());

        q.executeUpdate();
    }

    @Transactional
    public void removeGuildPosts(String guild) {
        String sql = "delete from posts where guild = :guild";

        Query q = em.createNativeQuery(sql, PostBean.class);
        q.setParameter("guild", guild);

        q.executeUpdate();
    }

    @Transactional
    public void removePost(String guild, String url) { // good
        String sql = "delete from posts where guild = :guild and url = :url";

        Query q = em.createNativeQuery(sql, PostBean.class);
        q.setParameter("guild", guild);
        q.setParameter("url", url);

        q.executeUpdate();
    }

    @Transactional
    public List<PostBean> getExistingPosts(String guild, String urlList) {
        String sql = "SELECT * FROM posts WHERE guild = :guild AND url IN " + urlList;

        Query q = em.createNativeQuery(sql, PostBean.class);
        q.setParameter("guild", guild);

        return q.getResultList();
    }
}
