package ca.nangasoft.tweetscribe.adapters;

import ca.nangasoft.tweetscribe.domain.TweetFormatter;
import twitter4j.Status;
import twitter4j.StatusAdapter;

import java.io.OutputStream;
import java.io.PrintWriter;

public class TopicListener extends StatusAdapter {
    private final TweetFormatter formatter;
    private final PrintWriter writer;

    public TopicListener(TweetFormatter formatter, OutputStream stream) {
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
