package PDA.jpa;

import PDA.beans.ChannelBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ChannelsRepository{

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<ChannelBean> getAllChannels() {
        String sql = "select * from channels";

        Query q = em.createNativeQuery(sql, ChannelBean.class);

        return q.getResultList();
    }

    public ChannelBean getChannel(String guild) {
        String sql = "select * from channels where guild = :guild";

        Query q = em.createNativeQuery(sql, ChannelBean.class);
        q.setParameter("guild", guild);

        List<ChannelBean> cbList = q.getResultList();

        return cbList.isEmpty() ? new ChannelBean() : cbList.get(0);
    }

    @Transactional
    public void putChannel(ChannelBean cb) { // TODO: check if guild/record is already in database then either update or do nothing
        em.persist(cb);
    }

    @Transactional
    public void updateChannel(ChannelBean cb) {
        String sql = "update channels set channelid = :channelid where guild = :guild";
        String channelid = cb.getChannelid();
        String guild = cb.getGuild();

        em.createNativeQuery(sql, ChannelBean.class)
                .setParameter("channelid", channelid)
                .setParameter("guild", guild)
                .executeUpdate();
    }

    // removes row from channels table with corresponding guild
    @Transactional
    public void removeChannel(String guild) {
        String sql = "delete from channels where guild = :guild";

        em.createNativeQuery(sql, ChannelBean.class)
                .setParameter("guild", guild)
                .executeUpdate();
    }

    // TODO: exists() helper class (maybe not helper in the future?)
}
