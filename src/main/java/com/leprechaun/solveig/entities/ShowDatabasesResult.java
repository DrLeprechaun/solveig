/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leprechaun.solveig.entities;

import java.util.List;

/**
 *
 * @author v_emelyanov
 */
public class ShowDatabasesResult {

    private List<Results> results;

    public ShowDatabasesResult() {
    }

    public ShowDatabasesResult(List<Results> results) {
        this.results = results;
    }

    /**
     * @return the results
     */
    public List<Results> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(List<Results> results) {
        this.results = results;
    }

    public class Results {

        private List<Series> series;

        public Results() {
        }

        public Results(List<Series> series) {
            this.series = series;
        }

        /**
         * @return the series
         */
        public List<Series> getSeries() {
            return series;
        }

        /**
         * @param series the series to set
         */
        public void setSeries(List<Series> series) {
            this.series = series;
        }

    }

    public class Series {

        private String name;
        private List<String> columns;
        private List<List<String>> values;

        public Series() {
        }

        public Series(String name, List<String> columns, List<List<String>> values) {
            this.name = name;
            this.columns = columns;
            this.values = values;
        }

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
         * @return the columns
         */
        public List<String> getColumns() {
            return columns;
        }

        /**
         * @param columns the columns to set
         */
        public void setColumns(List<String> columns) {
            this.columns = columns;
        }

        /**
         * @return the values
         */
        public List<List<String>> getValues() {
            return values;
        }

        /**
         * @param values the values to set
         */
        public void setValues(List<List<String>> values) {
            this.values = values;
        }

    }

}
