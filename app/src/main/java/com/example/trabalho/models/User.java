package com.example.trabalho.models;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.trabalho.HomeActivity;
import com.example.trabalho.presenter.contracts.ModelContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class User implements Parcelable, ModelContract.Model {

    private int id;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String gender;

    public String getWelcomeMessage() {
        String message = this.getGender().equals("Masculino") ? "Bem-vindo" : "Bem-vinda";
        message = message + ", " + this.getFirstName();
        return message;
    }

    public String getFirstName() {
        if (firstName != null) return firstName;
        if (name == null) return name;
        String[] arrayNames = name.split(" ");
        return arrayNames[0];
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        if (lastName != null) return lastName;
        if (name == null) return name;
        String[] arrayNames = name.split(" ");
        String lastNameAux = "";
        for (int i = 0; i < arrayNames.length; i++) {
            if (i == 1) lastNameAux = arrayNames[i];
            if (i > 1) lastNameAux = lastNameAux + " " + arrayNames[i];
        }
        return lastNameAux;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public User() {}

    protected User(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.gender = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.gender);
        parcel.writeString(this.getFirstName());
        parcel.writeString(this.getLastName());
        parcel.writeString(this.email);
        parcel.writeString(this.phone);
    }

    @Override
    public Map<String, Object> getInstanceinMap() {
        Map<String, Object> user = new HashMap<>();
        user.put("name", this.name);
        user.put("email", this.email);
        user.put("phone", this.phone);
        user.put("gender", this.gender);
        user.put("password", this.password);
        return user;
    }
}
