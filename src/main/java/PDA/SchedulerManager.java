package PDA;

// TODO: get rid of wildcard imports everywhere (for some reason intelliJ setting didn't work)
import PDA.beans.*;
import PDA.jpa.*;
import org.apache.commons.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.*;
import java.util.List;

@Component
public class SchedulerManager {

    @Scheduled(fixedRate = 1000000)
    public void patreonThread() {


    }

    // TODO: below will go in test class (need to figure out how to test with database)

    @Autowired
    private Channels channels;

    @Autowired
    private Urls urls;

    @Autowired
    private Posts posts;

    // 1000 = 1sec
    @Scheduled(fixedRate = 1000000)  // TODO: fixedRate should change to delayRate eventually
    public void channelTest() {
        System.out.println("channel");

        List<ChannelBean> cbList = channels.getAllChannels();
        System.out.println("cbList: " + cbList.size());

        for (ChannelBean channelBean : cbList){
            System.out.println("cb guild: " + channelBean.getGuild());
            System.out.println("cb ch_id: " + channelBean.getChannel_id());
            System.out.println("cb ch: " + channelBean.getChannelid() + "\n");
        }

    }

    @Scheduled(fixedRate = 1000000)
    public void urlTest() {
        System.out.println("url");

        urls.removeUrl("340283948", "nope");

        List<UrlBean> ubList = urls.getAllUrls();
        System.out.println("ubList: " + ubList.size());

        for (UrlBean urlBean : ubList){
            System.out.println("ub url: " + urlBean.getUrl());
            System.out.println("ub guild: " + urlBean.getGuild());
            System.out.println("ub url_id: " + urlBean.getUrl_id() + "\n");
        }

    }

    @Scheduled(fixedRate = 1000000)
    public void postTest() {
        System.out.println("post");

        posts.removePost("3904823094", "my custom url");

        List<PostBean> pbList = posts.getAllPosts();
        System.out.println("pbList: " + pbList.size());

        for (PostBean postBean : pbList){
            System.out.println("pb url: " + postBean.getUrl());
            System.out.println("pb guild: " + postBean.getGuild());
            System.out.println("pb url_id: " + postBean.getContent() + "\n");
        }
    }


}
