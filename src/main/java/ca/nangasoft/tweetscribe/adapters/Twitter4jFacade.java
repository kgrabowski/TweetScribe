package ca.nangasoft.tweetscribe.adapters;

import ca.nangasoft.tweetscribe.domain.TweetConsumer;
import ca.nangasoft.tweetscribe.domain.TweetFormatter;
import ca.nangasoft.tweetscribe.domain.TwitterFacade;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Twitter4jFacade implements TwitterFacade {
    private final TwitterStreamFactory streamFactory = new TwitterStreamFactory();

    public static FileOutputStream createFile(String fileName) {
        try {
            return new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to create file " + fileName, e);
        }
    }

    @Override
    public void subscribeToStream(String topicName, TweetConsumer consumer) {
        System.out.println("Starting Twitter stream for topic " + topicName);
        TwitterStream stream = streamFactory.getInstance();
        FileOutputStream outputStream = Twitter4jFacade.createFile(topicName + ".txt");
        stream.addListener(new TopicListener(new TweetFormatter(), outputStream));
        stream.filter(new FilterQuery().language("en").track(topicName));
    }
}
