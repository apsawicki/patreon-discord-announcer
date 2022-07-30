package PDA.commands;

import PDA.beans.*;
import PDA.discord.*;
import PDA.jpa.*;
import net.dv8tion.jda.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

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
@Scope("prototype")
public class showlinks extends TestGenericCommand {

	@Autowired
	private Urls urls;

	// Prints out the list of all links added to the discord server that issued the command
	public EmbedBuilder execute() {
		System.out.println("1");
		StringBuilder linkContainer = new StringBuilder("");

		System.out.println("2");
		List<UrlBean> ubList = urls.getGuildUrls(guild.getId());

		System.out.println("3");
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

		return embed;
	}
}
