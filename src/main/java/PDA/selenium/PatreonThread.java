package PDA.selenium;

import PDA.beans.GuildBean;
import PDA.beans.PostBean;
import PDA.beans.UrlBean;
import PDA.discord.DiscordBot;
import PDA.jpa.Guilds;
import PDA.jpa.Posts;
import PDA.jpa.Urls;
import PDA.utils.PostBeanHelper;

import net.dv8tion.jda.api.EmbedBuilder;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.util.List;

/**
 * Selenium web scraping implementation.
 * <p>
 * Responsibilities:
 * <p>
 * 1) Web scraping patreon website for posts
 * 2) Automatic Geetest captcha passing
 * 3) Storing posts into PostCard objects for later use
 * 4) Sending data to the DiscordBot object to be sent to a discord server
 */

@Component
public class PatreonThread extends PatreonScraper {

	@Autowired
	DiscordBot bot;

	@Autowired
	Urls urls;

	@Autowired
	Guilds guilds;

	@Autowired
	Posts posts;

	public void run() {
		// Initialize our waiting interface
		this.log.info("Setup complete.  Starting to scan.");

		for (GuildBean cb : guilds.getAllGuilds()) {
			String guild = cb.getGuild();
			this.log.info("Scanning current guild: '{}'", guild);

			for (UrlBean ub : urls.getGuildUrls(guild)) {
				String url = ub.getUrl();
				this.log.info("Scanning current url: '{}'", url);

				try {
					goToPatreonPage(driver, url);
				}
				catch (InvalidArgumentException e) {
					urls.removeUrl(guild, url);
					this.log.warn("URL '{}' was removed from the list of links from guild '{}'", url, guild);
					continue;
				}

				if (this.visibleElementFound(postCardSelector)) {
					this.sleep(4000);
				}

				this.log.info("Scanning all post cards.");
				List<WebElement> foundPostElements = driver.findElements(postCardSelector);

				for (WebElement ele : foundPostElements) {
					PostBean pb = PostBeanHelper.createPostBean(ele);
					pb.setGuild(guild);
					this.handlePost(guild, pb);
				}
			}
		}
		this.log.info("Run finished!");
	}

	// Checks if we have already announced this post, adds posts to container of posts if it is a new post. Then it calls announcePost(:PostCard, :Guild) to send the post to discord
	private void handlePost(String guild, PostBean pb) {
		if (posts.getPost(guild, pb.getUrl()).getGuild() == null) {
			posts.putPost(pb);
			this.announcePost(guild, pb);
		}
	}

	// Sends the data contained in postCard to the {@link DiscordBot} object and tells it to send the data to discord
	private void announcePost(String guild, PostBean postCard) {
		EmbedBuilder em = new EmbedBuilder();

		em.setTitle((postCard.isPrivate() ? "Private: " : "Public: ") + postCard.getTitle(), postCard.getUrl());
		em.setDescription(postCard.getContent());
		em.setFooter(postCard.getPublishDate(), null);
		em.setColor(postCard.isPrivate() ? Color.red : Color.green);
		bot.send(em.build(), guild);
	}
}