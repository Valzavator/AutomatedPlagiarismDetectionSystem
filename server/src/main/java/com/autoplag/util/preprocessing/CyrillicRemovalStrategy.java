package com.autoplag.util.preprocessing;

import java.util.function.UnaryOperator;

public class CyrillicRemovalStrategy implements UnaryOperator<String> {

    @Override
    public String apply(String str) {
        return str.replaceAll(
                "(\\p{IsCyrillic}+[\\p{Blank}\\p{Punct}]*)+",
                "/*deleted cyrillic text*/");
    }

}
