package ca.nangasoft.tweetscribe;

import ca.nangasoft.tweetscribe.adapters.Twitter4jFacade;

public class Main {
    public static void main(String[] args) {
        TweetScribe tweetScribe = createTweetScribe();
        tweetScribe.start();
    }

    private static TweetScribe createTweetScribe() {
        return new TweetScribe(
                new Twitter4jFacade(),
                new TopicPrompt(System.in, System.out)
        );
    }
}
