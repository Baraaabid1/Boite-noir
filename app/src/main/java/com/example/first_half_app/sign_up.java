package com.example.first_half_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class sign_up extends AppCompatActivity {

    ImageView Box ;
    EditText email , reg_password , usr_name , confirm ;
    Button  sign ;
    FirebaseAuth fAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Box = findViewById(R.id.image);
        usr_name = findViewById(R.id.name);
        reg_password = findViewById(R.id.pass);
        email = findViewById(R.id.email);
        confirm = findViewById(R.id.re_pass);
        fAuth= FirebaseAuth.getInstance();
        sign = findViewById(R.id.sign_button);

        email.setTranslationX(800);
        email.setAlpha(1);
        email.animate().translationX(0).setDuration(800).setStartDelay(300).start();

        Box.setTranslationX(800);
        Box.animate().translationX(0).setDuration(800).setStartDelay(300).start();

        reg_password.setTranslationX(800);
        reg_password.setAlpha(1);
        reg_password.animate().translationX(0).setDuration(800).setStartDelay(300).start();

        usr_name.setTranslationX(800);
        usr_name.setAlpha(1);
        usr_name.animate().translationX(0).setDuration(800).setStartDelay(300).start();

        confirm.setTranslationX(800);
        confirm.setAlpha(1);
        confirm.animate().translationX(0).setDuration(800).setStartDelay(300).start();

        sign.setTranslationX(800);
        sign.setAlpha(1);
        sign.animate().translationX(0).setDuration(800).setStartDelay(300).start();



       sign.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String Email = email.getText().toString().trim();
               String Password = reg_password.getText().toString().trim();
               if(TextUtils.isEmpty(Email)){
                   email.setError("Email is required ");
                   return;
               }
               if(TextUtils.isEmpty(Password)){
                   reg_password.setError("Password is required ");
                   return;
               }

               fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       if(task.isSuccessful()){
                           Toast.makeText(sign_up.this,"User Created",Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),dash.class));
                           finish();
                       }else{
                           Toast.makeText(sign_up.this,"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                       }

                   }
               });


           }
       });
    }

    }
