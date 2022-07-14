package com.nascodefy.emosianpkm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password, age, address, organisasi;
    Spinner gender, kelas, sma;
    Button btn_register;
    TextView toLogin;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String userid;

    DatabaseReference databaseUsers;


    DatabaseReference reference;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Register");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sma = findViewById(R.id.pilihan_sma);
        organisasi = findViewById(R.id.organisasi);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        age = findViewById(R.id.umur);
        address = findViewById(R.id.address);
        gender = findViewById(R.id.jenis_kelamin);
        //role = findViewById(R.id.pilihan_role);
        btn_register = findViewById(R.id.btn_register);
        toLogin = findViewById(R.id.to_login);
        kelas = findViewById(R.id.pilihan_kelas);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(toLogin);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jenis_kelamin,
                R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> kelasAdapter = ArrayAdapter.createFromResource(this, R.array.kelas,
                R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> smaAdapter = ArrayAdapter.createFromResource(this, R.array.listsma,
                R.layout.support_simple_spinner_dropdown_item);

        gender.setAdapter(adapter);
        kelas.setAdapter(kelasAdapter);
        sma.setAdapter(smaAdapter);

        auth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);

        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingBar.setTitle("Sedang Melakukan Registrasi..");
                loadingBar.setMessage("Mohon Tunggu Beberapa Saat");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();


                final String txt_username = username.getText().toString();
                final String txt_email = email.getText().toString();
                final String txt_password = password.getText().toString();
                final String txt_age = age.getText().toString();
                final String txt_address = address.getText().toString();
                final String txt_gender = gender.getSelectedItem().toString();
                final String txt_sma = sma.getSelectedItem().toString();
                final String txt_organisasi = organisasi.getText().toString();
                final String txt_kelas = kelas.getSelectedItem().toString();


                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)
                        || TextUtils.isEmpty(txt_age) || TextUtils.isEmpty(txt_address) || TextUtils.isEmpty(txt_organisasi)){
                    Toast.makeText(RegisterActivity.this, "Harap Semua Kolom Di Isi", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                } else if (txt_password.length() < 6 ){
                    Toast.makeText(RegisterActivity.this, "Password Harus 6 Karakter", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                } else {
                    register(txt_username, txt_email, txt_password, txt_address, txt_age, txt_gender, txt_sma, txt_organisasi, txt_kelas);
                }
            }
        });
    }

    private void register(final String username, final String email, String password, final String address, final String age, final String gender, final String sma, final  String organisasi, final String kelas){

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();
                            String role = "Siswa";

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);


                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("email", email);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("search", username.toLowerCase());
                            hashMap.put("umur", age);
                            hashMap.put("alamat", address);
                            hashMap.put("role", role);
                            hashMap.put("jenis_kelamin", gender);
                            hashMap.put("sma", sma);
                            hashMap.put("organisasi", organisasi);
                            hashMap.put("kelas" , kelas);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        Toast.makeText(RegisterActivity.this, "Registrasi Berhasil, Silahkan Login", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });



                        } else {
                            Toast.makeText(RegisterActivity.this, "Email Sudah Terdaftar, Silahkan Gunakan Email lain", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
    }
}
