package ca.nangasoft.tweetscribe.domain;

import java.util.List;

import static java.util.Collections.emptyList;

public class TweetScribe {
    private final TwitterFacade twitter;
    private final TopicPrompt prompt;
    private final TweetConsumerFactory consumerFactory;

    public TweetScribe(TwitterFacade twitter, TopicPrompt prompt, TweetConsumerFactory consumerFactory) {
        this.twitter = twitter;
        this.prompt = prompt;
        this.consumerFactory = consumerFactory;
    }

    public void start() {
        List<String> topics = promptForTopics();
        topics.forEach(this::subscribeToTopic);
    }

    private List<String> promptForTopics() {
        List<String> topics = emptyList();
        while (topics.isEmpty()) {
            topics = prompt.askUserForTopics();
        }
        return topics;
    }

    private void subscribeToTopic(String topicName) {
        TweetConsumer consumer = consumerFactory.newConsumer(topicName);
        twitter.subscribeToStream(topicName, consumer);
    }
}
