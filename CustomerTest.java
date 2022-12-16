import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerTest {

    private static final Movie THE_GRINCH = new Movie("The Grinch", Movie.CHILDREN);
    private static final Movie BLACK_PANTHER = new Movie("Black Panther: Wakanda Forever", Movie.NEW_RELEASE);
    private static final Movie SPIDER_MAN = new Movie("Spiderman", Movie.REGULAR);

    private final Customer customer = new Customer("Tom");

    @Test
    public void childrenRental() {
        customer.addRental(new Rental(THE_GRINCH, 2));
        assertEquals(customer.statement(), expectedMessageFor("The Grinch", 1.5, 1.5, 1));
    }

    @Test
    public void shouldDiscountChildrensRentals() {
        customer.addRental(new Rental(THE_GRINCH, 4));
        assertEquals(customer.statement(), expectedMessageFor("The Grinch", 3.0, 3.0, 1));
    }

    @Test
    public void newReleaseRental() {
        customer.addRental(new Rental(BLACK_PANTHER, 1));
        assertEquals(customer.statement(), expectedMessageFor("Black Panther: Wakanda Forever", 3.0, 3.0, 1));
    }

    @Test
    public void shouldNotDiscountNewReleaseRentalsButBonusFrequentRenterPoints() {
        customer.addRental(new Rental(BLACK_PANTHER, 4));
        assertEquals(customer.statement(), expectedMessageFor("Black Panther: Wakanda Forever", 12.0, 12.0, 2)); 
    }

    @Test
    public void regularRental() {
        customer.addRental(new Rental(SPIDER_MAN, 2));
        assertEquals(customer.statement(), expectedMessageFor("Spiderman", 2.0, 2.0, 1));
    }

    @Test
    public void shouldDiscountRegularRental() {
        customer.addRental(new Rental(SPIDER_MAN, 4));
        assertEquals(customer.statement(), expectedMessageFor("Spiderman", 5.0, 5.0, 1));   
    }

    @Test
    public void shouldSumVariousRentals() {
        customer.addRental(new Rental(THE_GRINCH, 2));
        customer.addRental(new Rental(SPIDER_MAN, 1));
        customer.addRental(new Rental(BLACK_PANTHER, 3));
        assertEquals(customer.statement(), "Rental record for Tom\n\tThe Grinch\t1.5\n\tSpiderman\t2.0\n\tBlack Panther: Wakanda Forever\t9.0\nAmount owed is 12.5\nYou earned 4 frequent renter points");
    }
    
    private static String expectedMessageFor(String rental, double price, double total, int renterPointsEarned) {
        return "Rental record for Tom\n\t" + rental + "\t" + price + "\nAmount owed is " + total + "\nYou earned " + renterPointsEarned + " frequent renter points";
    }

}