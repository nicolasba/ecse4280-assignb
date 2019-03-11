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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

public class StepDefs {

    //Selenium
    private WebDriver driver;
    private WebDriverWait wait;
    private String driverPath = "drivers/geckodriver.exe";
    private String url = "https://login.live.com/login.srf?wa=wsignin1.0&rpsnv=13&ct=1552266948" +
            "&rver=7.0.6737.0&wp=MBI_SSL&wreply=https%3a%2f%2foutlook.live.com%2fowa%2f%3fnlp%3d1%26Rp" +
            "sCsrfState%3d4db93362-4c78-4700-88b1-96478e6208c0&id=292841&aadredir=1&CBCXT=out&lw=1&fl=dob%2" +
            "cflname%2cwld&cobrandid=90015";    //Outlook url

    private Robot robot;

    //Dummy data
    private String dummyUsername = "A428test@outlook.com";
    private String dummyPassword = "428assignment";
    private String dummySubject = "Sending funny image :) :)";

    private String recipientEmail;

    @Before
    public void setUp() {

        //Sets up Firefox driver and logs in using dummy credentials
        System.setProperty("webdriver.gecko.driver", driverPath);
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 30);

        //Set up robot instance
        try{
            robot = new Robot();
        }
        catch(AWTException e){
            e.printStackTrace();
        }

        //Access Outlook website and sign in using dummy account
        driver.get(url);
        driver.manage().window().maximize();

        //Enter dummy username and click on next
        wait.until(ExpectedConditions.elementToBeClickable(By.id("i0116")));
        WebElement usernameInput = driver.findElement(By.id("i0116"));
        usernameInput.sendKeys(dummyUsername); //Enter username
        wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
        driver.findElement(By.id("idSIButton9")).click();

