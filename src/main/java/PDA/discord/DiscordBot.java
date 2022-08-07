package PDA.discord;

// import PDA.DiscordBotJoin;

import PDA.beans.GuildBean;
import PDA.jpa.Guilds;
import ch.qos.logback.classic.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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


    private final Logger log;


	private JDA jda;
	private Guilds guilds;

	@Autowired
	public DiscordBot(JDA jda, Guilds guilds) { // TODO: check guilds currently on the jda -- handle
		log = (Logger) LoggerFactory.getLogger(this.getClass().getName());
		log.info("Finished Discord Bot Initialization");
		this.jda = jda;
		this.guilds = guilds;

		setupGuilds();
	}

	// sending embed to Guild "id" discord server
	public synchronized void send(MessageEmbed embed, Guild guild) {
		send(embed, guild.getId());
	}

	public synchronized void send(MessageEmbed embed, String guild) {
		GuildBean cb = guilds.getGuild(guild);

		jda.getTextChannelById(cb.getChannelid()).sendMessageEmbeds(embed).queue();
	}

	// sending text to Guild "id" discord server
	public synchronized void send(String text, Guild guild) {
		send(text, guild.getId());
	}

	public synchronized void send(String text, String guild) { // sending text
		GuildBean cb = guilds.getGuild(guild);

		jda.getTextChannelById(cb.getChannelid()).sendMessage(text);
	}

	private void setupGuilds() {
		for (Guild g : jda.getGuilds()) {
			System.out.println("hi: " + g.getName());
			System.out.println("response: " + guilds.getGuild(g.getId()).getGuild());
			if (guilds.getGuild(g.getId()).getGuild() == null) {
				GuildBean gb = new GuildBean();
				gb.setGuild(g.getId());
				gb.setPrefix("!");
				gb.setChannelid(g.getChannels().get(0).getId());

				guilds.putGuild(gb);
			}
		}
	}
}