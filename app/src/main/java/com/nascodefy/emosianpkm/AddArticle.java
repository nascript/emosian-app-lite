package com.nascodefy.emosianpkm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.nascodefy.emosianpkm.FragmentsAdmin.BerandaFragmentAdmin;
import com.nascodefy.emosianpkm.Model.Article;
import com.nascodefy.emosianpkm.Model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddArticle extends AppCompatActivity {

    ImageView imageViewAddArticle;
    EditText addTitleArticle, addDescArticle;
    Button btn_submit_article;
    private static final int IMAGE_REQUEST = 1;

    private Uri imageUri;
    private StorageTask uploadTask;
    DatabaseReference reference;
    FirebaseUser fuser;
    String date;

    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        imageViewAddArticle = findViewById(R.id.add_img_article_view);
        addTitleArticle = findViewById(R.id.add_title_article_view);
        addDescArticle = findViewById(R.id.add_description_article_view);
        btn_submit_article = findViewById(R.id.submit_article);



        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        date = format.format(new Date());


        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        reference = FirebaseDatabase.getInstance().getReference("Artikel").child(date);

        //add img default
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("imageURL", "default");
        reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AddArticle.this, "Silahkan Tambah Gambar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //set & cek img after upload
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Article article = dataSnapshot.getValue(Article.class);

                if (article.getImageURL().equals("default")){
                    imageViewAddArticle.setImageResource(R.drawable.add_image);
                } else {
                    try {
                        Glide.with(AddArticle.this).load(article.getImageURL()).into(imageViewAddArticle);
                    } catch( Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        imageViewAddArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });

        btn_submit_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_titleArticle = addTitleArticle.getText().toString();
                String txt_descArticle = addDescArticle.getText().toString();

                if (TextUtils.isEmpty(txt_titleArticle) || TextUtils.isEmpty(txt_descArticle)) {
                    Toast.makeText(AddArticle.this, "Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else  {
                    saveArticle(txt_titleArticle, txt_descArticle);
                }
            }
        });
    }

    private void saveArticle(String txt_titleArticle, String txt_descArticle) {


        reference = FirebaseDatabase.getInstance().getReference("Artikel").child(date);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ArticleTitle", txt_titleArticle);
        hashMap.put("articleDesc", txt_descArticle);
        //hashMap.put("search", txt_titleArticle);
        hashMap.put("search", txt_titleArticle.toLowerCase());

        reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Toast.makeText(AddArticle.this, "Berhasil Menambah Artikel", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddArticle.this, LandingPageAdmin.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = AddArticle.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(AddArticle.this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  task.getException();
                    }

                    return  fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("Artikel").child(date);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", ""+mUri);
                        reference.updateChildren(map);

                        Toast.makeText(AddArticle.this, "Berhasil Menambah Gambar", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    } else {
                        Toast.makeText(AddArticle.this, "Gagal!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddArticle.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(AddArticle.this, "Tidak ada gambar terpilih", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(AddArticle.this, "Proses Upload Gambar", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }
}