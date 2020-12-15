package com.Huzaifa.centralpark;

public class User {
    String myId;
    String bookingTime;
    String currentBookingSpot;

    public User() {
        this.myId="-1";
        this.bookingTime="0";
        this.currentBookingSpot = "0";
    }

    public User(User obj) {
        this.myId=obj.myId;
        this.bookingTime=obj.bookingTime;
        this.currentBookingSpot = obj.currentBookingSpot;
    }

    public User(String myId, String bookingTime, String currentBookingSpot) {
        this.myId=myId;
        this.bookingTime=bookingTime;
        this.currentBookingSpot = currentBookingSpot;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getCurrentBookingSpot() {
        return currentBookingSpot;
    }

    public void setCurrentBookingSpot(String currentBookingSpot) {
        this.currentBookingSpot = currentBookingSpot;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }
}
