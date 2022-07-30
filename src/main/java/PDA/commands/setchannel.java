package PDA.commands;

import PDA.beans.*;
import PDA.discord.DiscordBot;
import PDA.jpa.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * setchannel discord bot command.
 * <p>
 * Responsibilities:
 * <p>
 * 1) Check if a text channel ID was provided
 * 2) Check if the link is already in the list of links
 * 3) Set the text channel where the discord bot outputs depending on what text channel ID was provided
 */

@Component
public class setchannel extends GenericBotCommand {

	@Autowired
	private DiscordBot bot;

	@Autowired
	private Channels channels;

	// Sets the text channel for the unique discord that issued the command
//	@Override
	public void execute() {
		if (args.length <= 1) {
			bot.send("No link provided", guild);
		} else {

			ChannelBean cb = new ChannelBean();
			cb.setGuild(guild.getId());
			cb.setChannelid(args[1]);

			channels.updateChannelByGuild(cb); // TODO: throw error based on if the channel is already set or somethign went wrong
			bot.send(args[1] + " has been set as the bot output channel", guild);
		}
	}
}
