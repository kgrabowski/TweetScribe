package ca.nangasoft.tweetscribe;

import twitter4j.*;

public class Main {
    public static void main(String[] args) {
        TwitterStreamFactory streamFactory = new TwitterStreamFactory();
        subscribeToTopic("music", streamFactory);
    }

    private static void subscribeToTopic(String topic, TwitterStreamFactory streamFactory) {
        TwitterStream stream = streamFactory.getInstance();
        stream.addListener(new TopicListener(topic));
        stream.filter(new FilterQuery().language("en").track(topic));
    }

    private static class TopicListener extends StatusAdapter {
        private final String topic;

        private TopicListener(String topic) {
            this.topic = topic;
        }

        @Override
        public void onStatus(Status status) {
            System.out.printf("[%s] [%s] %s: %s%n%n", topic, status.getLang(), status.getUser().getName(), status.getText());
        }

        @Override
        public void onException(Exception ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
