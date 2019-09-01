package ca.nangasoft.tweetscribe.domain;

public interface TweetConsumerFactory {
    TweetConsumer newConsumer(String topicName);
}
