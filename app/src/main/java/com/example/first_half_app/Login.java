package com.example.first_half_app;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Login extends AppCompatActivity {
    ImageView Box ;
    EditText email , password ;
    Button login , sign ;
    FirebaseAuth fAuth ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Box = findViewById(R.id.box_image);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        login = findViewById(R.id.log_button);
        sign = findViewById(R.id.sign_button);


        email.setTranslationX(800);
        email.setAlpha(1);
        email.animate().translationX(0).setDuration(800).setStartDelay(300).start();

        Box.setTranslationX(800);
        Box.animate().translationX(0).setDuration(800).setStartDelay(300).start();

        password.setTranslationX(800);
        password.setAlpha(1);
        password.animate().translationX(0).setDuration(800).setStartDelay(300).start();

        login.setTranslationX(800);
        login.setAlpha(1);
        login.animate().translationX(0).setDuration(800).setStartDelay(300).start();

        sign.setTranslationX(800);
        sign.setAlpha(1);
        sign.animate().translationX(0).setDuration(800).setStartDelay(300).start();


     sign.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent=new Intent(Login.this, sign_up.class);
             startActivity(intent);
         }
     });

     login.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String Email = email.getText().toString().trim();
             String Password = password.getText().toString().trim();

             if(TextUtils.isEmpty(Email)){
                 email.setError("Email is required ");
                 return;
             }
             if(TextUtils.isEmpty(Password)){
                 password.setError("Password is required ");
                 return;
             }
             fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(Login.this,"Logged is Successfully",Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(getApplicationContext(),dash.class));
                     }else{
                         Toast.makeText(Login.this,"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                     }
                 }



             });
         }
     });


    }
}