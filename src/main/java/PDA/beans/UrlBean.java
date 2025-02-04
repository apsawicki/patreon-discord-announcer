package PDA.beans;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name="urls")
public class UrlBean {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="url_id")
    protected int url_id;

    @Column(name="guild")
    protected String guild;

    @Column(name="url")
    protected String url;

    public int getUrl_id() {
        return url_id;
    }

    public void setUrl_id(int url_id) {
        this.url_id = url_id;
    }

    public String getGuild() {
        return guild;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
