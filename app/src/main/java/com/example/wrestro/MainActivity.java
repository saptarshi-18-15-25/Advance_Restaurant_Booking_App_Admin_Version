package com.example.wrestro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 71;
    Button cBtn,uBtn,aBtn;
    Uri filePath,downloadUri;
    ImageView iv,iShow;
    StorageReference storageReference;

    String D_name;
   // String dwnldUrl;
    String generatedFilePath;

    EditText dishName;
    //Button btn, sbtn;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cBtn = findViewById(R.id.choose);
        uBtn = findViewById(R.id.uBtn);
        iv = findViewById(R.id.iv);
       // iShow = findViewById(R.id.ivIEW);
        aBtn = findViewById(R.id.addDt);

        dishName = findViewById(R.id.dish_Name);


        //storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Dish");
        storageReference = FirebaseStorage.getInstance().getReference();

        cBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

       uBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                D_name = dishName.getText().toString().trim();

                uploadImage();


            }
        });

       aBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(MainActivity.this,Main2Activity.class);
               i.putExtra("DN",D_name);
               i.putExtra("DLink",generatedFilePath);


               Toast.makeText(MainActivity.this,"## Stored path is "+generatedFilePath,Toast.LENGTH_LONG).show();

               startActivity(i);
           }
       });


    }

    private void uploadImage() {

        if(filePath != null)
        {
            final StorageReference ref = storageReference.child(D_name);
            // Toast.makeText(MainActivity.this,"Up",Toast.LENGTH_LONG).show();
            ref.child(D_name).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(MainActivity.this,"Uploaded Successfully",Toast.LENGTH_LONG).show();

                  //generatedFilePath = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                    Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                    firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = uri.toString();
                            Log.e("TAG:", "the url is: " + url);

                            Toast.makeText(MainActivity.this,uri.toString(),Toast.LENGTH_LONG).show();
                            //String ref = yourStorageReference.getName();
                            //Log.e("TAG:", "the ref is: " + ref);

                            //Picasso.with(MainActivity.this).load(url).into(iShow);
                            generatedFilePath = url;
                        }
                    });

                }
            });


           /* storageReference.child(D_name).putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           // pd.dismiss();
                           // Toast.makeText(MyProfile.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();

                            if(downloadUri.isSuccessful()){
                                generatedFilePath = downloadUri.getResult().toString();
                                Toast.makeText(MainActivity.this,"## Stored path is "+generatedFilePath,Toast.LENGTH_LONG).show();
                                //dwnldUrl = generatedFilePath;
                               // System.out.println("## Stored path is "+generatedFilePath);
                            }}
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                           // pd.dismiss();
                            Toast.makeText(MainActivity.this,"ttu chutiya",Toast.LENGTH_LONG).show();
                        }
                    });

                    <ImageView
        android:id="@+id/ivIEW"
        android:layout_width="200dp"
        android:layout_height="200dp"/>




                    */


            //}


        }


    }

    private void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            //Toast.makeText(MainActivity.this,""+filePath,Toast.LENGTH_LONG).show();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                iv.setImageBitmap(bitmap);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
