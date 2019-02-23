package org.kaliy.vowelscounter.kernel;

import org.kaliy.vowelscounter.VowelsCounter;
import org.kaliy.vowelscounter.report.FileReporter;
import org.kaliy.vowelscounter.report.Reporter;
import org.kaliy.vowelscounter.source.FileSource;
import org.kaliy.vowelscounter.source.WordSource;

import java.io.IOException;
import java.nio.file.FileSystems;

public class Application {
    private final VowelsCounter counter;
    private final WordSource source;
    private final Reporter sink;

    public Application(VowelsCounter counter, WordSource source, Reporter sink) {
        this.counter = counter;
        this.source = source;
        this.sink = sink;
    }

    public void calculate() throws IOException {
        sink.report(counter.countVowels(source.createSource()));
    }

    public static void main(String... args) throws IOException {
        String inputFilename = args.length == 2 ? args[0] : "INPUT.TXT";
        String outputFilename = args.length == 2 ? args[1] : "OUTPUT.TXT";
        VowelsCounter vowelsCounter = new VowelsCounter();
        FileSource source = new FileSource(FileSystems.getDefault(), inputFilename);
        FileReporter sink = new FileReporter(FileSystems.getDefault(), outputFilename);

        new Application(vowelsCounter, source, sink).calculate();
    }
}
