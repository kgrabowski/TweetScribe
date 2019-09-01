package ca.nangasoft.tweetscribe.adapters;

import ca.nangasoft.tweetscribe.TweetFormatter;
import ca.nangasoft.tweetscribe.TwitterFacade;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Twitter4jFacade implements TwitterFacade {
    public static FileOutputStream createFile(String fileName) {
        try {
            return new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to create file " + fileName, e);
        }
    }

    @Override
    public void subscribeToStream(String topicName) {
        System.out.println("Starting Twitter stream for topic " + topicName);
        TwitterStreamFactory streamFactory = new TwitterStreamFactory();
        TwitterStream stream = streamFactory.getInstance();
        FileOutputStream outputStream = Twitter4jFacade.createFile(topicName + ".txt");
        stream.addListener(new TopicListener(new TweetFormatter(), outputStream));
        stream.filter(new FilterQuery().language("en").track(topicName));
    }
}
