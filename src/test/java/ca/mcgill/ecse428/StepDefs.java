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

    private WebDriver driver;
    private WebDriverWait wait;
    private String driverPath = "drivers/geckodriver.exe";
    private String url = "https://login.live.com/login.srf?wa=wsignin1.0&rpsnv=13&ct=1552266948&rver=7.0.6737.0&wp=MBI_SSL&wreply=https%3a%2f%2foutlook.live.com%2fowa%2f%3fnlp%3d1%26RpsCsrfState%3d4db93362-4c78-4700-88b1-96478e6208c0&id=292841&aadredir=1&CBCXT=out&lw=1&fl=dob%2cflname%2cwld&cobrandid=90015";
    private String dummyUsername = "tempUserEcse428@outlook.com";
    private String dummyPassword = "passwordecse428";
    private String dummySubject = "Sending funny image :) :)";
    private String inboxWindow;
    private String attachFileWindow;

    @Before
    public void setUp() {

        //Sets up Firefox driver and logs in using dummy credentials
        System.setProperty("webdriver.gecko.driver", driverPath);
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 30);

        //Access Outlook website and sign in using dummy account
        driver.get(url);
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("i0116")));
        WebElement usernameInput = driver.findElement(By.id("i0116"));
        usernameInput.sendKeys(dummyUsername); //Enter username
        wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
        driver.findElement(By.id("idSIButton9")).click();


        wait.until(ExpectedConditions.elementToBeClickable(By.id("i0118")));
        WebElement passwordInput = driver.findElement(By.id("i0118"));
        passwordInput.sendKeys(dummyPassword); //Enter username
        wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
        driver.findElement(By.id("idSIButton9")).click();
//        driver.findElement(By.className("CwaK9")).click();   //Click on "Submit"
    }

    @Given("^I am logged into my email account$")
    public void iAmLoggedIntoMyEmailAccount() {

        //Outlook
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".\\_1Tegvg68M2d-4Z3rDrt2B3")));
        driver.findElement(By.cssSelector(".\\_1Tegvg68M2d-4Z3rDrt2B3")).click();

        //Gmail
//        //Click on "Compose" button
//        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".T-I-KE")));
//        driver.findElement(By.cssSelector(".T-I-KE")).click();
    }

    @And("^I enter my friend's \"([^\"]*)\"$")
    public void iEnterMyFriendS(String email) throws Throwable {

        //Outlook
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ms-BasePicker-input")));
        driver.findElement(By.cssSelector(".ms-BasePicker-input")).sendKeys(email);
//        driver.findElement(By.cssSelector(".ms-BasePicker-input")).sendKeys("${KEY_ENTER}");

        //Gmail
//        inboxWindow = driver.getWindowHandle();
//        //Enter recipient email in text field
//        wait.until(ExpectedConditions.elementToBeClickable(By.name("to")));
//        driver.findElement(By.name("to")).sendKeys(email);
    }


    @And("^I attach an \"([^\"]*)\" from my computer$")
    public void iAttachAnFromMyComputer(String image) throws Throwable {

        //Enter image name contained under the "images" folder
        String imagePath = System.getProperty("user.dir") + File.separator + "images" + File.separator + image;
        Robot robot = new Robot();

        //Click on "Attach"
        wait.until(ExpectedConditions.elementToBeClickable(By.name("Attach")));
        driver.findElement(By.name("Attach")).click();
//        driver.findElement(By.id(":9u")).click();   //Click on "attach file" button

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

    @And("^I attach an \"([^\"]*)\" from the cloud$")
    public void iAttachAnFromTheCloud(String image) throws Throwable {

        WebElement attachImageDriveBtn = driver.findElement(By.id(":a0"));
        wait.until(ExpectedConditions.elementToBeClickable(attachImageDriveBtn));
        attachImageDriveBtn.click();

        for (String s : driver.getWindowHandles())
            System.out.println(s);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[2]/div[3]/div/div/div")));
        WebElement textInputBar = driver.findElement(By.xpath("//div[2]/div[3]/div/div/div"));
        wait.until(ExpectedConditions.elementToBeClickable(textInputBar));
        textInputBar.click();

//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("fe-Rg-cb")));
//        List<WebElement> imagesNames = driver.findElements(By.className("fe-Rg-cb"));
//        for (WebElement e : imagesNames){
//            wait.until(ExpectedConditions.elementToBeClickable(e));
//            if (e.getAttribute("aria-label").equals(image))
//                e.click();
//        }

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='picker:ap:0']")));
        WebElement insertBtn = driver.findElement(By.xpath("//div[@id='picker:ap:0']"));
        wait.until(ExpectedConditions.elementToBeClickable(insertBtn));
        insertBtn.click();
    }

    @When("^I send the email$")
    public void iSendTheEmail() throws Exception{

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ms-Button--primary")));
        Thread.sleep(3000);
        driver.findElement(By.id("subjectLine0")).sendKeys(dummySubject);
        driver.findElement(By.cssSelector(".ms-Button--primary")).click();
    }


    @Then("^I should receive a successful confirmation$")
    public void iShouldReceiveASuccessfulConfirmation() {

        //Gmail
//        Assert.assertTrue(wait.until(ExpectedConditions.textToBe(By.className("bAq"), "Message sent.")));
    }

    @After
    public void tearDown() {

//        driver.close();
    }
}
