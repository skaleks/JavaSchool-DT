package com.magenta.crud.type;


public enum OptionRelative {

    EXCLUDED("EXCLUDED"),
    RELATED("RELATED");

    String optionRelative;

    OptionRelative(String optionRelative) {
        this.optionRelative = optionRelative;
    }

    public String getRule() {
        return optionRelative;
    }
}
