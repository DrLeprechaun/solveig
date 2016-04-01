/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leprechaun.solveig.entities;

/**
 *
 * @author v_emelyanov
 */
public class UserEntity {
    
    private String name;
    private boolean adminFlag;
    
    public UserEntity() {};
    
    public UserEntity(String name, boolean adminFlag) {
        this.name = name;
        this.adminFlag = adminFlag;
    }
    
    //Getters and setters
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the adminFlag
     */
    public boolean getAdminFlag() {
        return adminFlag;
    }

    /**
     * @param adminFlag the adminFlag to set
     */
    public void setAdminFlag(boolean adminFlag) {
        this.adminFlag = adminFlag;
    }
    
}
