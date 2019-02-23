package org.kaliy.vowelscounter.source;

import java.util.stream.Stream;

@FunctionalInterface
public interface WordSource {
    Stream<String> createSource();
}
