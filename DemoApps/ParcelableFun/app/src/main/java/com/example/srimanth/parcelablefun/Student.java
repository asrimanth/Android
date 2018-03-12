package com.example.srimanth.parcelablefun;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by srimanth on 2/19/18.
 */

public class Student implements Parcelable {

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>()
    {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        public Student[] newArray(int size)
        {
            return new Student[size];
        }
    };
    private long id;
    private String name;
    private String grade;

    public Student(long id, String name, String grade)
    {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }


    //Parcelable part
    Student(Parcel source)
    {
        this.id = source.readLong();
        this.name = source.readString();
        this.grade = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.grade);
    }

    @Override
    public String toString() {
        return "Student {" + "id= " + id + " name= "+ name + " grade= " + grade +"}\n";
    }
}
