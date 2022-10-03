package PDA.beans;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name="guilds")
public class GuildBean {

    @Column(name="guild")
    protected String guild;     // discord server id

    @Column(name="channelid")
    protected String channelid; // text channel id

    @Column(name="prefix")
    protected String prefix; // discord server command prefix

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="guild_id")
    protected int guild_id;   // db channel index id


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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getGuild_id() {
        return guild_id;
    }

    public void setGuild_id(int guild_id) {
        this.guild_id = guild_id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof GuildBean)) {
            return false;
        }

        return this.guild.equals(((GuildBean) o).getGuild());
    }
}
