package com.daffafakhir.splashscreen;

public class JuzModel {
    private String namaJuz;
    private boolean isChecked;

    public JuzModel(String namaJuz, boolean isChecked) {
        this.namaJuz = namaJuz;
        this.isChecked = isChecked;
    }

    public String getNamaJuz() {
        return namaJuz;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
