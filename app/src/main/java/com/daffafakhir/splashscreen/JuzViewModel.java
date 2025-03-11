package com.daffafakhir.splashscreen;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class JuzViewModel extends ViewModel {
    private List<JuzModel> juzList;

    public List<JuzModel> getJuzList() {
        if (juzList == null) {
            juzList = new ArrayList<>();
            // Inisialisasi data, misalnya:
            for (int i = 1; i <= 30; i++) {
                juzList.add(new JuzModel("Juz " + i, false));
            }
        }
        return juzList;
    }
}