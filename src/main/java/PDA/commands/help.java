package PDA.commands;

import PDA.discord.DiscordBot;
import net.dv8tion.jda.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

/**
 * help discord bot command.
 *
 * Responsibilities:
 *
 * 1) Will print out a list of each discord bot command available through the PDA
 *
 */

@Component
public class help extends TestGenericCommand {

	// Prints out the list of commands available to be used by a user
	public EmbedBuilder execute() {
		embed.setTitle("PDA Commands", null);
		embed.setDescription("/help\n/setchannel\n/addlink\n/removelink\n/showlinks\n/getpublicposts\n/getprivateposts\n/changeprefix");

		return embed;
	}
}
