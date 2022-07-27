package PDA.discord;

// import PDA.DiscordBotJoin;

import PDA.PDA;
import PDA.beans.*;
import PDA.jpa.*;
import PDA.utils.*;
import ch.qos.logback.classic.Logger;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.channel.concrete.*;
import net.dv8tion.jda.internal.entities.*;
import net.dv8tion.jda.internal.managers.channel.concrete.*;
import org.json.simple.parser.*;
import org.json.simple.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.apache.commons.io.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.*;
import java.nio.charset.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

//

	public DiscordBot() throws LoginException, InterruptedException {

		this.log = (Logger) LoggerFactory.getLogger(this.getClass().getName());
		this.log.info("Initializing Discord Bot...");

//		this.embedMap = new HashMap<>();
//		this.channels = new HashMap<>();

		//! setup JDA bot
		setupJDA(parseToken());


		//! setup all the embeds for each guild the bot is in
//		setupEmbeds();

		//! setup all text channels per discord server
//		setupTextChannels();

		//! setup the container for private and public posts so it's not null
//		setupPosts();
		this.log.info("Finished Discord Bot Initialization");
	}
//
//	/**
//	 * Sets the title for the unique discord server's {@link EmbedBuilder} in embedMap
//	 *
//	 * @param title holds the String value of the title we want to set
//	 * @param url holds the url for the hyperlink we attach to the title
//	 * @param id is the reference to the guild that we want to set the title for
//	 */
//	public void setTitle(String title, String url, Guild id) {
//		this.embedMap.put(id, this.embedMap.get(id).setTitle(title, url));
//	}
//
//	/**
//	 * Adds the channel output for the discord bot on a specific discord server
//	 *
//	 * @param channelId holds the String value of the TextChannel we want to add for the unique discord server's {@link TextChannel} in channels
//	 * @param id is the reference to the guild that we want to add the channel for
//	 */
//	public void addChannel(String channelId, Guild id) {
//
//		// JDA has another overloaded getTextChannelById() method that uses a long instead of a String
//		// for some reason it will print a NumberFormatException error when using the String version even though it works
//		try {
//			this.channels.put(id, jda.getTextChannelById(channelId));
//		} catch (NumberFormatException ignored) {
//		}
//	}
//
//	/**
//	 * Sets the description for the unique discord server's {@link EmbedBuilder} in embedMap
//	 *
//	 * @param description holds the String value of the description we want to set
//	 * @param id is the reference to the guild that we want to set the description for
//	 */
//	public void setDescription(String description, Guild id) {
//		this.embedMap.put(id, this.embedMap.get(id).setDescription(description));
//	}
//
//	/**
//	 * Adds a field for the unique discord server's {@link EmbedBuilder} in embedMap
//	 *
//	 * @param title holds the String value of the title for the field we want to add
//	 * @param value holds the String value of the description for the field we want to add
//	 * @param id is the reference to the guild that we want to add the field for
//	 */
//	public void addField(String title, String value, Guild id) {
//		this.embedMap.put(id, this.embedMap.get(id).addField(title, value, true));
//	}
//
//	/**
//	 * Sets the color for the unique discord server's {@link EmbedBuilder} in embedMap
//	 *
//	 * @param color holds the color we want to set the embed color to
//	 * @param id is the reference to the guild that we want to add the color for
//	 */
//	public void setColor(Color color, Guild id) {
//		this.embedMap.put(id, this.embedMap.get(id).setColor(color));
//	}
//
//	/**
//	 * Sets the footer for the unique discord server's {@link EmbedBuilder} in embedMap
//	 *
//	 * @param text holds the String value of the description for the footer we want to add
//	 * @param userUrl holds the String value of the hyperlink we want to add onto the text(description) of the footer
//	 * @param id is the reference to the guild that we want to set the footer for
//	 */
//	public void setFooter(String text, String userUrl, Guild id) {
//		this.embedMap.put(id, this.embedMap.get(id).setFooter(text, userUrl));
//	}
//
	// sending embed to Guild "id" discord server

	public synchronized void send(MessageEmbed embed, Guild id) {
		ChannelBean cb = channels.getChannel(id.getId());

		TextChannel textChannel = new TextChannelImpl(Long.parseLong(cb.getChannelid()), (GuildImpl) id);
		textChannel.sendMessageEmbeds(embed).queue();
	}

	// sending text to Guild "id" discord server
	public synchronized void send(String text, Guild id) { // sending text
		ChannelBean cb = channels.getChannel(id.getId());

		TextChannel textChannel = new TextChannelImpl(Long.parseLong(cb.getChannelid()), (GuildImpl) id);
		textChannel.sendMessage(text);
	}
//
//	/**
//	 * Will add a discord server and an empty {@link EmbedBuilder} object to the embedMap
//	 *
//	 * @param id is the reference to the guild that we want to add to the embedMap
//	 */
//	public void addGuild(Guild id) {
//		this.embedMap.put(id, this.embedMap.getOrDefault(id, new EmbedBuilder()));
//	}
//
//	/**
//	 * Returns the set of all discord servers we store, for testing purposes
//	 *
//	 * @return the set of all discord servers we store
//	 */
//	public Set<Guild> getAllGuilds() {
//		return this.embedMap.keySet();
//	}
//
//	/**
//	 * Returns the {@link JDA} object being used to instantiate the {@link DiscordBot} object, for testing purposes
//	 *
//	 * @return the jda object stored in {@link DiscordBot}
//	 */
//	public JDA getJDA() {
//		return this.jda;
//	}
//
	/**
	 * Initializes the jda object to allow us to talk with discord
	 *
	 * @param token holds the String token value that we need in order to initialize a discord bot through JDA
	 * @throws InterruptedException in case a thread is interrupted
	 */
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

//	/**
//	 * Initializes an empty {@link EmbedBuilder} object for each discord server
//	 */
//	private void setupEmbeds() {
//		for (Guild guild : this.jda.getGuilds()) {
//			if (!this.embedMap.containsKey(guild)) {
//				addGuild(guild);
//				PDA.guildSet.add(guild);
//			}
//		}
//	}

//	/**
//	 * Initializes a TextChannel for each discord server by finding the first TextChannel in the discord server and adding it to the channels HashMap
//	 */
//	private void setupTextChannels() {
//		for (Guild guild : PDA.guildSet) {
//			List<TextChannel> chanList = new LinkedList<>(); //guild.getTextChannelsByName("testing", true);
//
//			// if we can't find a "testing" then output to the first channel we find
//			if (chanList.isEmpty()) {
//				chanList = guild.getTextChannels();
//			}
//
//			if (!chanList.isEmpty())
//				addChannel(chanList.get(0).getId(), guild);
//		}
//	}

//	/**
//	 * Initializes an empty {@link LinkedList} container for {@link PostCard} objects and adds it to the postCards HashMap in PDA.java
//	 */
//	private void setupPosts() {
//		for (Guild guild : PDA.guildSet) {
//			LinkedList<PostCard> temp = new LinkedList<>();
//
//			// TODO: when we save the posts we will take it from there instead
//			PDA.postCards.put(guild, temp);
//		}
//	}

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
}