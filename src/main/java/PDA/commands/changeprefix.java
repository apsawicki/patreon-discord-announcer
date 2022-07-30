package PDA.commands;

import PDA.discord.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class changeprefix extends GenericBotCommand {

    @Autowired
    private DiscordBot bot;

//    @Override
    public void execute() {
        if (args.length <= 1){
            bot.send("no prefix provided", guild);
        }
        else if (args[0].length() > 10){
            bot.send("no prefix with length greater than 10 characters allowed", guild);
        }

        bot.prefix = args[1];
        bot.send("\"" + args[1] + "\"" + " has been set as the command prefix", guild);
    }
}
