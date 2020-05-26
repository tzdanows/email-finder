package edu.depaul.email;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class cucumberDefinitions {

    // Note: had issues with dependencies for some reason with Cucumber for JUnit 5, will pick this up later

    @Given("Request to find links on given link")
    public void parsing_cdmDePaul(Integer int1){
        // write code here to turn the phrase above into a concrete action
        throw new io.cucumber.java.PendingException();

    }

    @When("Valid emails are found")
    public void linkfound(Integer int2){
        // write code here to turn the phrase above into a concrete action
        throw new io.cucumber.java.PendingException();

    }

    @Then("Emails are stored")
    public void linkextracted(Integer int3){
        // write code here to turn the phrase above into a concrete action
        throw new io.cucumber.java.PendingException();

    }
}
