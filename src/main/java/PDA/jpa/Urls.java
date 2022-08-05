package PDA.jpa;

import PDA.beans.UrlBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.util.List;

@Component
public class Urls {

    @Autowired
    UrlsRepository urlsRepository;

    public List<UrlBean> getAllUrls() {
        return urlsRepository.getAllUrls();
    }

    public List<UrlBean> getGuildUrls(String guild) {
        return urlsRepository.getGuildUrls(guild);
    }

    public void putUrl(UrlBean ub) {
        urlsRepository.putUrl(ub);
    }

    public void removeUrl(String guild, String url) {
        urlsRepository.removeUrl(guild, url);
    }

    public void removeGuildUrls(String guild) { urlsRepository.removeGuildUrls(guild);}
}
