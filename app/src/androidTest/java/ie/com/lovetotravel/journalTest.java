package ie.com.lovetotravel;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import ie.com.lovetotravel.models.Journal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

@RunWith(AndroidJUnit4.class)
public class journalTest {

    Journal jTest = new Journal("Entry", "This is a test journal entry to test the model class", "Tuesday, 26 February 2018", false);

    @Test
    public void testCreate() {
        assertEquals("Entry", jTest.getTitle());
        assertEquals("This is a test journal entry to test the model class", jTest.getEntry());
        assertEquals("Tuesday, 26 February 2018", jTest.getDate());
        assertEquals(false, jTest.isFavourite());
    }

    @Test
    public void testEquals()
    {
        Journal jTest2   = new Journal ("Entry2", "This is a second test journal entry to test the model class", "Wednesday, 27 February 2018", true);

        assertEquals(jTest, jTest);
        assertSame(jTest, jTest);
        assertNotSame(jTest, jTest2);
        assertNotEquals(jTest, jTest2);
    }

}
