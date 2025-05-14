package com.daffafakhir.splashscreen;


import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;

public class DoaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doa);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Kumpulan Doa");
        }

        // Inisialisasi data doa
        ArrayList<HashMap<String, String>> doaList = new ArrayList<>();

        // Tambahkan data doa
        addDoa(doaList, "Doa Buka Puasa ",
                "اللَّهُمَّ لَكَ صُمْتُ وَعَلَى رِزْقِكَ أَفْطَرْتُ، ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ، وَثَبَتَ الْأَجْرُ إِنْ شَاءَ اللَّهُ",
                "Allaahumma laka sumtu wa 'alaa rizqika afthartu, dzahabazh zhama'u wabtallatil 'uruuqu, wa tsabatal ajru insyaa Allah",
                "Ya Allah, untuk-Mu aku berpuasa dan dengan rezeki-Mu aku berbuka. Telah hilang dahaga, telah basah kerongkongan dan semoga ada pahala yang ditetapkan, insya Allah");

        addDoa(doaList, "Doa Sahur ",
                "اَللّٰهُمَّ اِنِّىْ نَوَيْتُ صَوْمَ غَدٍ فَرْضًا لِشَهْرِ رَمَضَانَ هٰذِهِ السَّنَةِ لِلّٰهِ تَعَالَى",
                "Allahumma inni nawaitu shauma ghadin fardhan li syahri Ramadhaana haadzihis sanati lillaahi ta'aalaa",
                "Ya Allah, sesungguhnya aku berniat puasa esok hari untuk menunaikan kewajiban di bulan Ramadhan tahun ini, karena Allah Ta'ala");

        addDoa(doaList, "Doa Sebelum Makan",
                "اَللّٰهُمَّ بَارِكْ لَنَا فِيْمَا رَزَقْتَنَا وَقِنَا عَذَابَ النَّارِ",
                "Alloohumma barik lanaa fiimaa rozaqtanaa waqinaa 'adzaa ban naar",
                "Ya Allah, berkahilah rezeki yang telah Engkau berikan kepada kami dan lindungilah kami dari siksa api neraka");

        addDoa(doaList, "Doa Sesudah Makan",
                "اَلْحَمْدُ لِلّٰهِ الَّذِىْ اَطْعَمَنَا وَسَقَانَا وَجَعَلَنَا مِنَ الْمُسْلِمِيْنَ",
                "Alhamdu lillaahil ladzii ath'amanaa wa saqoonaa wa ja'alanaa minal muslimiin",
                "Segala puji bagi Allah yang telah memberi kami makan dan minum serta menjadikan kami termasuk golongan orang-orang Islam");

        addDoa(doaList, "Doa Sebelum Tidur",
                "بِاسْمِكَ اللّٰهُمَّ اَحْيَا وَاَمُوْتُ",
                "Bismika allahumma ahya wa amuut",
                "Dengan nama-Mu ya Allah, aku hidup dan aku mati");

        addDoa(doaList, "Doa Bangun Tidur",
                "اَلْحَمْدُ لِلّٰهِ الَّذِىْ اَحْيَانَا بَعْدَ مَا اَمَاتَنَا وَاِلَيْهِ النُّشُوْرُ",
                "Alhamdu lillaahil ladzii ahyaanaa ba'da maa amaatanaa wa ilaihin nusyuur",
                "Segala puji bagi Allah yang telah menghidupkan kami setelah mematikan kami dan kepada-Nya kami dibangkitkan");

        addDoa(doaList, "Doa Masuk Masjid",
                "اَللّٰهُمَّ افْتَحْ لِيْ اَبْوَابَ رَحْمَتِكَ",
                "Allahummaf tahlii abwaaba rohmatik",
                "Ya Allah, bukakanlah pintu-pintu rahmat-Mu untukku");

        addDoa(doaList, "Doa Keluar Masjid",
                "اَللّٰهُمَّ اِنِّى اَسْأَلُكَ مِنْ فَضْلِكَ",
                "Allahumma innii as-aluka min fadllik",
                "Ya Allah, sesungguhnya aku memohon keutamaan dari-Mu");

        addDoa(doaList, "Doa Belajar",
                "رَبِّ زِدْنِيْ عِلْمًا وَارْزُقْنِيْ فَهْمًا",
                "Rabbi zidnii 'ilmaa warzuqnii fahmaa",
                "Ya Tuhanku, tambahkanlah ilmuku dan berilah aku pemahaman");

        addDoa(doaList, "Doa untuk Kedua Orang Tua",
                "رَبِّ اغْفِرْ لِيْ وَلِوَالِدَيَّ وَارْحَمْهُمَا كَمَا رَبَّيَانِيْ صَغِيْرًا",
                "Rabbighfirlii wa liwaalidayya warhamhumaa kamaa rabbayaanii shaghiiraa",
                "Ya Tuhanku, ampunilah aku dan kedua orang tuaku, sayangilah mereka sebagaimana mereka menyayangiku di waktu kecil");

        // Tambah doa ketika sakit
        addDoa(doaList, "Doa Ketika Sakit",
                "اللَّهُمَّ رَبَّ النَّاسِ، أَذْهِبِ الْبَاسَ، اشْفِ أَنْتَ الشَّافِي، لاَ شِفَاءَ إِلاَّ شِفَاؤُكَ، شِفَاءً لاَ يُغَادِرُ سَقَمًا",
                "Allaahumma rabban naas, adzhibil ba's, isyfi antasy-syaafii, laa syifaa'a illaa syifaa'uk, syifaa'an laa yughaadiru saqaman",
                "Ya Allah, Tuhan sekalian manusia, hilangkanlah penyakit, sembuhkanlah, Engkaulah yang menyembuhkan, tidak ada kesembuhan kecuali kesembuhan dari-Mu, kesembuhan yang tidak meninggalkan penyakit");

        // Set adapter
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                doaList,
                R.layout.item_doa,
                new String[]{"nama", "arab", "latin", "arti"},
                new int[]{R.id.tvNamaDoa, R.id.tvArabDoa, R.id.tvLatinDoa, R.id.tvArtiDoa}
        );

        ListView listView = findViewById(R.id.listViewDoa);
        listView.setAdapter(adapter);
    }

    private void addDoa(
            ArrayList<HashMap<String, String>> list,
            String nama,
            String arab,
            String latin,
            String arti
    ) {
        HashMap<String, String> map = new HashMap<>();
        map.put("nama", nama);
        map.put("arab", arab);
        map.put("latin", latin);
        map.put("arti", arti);
        list.add(map);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}