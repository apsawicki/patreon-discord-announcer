package PDA.commands;

import PDA.beans.*;
import PDA.jpa.Channels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class setchannel extends AbstractCommand {

	@Autowired
	Channels channels;

	// Sets the text channel for the unique discord that issued the command
	@Override
	public void execute() {
		if (args.length <= 1) {
			send("No link provided");
		} else {

			ChannelBean cb = new ChannelBean();
			cb.setGuild(guild.getId());
			cb.setChannelid(args[1]);

			channels.updateChannelByGuild(cb); // TODO: throw error based on if the channel is already set or somethign went wrong
			send(args[1] + " has been set as the bot output channel");
		}
	}
}
