package delta.main;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.relevantcodes.extentreports.LogStatus;
import generics.Excel;
import generics.Property;
import generics.Utility;


public class DeltaDriver  extends BaseDriver{

	public String browser="FF";
	public String hubURL;
	@BeforeMethod
	public void launchApp(XmlTest xmltest) {
	
		//driver=new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver", "C:\\Satya\\Softwares\\chromedriver_win32\\chromedriver.exe");
		driver=new ChromeDriver();
		
		String appURL=Property.getPropertyValue(configPptPath,"URL");
		String timeOut=Property.getPropertyValue(configPptPath,"TimeOut");
		
		driver.get(appURL);
		driver.manage().timeouts().implicitlyWait(Long.parseLong(timeOut),TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}
	@Test(dataProvider="getScenarios")
	public void testScenarios(String scenarioSheet,String executionStatus)	{
		testReport = eReport.startTest(browser+"_"+scenarioSheet);
		if(executionStatus.equalsIgnoreCase("yes")){	
			int stepCount=Excel.getRowCount(scenariosPath,scenarioSheet);
			for(int i=1;i<=stepCount;i++)
			{	
				String description=Excel.getCellValue(scenariosPath, scenarioSheet,i,0);
				String action=Excel.getCellValue(scenariosPath, scenarioSheet,i,1);
				String input1=Excel.getCellValue(scenariosPath, scenarioSheet,i,2);
				String input2=Excel.getCellValue(scenariosPath, scenarioSheet,i,3);
				String msg="description:"+description+" action:"+action+" input1:"+input1+" input2:"+input2;
				testReport.log(LogStatus.INFO,msg);
				KeyWord.executeKeyWord(driver, action, input1, input2,testReport);
			}
		}
		else
		{
			testReport.log(LogStatus.SKIP,"Execution Status is 'NO'");
			throw new SkipException("Skipping Scenario as execution status is 'NO'");
		}
		if(testReport.getRunStatus()==LogStatus.FAIL)
		{
			Assert.fail();
		}
	}
	
	@AfterMethod
	public void quitApp(ITestResult test){
		
		if(test.getStatus()==ITestResult.FAILURE)
		{
			String pImage=Utility.getPageScreenShot(driver,screenShotsFolderPath);
			String p = testReport.addScreenCapture("."+pImage);
			testReport.log(LogStatus.FAIL,"Page screen shot:"+p);
		}
		eReport.endTest(testReport);
		driver.quit();
		
	}
}









