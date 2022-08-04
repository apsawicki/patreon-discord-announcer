package PDA.commands;

import PDA.beans.*;

import java.util.*;

/**
 * showlinks discord bot command.
 * <p>
 * Responsibilities:
 * <p>
 * 1) Check if the user has any patreon links currently being tracked for their discord server
 * 2) Send an embed holding the list of patreon links that the discord server is currently tracking
 */

public class showlinks extends GenericBotCommand {

	// Prints out the list of all links added to the discord server that issued the command
	@Override
	public void execute() {
		StringBuilder linkContainer = new StringBuilder("");

		List<UrlBean> ubList = urls.getGuildUrls(guild.getId());

		for (UrlBean url : ubList){
			linkContainer.append(url.getUrl()).append("\n");
		}

//		for (String patreonUrl : PDA.patreonUrls.keySet()) {
//			if (PDA.patreonUrls.get(patreonUrl).contains(guild)) {
//				linkContainer.append(patreonUrl).append("\n");
//			}
//		}
		if (linkContainer.length() == 0)
			linkContainer.append("no links added");

		embed.setTitle("Links", "");
		embed.setDescription(linkContainer.toString());

		bot.send(embed.build(), guild);
	}
}
