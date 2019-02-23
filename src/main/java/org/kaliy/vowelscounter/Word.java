package org.kaliy.vowelscounter;

import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public class Word {
    private Set<Character> chars;
    private final int length;

    public Word(Set<Character> chars, int length) {
        this.chars = chars;
        this.length = length;
    }

    public Set<Character> getChars() {
        return chars;
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Word that = (Word) o;
        return length == that.length &&
               Objects.equals(chars, that.chars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chars, length);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Word.class.getSimpleName() + "[", "]")
                .add("chars=" + chars)
                .add("length=" + length)
                .toString();
    }
}
