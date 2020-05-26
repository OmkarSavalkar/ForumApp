package com.example.welcome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Main5Activity extends AppCompatActivity {

    public DatabaseReference databaseReference, groupref;
    public TextView textView;
    public EditText answers;
    public Button ansend;
    public TextView textView2;
    public SwipeMenuListView listView1;
    public ArrayList<String> arrayList = new ArrayList<>();
    public ArrayAdapter<String> adapter;
     public String currentquestion;
     public int ai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        adapter=new ArrayAdapter<String>(this,R.layout.a_listviewlayout,R.id.textView12,arrayList);

        textView = findViewById(R.id.txtviewtitle);
        textView2 = findViewById(R.id.textView2question);
        answers = findViewById(R.id.editText8);
        ansend = findViewById(R.id.button4);
        listView1 = findViewById(R.id.alist);


        //----------------------------------------for setting the question----------------------------------------------------------
        currentquestion = getIntent().getExtras().get("question").toString();
        //setting question
        textView2.setText(currentquestion);

        //new
        groupref = FirebaseDatabase.getInstance().getReference().child(currentquestion);


        //------------------------------------answer post button click----------------------------------------------------------------
        ansend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ansedtext = answers.getText().toString();
                if (ansedtext == null) {
                    Toast.makeText(Main5Activity.this, "Enter Answer!", Toast.LENGTH_SHORT).show();
                }
                groupref.push().setValue(answers.getText().toString());
                //String newItem=answers.getText().toString();
                //arrayList.add(newItem);
                //adapter.notifyDataSetChanged();
                listView1.setAdapter(adapter);
                Toast.makeText(Main5Activity.this, "Answer Posted Successfully!", Toast.LENGTH_SHORT).show();
                answers.setText("");
            }
        });

        //---------------------------------on answer to comments using alert dialog---------------------------------------------------
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String currentanswer=adapterView.getItemAtPosition(position).toString();
                AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(Main5Activity.this);
                alertdialogbuilder.setTitle("Comment...");
                alertdialogbuilder.setIcon(R.drawable.ic_exit);
                alertdialogbuilder.setMessage("Want To Comment For This Answer???");
                alertdialogbuilder.setCancelable(false);
                alertdialogbuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(getApplicationContext(),Main6Activity.class);
                        //maybe to pass answer
                        intent.putExtra("answers",currentanswer);
                        startActivity(intent);
                        answers.setText("");
                    }
                });
                alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //  Toast.makeText(Main4Activity.this, "You clicked No!", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog=alertdialogbuilder.create();
                alertDialog.show();

            }
        });

        groupref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String string = dataSnapshot.getValue(String.class);
                arrayList.add(string);
                adapter.notifyDataSetChanged();
                listView1.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String string = dataSnapshot.getValue(String.class);
                arrayList.remove(string);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //---------------------------------------swipe answers likes--------------------------------------------------
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(67, 95, 247)));
                // set item width
                openItem.setWidth(150);
                // set item title
                openItem.setTitle("View\nLikes");
                // set item title fontsize
                openItem.setTitleSize(16);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // set a icon
                // openItem.setIcon(R.drawable.ic_exit);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem  deleteItem= new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(252, 225, 101)));
                // set item width
                deleteItem.setWidth(150);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_favorite_black_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView1.setMenuCreator(creator);
        listView1.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        //open

                        ai=0;
                        groupref.push().setValue(ai);


                        break;
                    case 1:
                        // delete
                        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(Main5Activity.this);
                        alertdialogbuilder.setTitle("Likes "+ai);
                        alertdialogbuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Main5Activity.this, "You clicked cancel!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        AlertDialog alertDialog=alertdialogbuilder.create();
                        alertDialog.show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    //------------------------------------------answer search-----------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //------------------------------------------on clear edittext of answer-----------------------------------------------------
    public void answerclearclick(View view)
    {
        answers.setText("");
    }
}
