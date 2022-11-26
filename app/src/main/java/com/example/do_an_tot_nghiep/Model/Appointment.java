package com.example.do_an_tot_nghiep.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointment {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("numerical_order")
    @Expose
    private Integer numericalOrder;

    @SerializedName("position")
    @Expose
    private Integer position;

    @SerializedName("patient_id")
    @Expose
    private Integer patientId;

    @SerializedName("patient_name")
    @Expose
    private String patientName;

    @SerializedName("patient_phone")
    @Expose
    private String patientPhone;

    @SerializedName("patient_birthday")
    @Expose
    private String patientBirthday;

    @SerializedName("patient_reason")
    @Expose
    private String patientReason;

    @SerializedName("appointment_time")
    @Expose
    private String appointmentTime;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("create_at")
    @Expose
    private String createAt;

    @SerializedName("update_at")
    @Expose
    private String updateAt;

    @SerializedName("doctor")
    @Expose
    private Doctor doctor;

    @SerializedName("speciality")
    @Expose
    private Speciality speciality;

    @SerializedName("room")
    @Expose
    private Room room;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getNumericalOrder() {
        return numericalOrder;
    }

    public void setNumericalOrder(Integer numericalOrder) {
        this.numericalOrder = numericalOrder;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientBirthday() {
        return patientBirthday;
    }

    public void setPatientBirthday(String patientBirthday) {
        this.patientBirthday = patientBirthday;
    }

    public String getPatientReason() {
        return patientReason;
    }

    public void setPatientReason(String patientReason) {
        this.patientReason = patientReason;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
