package ca.mcgill.ecse428;

import cucumber.api.java.Before;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.security.Key;

public class StepDefs {

    private WebDriver driver;
    private WebDriverWait wait;
    private String driverPath = "drivers/geckodriver.exe";
    private String url = "https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
    private String dummyUsername = "tempUserEcse428@gmail.com";
    private String dummyPassword = "passwordecse428";
    private String dummySubject = "Sending funny image :) :)";

    @Before
    public void setUp(){

        //Sets up Firefox driver and logs in using dummy credentials
        System.setProperty("webdriver.gecko.driver", driverPath);
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 30);

        //Access Outlook website and sign in using dummy account
        driver.get(url);
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("identifierId")));
        driver.findElement(By.id("identifierId")).sendKeys(dummyUsername); //Enter username
        driver.findElement(By.className("CwaK9")).click();   //Click on "Next"

        wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        driver.findElement(By.name("password")).sendKeys(dummyPassword);   //Enter password
        driver.findElement(By.className("CwaK9")).click();   //Click on "Submit"

    }

    @Given("^I am logged into my email account$")
    public void iAmLoggedIntoMyEmailAccount() {

        //Click on "Compose" button
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".T-I-KE")));
        driver.findElement(By.cssSelector(".T-I-KE")).click();
    }

    @And("^I enter my friend's \"([^\"]*)\"$")
    public void iEnterMyFriendS(String email) throws Throwable {

        //Enter recipient email in text field
        wait.until(ExpectedConditions.elementToBeClickable(By.name("to")));
        driver.findElement(By.name("to")).sendKeys(email);
    }

    @And("^I attach an \"([^\"]*)\"$")
    public void iAttachAn(String image) throws Throwable {

        //Enter image name contained under the "images" folder
        String imagePath = System.getProperty("user.dir") + File.separator + "images" + File.separator + image;
        Robot robot = new Robot();

        driver.findElement(By.id(":9u")).click();   //Click on "attach file" button

        //A native Windows popup appears to select an image, however Selenium cannot interact with it
        //The robot object is able to simulate keyboard key presses and can fill the file selection text field
        StringSelection selection = new StringSelection(imagePath);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();    //Copy image path to clipboard
        clipboard.setContents(selection, selection);

        //Paste from clipboard to input textfield
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.delay(100);
        robot.keyPress(KeyEvent.VK_V);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_V);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(200);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(200);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(200);
    }

    @When("^I send the email$")
    public void iSendTheEmail() {

        wait.until(ExpectedConditions.elementToBeClickable(By.id(":82")));
        driver.findElement(By.name("subjectbox")).sendKeys(dummySubject);
        driver.findElement(By.id(":82")).click();
    }


    @Then("^I should receive a successful confirmation$")
    public void iShouldReceiveASuccessfulConfirmation() {
        Assert.assertTrue(wait.until(ExpectedConditions.textToBe(By.className("bAq"), "Message sent.")));
    }

    @After
    public void tearDown(){

        driver.close();
    }
}
