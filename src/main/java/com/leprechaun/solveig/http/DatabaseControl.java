/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leprechaun.solveig.http;
import com.leprechaun.solveig.entities.ResponseEntity;

/**
 *
 * @author v_emelyanov
 */

import com.leprechaun.solveig.http.RequestExecutor;

public class DatabaseControl {
    
    //Databases
    public static ResponseEntity showDatabases(String host, String port) {
        return RequestExecutor.simpleExecutor(host, port, "SHOW%20DATABASES");
    }
    
    public static ResponseEntity createDatabase(String host, String port, String name) {
        return RequestExecutor.simpleExecutor(host, port, "CREATE%20DATABASE%20" + name);
    }
    
    public static ResponseEntity dropDatabase(String host, String port, String databaseName) {
        return RequestExecutor.simpleExecutor(host, port, "DROP%20DATABASE%20" + databaseName);
    }
    
    //Users
    public static ResponseEntity showUsers(String host, String port) {
        return RequestExecutor.simpleExecutor(host, port, "SHOW%20USERS");
    }
    
    public static ResponseEntity createUser(String host, String port, String userName, String userPassword) {
        return RequestExecutor.simpleExecutor(host, port, "CREATE+USER+%22" + userName +"%22+WITH+PASSWORD+%27" + userPassword + "%27&db=_internal");
    }
    
    public static ResponseEntity dropUser(String host, String port, String userName) {
        return RequestExecutor.simpleExecutor(host, port, "DROP+USER+%22" + userName +"%22&db=_internal");
    }
    
}
