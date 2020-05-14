package com.gmail.maxsvynarchuk.facade.converter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public interface Converter<SOURCE, TARGET> {

    TARGET convert(SOURCE source);

    default List<TARGET> convertAll(Collection<SOURCE> sourceCollection) {
       return sourceCollection.stream()
               .map(this::convert)
               .collect(Collectors.toList());
    }

}
