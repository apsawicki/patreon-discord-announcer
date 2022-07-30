package PDA.commands;

import PDA.discord.*;
import PDA.jpa.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

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
public class removelink extends GenericBotCommand {

	@Autowired
	private DiscordBot bot;

	@Autowired
	private Urls urls;

	// Removes the discord server from the list of discord servers assigned to the patreon link given by the user
//	@Override
	public void execute() {
		if (args.length <= 1) {
			bot.send("No link provided", guild);
		} else {

			try { // TODO: throw exceptions when accessing database
				urls.removeUrl(guild.getId(), args[1]);
				bot.send(args[1] + " has been removed from the list of links", guild);
			}
			catch (Exception e) {
				bot.send(args[1] + " was either not in the list of links or was not removed", guild);
			}


//			ArrayList<Guild> guilds;
//
//			if ((guilds = PDA.patreonUrls.get(args[1])) == null) {
//				bot.send(args[1] + " is not in the list of links", guild);
//				return;
//			}
//
//			if (PDA.patreonUrls.containsKey(args[1]) && guilds.remove(guild)) {
//				PDA.patreonUrls.put(args[1], guilds);
//				bot.send(args[1] + " has been removed from the patreon link list", guild);
//			}
		}
	}
}
