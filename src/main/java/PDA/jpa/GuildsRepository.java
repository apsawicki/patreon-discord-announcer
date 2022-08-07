package PDA.jpa;

import PDA.beans.GuildBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class GuildsRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<GuildBean> getAllChannels() {
        String sql = "select * from guilds";

        Query q = em.createNativeQuery(sql, GuildBean.class);

        return q.getResultList();
    }

    @Transactional
    public GuildBean getChannel(String guild) {
        String sql = "select * from guilds where guild = :guild";

        Query q = em.createNativeQuery(sql, GuildBean.class);
        q.setParameter("guild", guild);

        List<GuildBean> cbList = q.getResultList();

        return cbList.isEmpty() ? new GuildBean() : cbList.get(0);
    }

    @Transactional
    public void putGuild(GuildBean gb) { // TODO: check if guild/record is already in database then either update or do nothing
        String sql = "insert into guilds (guild, channelid, prefix) values (:guild, :channelid, :prefix)";
        String guild = gb.getGuild();
        String channelid = gb.getChannelid();
        String prefix = gb.getPrefix();

        Query q = em.createNativeQuery(sql, GuildBean.class);
        q.setParameter("guild", guild);
        q.setParameter("channelid", channelid);
        q.setParameter("prefix", prefix);

        q.executeUpdate();
    }

    @Transactional
    public void updateChannel(GuildBean gb) {
        String sql = "update guilds set channelid = :channelid where guild = :guild";
        String channelid = gb.getChannelid();
        String guild = gb.getGuild();

        Query q = em.createNativeQuery(sql, GuildBean.class);
        q.setParameter("channelid", channelid);
        q.setParameter("guild", guild);

        q.executeUpdate();
    }

    // removes row from channels table with corresponding guild
    @Transactional
    public void removeGuild(String guild) {
        String sql = "delete from guilds where guild = :guild";

        em.createNativeQuery(sql, GuildBean.class)
                .setParameter("guild", guild)
                .executeUpdate();
    }

    @Transactional
    public void updatePrefix(GuildBean gb) {
        String sql = "update guilds set prefix = :prefix where guild = :guild";
        String prefix = gb.getPrefix();
        String guild = gb.getGuild();

        Query q = em.createNativeQuery(sql, GuildBean.class);
        q.setParameter("prefix", prefix);
        q.setParameter("guild", guild);

        q.executeUpdate();
    }

    // TODO: exists() helper class (maybe not helper in the future?)
}
