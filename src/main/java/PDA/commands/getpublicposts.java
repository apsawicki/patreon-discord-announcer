package PDA.commands;

import PDA.beans.*;

/**
 * getpublicposts discord bot command.
 *
 * Responsibilities:
 *
 * 1) Will print out all public posts associated with the discord server
 *
 */

public class getpublicposts extends GenericBotCommand {

	// Prints out all public posts unique to the discord that issued the command
	@Override
	public void execute() {
		embed.setTitle("Public Posts: ", null); // TODO: ask for user input on specific patreon public posts

		bot.send(embed.build(), guild);

		for (PostBean post : posts.getGuildPosts(guild.getId())) {
			if (post.isPrivate())
				continue;

			embed.setTitle(post.getTitle(), null);
			embed.setDescription(post.getContent());
			embed.setFooter(post.getPublishDate(), post.getUrl());
			bot.send(embed.build(), guild);
		}
	}
}
