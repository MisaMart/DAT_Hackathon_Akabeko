package cz.czechitas.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class TestyPrihlasovaniNaKurzy {

    public static final String URL_APLIKACE = "https://cz-test-jedna.herokuapp.com/";
    WebDriver prohlizec;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        prohlizec.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }


    @Test
    public void poPrihlaseniMusiBytZobrazenNapisPrihlasen() {
        prohlizec.navigate().to(URL_APLIKACE);
        klikniNaPrihlasit();
        prihlaseniUzivatele();

        WebElement napisPrihlasen = prohlizec.findElement(By.xpath("//span[text()='Přihlášen']"));
        Assertions.assertEquals("Přihlášen", napisPrihlasen.getText(), "uzivatel neni prihlasen");
    }

    @Test
    public void RodicVybereKurzPrihlasiSeAZapiseDite() {
        prohlizec.navigate().to(URL_APLIKACE);

        vyberTypKurzu();
        klikniNaVytvoritPrihlasku();
        prihlaseniUzivatele();
        vyplnPrihlasku();
        odesliPrihlasku();

        WebElement overeniPrihlaskyPresPokynyKPlatbe = prohlizec.findElement(By.xpath("//td[text() = 'Pokyny k platbě']"));
        Assertions.assertEquals("Pokyny k platbě",overeniPrihlaskyPresPokynyKPlatbe.getText(), "odeslani prihlasky nebylo overeno");
    }

    @Test
    public void RodicSePrihlasiVybereKurzAZapiseDite() {
        prohlizec.navigate().to(URL_APLIKACE);

        klikniNaPrihlasit();
        prihlaseniUzivatele();
        klikniVytvoritNovouPrihlaskuPoPrihlaseni();
        vyberTypKurzu();
        klikniNaVytvoritPrihlasku();
        vyplnPrihlasku();
        odesliPrihlasku();

        WebElement overeniPrihlaskyPresPokynyKPlatbe = prohlizec.findElement(By.xpath("//td[text() = 'Pokyny k platbě']"));
        Assertions.assertEquals("Pokyny k platbě",overeniPrihlaskyPresPokynyKPlatbe.getText(), "odeslani prihlasky nebylo overeno");
    }

    @Test
    public void rodicSePrihlasiAUpraviSveJmeno() {
        prohlizec.navigate().to(URL_APLIKACE);
        String noveJmenoAPrijmeni = "Jan Nebeský";

        klikniNaPrihlasit();
        prihlaseniUzivatele();
        otevriProfilRodice();
        opravJmenoVProfiluRodice(noveJmenoAPrijmeni);
        ulozZmenyVProfilu();

        WebElement jmenoPrihlaseneho = prohlizec.findElement(By.xpath("//a[@class='dropdown-toggle']"));
        Assertions.assertEquals(noveJmenoAPrijmeni, jmenoPrihlaseneho.getText(), "zmena jmena se neulozila");
    }

    public void klikniVytvoritNovouPrihlaskuPoPrihlaseni() {
        WebElement buttonVytvorNovouPrihlaskuPoPrihlaseni = prohlizec.findElement(By.xpath("//a[text()='Vytvořit novou přihlášku']"));
        buttonVytvorNovouPrihlaskuPoPrihlaseni.click();
    }

    public void vyberTypKurzu() {
        WebElement typKurzu = prohlizec.findElement(By.xpath("(//div[@class = 'card'])[2]//a[text()='Více informací']"));
        typKurzu.click();
    }

    public void klikniNaPrihlasit() {
        WebElement zalozkaPrihlasit = prohlizec.findElement(By.xpath("//i[@class='fa fa-user mr-2']"));
        zalozkaPrihlasit.click();
    }

    public void otevriProfilRodice() {
        WebElement klikniNaPrihlasen = prohlizec.findElement(By.xpath("//a[@class ='dropdown-toggle']"));
        klikniNaPrihlasen.click();
        WebElement polozkaProfil = prohlizec.findElement(By.xpath("//a[@href = 'https://cz-test-jedna.herokuapp.com/profil']"));
        polozkaProfil.click();
    }

    public void opravJmenoVProfiluRodice(String jmenoAPrijmeni) {
        WebElement polozkaJmenoAPrijmeni = prohlizec.findElement(By.id("name"));
        polozkaJmenoAPrijmeni.clear();
        polozkaJmenoAPrijmeni.sendKeys(jmenoAPrijmeni);
    }

    public void ulozZmenyVProfilu() {
        WebElement tlacitkoZmenit = prohlizec.findElement(By.xpath("//button[@type='submit']"));
        tlacitkoZmenit.click();
    }

    public void klikniNaVytvoritPrihlasku() {
        WebElement odkazVytvoritPrihlasku = prohlizec.findElement(By.xpath("//a[@href='https://cz-test-jedna.herokuapp.com/zaci/pridat/41-html-1']"));
        odkazVytvoritPrihlasku.click();
    }

    public void vyplnPrihlasku() {
        WebElement poleUkazTermin = prohlizec.findElement(By.xpath("//div[@class='filter-option-inner-inner']"));
        poleUkazTermin.click();
        WebElement poleVyberTermin = prohlizec.findElement(By.xpath("//div[@id = 'bs-select-1']"));
        poleVyberTermin.click();
        WebElement poleKrestniJmenoZaka = prohlizec.findElement(By.xpath("//input[@id='forename']"));
        poleKrestniJmenoZaka.sendKeys("Petr");
        WebElement polePrijmeniZaka = prohlizec.findElement(By.xpath("//input[@id='surname']"));
        polePrijmeniZaka.sendKeys("Nebeský");
        WebElement poleDatumNarozeniZaka = prohlizec.findElement(By.xpath("//input[@id='birthday']"));
        poleDatumNarozeniZaka.sendKeys("01.01.2010");
        WebElement zaskrtnoutPlatbaHotove = prohlizec.findElement(By.xpath("(//label[@class='custom-control-label'])[4]"));
        zaskrtnoutPlatbaHotove.click();
        WebElement souhlasSPodminkami = prohlizec.findElement(By.xpath("(//label[@class='custom-control-label']) [6]"));
        souhlasSPodminkami.click();
    }

    public void odesliPrihlasku() {
        WebElement vytvorPrihlaskuPoVyplneni = prohlizec.findElement(By.xpath("//input[@class='btn btn-primary mt-3']"));
        vytvorPrihlaskuPoVyplneni.click();
    }

    public void prihlaseniUzivatele() {
        WebElement polickoEmail = prohlizec.findElement(By.id("email"));
        WebElement polickoHeslo = prohlizec.findElement(By.id("password"));
        WebElement tlacitkoPrihlasit = prohlizec.findElement(By.xpath("//button[contains(text(),'Přihlásit')]"));
        polickoEmail.sendKeys("nebesky.email@email.cz");
        polickoHeslo.sendKeys("Honza001");
        tlacitkoPrihlasit.click();
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(3_000);
        prohlizec.quit();
    }
}
