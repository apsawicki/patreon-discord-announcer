package PDA.jpa;

import PDA.beans.ChannelBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Channels {

   @Autowired ChannelsRepository channelsRepository;

    public List<ChannelBean> getAllChannels(){
        return channelsRepository.getAllChannels();
    }

    public String getChannel(){

        return null;
    }

    public void putChannel(){ // persist
        ChannelBean cb = new ChannelBean();
        cb.setChannelid("203894");
        cb.setGuild("302039480298");
        channelsRepository.putChannel(cb);
    }

    public void updateChannel(){ // merge

    }


}
