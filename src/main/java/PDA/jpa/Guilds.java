package PDA.jpa;

import PDA.beans.GuildBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Guilds {

   @Autowired
   GuildsRepository guildsRepository;

    public List<GuildBean> getAllGuilds() { // good
        return guildsRepository.getAllGuilds();
    }

    public GuildBean getGuild(String guild){ // good
        return guildsRepository.getGuild(guild);
    }

    public void putGuild(GuildBean gb){ // persist - good
        guildsRepository.putGuild(gb);
    }

    public void updateChannelByGuild(GuildBean gb){ // good
        guildsRepository.updateChannel(gb);
    }

    public void removeGuild(String guild) { // good
        guildsRepository.removeGuild(guild);
    }

    public void updatePrefixByGuild(GuildBean gb) {
        guildsRepository.updatePrefix(gb);
    }

    public List<GuildBean> getExistingGuilds(List<GuildBean> gbList) {
        StringBuilder guildList = new StringBuilder("(");

        // fence post problem
        guildList.append("'").append(gbList.get(0).getGuild()).append("'");
        for (int i = 1; i < gbList.size(); i++) {
            guildList.append(", '").append(gbList.get(i).getGuild()).append("'");
        }
        guildList.append(")");

        return guildsRepository.getExistingGuilds(guildList.toString());
    }
}
