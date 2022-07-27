package PDA.jpa;

import PDA.beans.PostBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Posts {

    @Autowired
    PostsRepository postsRepository;

    public List<PostBean> getAllPosts() {
        return postsRepository.getAllPosts();
    }

    public List<PostBean> getGuildPosts(String guild) {
        return postsRepository.getGuildPosts(guild);
    }

    public void putPost(PostBean pb) {
        postsRepository.putPost(pb);
    }

    public void removePost(String guild, String url) {
        postsRepository.removePost(guild, url);
    }


}
