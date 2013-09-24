package test.java;

import test.java.ConvertorPage;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class TestConvertor 
{
	private final static double EPSILON = 0.0001;

	private static WebDriver chromeDriver;
	private static WebDriver firefoxDriver;
	
	
    @Test
    public void TestChrome() 
    {
    	System.out.println("Test Chrome");
    	File file = new File("./drivers/chromedriver");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		chromeDriver = new ChromeDriver();
		chromeDriver.get("http://www.mail.ru/");
		
		CheckInt();
		CheckDouble();
    }
    
    @Test
    public void TestFirefox()
    {
    	System.out.println("Test Firefox");
		firefoxDriver= new FirefoxDriver();
		firefoxDriver.get("http://www.mail.ru/");
    	
		CheckInt();
		CheckDouble();
    }
    
    public void CheckInt()
    {
    	ConvertorPage convertor = new ConvertorPage(chromeDriver);
		convertor.ConvertFromRubles();
		convertor.ConvertToUsd();
		convertor.TypeInput("10");
		
		Double res = convertor.GetResult().doubleValue();
		Double expected = (10.0 * convertor.rublesValue) / convertor.usdValue;
		org.junit.Assert.assertTrue(equals(res, expected));
    }
    
    public void CheckDouble()
    {
    	ConvertorPage convertor = new ConvertorPage(chromeDriver);
		convertor.ConvertFromUsd();
		convertor.ConvertToRubles();
		convertor.TypeInput("4.23423");
		
		Double res = convertor.GetResult().doubleValue();
		Double expected = (4.23423 * convertor.usdValue) /  convertor.rublesValue;
		org.junit.Assert.assertTrue(equals(res, expected));
    }
    
    
	@AfterClass
	public static void CloseDrivers()
	{
		chromeDriver.quit();
		firefoxDriver.quit();
	}
	
	public static boolean equals(double a, double b){
	    return a == b ? true : Math.abs(a - b) < EPSILON;
	}
}
