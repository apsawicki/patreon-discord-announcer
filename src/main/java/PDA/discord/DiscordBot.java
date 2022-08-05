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


    public final Logger log;

	@Autowired
	private JDA jda;

	@Autowired
	Channels channels;


	public DiscordBot() {
		this.log = (Logger) LoggerFactory.getLogger(this.getClass().getName());
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
}