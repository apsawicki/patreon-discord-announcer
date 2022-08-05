package PDA.commands;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;

/**
 * Implementation of the abstract class for all bot commands
 * <p>
 * Responsibilities:
 * <p>
 * 1) Initialize the arguments per bot command
 * 2) Initialize the discord server id per bot command
 */

public abstract class AbstractCommand {

    protected MessageChannel channel;

    // args holds the arguments given to the bot command
    protected String[] args;

    // guild holds the reference to the discord server that we will output the command's output to
    protected Guild guild;

    // embed builder so each command can create its own embed
    protected EmbedBuilder embed = new EmbedBuilder();


    //set the arguments for the command
    public void setArgs(String[] args) {
        this.args = args;
    }

    // set the discord server id for the command
    public void setGuildID(Guild guild) {
        this.guild = guild;
    }

    // set the discord server channel for the command
    public void setChannel(MessageChannel channel) {
        this.channel = channel;
    }

    public void send(EmbedBuilder embed) {
        channel.sendMessageEmbeds(embed.build()).queue();
    }

    public void send(String message) {
        channel.sendMessage(message).queue();
    }

    public abstract void execute();
}
