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

import java.util.ArrayList;
import java.util.List;

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

		setupAddGuilds();
		setupRemoveGuilds();
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

	private void setupAddGuilds() {

		// all guilds connected to jda
		List<GuildBean> gbList = new ArrayList<>();
		for (Guild g : jda.getGuilds()) {
			GuildBean gb = new GuildBean();
			gb.setGuild(g.getId());
			gbList.add(gb);
		}

		// getting all guilds in the database that are connected to jda
		List<GuildBean> existingGuilds = guilds.getExistingGuilds(gbList);

		// then taking those guilds away from all the ones connected to jda to get the guilds that are connected to jda but are not in the database
		gbList.remove(existingGuilds);

		// adding jda guilds not in database to the database
		for (GuildBean gb : gbList) {
			Guild g = jda.getGuildById(gb.getGuild());

			gb.setPrefix("!");
			try {
				gb.setChannelid(g.getChannels().get(0).getId());
			}
			catch (Exception e) {
				gb.setChannelid(""); // after user creates a text channel they can use setchannel command
			}

			log.info("Adding guild \"{}\" to database", g.getName());
			guilds.putGuild(gb);
		}
	}

	private void setupRemoveGuilds() {

		// all guilds connected with jda
		List<GuildBean> gbList = new ArrayList<>();
		for (Guild g : jda.getGuilds()) {
			GuildBean gb = new GuildBean();
			gb.setGuild(g.getId());
			gbList.add(gb);
		}

		// getting every guild in database, then removing the ones connected to jda to get deprecated guilds in list
		List<GuildBean> deprecatedGuilds = guilds.getAllGuilds();
		deprecatedGuilds.remove(gbList);

		// removing deprecated guilds from database
		for (GuildBean gb : deprecatedGuilds) {
			guilds.removeGuild(gb.getGuild());
		}
	}
}