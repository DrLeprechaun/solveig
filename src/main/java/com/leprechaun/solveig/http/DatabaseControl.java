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
    
    public static ResponseEntity showDatabases(String host, String port) {
        return RequestExecutor.getExecutor(host, port, "SHOW%20DATABASES");
    }
    
    public static ResponseEntity createDatabase(String host, String port, String name) {
        return RequestExecutor.getExecutor(host, port, "CREATE%20DATABASE%20" + name);
    }
    
    public static ResponseEntity dropDatabase(String host, String port, String databaseName) {
        return RequestExecutor.getExecutor(host, port, "DROP%20DATABASE%20" + databaseName);
    }
    
}
