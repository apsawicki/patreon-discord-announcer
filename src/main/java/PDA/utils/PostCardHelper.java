package PDA.utils;

import PDA.patreon.PostCard;
import ch.qos.logback.classic.Logger;
import net.dv8tion.jda.api.entities.Guild;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class PostCardHelper {

//    private static final Logger log = (Logger) LoggerFactory.getLogger("PostCardHelper");
//
//    /**
//     * Will save the announced post to the posts.json file
//     *
//     * @param patreonUrl is the patreonUrl that the post was created from
//     * @param guild is the reference to the guild that we want to save the posts for
//     * @param postCard is the object holding all the information from the post on patreon
//     */
//    public static void saveAnnouncedPostCard(String patreonUrl, Guild guild, PostCard postCard) {
//        JSONObject savedJson = JSONHelper.parseJSONFile("posts.json");
//
//        if (savedJson == null) {
//            log.error("An error occurred while reading posts.json");
//            System.exit(1);
//        }
//
//        if (savedJson.has(patreonUrl)) { // * This Patreon URL already exists
//            JSONObject guildIds = savedJson.getJSONObject(patreonUrl);
//
//            saveInExistingPatreonURL(patreonUrl, guildIds, guild, postCard);
//        } else { // * New Patreon URL
//            JSONObject newGuildId = new JSONObject();
//            JSONObject newPost = new JSONObject();
//
//            // Put the post card details into the new post JSON Object
//            newPost.put(postCard.getUrl(), JSONHelper.createPostJSONObject(postCard));
//
//            // Put the new post JSON Object into the new guild ID JSON Object
//            newGuildId.put(guild.getId(), newPost);
//
//            // Put the new guild ID JSON Object into the master JSON file
//            savedJson.put(patreonUrl, newGuildId);
//        }
//
//        // * Finally, save the new JSON file into posts.json
//        try {
//            FileWriter file = new FileWriter("posts.json");
//            file.write(savedJson.toString());
//            file.flush();
//            file.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * Saves any new post data into the {@link JSONObject} provided to the function
//     *
//     * @param patreonUrl holds the patreon link that was used to get the {@link PostCard} post data
//     * @param guildIds is the {@link JSONObject} that holds all the current data
//     * @param guild is the reference to the discord server that the {@link PostCard} is unique to
//     * @param postCard is the information saved from a patreon link's post saved into an object
//     */
//    private static void saveInExistingPatreonURL(String patreonUrl, JSONObject guildIds, Guild guild, PostCard postCard) {
//        if (guildIds.has(guild.getId())) { // * This guild already contains posts from this Patreon URL
//            JSONObject postUrls = guildIds.getJSONObject(guild.getId());
//
//            if (postUrls.has(postCard.getUrl())) { // ! This guild already stored this post URL
//                log.warn("The post tried storing a post that already exists.  Guild: '{}' -- URL: '{}'", guild.getId(), patreonUrl);
//            } else { // * This is a new post URL
//                postUrls.put(postCard.getUrl(), JSONHelper.createPostJSONObject(postCard));
//            }
//        } else { // * New guild ID for this Patreon URL
//            JSONObject patreonUrlObject = new JSONObject();
//
//            patreonUrlObject.put(postCard.getUrl(), JSONHelper.createPostJSONObject(postCard));
//            guildIds.put(guild.getId(), patreonUrlObject);
//        }
//    }
//
//    /**
//     * Allows the ability to add a {@link PostCard} object to the postCards HashMap that holds all sent posts to dis
//     *
//     * @param postCard holds a reference to a {@link PostCard} object to be added to the postCards HashMap
//     * @param guild    holds a reference to a {@link Guild} object that will be used to know which guild to attach the {@link PostCard} object to
//     */
//    public static void addPostCard(PostCard postCard, Guild guild) {
//        LinkedList<PostCard> cards = postCards.get(guild);
//
//        if (!cards.contains(postCard))
//            cards.add(postCard);
//
//        postCards.put(guild, cards);
//        log.info("Saved post URL '{}' to guild '{}'", postCard.getUrl(), guild.getId());
//    }
}
