package ca.nangasoft.tweetscribe;

import twitter4j.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String topicName = promptForTopic();
        System.out.println("Starting Twitter stream for topic " + topicName);
        TwitterStreamFactory streamFactory = new TwitterStreamFactory();
        TwitterStream stream = streamFactory.getInstance();
        FileOutputStream outputStream = createFile(topicName + ".txt");
        stream.addListener(new TopicListener(new TweetFormatter(), outputStream));
        stream.filter(new FilterQuery().language("en").track(topicName));
    }

    private static FileOutputStream createFile(String fileName) {
        try {
            return new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to create file " + fileName, e);
        }
    }

    private static String promptForTopic() {
        System.out.println("Enter a topic name: ");

        try (Scanner scanner = new Scanner(System.in)) {
            return scanner.next();
        }
    }

    private static class TopicListener extends StatusAdapter {
        private final TweetFormatter formatter;
        private final PrintWriter writer;

        private TopicListener(TweetFormatter formatter, OutputStream stream) {
            this.formatter = formatter;
            this.writer = new PrintWriter(stream);
        }

        @Override
        public void onStatus(Status status) {
            String tweetText = formatter.format(status.getUser().getScreenName(), status.getText());
            writer.println(tweetText);
            writer.flush();
        }
    }
}
