package com.parser.utilities;

public class Builder {
    public String getOutput_path() {
        return output_path;
    }

    public void setOutput_path(String output_path) {
        this.output_path = output_path;
    }

    private String output_path;

    public Builder ( String output_path){
        this.output_path = output_path;
    }
}
