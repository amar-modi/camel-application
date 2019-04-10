package com.amarmodi.cameldemo.processors;

import com.amarmodi.cameldemo.domain.Country;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CountryGetProcessor implements org.apache.camel.Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        List<Map<String, Object>> rows = (List<Map<String, Object>>) exchange.getIn().getBody();
        List<Country> countries = new ArrayList<Country>();
        for(Map<String,Object> row: rows){
            Country country = new Country();
            country.setAlpha3Code((String)row.get("COUNTRY_CODE"));
            String population = (String) row.get("POPULATION");
            country.setPopulation(population);
            countries.add(country);
        }
        exchange.getOut().setBody(countries);
    }
}
