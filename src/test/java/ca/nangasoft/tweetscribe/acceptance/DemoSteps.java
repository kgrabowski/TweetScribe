package ca.nangasoft.tweetscribe.acceptance;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class DemoSteps {
    @Given("nothing really")
    public void nothingReally() {
    }

    @When("nothing happens")
    public void nothingHappens() {
    }

    @Then("assert true")
    public void assertTrue() {
        assertThat(true).isTrue();
    }
}
