package PDA.commands;

import PDA.jpa.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * removelink discord bot command.
 * <p>
 * Responsibilities:
 * <p>
 * 1) Check if a link was provided
 * 2) Check if the link is not in the list of links
 * 3) Remove the guild from the list of guilds associated with the particular link
 */

@Component
public class removelink extends AbstractCommand {

	@Autowired
	Urls urls;

	// Removes the discord server from the list of discord servers assigned to the patreon link given by the user
	@Override
	public void execute() {
		if (args.length <= 1) {
			send("No link provided");
		} else {

			try { // TODO: throw exceptions when accessing database
				urls.removeUrl(guild.getId(), args[1]);
				send(args[1] + " has been removed from the list of links");
			}
			catch (Exception e) {
				send(args[1] + " was either not in the list of links or was not removed");
			}
		}
	}
}
