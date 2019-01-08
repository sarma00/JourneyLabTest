package testCases;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Test_Case_JourneyLab {

	FileInputStream fis;
	Properties prop;
	WebDriver driver;
	Alert alert;
	DesiredCapabilities capabilities;

	@BeforeTest
	public void initialize(){
		try{
			fis= new FileInputStream(System.getProperty("user.dir")+"\\src\\resource\\config.properties");
			prop= new Properties();
			prop.load(fis);
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (IOException E){
			System.out.println(E);
		}	
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void journeyLabTest() throws Exception{		
		driver.get(prop.getProperty("baseUrl"));
		WebElement elmSubmitBtn = driver.findElement(By.id(prop.getProperty("buttonSubmit_id")));
		elmSubmitBtn.click();
		Thread.sleep(2000);
		String getResponse = driver.findElement(By.id(prop.getProperty("responseField_id"))).getText();	
		WebElement elmInputTxt = driver.findElement(By.id(prop.getProperty("textBox_id")));		
		elmInputTxt.clear();				
		elmInputTxt.sendKeys(getResponse);
		Thread.sleep(2000);
		int count=0;
		for(int i=0;i<=200;i++){			
			System.out.println(getResponse);
			System.out.println("Iteration  = " + i);
			elmSubmitBtn.click();
			Thread.sleep(2000);
			if((i==4 && i>0)||count==7){	
				System.out.println("Inside if" + "i " + i +" and count = "+count);
					Wait wait = new FluentWait<WebDriver>(driver)
							.withTimeout(10, TimeUnit.SECONDS)
							.pollingEvery(2, TimeUnit.SECONDS)
							.ignoring(NoAlertPresentException.class);
					wait.until(ExpectedConditions.alertIsPresent());
					driver.switchTo().alert().accept();
					count=0;								
			}
			getResponse = driver.findElement(By.id(prop.getProperty("responseField_id"))).getText();
			elmInputTxt.clear();				
			elmInputTxt.sendKeys(getResponse);
			count=count+1;
		}	
	}
	
	@AfterTest
	public void tearDown(){
		driver.close();
	}
}
