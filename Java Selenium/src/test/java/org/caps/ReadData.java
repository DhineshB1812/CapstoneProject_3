package org.caps;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ReadData {

	WebDriver driver;

	@BeforeClass
	public void setUp() {
		// Set up WebDriver
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();

	}

	@Test
	public void testBrowser() {
		driver.get("https://www.saucedemo.com/");
		System.out.println("Page title: " + driver.getTitle());
	}

	@Test(dataProvider = "Credentials")
	public void login(String username, String password) {

		System.out.println("Logging in with Username: " + username + ", Password: " + password);

		// Example:
		// driver.findElement(By.id("username")).sendKeys(username);
		// driver.findElement(By.id("password")).sendKeys(password);
		// driver.findElement(By.id("loginButton")).click();
	}

	@DataProvider(name = "Credentials")
	public Object[][] datas() throws IOException {
		File file = new File("");
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Login");

		int rowCount = sheet.getLastRowNum();
		short columnCount = sheet.getRow(0).getLastCellNum();

		Object[][] data = new Object[rowCount][columnCount];

		for (int r = 0; r < rowCount; r++) {
			XSSFRow row = sheet.getRow(r + 1);

			for (int c = 0; c < columnCount; c++) {
				XSSFCell cell = row.getCell(c);
				data[r][c] = cell.getStringCellValue();
			}

		}

		return data;

	}

}
