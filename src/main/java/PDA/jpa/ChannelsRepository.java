package PDA.jpa;

import PDA.beans.ChannelBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Transactional
    public void putChannel(ChannelBean cb) {
        em.persist(cb);
    }



}
