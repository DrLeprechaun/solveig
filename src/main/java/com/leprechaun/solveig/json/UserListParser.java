/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leprechaun.solveig.json;

import com.google.gson.Gson;
import com.leprechaun.solveig.entities.ShowUsersResult;
import java.util.ArrayList;
import java.util.List;
import com.leprechaun.solveig.entities.UserEntity;

/**
 *
 * @author v_emelyanov
 */
public class UserListParser {
    
    //public static List<String> userStringList(String jsonString) {
    public static List<UserEntity> userStringList(String jsonString) {

        //List<String> userList = new ArrayList();
        List<UserEntity> userList = new ArrayList();
        Gson gson = new Gson();

        ShowUsersResult showUsersResult = gson.fromJson(jsonString, ShowUsersResult.class);

        for (ShowUsersResult.Results results : showUsersResult.getResults()) {
            for (ShowUsersResult.Series series : results.getSeries()) {
                for (List<String> values : series.getValues()) {
                    //for (String str : values) {
                        //userList.add(str);
                    //}
                    userList.add(new UserEntity(values.get(0), Boolean.valueOf(values.get(1))));
                }
            }
        }

        return userList;
    }
    
}
