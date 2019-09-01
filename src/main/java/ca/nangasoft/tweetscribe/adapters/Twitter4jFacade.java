package ca.nangasoft.tweetscribe.adapters;

import ca.nangasoft.tweetscribe.domain.TweetConsumer;
import ca.nangasoft.tweetscribe.domain.TwitterFacade;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStreamFactory;

public class Twitter4jFacade implements TwitterFacade {
    private final TwitterStreamFactory streamFactory = new TwitterStreamFactory();

    @Override
    public void subscribeToStream(String topicName, TweetConsumer consumer) {
        System.out.println("Starting Twitter stream for topic " + topicName);
        streamFactory.getInstance()
                .addListener(new TweetConsumerAdapter(consumer))
                .filter(new FilterQuery().language("en").track(topicName));
    }

    private static class TweetConsumerAdapter extends StatusAdapter {
        private final TweetConsumer tweetConsumer;

        private TweetConsumerAdapter(TweetConsumer tweetConsumer) {
            this.tweetConsumer = tweetConsumer;
        }

        @Override
        public void onStatus(Status status) {
            tweetConsumer.onTweet(status.getUser().getScreenName(), status.getText());
        }
    }
}
