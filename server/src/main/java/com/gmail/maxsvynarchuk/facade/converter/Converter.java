package com.gmail.maxsvynarchuk.facade.converter;

import java.util.Collection;
import java.util.stream.Collectors;

public interface Converter<SOURCE, TARGET> {

    TARGET convert(SOURCE source);

    default Collection<TARGET> convertAll(Collection<SOURCE> sourceCollection) {
       return sourceCollection.stream()
               .map(this::convert)
               .collect(Collectors.toList());
    }

}
