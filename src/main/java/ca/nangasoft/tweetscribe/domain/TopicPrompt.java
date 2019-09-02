package ca.nangasoft.tweetscribe.domain;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class TopicPrompt {
    private static final int MAX_TOPICS = 5;

    private final Scanner scanner;
    private final PrintWriter writer;

    public TopicPrompt(InputStream stdin, OutputStream stdout) {
        this.scanner = new Scanner(stdin);
        this.writer = new PrintWriter(stdout, true);
    }

    public List<String> askUserForTopics() {
        writer.printf("Choose topics to subscribe to (up to %d):%n", MAX_TOPICS);
        String answer = scanner.nextLine();
        return Stream.of(answer.split("\\s"))
                .limit(MAX_TOPICS)
                .collect(toList());
    }
}
