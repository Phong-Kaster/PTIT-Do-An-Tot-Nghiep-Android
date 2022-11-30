package com.example.do_an_tot_nghiep.Model;

import com.example.do_an_tot_nghiep.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Phong-Kaster
 * @since 21-11-2022
 */
public class Option {
    private String name;
    private int icon;

    public Option() {
    }

    public Option(int icon, String name) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
