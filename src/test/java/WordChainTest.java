import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class WordChainTest {

    @Test
    public void sameStartAndEnd_onlyStartAndEndReturned() throws Exception {
        WordChain wordChain = new WordChain();
        String[] result = wordChain.findSteps("cat", "cat");
        String[] expected = new String[] {"cat"};
        assertArrayEquals(expected, result);
    }

    @Test
    public void twoSteps_stepInBetweenReturned() throws Exception {
        WordChain wordChain = new WordChain();
        String[] result = wordChain.findSteps("cat", "cog");
        String[] expected = new String[] {"cat", "cot", "cog"};
        assertArrayEquals(expected, result);
    }

    @Test
    public void threeSteps_bothReturned() throws Exception {
        WordChain wordChain = new WordChain();
        String[] result = wordChain.findSteps("cat", "dog");
        String[] expected = new String[] {"cat", "cot", "cog", "dog"};
        assertArrayEquals(expected, result);
    }

    @Test
    public void reuseDataStructure() throws Exception {
        WordChain wordChain = new WordChain();

        String[] result = wordChain.findSteps("cat", "dog");
        String[] expected = new String[] {"cat", "cot", "cog", "dog"};
        assertArrayEquals(expected, result);

        result = wordChain.findSteps("dog", "cat");
        expected = new String[] {"dog", "cog", "cot", "cat"};
        assertArrayEquals(expected, result);
    }

    @Test
    public void longerWords() throws Exception {
        WordChain wordChain = new WordChain();

        String[] result = wordChain.findSteps("winter", "summer");
        String[] expected = new String[] {"cat", "cot", "cog", "dog"};
        assertArrayEquals(expected, result);
    }
}