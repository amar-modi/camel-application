package com.amarmodi.cameldemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    private String name;
    private String alpha3Code;
    private String population;

    public Country(String name, String alpha3Code, String population) {
        this.name = name;
        this.alpha3Code = alpha3Code;
        this.population = population;
    }

    public Country() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", alpha3Code='" + alpha3Code + '\'' +
                ", population='" + population + '\'' +
                '}';
    }
}
