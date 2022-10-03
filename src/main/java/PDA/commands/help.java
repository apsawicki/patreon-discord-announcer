package PDA.commands;

import PDA.jpa.Guilds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * help discord bot command.
 *
 * Responsibilities:
 *
 * 1) Will print out a list of each discord bot command available through the PDA
 *
 */

@Component
public class help extends AbstractCommand {

	@Autowired
	Guilds guilds;

	// Prints out the list of commands available to be used by a user
	@Override
	public void execute() {
		String commands = "!help\n!setchannel\n!addlink\n!removelink\n!showlinks\n!getpublicposts\n!getprivateposts\n!changeprefix";
		String prefix = guilds.getGuild(guild.getId()).getPrefix();

		embed.setTitle("PDA Commands", null);
		embed.setDescription(commands.replaceAll("!", prefix));

		send(embed);
	}
}
