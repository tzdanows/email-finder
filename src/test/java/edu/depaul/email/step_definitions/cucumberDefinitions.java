package edu.depaul.email.step_definitions;

import edu.depaul.email.PageFetcher;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.jsoup.nodes.Document;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class cucumberDefinitions {

    private double initialCost;
    private String globalURL;
    private String localFileLocation;


    @Given("Individual is looking for website {string}")
    public void getting_URL(String url){
        // write code here to turn the phrase above into a concrete action
        globalURL = url;

    }

    @Given("Individual is looking for a file located at {string}")
    public void getting_FILE(String file){
        // write code here to turn the phrase above into a concrete action
        // file = "/src/test/resources/testingSM.html";
        localFileLocation = System.getProperty("user.dir") + file;
    }

    @Then("The documents are the {string}")
    public void docsEQ(String accuracy){
        // write code here to turn the phrase above into a concrete action
        if(accuracy.equals("same")){
            PageFetcher fetch = new PageFetcher();

            String finalURL = fetch.getString(globalURL);
            String finalFILE = fetch.get(localFileLocation).outerHtml();
            assertEquals(finalFILE, finalURL);
        } else {
            PageFetcher fetch = new PageFetcher();

            String finalURL = fetch.getString(globalURL);
            String finalFILE = fetch.get(localFileLocation).outerHtml();
            assertNotEquals(finalFILE, finalURL);
        }

    }
}
