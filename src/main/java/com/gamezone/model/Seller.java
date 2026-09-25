package com.gamezone.model;

/**
 * represents a store seller, inheriting basic personal attributes
 * and managing specific employee details like code and work shift.
 */
public class Seller extends Person {
    private String employeeCode;
    private String workShift;

    /**
     * creates a new seller with the given personal information, employee code, and work shift
     *
     * @param name the seller's full name
     * @param identification the seller's identification number
     * @param phoneNumber the seller's contact phone number
     * @param employeeCode the unique employee identification code
     * @param workShift the work shift assigned to the seller
     */
    public Seller(String name, String identification, String phoneNumber, String employeeCode, String workShift) {
        super(name, identification, phoneNumber);
        this.employeeCode = employeeCode;
        this.workShift = workShift;
    }

    /**
     * returns a textual description specific to the seller's role
     *
     * @return a description including the seller's name and employee code
     */
    @Override
    public String getDescription() {
        return "Seller: " + getName() + " - Employee Code: " + employeeCode;
    }

    /**
     * returns the seller's employee code
     *
     * @return the employee code
     */
    public String getEmployeeCode() {
        return employeeCode;
    }

    /**
     * sets or updates the seller's employee code
     *
     * @param employeeCode the new employee code to set
     */
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    /**
     * returns the seller's work shift
     *
     * @return the work shift
     */
    public String getWorkShift() {
        return workShift;
    }

    /**
     * sets or updates the seller's work shift
     *
     * @param workShift the new work shift to set
     */
    public void setWorkShift(String workShift) {
        this.workShift = workShift;
    }
}
