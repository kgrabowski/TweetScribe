package ca.nangasoft.tweetscribe;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TopicPrompt {
    private final Scanner scanner;
    private final PrintWriter writer;

    public TopicPrompt(InputStream stdin, OutputStream stdout) {
        this.scanner = new Scanner(stdin);
        this.writer = new PrintWriter(stdout, true);
    }

    public List<String> askUserForTopics() {
        writer.println("Choose topics to subscribe to: ");
        String answer = scanner.nextLine();
        return Arrays.asList(answer.split(" "));
    }
}
