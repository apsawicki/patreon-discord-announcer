package PDA.commands;

/**
 * getprivateposts discord bot command.
 *
 * Responsibilities:
 *
 * 1) Will print out all private posts associated with the discord server
 *
 */

public class getprivateposts extends GenericBotCommand {

	// Prints out all private posts unique to the discord that issued the command
	@Override
	public void execute() {
//		bot.setTitle("Private Posts:", null, guild);
//		bot.send(guild);
//
//		for (PostCard currentPostCard : PDA.postCards.get(guild)) {
//			if (!currentPostCard.isPrivate())
//				continue;
//
//			synchronized (bot){
//				bot.setTitle(currentPostCard.getTitle(), null, guild);
//				bot.setDescription(currentPostCard.getContent(), guild);
//				bot.setFooter(currentPostCard.getPublishDate(), currentPostCard.getUrl(), guild);
//				bot.send(guild);
//			}
//		}
	}
}
