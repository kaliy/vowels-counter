package org.kaliy.vowelscounter;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VowelsCounter {
    private final static Set<Character> VOWELS = Sets.newHashSet('a', 'e', 'i', 'o', 'u');

    public Stream<AverageVowels> countVowels(Stream<String> source) {
        return source.map(s -> {
            List<Character> allVowels = s.chars()
                    .map(Character::toLowerCase)
                    .mapToObj(c -> (char) c)
                    .filter(VOWELS::contains)
                    .collect(Collectors.toList());
            System.out.println(allVowels);
            return new AverageVowels(new Word(Sets.newHashSet(allVowels), s.length()), allVowels.size());
        });
    }

}
