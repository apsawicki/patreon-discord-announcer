package PDA.commands;

import PDA.beans.*;
import PDA.jpa.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * showlinks discord bot command.
 * <p>
 * Responsibilities:
 * <p>
 * 1) Check if the user has any patreon links currently being tracked for their discord server
 * 2) Send an embed holding the list of patreon links that the discord server is currently tracking
 */

@Component
public class showlinks extends AbstractCommand {

	@Autowired
	Urls urls;

	// Prints out the list of all links added to the discord server that issued the command
	@Override
	public void execute() {
		StringBuilder linkContainer = new StringBuilder("");

		List<UrlBean> ubList = urls.getGuildUrls(guild.getId());

		for (UrlBean url : ubList){
			linkContainer.append(url.getUrl()).append("\n");
		}

		if (linkContainer.length() == 0)
			linkContainer.append("no links added");

		embed.setTitle("Links", "");
		embed.setDescription(linkContainer.toString());

		send(embed);
	}
}
