package tests;

import base.BaseTest;
import pages.HomePage;
import pages.PerfumePage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

public class DouglasTest extends BaseTest {
    private PerfumePage perfumePage;

    @DataProvider(name = "filterData")
    public Object[][] getFilterData() throws IOException {
        List<Object[]> data = new ArrayList<>();
        String csvPath = "src/test/resources/filterData.csv"; 
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                
                line = line.replace("\uFEFF", "").trim();
                String[] values = line.split(",", 2); 
                data.add(values);
            }
        }
        return data.toArray(new Object[0][]);
    }
    @Test
    public void acceptCookie() {
        HomePage homePage = new HomePage(driver);
        homePage.openHomePage();
        homePage.acceptCookies();
        
    }
     @Test
    public void verifyPerfumeNavigation() {
        HomePage homePage = new HomePage(driver);
        homePage.openHomePage();
        homePage.acceptCookies();
        homePage.clickPerfumeLink();

        String url = homePage.getCurrentUrl();
        

        assertTrue(url.contains("/de/c/parfum/01"));
        
    }
     @BeforeMethod
    public void goToPerfumePage() {
        HomePage homePage = new HomePage(driver);
        homePage.openHomePage();
        homePage.acceptCookies();
        homePage.clickPerfumeLink();
        perfumePage = new PerfumePage(driver);
    }
      @Test(dataProvider = "filterData")
    public void verifyPerfumeProductListWithFilters(String filterName, String filterOption) {
        perfumePage.applyFilter(filterName, filterOption);

        List<WebElement> products = perfumePage.getProducts();
        assertTrue(products.size() > 0, "No products found for filter: " + filterName + " - " + filterOption);

        
        for (int i = 0; i < Math.min(3, products.size()); i++) {
            System.out.println(products.get(i).getText());
        }
    }
}

