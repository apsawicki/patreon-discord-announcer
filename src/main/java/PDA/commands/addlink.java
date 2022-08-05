package PDA.commands;

import PDA.beans.*;
import PDA.jpa.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * addlink discord bot command.
 * <p>
 * Responsibilities:
 * <p>
 * 1) Check if a link was provided
 * 2) Check if the link is already in the list of links
 * 3) Add the guild to the list of guilds associated with the particular link
 */
// TODO: after add link, add all current posts to db without posting
@Component
public class addlink extends AbstractCommand {

    @Autowired
    Urls urls;

	// Adds a patreonUrl link to the HashMap patreonUrls mapped to the guild that issued the command
	@Override
	public void execute() {
		if (args.length <= 1) {
			send("No link provided");
		} else {

            UrlBean ub = new UrlBean();
            ub.setGuild(guild.getId());
            ub.setUrl(args[1]);

            try{ // TODO: throw exceptions when accessing database
                urls.putUrl(ub);
                send(args[1] + " has been added to the list of links");
            }
            catch (Exception e){
                send(args[1] + " was either not added or already in the list of links");
            }
		}
	}
}
