package PDA.commands;

import PDA.discord.DiscordBot;

/**
 * setchannel discord bot command.
 * <p>
 * Responsibilities:
 * <p>
 * 1) Check if a text channel ID was provided
 * 2) Check if the link is already in the list of links
 * 3) Set the text channel where the discord bot outputs depending on what text channel ID was provided
 */

public class setchannel extends GenericBotCommand {

//	/**
//	 * Sets the text channel for the unique discord that issued the command
//	 *
//	 * @param bot holds the reference to the singular {@link DiscordBot} object
//	 */
//	@Override
//	public void execute(DiscordBot bot) {
//		if (args.length <= 1) {
//			bot.send("No link provided", guild);
//		} else {
//			bot.addChannel(args[1], guild);
//			bot.send(args[1] + " has been set as the bot output channel", guild);
//		}
//	}
}
