package PDA.patreon;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Container for all information found on a patreon post.
 * <p>
 * Responsibilities:
 * <p>
 * 1) Holds an individual post's information found on patreon
 * 2) Checks to see if the individual post is private or not
 * 3) Allows someone to compare posts to see if they are equal or not
 */

public class PostCard {

	private final String publishDate;
	private final String title;
	private final String url;
	private final String content;
	private final boolean isPrivate;


	 // Constructor initializes instance variables, checking if the post is private and setting the boolean respectively, gets and cleans up the url of the specific post.
	public PostCard(WebElement postCard) {
		this.isPrivate = !getTagText(postCard, By.cssSelector("[data-tag='locked-rich-text-post']")).equals("N/A");
		this.publishDate = getTagText(postCard, By.cssSelector("[data-tag='post-published-at']"));
		this.title = getTagText(postCard, By.cssSelector("[data-tag='post-title']"));
		this.content = getTagText(postCard, By.cssSelector("[data-tag='post-content']"));

		String urlContainer = "N/A";

		// If the title exists then set the URL to the title's anchor href link, otherwise it's also N/A
		if (!this.title.equals("N/A")) {
			try {
				urlContainer = postCard.findElement(By.cssSelector("[data-tag='post-title']")).findElement(By.tagName("a")).getAttribute("href");

				// If the post is private then clean the URL of any possible junk that may tarnish it and lead us to a login page
				if (this.isPrivate) {
					int corruptLinkIndex = urlContainer.indexOf("https%3A%2F%2F");

					if (corruptLinkIndex != -1) {
						urlContainer = "https://" + urlContainer.substring(corruptLinkIndex + 14);
						urlContainer = urlContainer.replace("%2Fposts%2F", "/posts/");
					}
				}
			} catch (NoSuchElementException ignored) { /* Ignore the exception */ }
		}

		this.url = urlContainer;
	}

	// Constructor for json-loaded posts
	public PostCard(String publishDate, String title, String url, String content, boolean isPrivate) {
		this.publishDate = publishDate;
		this.title = title;
		this.url = url;
		this.content = content;
		this.isPrivate = isPrivate;
	}

	public String getPublishDate() {
		return this.publishDate;
	}

	public String getTitle() {
		return this.title;
	}

	public String getUrl() {
		return this.url;
	}

	public String getContent() {
		return this.content;
	}

	public boolean isPrivate() {
		return this.isPrivate;
	}

	/**
	 * Gets the test of the given tag of a WebElement
	 *
	 * @param selector is the tag selector that is specified to find specific text
	 * @param postCard is the WebElement we are going to be parsing to find specified text
	 * @return String holding the text found, or "N/A" if no text was found
	 */
	private String getTagText(WebElement postCard, By selector) {
		try {
			return postCard.findElement(selector).getText();
		} catch (NoSuchElementException e) {
			return "N/A";
		}
	}


	@Override
	public boolean equals(Object o) {
		// If the object is being compared with itself then return true
		if (o == this)
			return true;

		// If the object is not a PostCard object then return false
		if (!(o instanceof PostCard))
			return false;

		// If the object and this PostCard have the same URL then return true
		return this.url.equals(((PostCard) o).getUrl());
	}


	@Override
	public String toString() {
		return "Title: " + this.getTitle()
				+ "\nPublish Date: " + this.getPublishDate()
				+ "\nURL: " + this.getUrl()
				+ "\nContent: " + this.getContent()
				+ "\nisPrivate: " + this.isPrivate();
	}
}
