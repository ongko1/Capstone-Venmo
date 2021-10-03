package com.techelevator;
import com.techelevator.item.*;
import org.junit.*;
import static junit.framework.TestCase.assertEquals;

public class ItemTest {
    private Item itemTest;
    @Test
    public void return_price_as_string() {
        itemTest = new Drink("Cola", 1.25,5);
        Assert.assertEquals("1.25", itemTest.getPriceAsString());
    }
    @Test
    public void return_price_as_int_pennies_over_one_dollar() {
        itemTest = new Candy("Wonka Bar", 1.50,5);
        assertEquals(150.0, itemTest.getPriceInPennies());
    }
    @Test
    public void return_price_as_int_pennies_under_one_dollar() {
        itemTest = new Gum("U-Chews", 0.85, 5);
        assertEquals(85.0, itemTest.getPriceInPennies());
    }
}