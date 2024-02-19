package byog.Core;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestGame {
    @Test
        public void testGetSeed()
    {
        String input = "n323452S174070483Hello";
        String exp = "323452";
        assertEquals(exp,Game.getSeed(input));
        input="jk352ikopu435435qrwq";
        exp = "";
        assertEquals(exp,Game.getSeed(input));
        input = "lwqquwyeyq";
        exp = "";
        assertEquals(exp,Game.getSeed(input));
    }
    @Test
        public void testGetOption()
    {
        String input = "N543SWWWWAA";
        int exp = 0;
        assertEquals(exp, Game.getOption(input));
        input = "lrqwrqw423rqwr";
        exp = 1;
        assertEquals(exp, Game.getOption(input));
        input = "Lrqwrqwrqwr";
        exp = 1;
        assertEquals(exp, Game.getOption(input));
        input = "32wefsdql423:Q";
        exp = 2;
        assertEquals(exp, Game.getOption(input));
    }
}
