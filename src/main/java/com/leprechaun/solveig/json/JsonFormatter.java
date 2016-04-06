/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leprechaun.solveig.json;

/**
 *
 * @author v_emelyanov
 */
public class JsonFormatter {

    public static String jsonFormatter(String json) {

        String formattedJson = new String();

        int tabCounter = 0;

        //for (char ch : json.toCharArray()) {
        for (int j = 0; j < json.toCharArray().length; j++) {

            try {

                char ch = json.toCharArray()[j];

                formattedJson += ch;

                if (ch == '{' || ch == '[') {
                    formattedJson += "\n";
                    tabCounter++;
                    for (int i = 0; i < tabCounter; i++) {
                        formattedJson += "\t";
                    }

                }
                if (ch == '}' || ch == ']') {

                    if (json.toCharArray()[j + 1] != ',') {
                        formattedJson += "\n";
                        tabCounter--;
                        for (int i = 0; i < tabCounter; i++) {
                            formattedJson += "\t";
                        }
                    }
                }
                if (ch == ',') {

                    /*try {
                     if (formattedJson.toCharArray()[formattedJson.length() - 1] == 'n' && formattedJson.toCharArray()[formattedJson.length() - 2] == '\\') {
                     formattedJson = formattedJson.substring(0, formattedJson.length() - 2);
                     }
                     } catch (Exception e) {
                     }*/
                    formattedJson += "\n";
                    for (int i = 0; i < tabCounter; i++) {
                        formattedJson += "\t";
                    }
                }
            } catch (Exception e) {

            }

        }

        return formattedJson;
    }

}
