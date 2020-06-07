package com.example.loginsqlite;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Book> list;
    private BookAdapter adapter;
    private String findname, findtype;
    private Button button;
    private EditText editText1;
    DatabaseHandler database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        listView = findViewById(R.id.listviewBook);
        list = new ArrayList<>();
        adapter = new BookAdapter(this, R.layout.des_book, list);
        listView.setAdapter(adapter);
        database = new DatabaseHandler(this);
        String array_spinner[];
        array_spinner=new String[3];
        array_spinner[0]="ALL";
        array_spinner[1]="Name";
        array_spinner[2]="Type";
        findname = "";
        findtype = "";
        final Spinner type = findViewById(R.id.txtType1);
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner);
        type.setAdapter(adapter);
        editText1 = findViewById(R.id.editText5);
        button = findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findname = editText1.getText().toString();
                findtype = "";
                if (type.getSelectedItem().toString().trim() != "Name") {
                    findtype = type.getSelectedItem().toString();
                }
                setData();
            }
        });
        database.QueryData("CREATE table if not exists book(id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(100), type varchar(20))");
        setData();
    }

    public void setData() {
        String sql = String.format("SELECT id, name, type FROM book where name like '%s' or type like '%s' ", "%"+findname+"%", "%"+findtype+"%");
        list.clear();
        Toast.makeText(this, sql, Toast.LENGTH_LONG).show();
        Cursor cursor = database.GetData(sql);
        while (cursor.moveToNext()) {
            list.add(new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_book, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            dialogAdd();
        }
        return super.onOptionsItemSelected(item);
    }

    public void delete(int i) {
        String sql = String.format("delete from book where id = %d", i);
        database.QueryData(sql);
        setData();
    }

    public void dialogEdit(final int i, final Book book) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_book);
        dialog.show();
        String array_spinner[];
        array_spinner=new String[5];
        array_spinner[0]="option 1";
        array_spinner[1]="option 2";
        array_spinner[2]="option 3";
        array_spinner[3]="option 4";
        array_spinner[4]="option 5";
        final Spinner type = dialog.findViewById(R.id.txtType);
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner);
        type.setAdapter(adapter);
        final EditText name = dialog.findViewById(R.id.txtName);
        Button add = dialog.findViewById(R.id.addBook);
        Button cancel = dialog.findViewById(R.id.canncel);
        name.setText(book.getName());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setName(name.getText().toString());
                book.setType(type.getSelectedItem().toString());
                String sql = String.format("update book set name = '%s', type = '%s' where id = %d ",
                        book.getName(), book.getType(), book.getId());
                database.QueryData(sql);
                Toast.makeText(HomeActivity.this, "Cập nhật thành công !!!", Toast.LENGTH_SHORT).show();
                list.set(i, book);
                setData();
                dialog.dismiss();
            }
        });
    }

    private void dialogAdd() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_book);
        dialog.show();
        String array_spinner[];
        array_spinner=new String[5];
        array_spinner[0]="option 1";
        array_spinner[1]="option 2";
        array_spinner[2]="option 3";
        array_spinner[3]="option 4";
        array_spinner[4]="option 5";
        final EditText name = dialog.findViewById(R.id.txtName);
        final Spinner type = dialog.findViewById(R.id.txtType);
        Button add = dialog.findViewById(R.id.addBook);
        Button cancel = dialog.findViewById(R.id.canncel);
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner);
        type.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = String.format("insert into book(name, type) values('%s', '%s')",
                        name.getText().toString(), type.getSelectedItem().toString());
                database.QueryData(sql);
                Toast.makeText(HomeActivity.this, "Thêm mới thành công !!!", Toast.LENGTH_SHORT).show();
                list.add(new Book(name.getText().toString(), type.getSelectedItem().toString()));
                dialog.dismiss();
                setData();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
