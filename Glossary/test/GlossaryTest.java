import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;

public class GlossaryTest {

    private static final String TEST_INPUT_FILE = "data/terms.txt";
    private static final String TEST_OUTPUT_FOLDER = "output";

    /*
     * Helper method to create input file for testing.
     */
    public void createTestInputFile() throws IOException {
        String testContent = "meaning\nsomething that one wishes to convey, especially by language\n\n"
                + "term\na word whose definition is in a glossary\n\n"
                + "word\na string of characters in a language, which has at least one character\n\n"
                + "definition\na sequence of words that gives meaning to a term\n\n"
                + "glossary\na list of difficult or specialized terms, with their definitions,\n"
                + "usually near the end of a book\n\n"
                + "language\na set of strings of characters, each of which has meaning\n\n"
                + "book\na printed or written literary work\n\n";

        FileWriter writer = new FileWriter(TEST_INPUT_FILE);
        writer.write(testContent);
        writer.close();
    }

    /*
     * Tests for readGlossary
     */

    @Test
    public void testReadGlossary() throws IOException {
        this.createTestInputFile(); // Create input file for the test

        // Read the glossary
        Map<String, String> glossary = Glossary.readGlossary(TEST_INPUT_FILE);

        // Compare terms and definitions while trimming both actual and expected values
        assertEquals("meaning",
                "something that one wishes to convey, especially by language".trim(),
                glossary.value("meaning").trim());
        assertEquals("term", "a word whose definition is in a glossary".trim(),
                glossary.value("term").trim());
        assertEquals("word",
                "a string of characters in a language, which has at least one character"
                        .trim(),
                glossary.value("word").trim());
        assertEquals("definition",
                "a sequence of words that gives meaning to a term".trim(),
                glossary.value("definition").trim());
        assertEquals("definition",
                "a sequence of words that gives meaning to a term"
                        .replaceAll("[\r\n]+", "").trim(),
                glossary.value("definition").replaceAll("[\r\n]+", "").trim());
        assertEquals("book", "a printed or written literary work".trim(),
                glossary.value("book").trim());
    }

    /*
     * Tests for createIndexPage
     */

    @Test
    public void testCreateIndexPage() throws IOException {
        this.createTestInputFile(); // Create input file for the test

        // Prepare a mock glossary for testing index page creation
        Map<String, String> glossary = new Map1L<>();
        glossary.add("meaning",
                "something that one wishes to convey, especially by language");
        glossary.add("term", "a word whose definition is in a glossary");
        glossary.add("word",
                "a string of characters in a language, which has at least one character");
        glossary.add("definition", "a sequence of words that gives meaning to a term");
        glossary.add("glossary",
                "a list of difficult or specialized terms, with their definitions, usually near the end of a book");
        glossary.add("language",
                "a set of strings of characters, each of which has meaning");
        glossary.add("book", "a printed or written literary work");

        // Call createIndexPage
        Glossary.createIndexPage(glossary, TEST_OUTPUT_FOLDER);

        // Assert that the index.html file was created
        File indexFile = new File(TEST_OUTPUT_FOLDER + "/index.html");
        assertTrue("Index file was not created", indexFile.exists());

        // Read and verify the content of index.html
        BufferedReader reader = new BufferedReader(new FileReader(indexFile));
        String line = reader.readLine();
        boolean term1Found = false;
        boolean term2Found = false;
        boolean term3Found = false;
        boolean term4Found = false;
        boolean term5Found = false;
        boolean term6Found = false;
        boolean term7Found = false;

        // Check if the terms are listed in the index page
        while (line != null) {
            if (line.contains("meaning.html")) {
                term1Found = true;
            }
            if (line.contains("term.html")) {
                term2Found = true;
            }
            if (line.contains("word.html")) {
                term3Found = true;
            }
            if (line.contains("definition.html")) {
                term4Found = true;
            }
            if (line.contains("glossary.html")) {
                term5Found = true;
            }
            if (line.contains("language.html")) {
                term6Found = true;
            }
            if (line.contains("book.html")) {
                term7Found = true;
            }
            line = reader.readLine();
        }
        reader.close();

        assertTrue("Index content is incorrect, meaning not found", term1Found);
        assertTrue("Index content is incorrect, term not found", term2Found);
        assertTrue("Index content is incorrect, word not found", term3Found);
        assertTrue("Index content is incorrect, definition not found", term4Found);
        assertTrue("Index content is incorrect, glossary not found", term5Found);
        assertTrue("Index content is incorrect, language not found", term6Found);
        assertTrue("Index content is incorrect, book not found", term7Found);
    }

    /*
     * Tests for createTermPages
     */

    @Test
    public void testCreateTermPages() throws IOException {
        this.createTestInputFile(); // Create input file for the test

        // Prepare a mock glossary for testing term page creation
        Map<String, String> glossary = new Map1L<>();
        glossary.add("meaning",
                "something that one wishes to convey, especially by language");
        glossary.add("term", "a word whose definition is in a glossary");
        glossary.add("word",
                "a string of characters in a language, which has at least one character");
        glossary.add("definition", "a sequence of words that gives meaning to a term");
        glossary.add("glossary",
                "a list of difficult or specialized terms, with their definitions, usually near the end of a book");
        glossary.add("language",
                "a set of strings of characters, each of which has meaning");
        glossary.add("book", "a printed or written literary work");

        // Call createTermPages
        Glossary.createTermPages(glossary, TEST_OUTPUT_FOLDER);

        // Assert that individual term pages were created
        File term1File = new File(TEST_OUTPUT_FOLDER + "/meaning.html");
        File term2File = new File(TEST_OUTPUT_FOLDER + "/term.html");
        File term3File = new File(TEST_OUTPUT_FOLDER + "/word.html");
        File term4File = new File(TEST_OUTPUT_FOLDER + "/definition.html");
        File term5File = new File(TEST_OUTPUT_FOLDER + "/glossary.html");
        File term6File = new File(TEST_OUTPUT_FOLDER + "/language.html");
        File term7File = new File(TEST_OUTPUT_FOLDER + "/book.html");

        assertTrue("Term1 page was not created", term1File.exists());
        assertTrue("Term2 page was not created", term2File.exists());
        assertTrue("Term3 page was not created", term3File.exists());
        assertTrue("Term4 page was not created", term4File.exists());
        assertTrue("Term5 page was not created", term5File.exists());
        assertTrue("Term6 page was not created", term6File.exists());
        assertTrue("Term7 page was not created", term7File.exists());
    }
}
