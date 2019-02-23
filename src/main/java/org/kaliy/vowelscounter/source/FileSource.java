package org.kaliy.vowelscounter.source;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileSource implements WordSource {
    private final FileSystem fileSystem;
    private final String filename;
    private final List<String> words; // TODO: there is no need to store everything in memory - use streams

    public FileSource(FileSystem fileSystem, String filename) {
        this.fileSystem = fileSystem;
        this.filename = filename;
        try (Stream<String> lines = Files.lines(fileSystem.getPath(filename))) {
            words = lines.flatMap(Pattern.compile("\\W+")::splitAsStream).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to read the file " + filename, e);
        }
    }

    @Override
    public Stream<String> createSource() {
        return words.stream();
    }
}
