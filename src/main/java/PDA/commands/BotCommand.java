package PDA.commands;

import PDA.discord.DiscordBot;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Guild;

/**
 * Interface for the bot commands
 */

public interface BotCommand {

	// execute the bot command
	EmbedBuilder execute();

	// set the arguments for the bot command
	void setArgs(String[] args);

	// set the discord server id for the bot command
	void setGuildID(Guild guild);
}
