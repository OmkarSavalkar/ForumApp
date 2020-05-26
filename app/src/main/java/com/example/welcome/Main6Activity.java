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
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main6Activity extends AppCompatActivity {

    public DatabaseReference groupref;
    public SwipeMenuListView listView2;
    public EditText editTextcomment;
    public Button postcommt,clearcommt;
    public ArrayList<String> arrayList = new ArrayList<>();
    public ArrayList<String> keysList = new ArrayList<>();
    public ArrayAdapter<String> adapter;
    public String currentanswer;
    public String itemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        adapter=new ArrayAdapter<String>(this,R.layout.q_listviewlayout,R.id.textView11,arrayList);

        listView2=findViewById(R.id.commentlist);
        editTextcomment=findViewById(R.id.editTextcomment);
        postcommt=findViewById(R.id.butto);
        clearcommt=findViewById(R.id.buttonclearcomment);

        //form answers
        currentanswer = getIntent().getExtras().get("answers").toString();

        groupref = FirebaseDatabase.getInstance().getReference().child(currentanswer);

        //-------------------------------------------on comment post button click------------------------------------------------------
        postcommt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commenttext = editTextcomment.getText().toString();
                if (commenttext == null) {
                    Toast.makeText(Main6Activity.this, "Enter Comment!", Toast.LENGTH_SHORT).show();
                }
                groupref.push().setValue(editTextcomment.getText().toString());
                 itemId = groupref.push().getKey();
                listView2.setAdapter(adapter);
                Toast.makeText(Main6Activity.this, "Comment Posted Successfully!", Toast.LENGTH_SHORT).show();
                editTextcomment.setText("");
            }
        });

        groupref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String string = dataSnapshot.getValue(String.class);
                arrayList.add(string);
                keysList.add(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
                listView2.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String string = dataSnapshot.getValue(String.class);
                arrayList.remove(string);
                keysList.remove(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //---------------------------------------swipe delete comment-----------------------------------------------------
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem  deleteItem= new SwipeMenuItem(getApplicationContext());
                // set item background
                //deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setBackground(new ColorDrawable(Color.rgb(131, 15, 158)));
                // set item width
                deleteItem.setWidth(143);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_sweep_black_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView2.setMenuCreator(creator);
        listView2.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        String key = keysList.get(position);
                        groupref.child(key).removeValue();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    //-------------------------------------on comment edittext clear click-------------------------------------------------------
    public void commentclearclick(View view)
    {
        editTextcomment.setText("");
    }
}
