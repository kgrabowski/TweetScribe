package ca.nangasoft.tweetscribe.test;

import ca.nangasoft.tweetscribe.TopicPrompt;
import ca.nangasoft.tweetscribe.TweetScribe;
import ca.nangasoft.tweetscribe.TwitterFacade;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.*;

class SubscribeToTweetStreamTest {
    private final TopicPrompt prompt = mock(TopicPrompt.class);
    private final TwitterFacade twitter = mock(TwitterFacade.class);

    private final TweetScribe tweetScribe = new TweetScribe(twitter, prompt);

    @Test
    void subscribeToSingleStream() {
        when(prompt.askUserForTopics()).thenReturn(asList("news"));

        tweetScribe.start();

        verify(twitter).subscribeToStream("news");
    }

    @Test
    void subscribeToMultipleStreams() {
        when(prompt.askUserForTopics()).thenReturn(asList("music", "cars", "soccer"));

        tweetScribe.start();

        verify(twitter).subscribeToStream("music");
        verify(twitter).subscribeToStream("cars");
        verify(twitter).subscribeToStream("soccer");
    }

    @Test
    void askUserRepeatedlyUntilAtLeastOneTopicProvided() {
        when(prompt.askUserForTopics())
                .thenReturn(emptyList())
                .thenReturn(emptyList())
                .thenReturn(asList("gaming"));

        tweetScribe.start();

        verify(twitter).subscribeToStream("gaming");
    }
}
