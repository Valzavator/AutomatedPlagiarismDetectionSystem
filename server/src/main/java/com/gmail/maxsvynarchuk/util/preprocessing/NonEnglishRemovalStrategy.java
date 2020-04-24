package com.gmail.maxsvynarchuk.util.preprocessing;

import java.util.function.UnaryOperator;

public class NonEnglishRemovalStrategy implements UnaryOperator<String> {
    @Override
    public String apply(String str) {
        return str.replaceAll("[^\\p{ASCII}]+([\\W\\d]*[^\\p{ASCII}]+)*",
                "/*deleted cyrillic text*/");
    }

}
