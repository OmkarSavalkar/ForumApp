package com.example.welcome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Main7Activity extends AppCompatActivity {

    public ListView userslistview;
    public DatabaseReference groupref;
    public ArrayList<String> usersarrayList=new ArrayList<>();
    public ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        groupref= FirebaseDatabase.getInstance().getReference().child("users");
       // adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,usersarrayList);
            adapter=new ArrayAdapter<String>(this,R.layout.forlistview,R.id.textView10,usersarrayList);
        userslistview=findViewById(R.id.userslistviewid);

        groupref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

                String string=dataSnapshot.getValue(String.class);
                usersarrayList.add(string);

                adapter.notifyDataSetChanged();
                userslistview.setAdapter(adapter);

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //userslistview.setAdapter(adapter);
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String string=dataSnapshot.getValue(String.class);
                usersarrayList.remove(string);
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
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
