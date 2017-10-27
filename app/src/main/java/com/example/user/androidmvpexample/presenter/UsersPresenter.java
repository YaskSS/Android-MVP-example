package com.example.user.androidmvpexample.presenter;

import android.content.ContentValues;
import android.text.TextUtils;

import com.example.user.androidmvpexample.User;
import com.example.user.androidmvpexample.data.UsersData;
import com.example.user.androidmvpexample.database.UserTable;
import com.example.user.androidmvpexample.model.UsersModel;
import com.example.user.androidmvpexample.view.activity.MainActivity;

import java.util.List;

/**
 * Created by User on 26.10.2017.
 */

public class UsersPresenter {

    //является связующим звеном между представлением и моделью, которые не должны общаться напрямую

    private MainActivity view;
    private final UsersModel model;

    public UsersPresenter(UsersModel model) {
        this.model = model;
    }

    public void attachView(MainActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }

    public void viewIsReady() {
        loadUsers();
    }

    public void loadUsers(){
        model.loadsUsers(new UsersModel.LoadUserCallback() {
            @Override
            public void onLoad(List<User> users) {
                view.showToast(R.string.empty_values);
            }
        });
    }

    public void add() {
        UsersData userData = view.getUserData();
        if (TextUtils.isEmpty(userData.getName())) {
            view.showToast(R.string.empty_values);
            return;
        }

        ContentValues cv = new ContentValues(2);
        cv.put(UserTable.COLUMN.NAME, userData.getName());
        cv.put(UserTable.COLUMN.EMAIL, userData.getAge());
        view.showProgress();
        model.addUser(cv, new UsersModel.CompleteCallback() {
            @Override
            public void onComplete() {
                view.hideProgress();
                loadUsers();
            }
        });
    }

    public void clear() {
        view.showProgress();
        model.clearUsers(new UsersModel.CompleteCallback() {
            @Override
            public void onComplete() {
                view.hideProgress();
                loadUsers();
            }
        });
    }

}
