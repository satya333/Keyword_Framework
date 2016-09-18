package delta.main;

import java.io.File;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import generics.Utility;

public class KeywordLib {
	
	public static void selectFileToUpload(WebDriver driver,String action,String input1,String input2,ExtentTest testReport)
	{
		String absoluteFilePath=new File(input2).getAbsolutePath();
		System.out.println(absoluteFilePath);
		driver.findElement(Locator.getLocator(input1)).sendKeys(absoluteFilePath);
	}
	public static void enter(WebDriver driver,String action,String input1,String input2,ExtentTest testReport){
		driver.findElement(Locator.getLocator(input1)).sendKeys(input2);
		testReport.log(LogStatus.PASS,"executed enter");
		
	}
	public static void click(WebDriver driver,String action,String input1,String input2,ExtentTest testReport){
		driver.findElement(Locator.getLocator(input1)).click();
		testReport.log(LogStatus.PASS,"executed click");
	}
	public static void verifyElementPresent(WebDriver driver,String action,String input1,String input2,ExtentTest testReport){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		List<WebElement> elements = driver.findElements(Locator.getLocator(input1));
		if(elements.size()> 0 && elements.get(0).isDisplayed() )
		{
			testReport.log(LogStatus.PASS,"Element is Present and Displayed");
		}
		else
		{
			testReport.log(LogStatus.FAIL,"Element is Not Present or Displayed");
		}
	}
	public static void verifyImage(WebDriver driver,String action,String input1,String input2,ExtentTest testReport)
	{
		String actualImageComplete=Utility.getPageScreenShot(driver, AutomationConstants.ActualImageFolder);
		String expectedImage=AutomationConstants.ExpectedImageFolder+"/"+input2;
		int x=driver.findElement(Locator.getLocator(input1)).getLocation().getX();
		int y=driver.findElement(Locator.getLocator(input1)).getLocation().getY();
		int width=driver.findElement(Locator.getLocator(input1)).getSize().getWidth();
		int height=driver.findElement(Locator.getLocator(input1)).getSize().getHeight();
		String actualImage=Utility.cropImage(actualImageComplete,x,y,width, height, AutomationConstants.ActualImageFolder);
		if(Utility.compareImage(actualImage,expectedImage))
		{
			String p = testReport.addScreenCapture("."+actualImage);
			testReport.log(LogStatus.PASS,"Image verified is:"+p);
		}
		else
		{
			String e=testReport.addScreenCapture("."+expectedImage);
			testReport.log(LogStatus.FAIL,"Expected image is:"+e);
			String a=testReport.addScreenCapture("."+actualImage);
			testReport.log(LogStatus.FAIL,"Actual image is:"+a);
		}
	}
	
}
