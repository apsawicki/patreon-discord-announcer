package PDA.commands;

import PDA.beans.GuildBean;
import PDA.jpa.Guilds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class changeprefix extends AbstractCommand {

    @Autowired
    Guilds guilds;

    @Override
    public void execute() {

        if (args.length <= 1){
            send("no prefix provided");
            return;
        }
        else if (args[1].length() > 10){
            send("no prefix with length greater than 10 characters allowed");
            return;
        }

        GuildBean gb = new GuildBean();
        gb.setGuild(guild.getId());
        gb.setPrefix(args[1]);

        guilds.updatePrefixByGuild(gb);
        send("\"" + args[1] + "\"" + " has been set as the command prefix");
    }
}
