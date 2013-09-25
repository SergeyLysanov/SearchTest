package test.java;

import test.java.ConvertorPage;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.*;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class TestConvertor 
{
	private WebDriver 		driver;
	private ConvertorPage 	convertor;
	
	private final static double EPSILON = 0.0001;
	
	@Parameters({"browser", "url"})
	@BeforeClass
	public void SetupBrowser(String browser, String url)
	{
		System.out.println("Setup " + browser);
		
		if(browser.equals("chrome"))
		{
			File file = new File("./drivers/chromedriver");
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			driver = new ChromeDriver();
			driver.get(url);
		}
		else if(browser.equals("firefox"))
		{
			driver = new FirefoxDriver();
			driver.get(url);
		}
		
		convertor = new ConvertorPage(driver);
	}
	
	@Test
	public void CheckGeoTargeting()
	{
		String fromCourse = convertor.GetFrom();
		String toCourse = convertor.GetTo();
		
		//System.out.println("from: " + fromCourse);
		//System.out.println("to: " + toCourse);		
		org.testng.Assert.assertEquals(fromCourse, "доллар сша");
		org.testng.Assert.assertEquals(toCourse, "российского рубля");		
	}
	
	@Test
	public void CheckRublesCase()
	{
		convertor.ConvertFromRubles();
		convertor.TypeInput("0");
		String fromCourse = convertor.GetFrom();
		org.testng.Assert.assertEquals(fromCourse, "российских рублей");	
		
		convertor.TypeInput("1");
		fromCourse = convertor.GetFrom();
		org.testng.Assert.assertEquals(fromCourse, "российский рубль");
	}
	
	@Test
	public void CheckUsdCase()
	{
		convertor.ConvertFromUsd();
		convertor.TypeInput("10");
		String fromCourse = convertor.GetFrom();
		org.testng.Assert.assertEquals(fromCourse, "долларов сша");
		
		convertor.TypeInput("1");
		fromCourse = convertor.GetFrom();
		org.testng.Assert.assertEquals(fromCourse, "доллар сша");
		
		convertor.TypeInput("124");
		fromCourse = convertor.GetFrom();
		org.testng.Assert.assertEquals(fromCourse, "доллара сша");
	}
	
	
	@Parameters("intValue")
    @Test
    public void RublesToUsd_Int(Integer intValue)
    {
		convertor.ConvertFromRubles();
		convertor.ConvertToUsd();
		convertor.TypeInput(intValue.toString());
		
		Double res = convertor.GetResult().doubleValue();
		Double expected = (intValue * convertor.rublesValue) / convertor.usdValue;
		org.testng.Assert.assertEquals(res, expected, EPSILON);
    }
    
	@Parameters("doubleValue")
    @Test
    public void UsdToRubles_double(Double doubleValue)
    {
		convertor.ConvertFromUsd();
		convertor.ConvertToRubles();
		convertor.TypeInput(doubleValue.toString());
		
		Double res = convertor.GetResult().doubleValue();
		Double expected = (doubleValue * convertor.usdValue) /  convertor.rublesValue;
		org.testng.Assert.assertEquals(res, expected, EPSILON);
    }
    
	@Parameters("doubleValue")
    @Test
    public void CheckRevertButton(Double doubleValue)
    {
		convertor.ConvertFromEuro();
		convertor.ConvertToRubles();
		convertor.TypeInput(doubleValue.toString());
		convertor.Revert();
		
		Double res = convertor.GetResult().doubleValue();
		Double expected = (doubleValue * convertor.rublesValue) / convertor.euroValue;
		org.testng.Assert.assertEquals(res, expected, EPSILON);
    }
	
	@Parameters("uncorrectVal")
	@Test
	public void CheckUncorrectVal(String uncorrectVal)
	{
		convertor.ConvertFromEuro();
		convertor.ConvertToRubles();
		convertor.TypeInput(uncorrectVal);
		
		String style = convertor.GetInputColorStyle();
		org.testng.Assert.assertEquals(style, "color: red;");
	}
    
	@AfterClass
	public void CloseDriver()
	{
		driver.quit();
	}
}
