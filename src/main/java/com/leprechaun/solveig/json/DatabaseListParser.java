/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leprechaun.solveig.json;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.leprechaun.solveig.entities.ShowDatabasesResult;
import com.leprechaun.solveig.entities.ShowDatabasesResult.*;

/**
 *
 * @author v_emelyanov
 */
public class DatabaseListParser {

    public static List<String> databaseStringList(String jsonString) {

        List<String> databaseList = new ArrayList();
        Gson gson = new Gson();

        ShowDatabasesResult showDataaseResult = gson.fromJson(jsonString, ShowDatabasesResult.class);

        for (Results results : showDataaseResult.getResults()) {
            for (Series series : results.getSeries()) {
                for (List<String> values : series.getValues()) {
                    for (String str : values) {
                        databaseList.add(str);
                    }
                }
            }
        }

        return databaseList;
    }

}
