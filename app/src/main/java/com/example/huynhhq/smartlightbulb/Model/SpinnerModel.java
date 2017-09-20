package com.example.huynhhq.smartlightbulb.Model;

/**
 * Created by HuynhHQ on 9/14/2017.
 */

public class SpinnerModel {
    private String LanguageName = "";
    private String Image = "";

    public void setLanguageName(String LanguageName) {this.LanguageName = LanguageName;}

    public void setImage (String Image) {this.Image = Image;}

    public String getImage() {
        return this.Image;
    }

    public String getLanguageName() {
        return this.LanguageName;
    }
}
