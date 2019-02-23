package org.kaliy.vowelscounter;

import java.util.Set;

public class CountResult {
    private final Set<Character> chars;
    private final int number;

    public CountResult(Set<Character> chars, int number) {
        this.chars = chars;
        this.number = number;
    }

    public Set<Character> getChars() {
        return chars;
    }

    public int getNumber() {
        return number;
    }
}
