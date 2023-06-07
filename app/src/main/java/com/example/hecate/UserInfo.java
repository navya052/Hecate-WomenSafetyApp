package com.example.hecate;

public class UserInfo {

    String Name;
    String PhoneNumber;
    String Email;

    public UserInfo(String Name,String PhoneNumber,String Email){
        this.Name = Name;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
    }
    public String getName() {
        return Name;
    }
    public String getNumber() {
        return PhoneNumber;
    }
    public  String getEmail(){
        return Email;
    }
}
