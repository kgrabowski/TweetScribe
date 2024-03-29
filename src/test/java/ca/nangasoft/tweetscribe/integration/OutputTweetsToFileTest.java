package ca.nangasoft.tweetscribe.integration;

import ca.nangasoft.tweetscribe.adapters.FileOutputTweetConsumerFactory;
import ca.nangasoft.tweetscribe.domain.TweetConsumer;
import ca.nangasoft.tweetscribe.testhelpers.TestWorkspace;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;

class OutputTweetsToFileTest {
    private final TestWorkspace workspace = new TestWorkspace();

    @BeforeEach
    void createTestWorkspace() {
        workspace.create();
    }

    @AfterEach
    void deleteTestWorkspace() {
        workspace.delete();
    }

    @Test
    void singleLineTweet() {
        FileOutputTweetConsumerFactory factory = new FileOutputTweetConsumerFactory(workspace.getRootPath());
        TweetConsumer consumer = factory.newConsumer("cupcakes");

        consumer.onTweet("pastry_chef", "Here's a recipe for delightful cupcakes");

        assertThat(linesOf(file("cupcakes.txt"))).containsExactly(
                "@pastry_chef: Here's a recipe for delightful cupcakes"
        );
    }

    @Test
    void multiLineTweets() {
        FileOutputTweetConsumerFactory factory = new FileOutputTweetConsumerFactory(workspace.getRootPath());
        TweetConsumer consumer = factory.newConsumer("weather");

        consumer.onTweet("weathernetwork", "Weather for tomorrow:\n30 degrees\nsun with clouds\n40% chance of rain");

        assertThat(linesOf(file("weather.txt"))).containsExactly(
                "@weathernetwork: Weather for tomorrow:",
                "\t30 degrees",
                "\tsun with clouds",
                "\t40% chance of rain"
        );
    }

    @Test
    void multipleTweets() {
        FileOutputTweetConsumerFactory factory = new FileOutputTweetConsumerFactory(workspace.getRootPath());
        TweetConsumer consumer = factory.newConsumer("music");

        consumer.onTweet("alice", "I love the new Beatles album!");
        consumer.onTweet("bob", "@alice...\nBeatles don't have any new albums...\nReally");
        consumer.onTweet("alice", "Blasphemy!");

        assertThat(linesOf(file("music.txt"))).containsExactly(
                "@alice: I love the new Beatles album!",
                "@bob: @alice...",
                "\tBeatles don't have any new albums...",
                "\tReally",
                "@alice: Blasphemy!"
        );
    }

    private File file(String fileName) {
        return workspace.getFile(fileName);
    }
}
