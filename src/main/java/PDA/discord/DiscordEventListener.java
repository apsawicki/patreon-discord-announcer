package PDA.discord;

import PDA.commands.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

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
public class DiscordEventListener extends ListenerAdapter {

	private String prefix = "!"; // TODO: add prefix to db to make server specific prefix
	CommandFactory commandFactory;

	@Autowired
	public DiscordEventListener(CommandFactory commandFactory) {
		this.commandFactory = commandFactory;
	}

	// Runs everytime a message is sent in a discord server, will initialize a {@link BotCommand} object if the message contains a command
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		if (event.getAuthor().isBot()) { // TODO: fix getting message received event from self
			return;
		}

		System.out.println("message received");
		String[] args = event.getMessage().getContentRaw().split("\\s+"); // changing each word in a message to arguments separated by spaces

		int prefixLength = prefix.length();

		// Return if args array is invalid or if the string given is too short to be a command(prefix + 3 characters)
		if (args.length == 0 || args[0].length() < prefixLength + 3)
			return;

		AbstractCommand command;

		try {
			command = commandFactory.getCommand("PDA.commands." + args[0].substring(prefixLength).toLowerCase());
		} catch (Exception e) {
			// Ignore any exceptions as we don't care if someone puts an invalid command name
			e.printStackTrace();
			return;
		}


		// prepare and execute the command
		command.setGuildID(event.getGuild());
		command.setArgs(args);
		command.setChannel(event.getChannel());
		command.execute();
		System.out.println("finished");
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
//	}
}

