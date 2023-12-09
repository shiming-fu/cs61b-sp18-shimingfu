import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    static CharacterComparator offbyone = new OffByOne();

    @Test
    public void testEqualChars() {
        assertTrue(offbyone.equalChars('x', 'y'));
        assertTrue(offbyone.equalChars('r', 'q'));
        assertTrue(offbyone.equalChars('&', '%'));

        assertFalse(offbyone.equalChars('a', 'c'));
        assertFalse(offbyone.equalChars('a', 'a'));
        assertFalse(offbyone.equalChars('a', 'A'));
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
