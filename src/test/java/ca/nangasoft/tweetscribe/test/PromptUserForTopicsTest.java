package ca.nangasoft.tweetscribe.test;

import ca.nangasoft.tweetscribe.TopicPrompt;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PromptUserForTopicsTest {
    private final OutputStream stdout = new ByteArrayOutputStream();

    @Test
    void enterSingleTopic() {
        TopicPrompt prompt = promptWithPredefinedInput("oscars");
        List<String> topics = prompt.askUserForTopics();
        assertThat(topics).containsExactly("oscars");
    }

    @Test
    void enterMultipleTopics() {
        TopicPrompt prompt = promptWithPredefinedInput("cats chefs celebrities");
        List<String> topics = prompt.askUserForTopics();
        assertThat(topics).containsExactly(
                "cats",
                "chefs",
                "celebrities"
        );
    }

    @Test
    void promptsUserToProvideAnswer() {
        TopicPrompt prompt = promptWithPredefinedInput("irrelevant");
        prompt.askUserForTopics();
        assertThat(stdout.toString()).startsWith("Choose topics to subscribe to:");
    }

    private TopicPrompt promptWithPredefinedInput(String userInput) {
        return new TopicPrompt(new ByteArrayInputStream(userInput.getBytes()), stdout);
    }
}
