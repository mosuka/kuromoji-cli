// Copyright (c) 2022 Minoru Osuka
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// 		http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.github.mosuka.kuromoji.cli;

import java.util.List;

public class Token {
    private String surface;
    private List<String> attrs;

    public Token(String surface, List<String> attrs) {
        this.surface = surface;
        this.attrs = attrs;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public List<String> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<String> attrs) {
        this.attrs = attrs;
    }

    public boolean equals(Object obj) {
        Token token = (Token) obj;
        boolean status = false;

        if (this.getSurface().equals(token.getSurface())
        && this.getAttrs().equals(token.getAttrs())) {
            status = true;
        }

        return status;
    }
}
