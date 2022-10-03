package PDA.jpa;

import PDA.beans.PostBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Posts {

    @Autowired
    PostsRepository postsRepository;

    public PostBean getPost(String guild, String url) {
        return postsRepository.getPost(guild, url);
    }

    public List<PostBean> getAllPosts() {
        return postsRepository.getAllPosts();
    }

    public List<PostBean> getGuildPosts(String guild) {
        return postsRepository.getGuildPosts(guild);
    }

    public void putPost(PostBean pb) {
        postsRepository.putPost(pb);
    }

    public void removeGuildPosts(String guild) { postsRepository.removeGuildPosts(guild);}

    public void removePost(String guild, String url) {
        postsRepository.removePost(guild, url);
    }

    public List<PostBean> getExistingPosts(String guild, List<PostBean> pbList) {
        StringBuilder urlList = new StringBuilder("(");

        // fence post problem
        urlList.append("'").append(pbList.get(0).getUrl()).append("'");
        for (int i = 1; i < pbList.size(); i++) {
            urlList.append(", '").append(pbList.get(i).getUrl()).append("'");
        }
        urlList.append(")");

        return postsRepository.getExistingPosts(guild, urlList.toString());
    }
}
