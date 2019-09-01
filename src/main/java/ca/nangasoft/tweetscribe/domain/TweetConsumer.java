package ca.nangasoft.tweetscribe.domain;

public interface TweetConsumer {
    void onTweet(String screenName, String tweetText);
}
