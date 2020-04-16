package com.gmail.maxsvynarchuk.util.preprocessing;

import java.util.function.UnaryOperator;

public class CyrillicRemovalStrategy implements UnaryOperator<String> {

    @Override
    public String apply(String str) {
        return str.replaceAll(
                "(\\p{IsCyrillic}+[\\p{Space}\\p{Punct}]*)+",
                "/*deleted cyrillic text*/");
    }

}
