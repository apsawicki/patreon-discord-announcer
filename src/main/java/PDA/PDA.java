package PDA;

import PDA.discord.DiscordBot;
import PDA.patreon.PatreonThread;
import PDA.patreon.PostCard;
import PDA.utils.JSONHelper;
import ch.qos.logback.classic.Logger;
import net.dv8tion.jda.api.entities.Guild;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Patreon Discord Announcer startup implementation.
 * <p>
 * Responsibilities:
 * <p>
 * 1) Parse config file for discord bot token
 * 2) Declare and instantiate DiscordBot object
 * 3) Declare and instantiate PatreonThread object
 */

public class PDA {

//	public static HashMap<String, ArrayList<Guild>> patreonUrls = new HashMap<>();
//	public static String prefix = "!";
//	public static Set<Guild> guildSet = new HashSet<>();
//	public static HashMap<Guild, LinkedList<PostCard>> postCards = new HashMap<>();
//	static String discordToken = "";
//	static Logger log;
//
//	/**
//	 * Main method that will declare and initialize the {@link DiscordBot} and {@link PatreonThread} objects
//	 *
//	 * @param args holds command line arguments
//	 * @throws InterruptedException in case a thread is interrupted
//	 * @throws LoginException       in case the login for the discord bot token doesn't work
//	 */
//	public static void main(String[] args) throws InterruptedException, LoginException {
//		log = (Logger) LoggerFactory.getLogger("PDA");
//
//		log.info("Reading config.json...");
//		discordToken = parseConfig();
//
//		if (discordToken.isEmpty()) {
//			log.error("config.json does not contain a 'TOKEN' key.");
//			System.exit(1);
//		}
//
//		DiscordBot bot = new DiscordBot(discordToken);
//
//		log.info("Reading posts.json...");
//		JSONHelper.parseSavedData(bot, "posts.json");
//		log.info("Loaded {} URLs and their guild posts.", postCards.size());
//
//		log.info("Starting Selenium thread...");
//		PatreonThread testThread = new PatreonThread(bot);
//
//		testThread.start();
//		testThread.join();
//
//		log.error("An error has occurred.  Stopping...");
//		bot.getJDA().shutdown();
//		System.exit(1);
//	}
//
//	/**
//	 * parseConfig method that will read through the config.json file and get the discord token needed for initialization
//	 *
//	 * @return the token found in the config.json file
//	 */
//	private static String parseConfig() {
//		JSONObject configJson = JSONHelper.parseJSONFile("config.json");
//
//		if (configJson == null) {
//			log.error("An error occurred while reading config.json");
//			System.exit(1);
//		}
//
//		try {
//			return (String) configJson.get("TOKEN");
//		} catch (JSONException e) {
//			return "";
//		}
//	}
//
//
//
//	/**
//	 * Will check if a given url is a valid link and return a boolean respectively
//	 *
//	 * @param url is the String value of the url we want to check is valid or not
//	 * @return true if the url is valid, false otherwise
//	 */
//	public static boolean urlValid(String url){
//
//		//Regex for a valid URL
//		//The URL must start with either http or https and
//		//    then followed by :// and
//		//    then it must contain www. and
//		//    then followed by subdomain of length (2, 256) and
//		//    last part contains top level domain like .com, .org etc
//		String reg = "((http|https)://)(www.)?"
//				+ "[a-zA-Z0-9@:%._\\+~#?&//=]"
//				+ "{2,256}\\.[a-z]"
//				+ "{2,6}\\b([-a-zA-Z0-9@:%"
//				+ "._\\+~#?&//=]*)";
//
//		//compiles the regex
//		Pattern p = Pattern.compile(reg);
//
//		//If string is empty return false
//		if(url == null){
//			return false;
//		}
//
//		//find a match on the string
//		Matcher m = p.matcher(url);
//
//		//return the string if matched the regex
//		return m.matches();
//
//	}
}