package PDA.commands;

import PDA.beans.*;
import PDA.jpa.Urls;
import PDA.selenium.PatreonSingleLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

@Component
public class addlink extends AbstractCommand {

    @Autowired
    ApplicationContext context;

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

                PatreonSingleLink patreonSingleLink = context.getBean("patreonSingleLink", PatreonSingleLink.class);
                patreonSingleLink.setup();
                patreonSingleLink.readPosts(guild, args[1]);

                send(args[1] + " has been successfully added to the list of links");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                send(args[1] + " was either not added or already in the list of links");
            }
		}
	}
}
