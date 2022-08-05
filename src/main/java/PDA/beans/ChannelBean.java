package PDA.beans;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name="channels")
public class ChannelBean {

    @Column(name="guild")
    protected String guild;     // discord server id

    @Column(name="channelid")
    protected String channelid; // text channel id

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="channel_id")
    protected int channel_id;   // db channel index id


    public String getGuild() {
        return guild;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }
}
