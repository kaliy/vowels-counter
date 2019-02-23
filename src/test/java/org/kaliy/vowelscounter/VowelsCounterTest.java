package org.kaliy.vowelscounter;

import com.google.common.collect.Sets;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class VowelsCounterTest {

    private VowelsCounter vowelsCounter = new VowelsCounter();

    @Test
    void countsVowelsInASingleWordWithNoRepeatedVowels() {
        Stream<AverageVowels> result = vowelsCounter.countVowels(Stream.of("pikachu"));
        assertThat(result).containsOnly(new AverageVowels(
                new Word(Sets.newHashSet('a', 'i', 'u'), 7), 3
        ));
    }

    @Test
    void countsVowelsInASingleWordWithRepeatedVowels() {
        Stream<AverageVowels> result = vowelsCounter.countVowels(Stream.of("charmander"));
        assertThat(result).containsOnly(new AverageVowels(
                new Word(Sets.newHashSet('a', 'e'), 10), 3
        ));
    }

    @Test
    void ignoresCaseOnCountingVowels() {
        Stream<AverageVowels> result = vowelsCounter.countVowels(Stream.of("pIkachU"));
        assertThat(result).containsOnly(new AverageVowels(
                new Word(Sets.newHashSet('a', 'i', 'u'), 7), 3
        ));
    }

    @Test
    void calculatesAverageInTwoWords() {
        Stream<AverageVowels> result = vowelsCounter.countVowels(Stream.of("bamboo", "platon"));
        assertThat(result).containsOnly(new AverageVowels(
                new Word(Sets.newHashSet('a', 'o'), 6), 2.5f
        ));
    }

    @Test
    void calculatesAverageInMultipleWords() {
        Stream<AverageVowels> result = vowelsCounter.countVowels(Stream.of("WaWoWeW", "aaaoooe", "aaWooWe"));
        assertThat(result).containsOnly(new AverageVowels(
                new Word(Sets.newHashSet('a', 'o', 'e'), 7), 5f //(3+7+5)/3=5
        ));
    }

    @Test
    void calculatesAverageForMultipleWordsWithDifferentVowelsNumber() {
        Stream<String> inputStream = Stream.of("AAWOOWE", "bamboo", "WaWoWeW", "platon", "Pikachu", "aaaoooe");

        Stream<AverageVowels> result = vowelsCounter.countVowels(inputStream);

        assertThat(result).containsOnly(
                new AverageVowels(new Word(Sets.newHashSet('a', 'o', 'e'), 7), 5f),
                new AverageVowels(new Word(Sets.newHashSet('a', 'o'), 6), 2.5f),
                new AverageVowels(new Word(Sets.newHashSet('a', 'i', 'u'), 7), 3)
        );
    }
}
