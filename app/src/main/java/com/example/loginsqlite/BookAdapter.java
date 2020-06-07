package com.example.loginsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends BaseAdapter {

    private HomeActivity context;
    private int layout;
    private List<Book> bookList;

    public BookAdapter(HomeActivity context, int layout, List<Book> bookList) {
        this.context = context;
        this.layout = layout;
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtname, txttype;
        Button btnDelete, btnEdit;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder;

        if (view == null || view.getTag() == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtname = view.findViewById(R.id.txtNameBook);
            holder.txttype = view.findViewById(R.id.txtTypeBook);
            holder.btnEdit = view.findViewById(R.id.btnEditBook);
            holder.btnDelete = view.findViewById(R.id.btnDeleteBook);
        } else {
            holder = (ViewHolder)view.getTag();
        }
        if (position-1 > bookList.size()) return null;
        final Book book = bookList.get(position);
        holder.txtname.setText(book.getName());
        holder.txttype.setText(book.getType());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.delete(bookList.get(position).getId());
                Toast.makeText(context, "Xóa thành công !!!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.dialogEdit(position, bookList.get(position));
            }
        });

        return view;
    }
}
