package ca.nangasoft.tweetscribe.acceptance;

import ca.nangasoft.tweetscribe.Main;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;

public class CucumberSteps {
    private Path testWorkspace;
    private Process program;

    @Before
    public void createTestWorkspace() {
        try {
            testWorkspace = Files.createTempDirectory("");
        } catch (IOException e) {
            throw new RuntimeException("Failed to create test workspace", e);
        }
    }

    @After
    public void deleteTestWorkspace() {
        try {
            FileUtils.deleteDirectory(testWorkspace.toFile());
        } catch (IOException e) {
            System.err.println("Failed to delete test workspace");
            e.printStackTrace();
        }
    }

    @After
    public void terminateProgram() {
        if (program.isAlive()) {
            program.destroyForcibly();
        }
    }

    @When("the user starts the program")
    public void userStartsProgram() {
        try {
            program = startTweetScribe();
        } catch (IOException e) {
            throw new RuntimeException("Error starting TweetScribe", e);
        }
    }

    @When("the user chooses the topic {string}")
    public void userChoosesTopic(String topicName) {
        PrintWriter writer = new PrintWriter(program.getOutputStream());
        writer.println(topicName);
        writer.flush();
    }

    @When("the user chooses topics:")
    public void userChoosesTopics(List<String> topicNames) {
        PrintWriter writer = new PrintWriter(program.getOutputStream());
        writer.println(String.join(" ", topicNames));
        writer.flush();
    }

    @And("the user waits a bit")
    public void userWaitsABit() throws InterruptedException {
        Thread.sleep(3000);
    }

    @Then("the file {string} contains some tweets")
    public void fileContainsSomeTweets(String fileName) {
        String tweetRegex = "^(@\\w+: .+|\\t.*)$";

        assertThat(linesOf(getFile(fileName))).allMatch(line -> line.matches(tweetRegex));
    }

    private Process startTweetScribe() throws IOException {
        String javaHome = System.getProperty("java.home");
        String classPath = System.getProperty("java.class.path");

        Path javaPath = Paths.get(javaHome, "bin", "java");

        List<String> command = new ArrayList<>();
        command.add(javaPath.toString());
        command.add("-cp");
        command.add(classPath);
        command.add(Main.class.getName());

        return new ProcessBuilder(command)
                .directory(testWorkspace.toFile())
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .start();
    }

    private File getFile(String fileName) {
        return testWorkspace.resolve(fileName).toFile();
    }
}
