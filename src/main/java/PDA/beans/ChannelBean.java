package PDA.beans;

import javax.persistence.*;

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

    @Column(name="url_id")
    protected int url_id;       // db url index id

    @Column(name="post_id")
    protected int post_id;      // db post index id

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

    public int getUrl_id() {
        return url_id;
    }

    public void setUrl_id(int url_id) {
        this.url_id = url_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
}
