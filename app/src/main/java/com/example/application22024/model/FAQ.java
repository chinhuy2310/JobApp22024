package com.example.application22024.model;

import java.util.List;

public class FAQ {
    private String text;
    private List<String> buttons; // Các nút lựa chọn

    public FAQ(String text, List<String> buttons) {
        this.text = text;
        this.buttons = buttons;
    }

    public String getText() {
        return text;
    }

    public List<String> getButtons() {
        return buttons;
    }
}
