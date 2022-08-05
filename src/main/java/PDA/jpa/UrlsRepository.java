package PDA.jpa;

import PDA.beans.UrlBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UrlsRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<UrlBean> getAllUrls() { // good
        String sql = "select * from urls";

        Query q = em.createNativeQuery(sql, UrlBean.class);

        return q.getResultList();
    }

    @Transactional(readOnly = true) // TODO: eventually put urls in json rather than multiple rows in database
    public List<UrlBean> getGuildUrls(String guild) { // good
        String sql = "select * from urls where guild = :guild";

        Query q = em.createNativeQuery(sql, UrlBean.class);
        q.setParameter("guild", guild);

        return q.getResultList();
    }

    @Transactional // TODO: check if url/guild pair is already in the db and don't put if it is
    public void putUrl(UrlBean ub) { // good
        em.persist(ub);
    }

    @Transactional
    public void removeUrl(String guild, String url) { // good

        // TODO: if not in db or already in database -- throw exception

        String sql = "delete from urls where guild = :guild and url = :url";

        Query q = em.createNativeQuery(sql, UrlBean.class);
        q.setParameter("url", url);
        q.setParameter("guild", guild);

        q.executeUpdate();
    }

    @Transactional
    public void removeGuildUrls(String guild) {
        String sql = "delete from urls where guild = :guild";

        Query q = em.createNativeQuery(sql, UrlBean.class);
        q.setParameter("guild", guild);

        q.executeUpdate();
    }

    // TODO: exists() helper class (maybe not helper in the future?)
}
