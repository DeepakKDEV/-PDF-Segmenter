package com.example.PDFSegmenter.PDFSegmenter.textBOx;

public class TextBlock {
    private String text;
    private float y;

    public TextBlock(String text, float y) {
        this.text = text;
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public float getY() {
        return y;
    }
}
