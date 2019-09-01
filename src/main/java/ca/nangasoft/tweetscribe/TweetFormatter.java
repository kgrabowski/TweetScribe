package ca.nangasoft.tweetscribe;

public class TweetFormatter {
    public String format(String screenName, String tweetText) {
        return String.format("@%s: %s", screenName, tweetText.replace("\n", "\n\t"));
    }
}
