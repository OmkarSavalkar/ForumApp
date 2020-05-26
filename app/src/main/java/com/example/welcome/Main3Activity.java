package com.example.welcome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener{

    public FirebaseAuth firebaseauth;
    public DatabaseReference groupref;
    //public FirebaseUser user;
    //public String uid;
    public EditText nameregister;
    public EditText emailregister;
    public EditText passwordregister;
    public Button registerbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        groupref= FirebaseDatabase.getInstance().getReference().child("users");
       // user=FirebaseAuth.getInstance().getCurrentUser();
        firebaseauth=FirebaseAuth.getInstance();
        registerbutton=findViewById(R.id.button2);
        emailregister=findViewById(R.id.editText5);
        passwordregister=findViewById(R.id.editText6);
        nameregister=findViewById(R.id.editText3);


        //ProgressBar progressBar=findViewById(R.id.progressBar3);

        //----------------------------------------for logo ani animation-------------------------------------------------------
        ImageView iv=findViewById(R.id.imageView3);
        iv.animate().alpha(1f).rotation(360f).setDuration(1500);

        //---------------------register button click------------------
        registerbutton.setOnClickListener(this);
    }

    //--------------------------------------on register button clicked METHOD-------------------------------------------------
    public void registerclick()
    {
        String email=emailregister.getText().toString().trim();
        String password=passwordregister.getText().toString().trim();

        String name=nameregister.getText().toString().trim();
        groupref.push().setValue(name);

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressBar progressBar2=findViewById(R.id.progressBar3);
        progressBar2.setVisibility(View.VISIBLE);



        //------creating/registering user using database-------------
        firebaseauth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful())
                       {
                           startActivity(new Intent(getApplicationContext(),Main2Activity.class));
                           Toast.makeText(Main3Activity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                           finish();
                       }
                       else
                       {
                           Toast.makeText(Main3Activity.this, "!!something went wrong!!", Toast.LENGTH_SHORT).show();
                          progressBar2.setVisibility(View.INVISIBLE);
                       }
                    }
                });
    }

    @Override
    public void onClick(View view)
    {
        if (view==registerbutton)
        {

            registerclick();
        }
    }





    //-------------------------------------------------for exit code-----------------------------------------------------------
    public void onBackPressed()
    {
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        alertdialogbuilder.setTitle("Confirm Exit...");
        alertdialogbuilder.setIcon(R.drawable.ic_exit);
        alertdialogbuilder.setMessage("Do You really Want To Exit???");
        alertdialogbuilder.setCancelable(false);
        alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // Toast.makeText(Main3Activity.this, "You clicked cancel!", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog=alertdialogbuilder.create();
        alertDialog.show();
    }
}
