package ca.nangasoft.tweetscribe.domain;

public interface TwitterFacade {
    void subscribeToStream(String topicName, TweetConsumer consumer);
}
