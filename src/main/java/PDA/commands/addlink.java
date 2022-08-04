package PDA.commands;

import PDA.beans.*;

/**
 * addlink discord bot command.
 * <p>
 * Responsibilities:
 * <p>
 * 1) Check if a link was provided
 * 2) Check if the link is already in the list of links
 * 3) Add the guild to the list of guilds associated with the particular link
 */

public class addlink extends GenericBotCommand {

	// Adds a patreonUrl link to the HashMap patreonUrls mapped to the guild that issued the command
	@Override
	public void execute() {
		if (args.length <= 1) {
			bot.send("No link provided", guild);
		} else {

//			if (!PDA.urlValid(args[1])){
//				bot.send(args[1] + " is not a valid link", guild);
//				return;
//			}

//			ArrayList<Guild> guilds;

            UrlBean ub = new UrlBean();
            ub.setGuild(guild.getId());
            ub.setUrl(args[1]);

            try{ // TODO: throw exceptions when accessing database
                urls.putUrl(ub);
                bot.send(args[1] + " has been added to the list of links", guild);
            }
            catch (Exception e){
                bot.send(args[1] + " was either not added or already in the list of links", guild);
            }



//			if (PDA.patreonUrls.containsKey(args[1])) {
//				guilds = PDA.patreonUrls.get(args[1]);
//
//				if (guilds.contains(guild)) {
//					bot.send(args[1] + " is already in the list of links", guild);
//				} else {
//					guilds.add(guild);
//					bot.send(args[1] + " has been added to the list of links", guild);
//					PDA.patreonUrls.put(args[1], guilds);
//				}
//			} else {
//				guilds = new ArrayList<>();
//				guilds.add(guild);
//				PDA.patreonUrls.put(args[1], guilds);
//				bot.send(args[1] + " has been added to the list of links", guild);
//			}
		}
	}
}
