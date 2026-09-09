package com.gamezone.model;

public class Seller extends Person {
    private String employeeCode;
    private String workShift;

    public Seller(String name, String identification, String phoneNumber, String employeeCode, String workShift) {
        super(name, identification, phoneNumber);
        this.employeeCode = employeeCode;
        this.workShift = workShift;
    }

    @Override
    public String getDescription() {
        return "Seller: " + getName() + " - Employee Code: " + employeeCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }
}
