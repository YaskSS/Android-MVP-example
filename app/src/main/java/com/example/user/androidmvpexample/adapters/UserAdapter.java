package com.example.user.androidmvpexample.adapters;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.androidmvpexample.R;
import com.example.user.androidmvpexample.User;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 27.10.2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    List<User> data = new ArrayList<>();

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<User> users) {
        data.clear();
        data.addAll(users);
        notifyDataSetChanged();
        Log.d("qweee", "size  = " + getItemCount());
    }

    static class UserHolder extends RecyclerView.ViewHolder {

        TextView text;

        public UserHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }

        void bind(User user) {
            text.setText(String.format("id: %s, name: %s, age: %s", user.getId(), user.getName(), user.getAge()));
        }
    }

}
