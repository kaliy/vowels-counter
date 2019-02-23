package org.kaliy.vowelscounter.report;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import org.kaliy.vowelscounter.AverageVowels;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.stream.Stream;

public class FileReporter implements Reporter {
    private final String filename;
    private final FileSystem fileSystem;
    private static final DecimalFormatSymbols DECIMAL_SYMBOLS = DecimalFormatSymbols.getInstance();

    static {
        DECIMAL_SYMBOLS.setDecimalSeparator('.');
    }

    public FileReporter(FileSystem fileSystem, String filename) {
        this.filename = filename;
        this.fileSystem = fileSystem;
    }

    @Override
    public void report(Stream<AverageVowels> averageVowelsStream) throws IOException {
        Stream<String> lines = averageVowelsStream.map(aw ->
                String.format(Locale.US, "({%s}, %d) -> %s",
                        Joiner.on(", ").join(Sets.newTreeSet(aw.getWordVowels().getChars())),
                        aw.getWordVowels().getLength(),
                        new DecimalFormat("#.##", DECIMAL_SYMBOLS).format(aw.getAverageNumber()))
        );
        Files.write(fileSystem.getPath(filename), (Iterable<String>) lines::iterator);
    }
}
