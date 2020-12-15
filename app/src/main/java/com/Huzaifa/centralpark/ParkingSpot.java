package com.Huzaifa.centralpark;

public class ParkingSpot {
    public String spot;
    public String bookerId;
    public String bookingTime;
    public String status;

    public ParkingSpot(){
        spot="0";
        bookerId="-1";
        bookingTime="0";
        status="0";
    }

    public ParkingSpot(String spot, String bookerId, String bookingTime, String status) {
        this.spot = spot;
        this.bookerId = bookerId;
        this.bookingTime = bookingTime;
        this.status = status;
    }

    public ParkingSpot(ParkingSpot parkingSpot) {
        this.spot=parkingSpot.spot;
        this.bookerId=parkingSpot.bookerId;
        this.bookingTime=parkingSpot.bookingTime;
        this.status=parkingSpot.status;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public String getBookerId() {
        return bookerId;
    }

    public void setBookerId(String bookerId) {
        this.bookerId = bookerId;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
