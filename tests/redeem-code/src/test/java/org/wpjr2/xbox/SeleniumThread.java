package org.wpjr2.xbox;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class SeleniumThread extends Thread {

	private Selenium selenium;
	WebDriver driver;
	List<String> codes;
	long id;

	public SeleniumThread(List<String> codes) {
		this.codes = codes;
		this.id = System.currentTimeMillis();
		System.out.println("New thread: " + id);
	}
	
	@Override
	public void run() {
		try {
			setUp();
			testJavawebdriver();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "c://dev/chromedriver.exe");
		driver = new ChromeDriver();
		String baseUrl = "https://microsoft.promo.eprize.com/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	public void testJavawebdriver() throws Exception {
		selenium.open("/nameyourgame/");
		selenium.select("name=country_code", "label=Brazil");
		selenium.click("css=button[type=\"submit\"]");
		selenium.waitForPageToLoad("30000");
		
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		selenium.click("id=primary_opt_in");
		selenium.click("id=rules_checkbox");
		selenium.type("id=email_2", "wrpiresjr@outlook.com");
		selenium.type("id=confirm_email", "wrpiresjr@outlook.com");
		selenium.select("name=age.birth_day", "label=2");
		selenium.select("name=age.birth_month", "label=Sep");
		selenium.select("name=age.birth_year", "label=1976");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");

		for (String code: codes){
			selenium.type("id=purchase_code", code);
			selenium.click("css=div.profile > div.row > div.column.small-12 > div.submit > button[type=\"submit\"]");
			selenium.waitForPageToLoad("30000");
			WebElement element = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div/div[2]/div[2]/form/div/div[1]/div/p[2]"));
			if (element != null){
				if (element.getText().contains("Desculpe, este c") ||
						element.getText().contains("Sorry, this is not a valid code"))
				{
					System.out.println(id + " - " + sdf.format(Calendar.getInstance().getTime())+" - code invalid: " + code);
				} else {
					System.out.println(id + " - code valid: " + code);
					new File(code+".txt").createNewFile();
					System.exit(0);
				}
			}
		}
		
		selenium.close();
	}
	
}

