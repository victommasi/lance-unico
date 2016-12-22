import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.*;


public class CadastroDeClientesTest {

	@Test
	public void deveAdicionarNovoCliente(){
		System.setProperty("webdriver.gecko.driver", "/home/tdd04/geckodriver");
		
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		dc.setCapability("marionette", true);
		 
		WebDriver driver = new MarionetteDriver();
		driver.get("http://localhost:8080/lance-unico/pages/clientes/novo.xhtml");
		
		WebElement nome = driver.findElement(By.name("nome"));
		nome.sendKeys("Bruce Wayne");
		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("bruce@gmail.com");
		
		WebElement botao = driver.findElement(By.id("btn-salvar"));
		botao.click();
		
		assertTrue(driver.getPageSource().contains("Bruce Wayne"));
		assertTrue(driver.getPageSource().contains("bruce@gmail.com"));
		
		driver.close();
		
	}
}
