package PDA.utils;

import PDA.beans.PostBean;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class PostBeanHelper {

    public static PostBean createPostBean(WebElement postCard) {
        Boolean isPrivate = !getTagText(postCard, By.cssSelector("[data-tag='locked-rich-text-post']")).equals("N/A");
        String publishDate = getTagText(postCard, By.cssSelector("[data-tag='post-published-at']"));
        String title = getTagText(postCard, By.cssSelector("[data-tag='post-title']"));
        String content = getTagText(postCard, By.cssSelector("[data-tag='post-content']"));

        String urlContainer = "N/A";

        // If the title exists then set the URL to the title's anchor href link, otherwise it's also N/A
        if (!title.equals("N/A")) {
            try {
                urlContainer = postCard.findElement(By.cssSelector("[data-tag='post-title']")).findElement(By.tagName("a")).getAttribute("href");

                // If the post is private then clean the URL of any possible junk that may tarnish it and lead us to a login page
                if (isPrivate) {
                    int corruptLinkIndex = urlContainer.indexOf("https%3A%2F%2F");

                    if (corruptLinkIndex != -1) {
                        urlContainer = "https://" + urlContainer.substring(corruptLinkIndex + 14);
                        urlContainer = urlContainer.replace("%2Fposts%2F", "/posts/");
                    }
                }
            } catch (NoSuchElementException ignored) { /* Ignore the exception */ }
        }
        String url = urlContainer;

        PostBean pb = new PostBean();
        pb.setPrivate(isPrivate);
        pb.setPublishDate(publishDate);
        pb.setTitle(title);
        pb.setContent(content);
        pb.setUrl(url);

        return pb;
    }

    private static String getTagText(WebElement postCard, By selector) {
        try {
            return postCard.findElement(selector).getText();
        } catch (NoSuchElementException e) {
            return "N/A";
        }
    }
}
