package org.kaliy.vowelscounter.report;

import com.google.common.collect.Sets;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import org.junit.jupiter.api.Test;
import org.kaliy.vowelscounter.AverageVowels;
import org.kaliy.vowelscounter.Word;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FileReporterTest {
    private static final String FILENAME = "pikachu.txt";
    private FileSystem mockFs = Jimfs.newFileSystem(Configuration.unix());
    private FileReporter fileReporter = new FileReporter(mockFs, FILENAME);

    @Test
    void writesSingleResultToATextFile() throws IOException {
        fileReporter.report(Stream.of(
                new AverageVowels(new Word(Sets.newHashSet('a', 'o'), 6), 2.5f)
        ));

        assertThat(readFile()).containsExactly("({a, o}, 6) -> 2.5");
    }

    @Test
    void writesMultipleResultsToATextFile() throws IOException {
        fileReporter.report(Stream.of(
                new AverageVowels(new Word(Sets.newHashSet('a', 'o', 'e'), 7), 5f),
                new AverageVowels(new Word(Sets.newHashSet('a', 'o'), 6), 2.5f),
                new AverageVowels(new Word(Sets.newHashSet('a', 'i', 'u'), 7), 3)
        ));

        assertThat(readFile()).containsExactly("({a, e, o}, 7) -> 5", "({a, o}, 6) -> 2.5", "({a, i, u}, 7) -> 3");
    }

    @Test
    void roundsAverageNumbersToTwoDigits() throws IOException {
        fileReporter.report(Stream.of(
                new AverageVowels(new Word(Sets.newHashSet('a'), 6), 2.666f)
        ));

        assertThat(readFile()).containsExactly("({a}, 6) -> 2.67");
    }

    @Test
    void supportsBigAverageNumbers() throws IOException {
        fileReporter.report(Stream.of(
                new AverageVowels(new Word(Sets.newHashSet('a'), 6), 666013)
        ));

        assertThat(readFile()).containsExactly("({a}, 6) -> 666013");
    }

    private Stream<String> readFile() throws IOException {
        return Files.lines(mockFs.getPath(FILENAME));
    }
}