        //Enter dummy password and click on sign in
        wait.until(ExpectedConditions.elementToBeClickable(By.id("i0118")));
        WebElement passwordInput = driver.findElement(By.id("i0118"));
        passwordInput.sendKeys(dummyPassword); //Enter username
        wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
        driver.findElement(By.id("idSIButton9")).click();
    }

    @Given("^I am logged into my email account$")
    public void iAmLoggedIntoMyEmailAccount() {

        //Click on "New message"
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".\\_1Tegvg68M2d-4Z3rDrt2B3")));
        driver.findElement(By.cssSelector(".\\_1Tegvg68M2d-4Z3rDrt2B3")).click();
    }

    @And("^I enter my friend's \"([^\"]*)\"$")
    public void iEnterMyFriendS(String email){

        //Enter recipient email
        recipientEmail = email;
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ms-BasePicker-input")));
        driver.findElement(By.cssSelector(".ms-BasePicker-input")).sendKeys(recipientEmail);
    }


    @And("^I attach an \"([^\"]*)\" from my computer$")
    public void iAttachAnFromMyComputer(String image){

        //Enter image name contained under the "images" folder
        String imagePath = System.getProperty("user.dir") + File.separator + "images" + File.separator + image;

        //Click on "Attach"
        wait.until(ExpectedConditions.elementToBeClickable(By.name("Attach")));
        driver.findElement(By.name("Attach")).click();

        //Click on "Browse this computer"
        wait.until(ExpectedConditions.elementToBeClickable(By.name("Browse this computer")));
        driver.findElement(By.name("Browse this computer")).click();

        //A native Windows popup appears to select an image, however Selenium cannot interact with it
        //The robot object is able to simulate keyboard key presses and can fill the file selection text field
        StringSelection selection = new StringSelection(imagePath);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();    //Copy image path to clipboard
        clipboard.setContents(selection, selection);

        //Paste from clipboard to input textfield
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.delay(300);
        robot.keyPress(KeyEvent.VK_V);
        robot.delay(300);
        robot.keyRelease(KeyEvent.VK_V);
        robot.delay(300);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(300);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(300);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(300);
    }

    @And("^I attach an \"([^\"]*)\" from the cloud$")
    public void iAttachAnFromTheCloud(String image) throws Throwable {

        //Click on "Attach"
        wait.until(ExpectedConditions.elementToBeClickable(By.name("Attach")));
        driver.findElement(By.name("Attach")).click();

        //Click on "Browse cloud locations"
        wait.until(ExpectedConditions.elementToBeClickable(By.name("Browse cloud locations")));
        driver.findElement(By.name("Browse cloud locations")).click();

        Thread.sleep(2000);
        //Selects image in OneDrive that corresponds to the image name
        List<WebElement> cloudImagesList = driver.findElements(By.xpath("//*[starts-with(@class, '_2T9I4P_0kdYxjcQ-T-Fuie')]"));
        for (WebElement e: cloudImagesList){
            if (e.getAttribute("title").equals(image))
                e.click();
        }

        //Click on "Next"
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".\\_2IQpIn0UF3OF_CtC-Awq-- > .ms-Button")));
        driver.findElement(By.cssSelector(".\\_2IQpIn0UF3OF_CtC-Awq-- > .ms-Button")).click();
    }

    @When("^I send the email$")
    public void iSendTheEmail() throws Exception{

        //Enter a dummy subject and Click on "Send"
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ms-Button--primary")));
        driver.findElement(By.id("subjectLine0")).sendKeys(dummySubject);
        Thread.sleep(3000);
        driver.findElement(By.cssSelector(".ms-Button--primary")).click();
    }

    //Will return true if expectedEmail = actualEmail and expectSubject = actualSubject
    public boolean testSubjectAndEmail () throws Exception{

        Thread.sleep(3000);
        //Click on "Sent Items"
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div:nth-child(2) > div:nt" +
                "h-child(2) > .\\_34_bqC0c1-8H3B0lGzop-9:nth-child(1) > .\\_2qZaU4w9P1XG8-zs5arlR3")));
        driver.findElement(By.cssSelector("div:nth-child(2) > div:nth-child(2) > .\\_34_bqC0c1-" +
                "8H3B0lGzop-9:nth-child(1) > .\\_2qZaU4w9P1XG8-zs5arlR3")).click();

        //Press on "Down Arrow" key to select first email in list
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.delay(100);

        //Get subject and recipient email
        wait.until(ExpectedConditions.elementToBeClickable(By.className("dJ4kO5HcLAM4x-GXmxP8n")));
        String actualSubject = driver.findElement(By.className("dJ4kO5HcLAM4x-GXmxP8n")).getText();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("_1GbKnlrcyAfdgFr9WpTgdU")));
        String actualEmail = driver.findElement(By.className("_1GbKnlrcyAfdgFr9WpTgdU")).getText();

        return actualSubject.equals(dummySubject) && actualEmail.equals(recipientEmail);
    }

    @Then("^I should be able to verify that the email was successfully sent$")
    public void iShouldBeAbleToVerifyThatTheEmailWasSuccessfullySent() throws Exception{

        //Assert that expected/actual email and subject are the same
        Assert.assertTrue(testSubjectAndEmail());
    }

    public boolean isWarningPresent() throws Exception{

        Thread.sleep(3000);
        //Get warning
        wait.until(ExpectedConditions.elementToBeClickable(By.className("_2eDT5LAxGsFuAUVDuX3mz_ _3zUVDEaVDhyHmlV2" +
                "830f9z F99zb1PyC88tXPxMniJJF")));
        String warning = driver.findElement(By.className("_2eDT5LAxGsFuAUVDuX3mz_ _3zUVDEaVDhyHmlV2830f" +
                "9z F99zb1PyC88tXPxMniJJF")).getText();

        return warning.equals("This message must have at least one recipient.");
    }

    @Then("^I should be warned that the email could not be sent$")
    public void iShouldBeWarnedThatTheEmailCouldNotBeSent() throws Exception {

        Assert.assertTrue(isWarningPresent());
    }

    @After
    public void tearDown() {

        //Sign out


        //Terminate Web driver
        driver.close();
    }
}
