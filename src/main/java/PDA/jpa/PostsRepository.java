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
    public PostBean getPost(PostBean pb) {
        String sql = "select * from posts where guild = :guild and url = :url";

        Query q = em.createNativeQuery(sql, PostBean.class);
        q.setParameter("guild", pb.getGuild());
        q.setParameter("url", pb.getUrl());

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
        em.persist(pb);
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
}
