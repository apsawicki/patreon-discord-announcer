package PDA;

import PDA.beans.ChannelBean;
import PDA.jpa.Channels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SchedulerManager {

    @Autowired
    Channels channels;

    @Scheduled(fixedRate = 1000000) //1000 = 1second -- fixedRate should change to delayRate
    public void output(){
        System.out.println("hello");
        channels.putChannel();
        List<ChannelBean> cbList = channels.getAllChannels();

        System.out.println(cbList);

    }

}
