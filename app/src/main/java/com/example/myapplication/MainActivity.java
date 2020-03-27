package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;

import com.zxn.parcel.annotation.Parcelable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void vvv(View view) {
        People people = new People();
        people.setAge(18);
        people.setBirthday(System.currentTimeMillis());
        people.setName("小明");
        Log.i("People",
                String.format("name = %1s,age = %2s,birthday = %3s", people.getName(), String.valueOf(people.getAge()), String.valueOf(people.getBirthday())));

        Intent intent = new Intent();
        intent.putExtra("data", People$$Parcelable.toParcelable(people));

        People people2 = intent.getParcelableExtra("data");

        Log.i("People2",
                String.format("name = %1s,age = %2s,birthday = %3s", people2.getName(), String.valueOf(people2.getAge()), String.valueOf(people2.getBirthday())));
    }



}
