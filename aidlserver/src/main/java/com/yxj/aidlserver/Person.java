package com.yxj.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author:  Yxj
 * Time:    2019/5/22 上午10:46
 * -----------------------------------------
 * Description:
 */
public class Person implements Parcelable{

    private String name;
    private String address;
    private int age;

    protected Person(Parcel in) {
        name = in.readString();
        address = in.readString();
        age = in.readInt();
    }

    public Person(String name, String address, int age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeInt(age);
    }
}
