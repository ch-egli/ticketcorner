package ch.egli.ticketcorner

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.open
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.util.*


class ShopPageTest {
    private val shopPage = ShopPage()

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUpAll() {
            Configuration.browserCapabilities = ChromeOptions().addArguments("--remote-allow-origins=*")

            // Configuration.browserSize = "1280x800"
            Configuration.headless = true
        }

        private val logger: Logger = LoggerFactory.getLogger(ShopPageTest::class.java)

        private const val THUNERSEESPIELE_ESSEN_URL = "https://www.ticketcorner.ch/eventseries/hospitality-backstagefuehrungen-thunerseespiele-3263499/"
        private const val THUNERSEESPIELE_TICKETS_URL = "https://www.ticketcorner.ch/noapp/artist/thunerseespiele/?affiliate=FBM&utm_campaign=fbm&utm_source=fbm&utm_medium=dp"
        private var isAlarmSet = false
    }

    @BeforeEach
    fun setUp() {
        open(THUNERSEESPIELE_ESSEN_URL)
        shopPage.cookieButton.click()
    }

    @Test
    fun checkAvailability() {
        logger.info("##### ${getCurrentTimestamp()} -- START")

        Thread.sleep(5_000)

        while (true) {
            open(THUNERSEESPIELE_ESSEN_URL)
            Thread.sleep(14_000)
            shopPage.listButton.click()
            Thread.sleep(10_000)

            val firstAvailableDate = shopPage.firstAvailableDate.innerText().trim()
            logger.info("${getCurrentTimestamp()} firstAvailableDate: $firstAvailableDate")

            if (firstAvailableDate.contains("14") || firstAvailableDate.contains("15")) {
                logger.info("${getCurrentTimestamp()} found available tickets")
                informMe()
            }

            Thread.sleep(4_000)
        }
    }

    private fun getCurrentTimestamp(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(time) ?: ""
    }

    private fun informMe() {
        if (isAlarmSet) {
            return
        }

        isAlarmSet = true
        val options = ChromeOptions()
        options.addArguments("--remote-allow-origins=*")
        val driver = ChromeDriver(options)

        // Open a new Chrome Window to start purchasing tickets...
        driver.manage().window().maximize()
        driver.get(THUNERSEESPIELE_TICKETS_URL)

        // click cookie button
        driver.findElement(By.xpath("//*[@id=\"cmpwelcomebtnyes\"]/a")).click()

        /*
                driver.findElement(By.id("btnCookieAccepted")).click()
                driver.findElement(By.id("login")).click()
                //Thread.sleep(1_000)

                driver.findElement(By.id("txt_email")).sendKeys("christian.egli@gmx.net")
                driver.findElement(By.id("txt_password")).sendKeys(shopPage.password)
                //Thread.sleep(1_000)
                val loginButton = driver.findElement(By.id("btn_login"))
                val webDriverWait = WebDriverWait(driver, Duration.ofMillis(1000))
                val webElement: WebElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(loginButton))
                driver.executeScript("arguments[0].click();", webElement)

                driver.get(url)
        */
    }
}
