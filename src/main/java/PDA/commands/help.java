package PDA.commands;

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


	// Prints out the list of commands available to be used by a user
	@Override
	public void execute() {
		// TODO: dynamically get all commands
		embed.setTitle("PDA Commands", null);
		embed.setDescription("/help\n/setchannel\n/addlink\n/removelink\n/showlinks\n/getpublicposts\n/getprivateposts\n/changeprefix");

		send(embed);
	}
}
