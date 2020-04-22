package com.example.wrestro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.sql.Types.NULL;

public class Main2Activity extends AppCompatActivity {

    EditText dishPrice, dishDesc;
    Button btn;

    DatabaseReference databaseReference,dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dishPrice = findViewById(R.id.dish_Price);
        dishDesc = findViewById(R.id.dish_Desc);
        btn = findViewById(R.id.btn);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        dbRef = FirebaseDatabase.getInstance().getReference().child("DishImage");


        Intent i1s = getIntent();
        Bundle bundle = i1s.getExtras();

        final String D_name = (String)bundle.get("DN");
        final String D_img_link = (String)bundle.get("DLink");


        Toast.makeText(Main2Activity.this, D_img_link, Toast.LENGTH_LONG).show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //uploadImage();

               // D_name = dishName.getText().toString().trim();
                String D_price = dishPrice.getText().toString().trim();
                String D_desc = dishDesc.getText().toString().trim();

               // String D_img_link = "https://console.firebase.google.com/u/0/project/restro-12bcf/storage/restro-12bcf.appspot.com/files/"+D_name;

                Toast.makeText(Main2Activity.this, D_img_link, Toast.LENGTH_LONG).show();


               /* int l = D_name.length();
                int convert=0;
                for (int i = 0; i < l; i++) {
                    convert =  (convert*10)+ D_name.charAt(i);
                    //System.out.print(convert);
                }*/


                Dish dish = new Dish(D_img_link,D_price,D_desc);
               databaseReference.child("Dish").child(D_name).setValue(dish);


               DishImage dishImage = new DishImage(D_img_link);
               dbRef.child(D_name).setValue(dishImage);


                //FileUploader();

                Toast.makeText(Main2Activity.this, "Dish is added", Toast.LENGTH_LONG).show();

                //Toast.makeText(Main2Activity.this, "New Item is added", Toast.LENGTH_LONG).show();


            }
        });


    }
}
