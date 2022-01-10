package com.example.contract_list;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
   private ListView listView;
   private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        button = findViewById(R.id.getContract);
        getList();

    }

    private void getList() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                startManagingCursor(cursor);
                String[]  form = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID};
                int[] to = {android.R.id.text1, android.R.id.text2};
                SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_list_item_2, cursor,form, to);
                 listView.setAdapter(simpleCursorAdapter);
                 listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            }
        });
    }
}