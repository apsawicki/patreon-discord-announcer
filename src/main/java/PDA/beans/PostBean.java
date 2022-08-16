package PDA.beans;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name="posts")
public class PostBean {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="post_id")
    protected int post_id;

    @Column(name="guild")
    protected String guild;

    @Column(name="published")
    protected String publishDate;

    @Column(name="title")
    protected String title;

    @Column(name="url")
    protected String url;

    @Column(name="content")
    protected String content;

    @Column(name="isPrivate")
    protected boolean isPrivate;


    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getGuild() {
        return guild;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    @Override
    public String toString() {
        return "PostBean{" +
                "post_id=" + post_id +
                ", guild='" + guild + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof PostBean)) {
            return false;
        }

        return (this.url.equals(((PostBean) o).getUrl()) && this.guild.equals(((PostBean) o).getGuild()));
    }
}
