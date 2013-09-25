package test.java;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConvertorPage 
{
	private final WebDriver driver;
	
	//Course values
	public  Double rublesValue;
	public 	Double usdValue;
	public 	Double euroValue;
	
	//Locators
    private final By searchLocator = 		By.name("q");
    private final By inputFormLocator =		By.id("ival");
    private final By outputValLocator = 	By.id("oval");
    private final By revertLocator = 		By.id("change_conv");
    private final By courseLocator = 		By.xpath("//a[contains(.,'По курсу ЦБ РФ ')]");
    
    private final By toLocator	=			By.xpath("//span[@id='ocode']");
    private final By toRublesLocator = 		By.xpath("//ul[@id='select_second']//span[contains(@data-code, 'RUB')]");
    private final By toUsdLocator =    		By.xpath("//ul[@id='select_second']//span[contains(@data-code, 'USD')]");
    private final By toEuroLocator =    	By.xpath("//ul[@id='select_second']//span[contains(@data-code, 'EUR')]");
	
    private final By fromLocator	=		By.xpath("//span[@id='icode']");
    private final By fromRublesLocator =	By.xpath("//ul[@id='select_first']//span[contains(@data-code, 'RUB')]");
    private final By fromUsdLocator =    	By.xpath("//ul[@id='select_first']//span[contains(@data-code, 'USD')]");
    private final By fromEuroLocator =    	By.xpath("//ul[@id='select_first']//span[contains(@data-code, 'EUR')]");
	
    //Constructor
	public ConvertorPage(WebDriver driver) 
	{
        this.driver = driver;

        // Find the text input element by its name
        WebElement element = driver.findElement(searchLocator);
        element.sendKeys("курс валют");
        element.submit();
        
        //wait until convertor loaded
        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(courseLocator));
        
        InitializeCourses();
    }
	
	//Input value to convert
	public void TypeInput(String sValue)
	{
		WebElement element = driver.findElement(inputFormLocator);
		element.clear();
		element.sendKeys(sValue);
	}
	
	public void ConvertToRubles()
	{
		ClickElement(toLocator);
		ClickElement(toRublesLocator);
	}
	
	public void ConvertToUsd()
	{
		ClickElement(toLocator);
		ClickElement(toUsdLocator);
	}
	
	public void ConvertToEuro()
	{
		ClickElement(toLocator);
		ClickElement(toEuroLocator);
	}
	
	public void ConvertFromRubles(){
		ClickElement(fromLocator);
		ClickElement(fromRublesLocator);
	}
	public void ConvertFromUsd(){
		ClickElement(fromLocator);
		ClickElement(fromUsdLocator);
	}
	public void ConvertFromEuro(){
		ClickElement(fromLocator);
		ClickElement(fromEuroLocator);
	}
	
	public Number GetResult()
	{
		WebElement element = driver.findElement(outputValLocator);
		String sResult = element.getText();
		//System.out.println(sResult);
		
	    NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
	    Number number = 0;
	    try {
	    	number = format.parse(sResult);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return number;
	}
	
	public String GetFrom()
	{
		WebElement element = driver.findElement(fromLocator);
		return element.getText();
	}
	
	public String GetTo()
	{
		WebElement element = driver.findElement(toLocator);
		return element.getText();
	}
	
	public String GetInputColorStyle()
	{
		WebElement element = driver.findElement(inputFormLocator);
		return element.getAttribute("style");
	}
	
	public void Revert()
	{
		ClickElement(revertLocator);
	}
	
	private void InitializeCourses()
	{
		WebElement rubElement = driver.findElement(fromRublesLocator);
		rublesValue = Double.parseDouble(rubElement.getAttribute("data-value"));
		
		WebElement usdElement = driver.findElement(fromUsdLocator);
		usdValue = Double.parseDouble(usdElement.getAttribute("data-value"));
		
		WebElement euroElement = driver.findElement(fromEuroLocator);
		euroValue = Double.parseDouble(euroElement.getAttribute("data-value"));
		
		//System.out.println(rublesValue.toString() + usdValue.toString() + euroValue.toString());
	}
	
	private void ClickElement(By locator)
	{
		WebElement element = driver.findElement(locator);
		element.click();
	}

}
