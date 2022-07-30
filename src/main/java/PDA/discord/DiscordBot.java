package PDA.discord;

// import PDA.DiscordBotJoin;

import PDA.beans.*;
import PDA.commands.*;
import PDA.jpa.*;
import ch.qos.logback.classic.Logger;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.*;
import net.dv8tion.jda.api.hooks.*;
import net.dv8tion.jda.internal.*;
import net.dv8tion.jda.internal.entities.*;
import org.json.simple.parser.*;
import org.json.simple.JSONObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.apache.commons.io.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.nio.charset.*;

/**
 * Implementation of the JDA discord bot API.
 * <p>
 * Responsibilities:
 * <p>
 * 1) Initialize the discord bot with a given token
 * 2) Initialize containers for embeds, text channels, and posts per discord server
 * 3) Provide a layer of abstraction between the rest of the program and the JDA discord bot API to allow for sending messages and embeds to a server
 */

@Component
@Scope("singleton")
public class DiscordBot {

    public String prefix = "!";
    public final Logger log;
	private JDA jda;

	@Autowired
	Channels channels;



	public DiscordBot() throws LoginException, InterruptedException {

		this.log = (Logger) LoggerFactory.getLogger(this.getClass().getName());
		this.log.info("Initializing Discord Bot...");

		//! setup JDA bot
		setupJDA(parseToken());

		this.log.info("Finished Discord Bot Initialization");
	}

	public synchronized void send(MessageEmbed embed, Guild id) {
		ChannelBean cb = channels.getChannel(id.getId());

		getChannel(cb.getChannelid()).sendMessageEmbeds(embed).queue();
		this.log.info("Text channel embed sent");
	}

	public synchronized void send(String text, Guild id) {
		ChannelBean cb = channels.getChannel(id.getId());

		getChannel(cb.getChannelid()).sendMessage(text).queue();
		this.log.info("Text channel string sent");
	}

	public Guild getGuild(String id) {
		return getGuild(Long.parseLong(id));
	}

	public Guild getGuild(Long id) {
		return jda.getGuildById(id);
	}

	public TextChannel getChannel(String id ) {
		return getChannel(Long.parseLong(id));
	}

	public TextChannel getChannel(Long id) {
		return jda.getTextChannelById(id);
	}

	public void confirmChannels() {
		this.log.info("Confirming channels");
		for (Guild g : jda.getGuilds()){
			if (channels.getChannel(g.getId()).getChannelid() == null) {
				ChannelBean cb = new ChannelBean();
				cb.setChannelid(g.getTextChannels().get(0).getId());
				cb.setGuild(g.getId());
				this.log.info("channel: '{}'", cb.getChannelid());
				channels.putChannel(cb);
			}
		}
	}

	// Initializes the jda object to allow us to talk with discord
	private void setupJDA(String token) throws InterruptedException {
		try {
			this.jda = JDABuilder.createDefault(token).build();
			this.jda.getPresence().setActivity(Activity.playing("Type " + prefix + "help for commands"));
		} catch (LoginException e) {
			this.log.error("The given Discord Bot token '{}' is invalid!", token);
			System.exit(1);
		}
		this.jda.awaitReady();
		this.jda.addEventListener(new EventListener());
	}

	private String parseToken() {
		String jsonText;

		try {
			jsonText = IOUtils.toString(new FileInputStream("config.json"), StandardCharsets.UTF_8);
		}
		catch (IOException e) {
			log.error("An error occurred while reading config.json", e);
			return null;
		}

		JSONObject jsonToken;
		try {
			jsonToken = (JSONObject) new JSONParser().parse(jsonText);
		}
		catch (ParseException e) {
			log.error("An error occurred while parsing JSON String into JSONObject", e);
			return null;
		}


		return (String) jsonToken.get("TOKEN");
	}

	private class EventListener extends ListenerAdapter {

//		@Autowired help help;
//		@Autowired showlinks showlinks;

		@Autowired
		private ApplicationContext applicationContext;

		@Override
		public void onMessageReceived(MessageReceivedEvent event) {

			if (event.getAuthor().getId().equals(jda.getSelfUser().getId())){ // TODO: bot is receiving events from its own messages for some reason, need to fix
				DiscordBot.this.log.warn("message received from self");
				return;
			}

			DiscordBot.this.log.info("Message received");


			String[] args = event.getMessage().getContentRaw().split("\\s+"); // changing each word in a message to arguments separated by spaces

			int prefixLength = DiscordBot.this.prefix.length();

			// Return if args array is invalid or if the string given is too short to be a command(prefix + 3 characters)
			if (args.length == 0 || args[0].length() < prefixLength + 4) {
				DiscordBot.this.log.error("Failed length check");
				return;
			}

			TestGenericCommand command;
			DiscordBot.this.log.info("BotCommand 'command' declared");

			// use Class.forName to generate a new class with the given arguments, cutting out the prefix and case sensitivity.
			try {
				DiscordBot.this.log.info("Instantiating command");
				Class<?> clazz = Class.forName("PDA.commands." + args[0].substring(prefixLength).toLowerCase());
				command = (TestGenericCommand) clazz.getDeclaredConstructor().newInstance();
				
			} catch (Exception e) {
				DiscordBot.this.log.error("Command cannot be instantiated");
				DiscordBot.this.log.error("error", e);
				return; // Ignore any exceptions as we don't care if someone puts an invalid command name
			}

			// command variable should never be null as we will never reach here if it is null

			command.setGuildID(event.getGuild());
			DiscordBot.this.log.info("GuildId set");

			command.setArgs(args);

			send(command.execute().build(), event.getGuild());
			DiscordBot.this.log.info("Command output sent to discord");
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
}