package com.danosoftware.galaxyforce.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TextProvider {

    private final List<Text> text;
    private int characters;
    private boolean countCached;
    private boolean updated;

    public TextProvider() {
        this.text = new ArrayList<>();
        this.characters = 0;
        this.updated = false;
        this.countCached = true;
    }

    public void clear() {
        text.clear();
        characters = 0;
        countCached = true;
        updated = true;
    }

    public int count() {
        if (!countCached) {
            characters = countCharacters(text);
            countCached = true;
        }
        return characters;
    }

    public List<Text> text() {
        this.updated = false;
        return text;
    }

    public boolean hasUpdated() {
        return updated;
    }

    public void add(Text addText) {
        text.add(addText);
        characters += addText.getText().length();
        this.updated = true;
        this.countCached = false;
    }

    public void addAll(Collection<Text> addTexts) {
        text.addAll(addTexts);
        characters += countCharacters(addTexts);
        this.updated = true;
        this.countCached = false;
    }

    private int countCharacters(Collection<Text> texts) {
        int charCount = 0;
        for (Text text : texts) {
            charCount += text.getText().length();
        }
        return charCount;
    }
}
