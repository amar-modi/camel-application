package com.amarmodi.cameldemo.routes;

import com.amarmodi.cameldemo.processors.CountrySelectProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestClientRoute extends RouteBuilder {

    @Autowired
    private CountrySelectProcessor countrySelectProcessor;

    @Override
    public void configure() throws Exception {
        from("{{restClient}}")
                // Just randomly picking a country and setting it in the header
                .process(countrySelectProcessor)
                // Assign the requestMethod type
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                // this is the correct way to make a rest call
                .setHeader(Exchange.HTTP_URI, simple("https://restcountries.eu/rest/v2/alpha/${header.countryId}"))
                // Accessing the url and converting to string
                .to("https://restcountries.eu/rest/v2/alpha/us").convertBodyTo(String.class)
//                .log("The REST API returned: ${body}")
                .removeHeader(Exchange.HTTP_URI)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
        .to("{{restEndpointRoute}}");


    }
}
