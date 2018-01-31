package com.danosoftware.galaxyforce.options;

public enum OptionVibration implements Option
{

    ON("ON"), OFF("OFF");

    private String text = null;

    private OptionVibration(String text)
    {
        this.text = text;
    }

    @Override
    public String getText()
    {
        return text;
    }

}
