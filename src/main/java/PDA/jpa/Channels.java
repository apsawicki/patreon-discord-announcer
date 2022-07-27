package PDA.jpa;

import PDA.beans.ChannelBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Channels {

   @Autowired
   ChannelsRepository channelsRepository;

    public List<ChannelBean> getAllChannels() { // good
        return channelsRepository.getAllChannels();
    }

    public ChannelBean getChannel(String guild){ // good
        return channelsRepository.getChannel(guild);
    }

    public void putChannel(ChannelBean cb){ // persist - good
        channelsRepository.putChannel(cb);
    }

    public void updateChannelByGuild(ChannelBean cb){ // good
        channelsRepository.updateChannel(cb);
    }

    public void removeChannel(String guild) { // good
        channelsRepository.removeChannel(guild);
    }
}
