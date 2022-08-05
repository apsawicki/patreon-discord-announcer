package PDA;

import PDA.selenium.PatreonThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerManager {

    @Autowired
    PatreonThread patreonThread;

    @Scheduled(fixedRate = 1000000)
    public void patreonThread() {
        patreonThread.run();
    }

    // TODO: below will go in test class (need to figure out how to test with database)

//    @Autowired
//    private Channels channels;
//
//    @Autowired
//    private Urls urls;
//
//    @Autowired
//    private Posts posts;
//
//    // 1000 = 1sec
//    @Scheduled(fixedRate = 1000000)  // TODO: fixedRate should change to delayRate eventually
//    public void channelTest() {
//        System.out.println("channel");
//
//        List<ChannelBean> cbList = channels.getAllChannels();
//        System.out.println("cbList: " + cbList.size());
//
//        for (ChannelBean channelBean : cbList){
//            System.out.println("cb guild: " + channelBean.getGuild());
//            System.out.println("cb ch_id: " + channelBean.getChannel_id());
//            System.out.println("cb ch: " + channelBean.getChannelid() + "\n");
//        }
//
//    }
//
//    @Scheduled(fixedRate = 1000000)
//    public void urlTest() {
//        System.out.println("url");
//
//        List<UrlBean> ubList = urls.getAllUrls();
//        System.out.println("ubList: " + ubList.size());
//
//        for (UrlBean urlBean : ubList){
//            System.out.println("ub url: " + urlBean.getUrl());
//            System.out.println("ub guild: " + urlBean.getGuild());
//            System.out.println("ub url_id: " + urlBean.getUrl_id() + "\n");
//        }
//
//    }
//
//    @Scheduled(fixedRate = 1000000)
//    public void postTest() {
//        System.out.println("post");
//
//        List<PostBean> pbList = posts.getAllPosts();
//        System.out.println("pbList: " + pbList.size());
//
//        for (PostBean postBean : pbList){
//            System.out.println("pb url: " + postBean.getUrl());
//            System.out.println("pb guild: " + postBean.getGuild());
//            System.out.println("pb url_id: " + postBean.getContent() + "\n");
//        }
//    }


}
