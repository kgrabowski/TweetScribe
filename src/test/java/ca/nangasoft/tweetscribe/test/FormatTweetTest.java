package ca.nangasoft.tweetscribe.test;

import ca.nangasoft.tweetscribe.domain.TweetFormatter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FormatTweetTest {
    private final TweetFormatter formatter = new TweetFormatter();

    @Test
    void formatSingleLineTweet() {
        String tweet = formatter.format("kamil", "single line of text");

        assertThat(tweet).isEqualTo("@kamil: single line of text");
    }

    @Test
    void formatMultiLineTweet() {
        String tweet = formatter.format("fred", "this tweet\nhas\nmultiple lines");

        assertThat(tweet).isEqualTo(String.join("\n",
                "@fred: this tweet",
                "\thas",
                "\tmultiple lines"
        ));
    }
}
