package org.kaliy.vowelscounter.source;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FileSourceTest {
    private FileSystem mockFs = Jimfs.newFileSystem(Configuration.unix());
    private static final String FILENAME = "pikachu.txt";

    private void writeToTestFile(String... lines) throws IOException {
        Files.write(mockFs.getPath(FILENAME), Arrays.asList(lines), StandardCharsets.UTF_8);
    }

    @Test
    void createsSourceFromASingleWordInASingleLine() throws IOException {
        writeToTestFile("Naruto");

        FileSource source = new FileSource(mockFs, FILENAME);

        assertThat(source.createSource().collect(Collectors.toSet())).containsOnly("Naruto");
    }

    @Test
    void createsSourceFromMultipleWordsInASingleLine() throws IOException {
        writeToTestFile("Naruto Uzumaki");

        FileSource source = new FileSource(mockFs, FILENAME);

        assertThat(source.createSource().collect(Collectors.toSet())).containsOnly("Naruto", "Uzumaki");
    }

    @Test
    void createsSourceFromMultipleWordsInMultipleLines() throws IOException {
        writeToTestFile("Naruto Uzumaki", "Uchiha Sasuke");

        FileSource source = new FileSource(mockFs, FILENAME);

        assertThat(source.createSource().collect(Collectors.toSet())).containsOnly("Naruto", "Uzumaki", "Uchiha", "Sasuke");
    }

    @Test
    void ignoresPunctuationMarks() throws IOException {
        writeToTestFile("Hey, what's going on? Come back!");

        FileSource source = new FileSource(mockFs, FILENAME);

        assertThat(source.createSource().collect(Collectors.toSet())).containsOnly("Hey", "what", "s", "going", "on", "Come", "back");
    }

    @Test
    void throwsAnExceptionIfFileIsNotFound() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                new FileSource(mockFs, "Random.txt")
        ).withMessage("Unable to read the file Random.txt");
    }
}
