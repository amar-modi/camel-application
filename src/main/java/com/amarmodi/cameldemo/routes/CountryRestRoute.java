package com.amarmodi.cameldemo.routes;

import com.amarmodi.cameldemo.domain.Country;
import com.amarmodi.cameldemo.processors.CountryGetProcessor;
import com.amarmodi.cameldemo.processors.CountrySQLProcessor;
import com.amarmodi.cameldemo.processors.CountrySelectProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class CountryRestRoute extends RouteBuilder {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CountrySelectProcessor countrySelectProcessor;

    @Autowired
    private CountrySQLProcessor countrySQLProcessor;

    @Autowired
    private CountryGetProcessor countryGetProcessor;

    @Override
    public void configure() throws Exception {

//        JacksonDataFormat countryFormat = new JacksonDataFormat(Country.class);
//        // POST Endpoint
//        from("restlet:http://localhost:8082/country?restletMethods=POST")
//                .routeId("countryPostRoute")
//                .log("Received body from the call is ${body}")
//                .unmarshal(countryFormat)
//                .log("Unmarshalled record is ${body}")
//                .process(countrySQLProcessor)
//                .to("{{dbNode}}")
//                .to("{{selectCountryNode}}")
//                .convertBodyTo(String.class)
//                .log("Inserted country ${body}")
//                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));

    }
}
