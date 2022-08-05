package PDA.discord;

import PDA.beans.ChannelBean;
import PDA.commands.*;
import PDA.jpa.Channels;
import PDA.jpa.Posts;
import PDA.jpa.Urls;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
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

	private CommandFactory commandFactory;
	@Autowired private Channels channels;
	@Autowired private Posts posts;
	@Autowired private Urls urls;

	@Autowired // field injection autowiring on CommandFactory returns null instead of instantiated object
	public DiscordEventListener(CommandFactory commandFactory) {
		this.commandFactory = commandFactory;
	}

	// Runs everytime a message is sent in a discord server
	@EventListener
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

	// Runs everytime the discord bot is added to a server during program runtime
	@Override
	public void onGuildJoin(GuildJoinEvent event) {
		ChannelBean cb = new ChannelBean();
		cb.setGuild(event.getGuild().getId());
		cb.setChannelid(event.getGuild().getChannels().get(0).getId());

		channels.putChannel(cb);
	}

	// Runs everytime the discord bot leaves a server during runtime
	@Override
	public void onGuildLeave(GuildLeaveEvent event) {
		channels.removeChannel(event.getGuild().getId());
		posts.removeGuildPosts(event.getGuild().getId());
		urls.removeGuildUrls(event.getGuild().getId());
	}
}

