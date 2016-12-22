import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class PesquisaNoGoogle {

	public static void main(String[] args){
		
		System.setProperty("webdriver.gecko.driver", "/home/tdd04/geckodriver");
		
		 DesiredCapabilities dc = DesiredCapabilities.firefox();
		 dc.setCapability("marionette", true);
		 
		WebDriver driver = new MarionetteDriver();
		driver.get("http://www.google.com.br");
		
		WebElement we = driver.findElement(By.name("q"));
		we.sendKeys("triadworks");
		we.submit();
	}
}
