package ca.nangasoft.tweetscribe.adapters;

import ca.nangasoft.tweetscribe.domain.TweetConsumer;
import ca.nangasoft.tweetscribe.domain.TweetConsumerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;

public class FileOutputTweetConsumerFactory implements TweetConsumerFactory {
    private final Path targetPath;

    public FileOutputTweetConsumerFactory(Path targetPath) {
        this.targetPath = targetPath;
    }

    @Override
    public TweetConsumer newConsumer(String topicName) {
        return new FileOutputTweetConsumer(createFile(topicName + ".txt"));
    }

    private FileOutputStream createFile(String fileName) {
        try {
            return new FileOutputStream(getTargetPath(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to create file " + fileName, e);
        }
    }

    private File getTargetPath(String fileName) {
        return targetPath.resolve(fileName).toFile();
    }

    private static class FileOutputTweetConsumer implements TweetConsumer {
        private final PrintWriter writer;

        private FileOutputTweetConsumer(FileOutputStream stream) {
            this.writer = new PrintWriter(stream, true);
        }

        @Override
        public void onTweet(String screenName, String tweetText) {
            writer.println(format(screenName, tweetText));
        }

        private String format(String screenName, String tweetText) {
            return String.format("@%s: %s", screenName, tweetText.replace("\n", "\n\t"));
        }
    }
}
