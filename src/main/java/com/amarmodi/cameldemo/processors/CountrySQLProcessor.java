package com.amarmodi.cameldemo.processors;

import com.amarmodi.cameldemo.domain.Country;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class CountrySQLProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Country country = (Country) exchange.getIn().getBody();
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO COUNTRY (NAME, COUNTRY_CODE, POPULATION) VALUES ('");
        query.append(country.getName() + "','" + country.getAlpha3Code() + "'," + country.getPopulation() + ");");
        exchange.getIn().setBody(query.toString());
        exchange.getIn().setHeader("countryId", country.getAlpha3Code());
    }
}
