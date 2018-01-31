package com.danosoftware.galaxyforce.options;

public enum OptionController implements Option
{

    JOYSTICK("JOY"), ACCELEROMETER("TILT"), DRAG("DRAG");

    private String text = null;

    private OptionController(String text)
    {
        this.text = text;
    }

    @Override
    public String getText()
    {
        return text;
    }

}
