package com.nascodefy.emosianpkm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.nascodefy.emosianpkm.Model.User;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextView forgot_password;

    EditText email, password;
    Button btn_login;


    DatabaseReference databaseUsers;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loadingBar = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.btn_login);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();


                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(LoginActivity.this, "Harap Isi Semua Kolom", Toast.LENGTH_SHORT).show();
                } else {

                    loadingBar.setTitle("Sedang Login..");
                    loadingBar.setMessage("Mohon Tunggu Beberapa Saat");
                    loadingBar.setCanceledOnTouchOutside(true);
                    loadingBar.show();

                    auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        FirebaseUser firebaseUser = auth.getCurrentUser();
                                        final String userID = firebaseUser.getUid();

                                        databaseUsers.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                User user = dataSnapshot.getValue(User.class);
                                                assert user != null;

                                                loadingBar.dismiss();
                                                Log.v("Login", user.role);

                                                if (user.role.equals("Admin")) {
                                                    Intent intent = new Intent(LoginActivity.this, LandingPageAdmin.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                } else if (user.role.equals("Siswa")) {
                                                    Intent intent = new Intent(LoginActivity.this, LandingPageSiswa.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }else {
                                                    Toast.makeText(LoginActivity.this, "Role Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                loadingBar.dismiss();
                                                Toast.makeText(LoginActivity.this,databaseError.toString() , Toast.LENGTH_SHORT);

                                            }

                                        });

                                    } else {
                                        Toast.makeText(LoginActivity.this, "Autentikasi Gagal", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });
                }
            }
        });

    }


    public void toRegistUser(View view) {
        Intent toRegistUser = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(toRegistUser);
    }


}
