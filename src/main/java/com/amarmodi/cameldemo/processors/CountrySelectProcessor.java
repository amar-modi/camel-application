package com.amarmodi.cameldemo.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class CountrySelectProcessor implements Processor {

    private List<String> countryList = Arrays.asList("us","in","gb");

    @Override
    public void process(Exchange exchange) throws Exception {
        Random random = new Random();
        String cc = countryList.get(random.nextInt(countryList.size() -1));
        exchange.getIn().setHeader("countryId", cc);
    }
}
