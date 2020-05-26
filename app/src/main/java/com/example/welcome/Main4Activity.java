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

public class Main4Activity extends AppCompatActivity {


    public DatabaseReference groupref;
    public TextView textView;
    public EditText question;
    public Button qsend,qclear;
    public SwipeMenuListView listView;
    public ArrayList<String> arrayList=new ArrayList<>();
    public ArrayAdapter<String> adapter;
    public int li;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        groupref= FirebaseDatabase.getInstance().getReference().child("q");
        //adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        adapter=new ArrayAdapter<String>(this,R.layout.q_listviewlayout,R.id.textView11,arrayList);

        textView=findViewById(R.id.txtviewtitle);
        question=findViewById(R.id.editText7);
        qsend=findViewById(R.id.button3);
        qclear=findViewById(R.id.button7);
        listView=findViewById(R.id.qlist);



        groupref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

                String string=dataSnapshot.getValue(String.class);
                arrayList.add(string);

                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
               // listView.setBackgroundColor(Color.GRAY);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String string=dataSnapshot.getValue(String.class);
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

        //-------------------------------for listview items clicked----------------------------------------------------------------
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String currentquestion=adapterView.getItemAtPosition(position).toString();
                Intent intent=new Intent(getApplicationContext(),Main5Activity.class);
                //maybe to pass questions
                intent.putExtra("question",currentquestion);
                startActivity(intent);

                question.setText("");

            }
        });

        //--------------------------------swipe question likes-----------------------------------------------------------
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
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        //open

                        li=0;
                        groupref.push().setValue(li);


                        break;
                    case 1:
                        // delete
                        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(Main4Activity.this);
                        alertdialogbuilder.setTitle("Likes "+li);
                        alertdialogbuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                 Toast.makeText(Main4Activity.this, "You clicked cancel!", Toast.LENGTH_SHORT).show();
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

    //-------------------------------------------------On questions Activity post button clicked------------------------------------
    public void Qquesadd(View view)
    {
        String qedtext=question.getText().toString();
        if(qedtext == null)
        {
            Toast.makeText(this, "Please Enter Question", Toast.LENGTH_SHORT).show();
        }
        groupref.push().setValue(question.getText().toString());
        listView.setAdapter(adapter);
        Toast.makeText(this, "Question Posted successfully!", Toast.LENGTH_SHORT).show();
        question.setText("");
    }

    //---------------------------------------------on question clear button click---------------------------------------------------
    public void clearclick(View view)
    {
        question.setText("");
    }

    //----------------------------------for search button & also ->res->menu->search_menu.xml---------------------------------------
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


}
//--------for search res -> menu -> search_menu.xml also included----------

