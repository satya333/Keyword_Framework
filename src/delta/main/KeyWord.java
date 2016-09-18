package delta.main;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class KeyWord {
public static void executeKeyWord(WebDriver driver,String action,String input1,String input2,ExtentTest testReport){
	if(action.equalsIgnoreCase("enter"))
	{
		KeywordLib.enter(driver, action, input1, input2,testReport);
	}
	else if(action.equalsIgnoreCase("click"))
	{
		KeywordLib.click(driver, action, input1, input2, testReport);
	}
	else if(action.equalsIgnoreCase("verifyElementPresent"))
	{
		KeywordLib.verifyElementPresent(driver, action, input1, input2,testReport);
	}
	else if(action.equalsIgnoreCase("verifyImage"))
	{
		KeywordLib.verifyImage(driver, action, input1, input2, testReport);
	}
	else if(action.equalsIgnoreCase("SelectFileToUpload"))
	{
		KeywordLib.selectFileToUpload(driver, action, input1, input2, testReport);
	}
	else
	{
		testReport.log(LogStatus.ERROR,"Invalid action:"+action);
	}
	}
}
