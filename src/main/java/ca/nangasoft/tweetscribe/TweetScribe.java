package ca.nangasoft.tweetscribe;

import java.util.List;

import static java.util.Collections.emptyList;

public class TweetScribe {
    private final TwitterFacade twitter;
    private final TopicPrompt prompt;

    public TweetScribe(TwitterFacade twitter, TopicPrompt prompt) {
        this.twitter = twitter;
        this.prompt = prompt;
    }

    public void start() {
        List<String> topics = promptForTopics();
        topics.forEach(twitter::subscribeToStream);
    }

    private List<String> promptForTopics() {
        List<String> topics = emptyList();
        while (topics.isEmpty()) {
            topics = prompt.askUserForTopics();
        }
        return topics;
    }
}
