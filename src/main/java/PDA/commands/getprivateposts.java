package PDA.commands;

import PDA.beans.*;
import PDA.discord.*;
import PDA.jpa.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * getprivateposts discord bot command.
 *
 * Responsibilities:
 *
 * 1) Will print out all private posts associated with the discord server
 *
 */

@Component
public class getprivateposts extends GenericBotCommand {

	@Autowired
	private DiscordBot bot;

	@Autowired
	private Posts posts;

	// Prints out all private posts unique to the discord that issued the command
//	@Override
	public void execute() {
		embed.setTitle("Private Posts: ", null); // TODO: ask for user input on specific patreon private posts
		bot.send(embed.build(), guild);

		for (PostBean post : posts.getGuildPosts(guild.getId())) {
			if (!post.isPrivate())
				continue;

			embed.setTitle(post.getTitle(), null);
			embed.setDescription(post.getContent());
			embed.setFooter(post.getPublishDate(), post.getUrl());
			bot.send(embed.build(), guild);
		}
	}
}
