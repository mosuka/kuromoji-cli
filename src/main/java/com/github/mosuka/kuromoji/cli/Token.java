package com.github.mosuka.kuromoji.cli;

import java.util.List;

public class Token {
    public String surface;
    public List<String> attrs;

    public Token(String surface, List<String> attrs) {
        this.surface = surface;
        this.attrs = attrs;
    }

    public boolean equals(Object obj) {
        Token token = (Token) obj;
        boolean status = false;

        if (this.surface.equals(token.surface)
        && this.attrs.equals(token.attrs)) {
            status = true;
        }

        return status;
    }
}
