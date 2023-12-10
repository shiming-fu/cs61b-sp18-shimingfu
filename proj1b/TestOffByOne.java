import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('x', 'y'));
        assertTrue(offByOne.equalChars('r', 'q'));
        assertTrue(offByOne.equalChars('&', '%'));

        assertFalse(offByOne.equalChars('a', 'c'));
        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('a', 'A'));
    }
    /*
     * // You must use this CharacterComparator and not instantiate
     * // new ones, or the autograder might be upset.
     * static CharacterComparator offByOne = new OffByOne();
     * 
     * // Your tests go here.
     * Uncomment this class once you've created your CharacterComparator interface
     * and OffByOne class.
     **/
}
