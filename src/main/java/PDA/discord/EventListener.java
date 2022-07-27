package PDA.discord;

import PDA.PDA;
import PDA.commands.*;
import PDA.discord.DiscordBot;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.LinkedList;

/**
 * Asynchronous listener that waits for events to happen in a discord server.
 *
 * Responsibilities:
 *
 * 1) Wait for MessageReceivedEvents that occur for every message sent in a discord server
 * 2) Parse the message sent and see if a command recognized by the program was sent and act accordingly
 * 3) Wait for GuildJoinEvents that occur everytime a bot is added to the server during runtime
 * 4) Set up the objects needed for the PDA when a bot is added during runtime
 *
 */

@Component
public class EventListener extends ListenerAdapter {

	@Autowired
	protected DiscordBot bot;



	// Runs everytime a message is sent in a discord server, will initialize a {@link BotCommand} object if the message contains a command

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		String[] args = event.getMessage().getContentRaw().split("\\s+"); // changing each word in a message to arguments separated by spaces

		int prefixLength = bot.prefix.length();

		// Return if args array is invalid or if the string given is too short to be a command(prefix + 3 characters)
		if (args.length == 0 || args[0].length() < prefixLength + 3)
			return;

		BotCommand command;

		// use Class.forName to generate a new class with the given arguments, cutting out the prefix and case sensitivity.
		try {
			Class<?> clazz = Class.forName("PDA.commands." + args[0].substring(prefixLength).toLowerCase());
			command = (BotCommand) clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			// Ignore any exceptions as we don't care if someone puts an invalid command name
			return;
//			e.printStackTrace();
		}

		// command variable should never be null as we will never reach here if it is null
		synchronized (bot){
			command.setGuildID(event.getGuild());
			command.setArgs(args);
			command.execute();
		}
	}
//
//	/**
//	 * Runs everytime the discord bot is added to a server during program runtime, will add the server information to allow for the program to run correctly
//	 *
//	 * @param event is the container for the message that was sent and all if it's information
//	 */
//	@Override
//	public void onGuildJoin(@NotNull GuildJoinEvent event) {
//		// Adding to guild list/set
//
//		bot.log.info("Added server '{}' to guild list.", event.getGuild().getName());
//		bot.addGuild(event.getGuild());
//		PDA.guildSet.add(event.getGuild());
//
//		// Adding channel
//		bot.addChannel(event.getGuild().getTextChannels().get(0).getId(), event.getGuild());
//
//		// Adding to private/public posts container
//		LinkedList<PostCard> temp = new LinkedList<>();
//		PDA.postCards.put(event.getGuild(), temp);
//		commandRan = true;
//	}

}
