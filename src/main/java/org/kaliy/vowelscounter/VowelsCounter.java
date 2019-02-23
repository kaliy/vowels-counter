package org.kaliy.vowelscounter;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;

public class VowelsCounter {
    private final static Set<Character> VOWELS = Sets.newHashSet('a', 'e', 'i', 'o', 'u');

    public Stream<AverageVowels> countVowels(Stream<String> source) {
        return source.map(s -> {
            List<Character> allVowels = s.chars()
                    .map(Character::toLowerCase)
                    .mapToObj(c -> (char) c)
                    .filter(VOWELS::contains)
                    .collect(Collectors.toList());
            return new AverageVowels(new Word(Sets.newHashSet(allVowels), s.length()), allVowels.size());
        }).collect(groupingBy(AverageVowels::getWordVowels, averagingDouble(AverageVowels::getAverageNumber)))
                .entrySet().stream().map(entry -> new AverageVowels(entry.getKey(), entry.getValue()));
    }

}
