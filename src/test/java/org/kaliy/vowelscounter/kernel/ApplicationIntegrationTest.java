package org.kaliy.vowelscounter.kernel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationIntegrationTest {

    public static final String DEFAULT_INPUT_FILE = "INPUT.TXT";
    public static final String DEFAULT_OUTPUT_FILE = "OUTPUT.TXT";
    public static final String CUSTOM_INPUT_FILE = "anotherinput.txt";
    public static final String CUSTOM_OUTPUT_FILE = "anotheroutput.txt";

    @AfterEach
    void tearDown() throws IOException {
        removeFile(DEFAULT_INPUT_FILE);
        removeFile(DEFAULT_OUTPUT_FILE);
        removeFile(CUSTOM_INPUT_FILE);
        removeFile(CUSTOM_OUTPUT_FILE);
    }

    private void removeFile(String filename) throws IOException {
        Files.deleteIfExists(Paths.get(filename));
    }

    @Test
    void processesInputWithDefaultFilenames() throws IOException {
        Files.write(Paths.get(DEFAULT_INPUT_FILE), Arrays.asList("Platon made bamboo boats.", "Platon made bamboo boats."));

        Application.main();

        assertThat(Files.lines(Paths.get(DEFAULT_OUTPUT_FILE))).containsExactly("({a, o}, 5) -> 2", "({a, o}, 6) -> 2.5", "({a, e}, 4) -> 2");
    }

    @Test
    void processesInputWithCustomFilenames() throws IOException {
        Files.write(Paths.get(CUSTOM_INPUT_FILE), Arrays.asList("Platon made bamboo boats.", "Platon made bamboo boats."));

        Application.main(CUSTOM_INPUT_FILE, CUSTOM_OUTPUT_FILE);

        assertThat(Files.lines(Paths.get(CUSTOM_OUTPUT_FILE))).containsExactly("({a, o}, 5) -> 2", "({a, o}, 6) -> 2.5", "({a, e}, 4) -> 2");
    }
}
