package ca.nangasoft.tweetscribe;

import ca.nangasoft.tweetscribe.adapters.FileOutputTweetConsumerFactory;
import ca.nangasoft.tweetscribe.adapters.Twitter4jFacade;
import ca.nangasoft.tweetscribe.domain.TopicPrompt;
import ca.nangasoft.tweetscribe.domain.TweetScribe;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        TweetScribe tweetScribe = createTweetScribe();
        tweetScribe.start();
    }

    private static TweetScribe createTweetScribe() {
        return new TweetScribe(
                new Twitter4jFacade(),
                new TopicPrompt(System.in, System.out),
                new FileOutputTweetConsumerFactory(Paths.get(System.getProperty("user.dir")))
        );
    }
}
