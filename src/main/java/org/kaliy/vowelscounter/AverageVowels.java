package org.kaliy.vowelscounter;

import java.util.Objects;
import java.util.StringJoiner;

public class AverageVowels {
    private final Word wordVowels;
    private final int averageNumber;

    public AverageVowels(Word wordVowels, int averageNumber) {
        this.wordVowels = wordVowels;
        this.averageNumber = averageNumber;
    }

    public Word getWordVowels() {
        return wordVowels;
    }

    public int getAverageNumber() {
        return averageNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AverageVowels that = (AverageVowels) o;
        return averageNumber == that.averageNumber &&
               Objects.equals(wordVowels, that.wordVowels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordVowels, averageNumber);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AverageVowels.class.getSimpleName() + "[", "]")
                .add("wordVowels=" + wordVowels)
                .add("averageNumber=" + averageNumber)
                .toString();
    }
}
