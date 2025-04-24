import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class Glossary {

    /**
     * Reads the input glossary file and returns a map of terms to their
     * definitions.
     *
     * @param fileName
     *            Name of the file to read.
     * @return A map of terms to their definitions.
     * @requires fileName is a valid path to an existing file with a glossary in
     *           the format of alternating lines of terms and definitions.
     * @ensures The returned map contains a key-value pair for each term and its
     *          corresponding definition from the input file.
     */
    public static Map<String, String> readGlossary(String fileName) {
        Map<String, String> glossary = new Map1L<>();

        SimpleReader reader = new SimpleReader1L(fileName);
        String term = "";
        StringBuilder definition = new StringBuilder();

        while (!reader.atEOS()) {
            String line = reader.nextLine().trim();
            if (line.isEmpty()) {
                // If the line is empty, save the term and its definition
                if (!term.isEmpty() && definition.length() > 0) {
                    glossary.add(term, definition.toString());
                    definition.setLength(0); // Reset definition for the next term
                }
                term = "";
            } else if (term.isEmpty()) {
                term = line; // The first non-empty line is the term
            } else {
                definition.append(line).append("\n"); // Append the definition lines
            }
        }

        reader.close();
        return glossary;
    }

    /**
     * Creates the index HTML page listing all terms.
     *
     * @param terms
     *            A map of terms to their definitions.
     * @param outputFolder
     *            The folder to save the output HTML files.
     * @requires terms is a non-null map of glossary terms with their
     *           definitions, and outputFolder is a valid folder path where
     *           files can be written.
     * @ensures An HTML file `index.html` is written to the specified folder,
     *          listing all the terms in alphabetical order, each linked to its
     *          corresponding term page.
     */
    public static void createIndexPage(Map<String, String> terms, String outputFolder) {
        SimpleWriter out = new SimpleWriter1L(outputFolder + "/index.html");

        // Output the header for the index page
        out.println("<html><head><title>Glossary</title></head><body>");
        out.println("<h1>Glossary Index</h1>");
        out.println("<ul>");

        // Loop over all terms, creating a link for each
        Set<String> sortedTerms = new Set1L<>();
        for (Map.Pair<String, String> entry : terms) {
            sortedTerms.add(entry.key());
        }

        // Sorting the terms alphabetically
        Set<String> sortedSet = new Set1L<>();
        for (String term : sortedTerms) {
            sortedSet.add(term);
        }

        for (String term : sortedSet) {
            out.println("<li><a href=\"" + term + ".html\">" + term + "</a></li>");
        }

        out.println("</ul>");
        out.println("</body></html>");

        out.close();
    }

    /**
     * Creates individual HTML pages for each term.
     *
     * @param terms
     *            A map of terms to their definitions.
     * @param outputFolder
     *            The folder to save the output HTML files.
     * @requires terms is a non-null map of glossary terms with their
     *           definitions, and outputFolder is a valid folder path where
     *           files can be written.
     * @ensures An HTML file for each term is written to the specified folder,
     *          with the term displayed in red, bold, and italics, followed by
     *          its definition. A "Return to Index" link is also included on
     *          each page.
     */
    public static void createTermPages(Map<String, String> terms, String outputFolder) {
        // Iterate over all terms and create an HTML page for each term
        Set<String> sortedTerms = new Set1L<>();
        for (Map.Pair<String, String> entry : terms) {
            sortedTerms.add(entry.key());
        }

        // Sorting the terms alphabetically
        Set<String> sortedSet = new Set1L<>();
        for (String term : sortedTerms) {
            sortedSet.add(term);
        }

        for (String term : sortedSet) {
            SimpleWriter out = new SimpleWriter1L(outputFolder + "/" + term + ".html");

            // Output the header for the term page
            out.println("<html><head><title>" + term + "</title></head><body>");
            out.println("<h1><i><b><font color=\"red\">" + term + "</font></b></i></h1>");
            out.println("<p>" + terms.value(term) + "</p>");

            // Add a return link to the index page
            out.println("<hr>Return to <a href=\"index.html\">Index</a>");

            // Close the page
            out.println("</body></html>");
            out.close();
        }
    }

    /**
     * Main method to run the glossary generation program.
     *
     * @param args
     *            Command line arguments (not used).
     * @requires The input file specified by the user must exist, and the output
     *           folder must be a valid path where files can be written.
     * @ensures Glossary pages are created based on the input file, with an
     *          index page and individual term pages. All files are written to
     *          the specified output folder.
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // Get the input file name and output folder from the user
        out.print("Enter input file name: ");
        String inputFileName = in.nextLine();
        out.print("Enter output folder name: ");
        String outputFolder = in.nextLine();

        // Read the glossary from the input file
        Map<String, String> glossary = readGlossary(inputFileName);

        // Create the index page
        createIndexPage(glossary, outputFolder);

        // Create individual term pages
        createTermPages(glossary, outputFolder);

        in.close();
        out.close();

        System.out.println("Glossary generation complete!");
    }
}
