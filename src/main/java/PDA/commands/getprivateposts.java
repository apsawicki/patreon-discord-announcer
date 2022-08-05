package PDA.commands;

import PDA.beans.*;
import PDA.jpa.Posts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * getprivateposts discord bot command.
 *
 * Responsibilities:
 *
 * 1) Will print out all private posts associated with the discord server
 *
 */

@Component
public class getprivateposts extends AbstractCommand {

	@Autowired
	Posts posts;

	// Prints out all private posts unique to the discord that issued the command
	@Override
	public void execute() {
		embed.setTitle("Private Posts: ", null); // TODO: ask for user input on specific patreon private posts
		send(embed);

		for (PostBean post : posts.getGuildPosts(guild.getId())) {
			if (!post.isPrivate())
				continue;

			embed.setTitle(post.getTitle(), null);
			embed.setDescription(post.getContent());
			embed.setFooter(post.getPublishDate(), post.getUrl());
			send(embed);
		}
	}
}
