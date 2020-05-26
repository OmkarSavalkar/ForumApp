package com.example.welcome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.View;
import android.widget.Button;

public class Main8Activity extends AppCompatActivity {

    public Button button8,button9,button10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        button10=findViewById(R.id.button10);
        button8=findViewById(R.id.button8);
        button9=findViewById(R.id.button9);
    }
    public void toallusersclick(View view)
    {
        Intent intent=new Intent(this,Main7Activity.class);
        startActivity(intent);

    }
    public void toquestionsclick(View view)
    {
        Intent intent=new Intent(this,Main4Activity.class);
        startActivity(intent);

    }
    public void howtouseclick(View view)
    {
        Intent intent=new Intent(this,Main9Activity.class);
        startActivity(intent);
    }
    //-------------------------------------------for exit code---------------------------------------------------------------
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
                //  Toast.makeText(Main4Activity.this, "You clicked cancel!", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog=alertdialogbuilder.create();
        alertDialog.show();
    }
}
