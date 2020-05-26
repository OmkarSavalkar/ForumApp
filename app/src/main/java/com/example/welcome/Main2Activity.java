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

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    public FirebaseAuth firebaseauth;
   public EditText inputemail;
    public EditText inputpassword;
    public Button button;
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageView iv=findViewById(R.id.imageView2);
        iv.animate().alpha(1f).rotation(360f).setDuration(1500);

        Toast.makeText(this, "PLease Login", Toast.LENGTH_SHORT).show();
        firebaseauth=FirebaseAuth.getInstance();

        //--------------------------------------already registered--------------------------------------------------------
       /*if (firebaseauth.getCurrentUser()!=null)
       {
            finish();
            startActivity(new Intent(getApplicationContext(),Main8Activity.class));
        }*/

        inputemail = findViewById(R.id.editText);
        inputpassword = findViewById(R.id.editText2);
        button=findViewById(R.id.button);
        textView=findViewById(R.id.textView);
        ProgressBar progressBar=findViewById(R.id.progressBar2);


        button.setOnClickListener(this);
        textView.setOnClickListener(this);
    }

    //-------------------------------------on login button clicked METHOD-----------------------------------------------------
    public void loginclick()
    {
        String email=inputemail.getText().toString().trim();
        String password=inputpassword.getText().toString().trim();
        if (TextUtils.isEmpty(email))
        {
           Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressBar progressBar1=findViewById(R.id.progressBar2);
        progressBar1.setVisibility(View.VISIBLE);

        //for login using database
        firebaseauth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            finish();
                           startActivity(new Intent(getApplicationContext(),Main8Activity.class));
                        }
                        else
                        {
                            Toast.makeText(Main2Activity.this, "Login Failed!!", Toast.LENGTH_SHORT).show();
                            progressBar1.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view)
    {
        if (view==button)
        {
            //cal to login click ^^^
            loginclick();
        }
        if (view==textView)
        {
            Intent intent=new Intent(Main2Activity.this,Main3Activity.class);
            startActivity(intent);
            finish();
        }
    }


    //---------------------------------------------for exit code---------------------------------------------------------
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
               // Toast.makeText(Main2Activity.this, "You clicked cancel!", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog=alertdialogbuilder.create();
        alertDialog.show();
    }

    /*public void usersbuttonclick(View view)
    {
        Intent inten=new Intent(this,Main7Activity.class);
        startActivity(inten);
    }*/
}
