package com.danosoftware.galaxyforce.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provides text to be drawn to the screen.
 * <p>
 * Initially starts as empty. Text to be drawn can be added.
 * <p>
 * Reports total characters cached and any changes since text was last retrieved.
 */
public class TextProvider {

    private final List<Text> text;
    private int characters;
    private boolean updatedSinceLastRetrieve;

    public TextProvider() {
        this.text = new ArrayList<>();
        this.characters = 0;
        this.updatedSinceLastRetrieve = false;
    }

    // empties the contents of any cached text
    public void clear() {
        text.clear();
        characters = 0;
        updatedSinceLastRetrieve = true;
    }

    // how many characters are contained in the cached text
    public int count() {
        return characters;
    }

    // return all cached text
    // also resets "updated since last retrieved" flag
    public List<Text> text() {
        this.updatedSinceLastRetrieve = false;
        return text;
    }

    // has any text changed since the last time text was retrieved?
    public boolean hasUpdated() {
        return updatedSinceLastRetrieve;
    }

    // add a single text item
    public void add(Text addText) {
        text.add(addText);
        characters += addText.getText().length();
        this.updatedSinceLastRetrieve = true;
    }

    // add a collection of text
    public void addAll(Collection<Text> addTexts) {
        for (Text text : addTexts) {
            add(text);
        }
    }
}
