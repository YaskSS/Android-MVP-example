package com.example.user.androidmvpexample.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.user.androidmvpexample.User;
import com.example.user.androidmvpexample.database.DbHelper;
import com.example.user.androidmvpexample.database.UserTable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 26.10.2017.
 */

public class UsersModel {

    //запрос данных с сервера, сохранение в БД, чтение файлов и т.п.
    private DbHelper dbHelper;

    public UsersModel(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    //получение списка пользователей из БД
    public void loadsUsers(LoadUserCallback loadUserCallback) {
        LoadUsersTask loadUsersTask = new LoadUsersTask(loadUserCallback);
        loadUsersTask.execute();
    }

    //добавление пользователя в БД
    public void addUser(ContentValues contentValues, CompleteCallback completeCallback) {
        AddUserTask addUserTask = new AddUserTask(completeCallback);
        addUserTask.execute(contentValues);
    }

    // удаление всех пользователей из БД
    public void clearUsers(CompleteCallback completeCallback) {
        ClearUsersTask clearUsersTask = new ClearUsersTask(completeCallback);
        clearUsersTask.execute();
    }

    public interface LoadUserCallback {
        void onLoad(List<User> users);
    }

    public interface CompleteCallback {
        void onComplete();
    }

    class LoadUsersTask extends AsyncTask<Void, Void, List<User>> {

        private final LoadUserCallback callback;

        LoadUsersTask(LoadUserCallback callback) {
            this.callback = callback;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            List<User> users = new LinkedList<>();
            Cursor cursor = dbHelper.getReadableDatabase().query(UserTable.TABLE, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                User user = new User();
                user.setId(cursor.getLong(cursor.getColumnIndex(UserTable.COLUMN.ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN.NAME)));
                users.add(user);
            }
            cursor.close();
            return users;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            if (callback != null) {
                callback.onLoad(users);
            }
        }
    }

    class AddUserTask extends AsyncTask<ContentValues, Void, Void> {

        private CompleteCallback callback;

        public AddUserTask(CompleteCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            ContentValues contentValues1 = contentValues[0];
            dbHelper.getWritableDatabase().insert(UserTable.TABLE, null, contentValues1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (callback != null) {
                callback.onComplete();
            }
        }
    }

    class ClearUsersTask extends AsyncTask<Void, Void, Void>{

        private CompleteCallback callback;

        public ClearUsersTask(CompleteCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dbHelper.getWritableDatabase().delete(UserTable.TABLE, null, null);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (callback != null){
                callback.onComplete();
            }
        }
    }
}
