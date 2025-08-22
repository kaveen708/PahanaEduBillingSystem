package model;

import java.time.LocalDateTime;

public class Customer {
    private Integer id;
    private String accountNumber;
    private String name;
    private String address;
    private String phoneNumber;
    private int unitConsume;
    private LocalDateTime createdAt;

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public int getUnitConsume() { return unitConsume; }
    public void setUnitConsume(int unitConsume) { this.unitConsume = unitConsume; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }


}
