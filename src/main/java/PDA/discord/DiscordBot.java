package PDA.discord;

// import PDA.DiscordBotJoin;

import PDA.beans.*;
import PDA.jpa.*;
import ch.qos.logback.classic.Logger;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.internal.entities.*;
import org.json.simple.parser.*;
import org.json.simple.JSONObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.apache.commons.io.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
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


	public DiscordBot() throws InterruptedException {

		this.log = (Logger) LoggerFactory.getLogger(this.getClass().getName());
		this.log.info("Initializing Discord Bot...");



		//! setup JDA bot
		setupJDA(parseToken());



		this.log.info("Finished Discord Bot Initialization");
	}

	// sending embed to Guild "id" discord server
	public synchronized void send(MessageEmbed embed, Guild guild) {
		send(embed, guild.getId());
	}

	public synchronized void send(MessageEmbed embed, String guild) {
		ChannelBean cb = channels.getChannel(guild);

		jda.getTextChannelById(cb.getChannelid()).sendMessageEmbeds(embed).queue();
	}

	// sending text to Guild "id" discord server
	public synchronized void send(String text, Guild guild) {
		send(text, guild.getId());
	}

	public synchronized void send(String text, String guild) { // sending text
		ChannelBean cb = channels.getChannel(guild);

		jda.getTextChannelById(cb.getChannelid()).sendMessage(text);
	}

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
		this.jda.addEventListener(new DiscordEventListener());
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
}