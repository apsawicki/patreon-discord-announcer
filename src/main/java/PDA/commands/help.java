package PDA.commands;

import PDA.discord.DiscordBot;

/**
 * help discord bot command.
 *
 * Responsibilities:
 *
 * 1) Will print out a list of each discord bot command available through the PDA
 *
 */

public class help extends GenericBotCommand {


	// Prints out the list of commands available to be used by a user
	@Override
	public void execute() {
		embed.setTitle("PDA Commands", null);
		embed.setDescription("/help\n/setchannel\n/addlink\n/removelink\n/showlinks\n/getpublicposts\n/getprivateposts\n/changeprefix");

		bot.send(embed.build(), guild);
	}
}
