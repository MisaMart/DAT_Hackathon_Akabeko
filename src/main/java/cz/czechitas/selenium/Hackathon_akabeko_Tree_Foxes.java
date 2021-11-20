package cz.czechitas.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


import java.util.concurrent.TimeUnit;

public class Hackathon_akabeko_Tree_Foxes {

    public static final String URL_APLIKACE = "http://czechitas-datestovani-hackathon.cz";
    WebDriver prohlizec;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        prohlizec.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void DBP2A_83_loginPresHlavniStranku() {
        prohlizec.navigate().to(URL_APLIKACE);
        klikniNaPrihlasit();
        prihlaseniUzivatele();

        WebElement nadpisMyAccount = prohlizec.findElement(By.xpath("//h1[@class='page-heading' and text()='My account']"));
        Assertions.assertEquals("MY ACCOUNT", nadpisMyAccount.getText(), "uzivatel neni prihlasen");
    }

    @Test
    public void DBP2A_18_odhlaseniUzivatele() {
        prohlizec.navigate().to(URL_APLIKACE);
        klikniNaPrihlasit();
        prihlaseniUzivatele();
        klikniNaOdhlasit();


        WebElement nadpisAuthentication = prohlizec.findElement(By.xpath("//h1[@class='page-heading' and text() = 'Authentication']"));
        Assertions.assertEquals("AUTHENTICATION", nadpisAuthentication.getText(), "uzivatel neni odhlasen");
    }

    @Test
    public void DBP2A_18_LoginPriCheckoutu() throws InterruptedException {
        prohlizec.navigate().to(URL_APLIKACE);
        vyhledejVolnePokoje();
        vlozDoKosikuPrvniPokoj();
        Thread.sleep(2_000);
        jdiKPokladne();
        klikniTlacitkoProceedCheckout();
        klikniNaLoginVPokladne();
        Thread.sleep(2_000);
        klikniTlacitkoProceedPayment();
        prihlaseniUzivateleZPokladnyPresLogin();
        prihlaseniUzivateleZPokladnyPresLogin();

    }

    public void klikniNaLoginVPokladne() {
        WebElement tlacitkoLoginNow = prohlizec.findElement(By.xpath("//a[@id= 'openLoginFormBlock']"));
        tlacitkoLoginNow.click();
    }

    public void klikniNaPrihlasit() {
        WebElement zalozkaPrihlasit = prohlizec.findElement(By.xpath("//a[@class ='user_login navigation-link']"));
        zalozkaPrihlasit.click();
    }

    public void prihlaseniUzivatele() {
        WebElement polickoEmail = prohlizec.findElement(By.xpath("//input[@id='email']"));
        WebElement polickoHeslo = prohlizec.findElement(By.xpath("//input[@id='passwd']"));
        WebElement tlacitkoPrihlasit = prohlizec.findElement(By.xpath("//button[@id = 'SubmitLogin']"));
        polickoEmail.sendKeys("kavovacokolada@gmail.com");
        polickoHeslo.sendKeys("czechitas");
        tlacitkoPrihlasit.click();
    }
    public void prihlaseniUzivateleZPokladnyPresLogin() {
        WebElement polickoEmailPresLogin = prohlizec.findElement(By.xpath("//input[@id='login_email']"));
        WebElement polickoHesloPresLogin = prohlizec.findElement(By.xpath("//input[@id='login_passwd']"));
        WebElement tlacitkoPrihlasit = prohlizec.findElement(By.xpath("//button[@id = 'SubmitLogin']"));
        polickoEmailPresLogin.sendKeys("kavovacokolada@gmail.com");
        polickoHesloPresLogin.sendKeys("czechitas");
        tlacitkoPrihlasit.click();
    }
    public void klikniNaOdhlasit() {
        WebElement nabidkaUUzivatel = prohlizec.findElement(By.xpath("//span[@class = 'caret']"));
        nabidkaUUzivatel.click();
        WebElement polozkaOdhlasit = prohlizec.findElement(By.xpath("//a[text() = 'Logout']"));
        polozkaOdhlasit.click();
    }

    public void vyhledejVolnePokoje() {
        WebElement poleVyberHotel = prohlizec.findElement(By.id("id_hotel_button"));
        poleVyberHotel.click();
        poleVyberHotel.click();
        WebElement poleCheckIn = prohlizec.findElement(By.xpath("//input[@id='check_in_time']"));
        poleCheckIn.click();
        poleCheckIn.click();
        WebElement datumCheckIn = prohlizec.findElement(By.xpath("//td[@data-event ='click']//a[text()=20]"));
        datumCheckIn.click();
        WebElement poleCheckOut = prohlizec.findElement(By.xpath("//input[@id='check_out_time']"));
        poleCheckOut.click();
        WebElement datumCheckOut = prohlizec.findElement(By.xpath("//td[@data-event ='click']//a[text()=23]"));
        datumCheckOut.click();
        WebElement tlacitkoSearch = prohlizec.findElement(By.id("search_room_submit"));
        tlacitkoSearch.click();
    }

    public void vlozDoKosikuPrvniPokoj() {
        WebElement tlacitkoBookNow = prohlizec.findElement(By.xpath("//span[text() = 'Book Now'][1]"));
        tlacitkoBookNow.click();
    }

    public void jdiKPokladne() {
        WebElement tlacitkoProceedToCheckout = prohlizec.findElement(By.xpath("//div[@class = 'clearfix']//a[@title = 'Proceed to checkout']"));
        tlacitkoProceedToCheckout.click();
    }

    public void klikniTlacitkoProceedCheckout() {
        WebElement tlacitkoProceed = prohlizec.findElement(By.xpath("//div[@class = 'card']//a[@title = 'Proceed to checkout']"));
        tlacitkoProceed.click();
    }

    public void klikniTlacitkoProceedPayment() {
        WebElement tlacitkoProceed = prohlizec.findElement(By.xpath("//div[@class = 'card']//a[@title = 'Proceed to Payment']"));
        tlacitkoProceed.click();
    }
    public void vyhledejARezervujPokojAzKPokladne() {
        vyhledejVolnePokoje();
        vlozDoKosikuPrvniPokoj();
        jdiKPokladne();
        //nestihlo se


    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(3_000);
        prohlizec.quit();
    }
}
