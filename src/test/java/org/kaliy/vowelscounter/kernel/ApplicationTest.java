package org.kaliy.vowelscounter.kernel;

import com.google.common.collect.Sets;

import org.junit.jupiter.api.Test;
import org.kaliy.vowelscounter.AverageVowels;
import org.kaliy.vowelscounter.VowelsCounter;
import org.kaliy.vowelscounter.Word;
import org.kaliy.vowelscounter.report.Reporter;
import org.kaliy.vowelscounter.source.WordSource;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApplicationTest {

    @Test
    @SuppressWarnings("unchecked")
    void usesSourceAndVowelsCounterToProduceToSink() throws IOException {
        Stream<String> sourceStream = Stream.of("first", "second");
        WordSource source = () -> sourceStream;

        Stream<AverageVowels> reporterReturnStream = Stream.of(new AverageVowels(new Word(Sets.newHashSet('a', 'o', 'e'), 7), 5f));
        VowelsCounter counter = mock(VowelsCounter.class);
        when(counter.countVowels(sourceStream)).thenReturn(reporterReturnStream);

        Reporter reporter = mock(Reporter.class);
        ArgumentCaptor<Stream> reporterArgumentCaptor = ArgumentCaptor.forClass(Stream.class);

        new Application(counter, source, reporter).calculate();

        verify(reporter).report(reporterArgumentCaptor.capture());
        assertThat(reporterArgumentCaptor.getValue()).isSameAs(reporterReturnStream);
    }
}
