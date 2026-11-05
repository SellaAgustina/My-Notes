package com.example.mynotes;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtJudul, edtIsi;
    Button btnSimpan, btnTema;
    ListView listCatatan;

    TextView txtJudulAplikasi;
    TextView txtSubjudul;
    TextView txtBuatCatatan;
    TextView txtCatatanSaya;

    View rootLayout;
    View contentLayout;

    DatabaseHelper databaseHelper;
    PrefsManager prefsManager;

    ArrayList<String> daftarCatatan;
    ArrayAdapter<String> adapter;

    boolean darkMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout = findViewById(R.id.rootLayout);
        contentLayout = findViewById(R.id.contentLayout);

        edtJudul = findViewById(R.id.edtJudul);
        edtIsi = findViewById(R.id.edtIsi);

        btnSimpan = findViewById(R.id.btnSimpan);
        btnTema = findViewById(R.id.btnTema);

        listCatatan = findViewById(R.id.listCatatan);

        txtJudulAplikasi = findViewById(R.id.txtJudulAplikasi);
        txtSubjudul = findViewById(R.id.txtSubjudul);
        txtBuatCatatan = findViewById(R.id.txtBuatCatatan);
        txtCatatanSaya = findViewById(R.id.txtCatatanSaya);

        databaseHelper = new DatabaseHelper(this);
        prefsManager = new PrefsManager(this);

        daftarCatatan = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                daftarCatatan
        );

        listCatatan.setAdapter(adapter);

        tampilkanCatatan();

        btnSimpan.setOnClickListener(v -> {

            String judul = edtJudul.getText().toString().trim();
            String isi = edtIsi.getText().toString().trim();

            if (judul.isEmpty() || isi.isEmpty()) {
                Toast.makeText(this,
                        "Judul dan isi harus diisi!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            boolean berhasil =
                    databaseHelper.tambahCatatan(judul, isi);

            if (berhasil) {
                Toast.makeText(this,
                        "Catatan berhasil disimpan!",
                        Toast.LENGTH_SHORT).show();

                edtJudul.setText("");
                edtIsi.setText("");

                tampilkanCatatan();
            }
        });

        btnTema.setOnClickListener(v -> {

            darkMode = !darkMode;

            prefsManager.simpanTema(darkMode);

            terapkanTema();
        });
    }

    private void tampilkanCatatan() {

        daftarCatatan.clear();

        daftarCatatan.addAll(
                databaseHelper.ambilSemuaCatatan()
        );

        adapter.notifyDataSetChanged();
    }

    private void terapkanTema() {

        if (darkMode) {

            rootLayout.setBackgroundColor(Color.rgb(37, 36, 58));
            contentLayout.setBackgroundColor(Color.rgb(37, 36, 58));

            txtJudulAplikasi.setTextColor(Color.WHITE);
            txtSubjudul.setTextColor(Color.LTGRAY);
            txtBuatCatatan.setTextColor(Color.WHITE);
            txtCatatanSaya.setTextColor(Color.WHITE);

            edtJudul.setTextColor(Color.WHITE);
            edtIsi.setTextColor(Color.WHITE);

            edtJudul.setHintTextColor(Color.LTGRAY);
            edtIsi.setHintTextColor(Color.LTGRAY);

            btnTema.setText("Ganti ke Tema Terang");

        } else {

            rootLayout.setBackgroundColor(Color.rgb(245, 243, 255));
            contentLayout.setBackgroundColor(Color.rgb(245, 243, 255));

            txtJudulAplikasi.setTextColor(Color.rgb(91, 62, 150));
            txtSubjudul.setTextColor(Color.rgb(119, 119, 119));
            txtBuatCatatan.setTextColor(Color.rgb(51, 51, 51));
            txtCatatanSaya.setTextColor(Color.rgb(51, 51, 51));

            edtJudul.setTextColor(Color.BLACK);
            edtIsi.setTextColor(Color.BLACK);

            edtJudul.setHintTextColor(Color.GRAY);
            edtIsi.setHintTextColor(Color.GRAY);

            btnTema.setText("Ganti ke Tema Gelap");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefsManager != null) {
            darkMode = prefsManager.ambilTema();
            terapkanTema();
        }
    }
}