package ca.nangasoft.tweetscribe.test;

import ca.nangasoft.tweetscribe.domain.*;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.*;

class SubscribeToTweetStreamTest {
    private final TopicPrompt prompt = mock(TopicPrompt.class);
    private final TwitterFacade twitter = mock(TwitterFacade.class);
    private final TweetConsumerFactory consumerFactory = mock(TweetConsumerFactory.class);

    private final TweetScribe tweetScribe = new TweetScribe(twitter, prompt, consumerFactory);

    @Test
    void subscribeToSingleStream() {
        TweetConsumer newsConsumer = mock(TweetConsumer.class, "newsConsumer");

        when(prompt.askUserForTopics()).thenReturn(asList("news"));
        when(consumerFactory.newConsumer("news")).thenReturn(newsConsumer);

        tweetScribe.start();

        verify(twitter).subscribeToStream("news", newsConsumer);
    }

    @Test
    void subscribeToMultipleStreams() {
        TweetConsumer musicConsumer = mock(TweetConsumer.class, "musicConsumer");
        TweetConsumer carsConsumer = mock(TweetConsumer.class, "carsConsumer");
        TweetConsumer soccerConsumer = mock(TweetConsumer.class, "soccerConsumer");

        when(prompt.askUserForTopics()).thenReturn(asList("music", "cars", "soccer"));
        when(consumerFactory.newConsumer("music")).thenReturn(musicConsumer);
        when(consumerFactory.newConsumer("cars")).thenReturn(carsConsumer);
        when(consumerFactory.newConsumer("soccer")).thenReturn(soccerConsumer);

        tweetScribe.start();

        verify(twitter).subscribeToStream("music", musicConsumer);
        verify(twitter).subscribeToStream("cars", carsConsumer);
        verify(twitter).subscribeToStream("soccer", soccerConsumer);
    }

    @Test
    void askUserRepeatedlyUntilAtLeastOneTopicProvided() {
        when(prompt.askUserForTopics())
                .thenReturn(emptyList())
                .thenReturn(emptyList())
                .thenReturn(asList("gaming"));

        tweetScribe.start();

        verify(twitter).subscribeToStream(eq("gaming"), any());
    }
}
