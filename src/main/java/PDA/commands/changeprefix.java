package PDA.commands;

import PDA.DiscordBot;
import PDA.PDA;

public class changeprefix extends GenericBotCommand {


    @Override
    public void execute(DiscordBot bot) {
        if (args.length <= 1){
            bot.send("no prefix provided", guild);
        }
        else if (args[0].length() > 10){
            bot.send("no prefix with length greater than 10 characters allowed", guild);
        }

        PDA.prefix = args[1];
    }
}
