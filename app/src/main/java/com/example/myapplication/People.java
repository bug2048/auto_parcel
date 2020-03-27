package com.example.myapplication;

import com.zxn.parcel.annotation.Parcelable;

/**
 * TODO:describe what the class or interface does.
 *
 * @author liuliqiang
 * @date 2019-12-15
 */
@Parcelable
public class People {

    private int age;
    private String name;
    private long birthday;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }
}
