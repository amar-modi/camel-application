package com.amarmodi.cameldemo.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.*;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CountryRestRouteTest {

    @Autowired
    private CamelContext context;

    @Autowired
    protected CamelContext createCamelContext(){
        return context;
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;


    @Test
    public void persistCountry(){
        String input = "{\"name\":\"United States of America\",\"topLevelDomain\":[\".us\"],\"alpha2Code\":\"US\",\"alpha3Code\":\"USA\",\"callingCodes\":[\"1\"],\"capital\":\"Washington, D.C.\",\"altSpellings\":[\"US\",\"USA\",\"United States of America\"],\"region\":\"Americas\",\"subregion\":\"Northern America\",\"population\":323947000,\"latlng\":[38.0,-97.0],\"demonym\":\"American\",\"area\":9629091.0,\"gini\":48.0,\"timezones\":[\"UTC-12:00\",\"UTC-11:00\",\"UTC-10:00\",\"UTC-09:00\",\"UTC-08:00\",\"UTC-07:00\",\"UTC-06:00\",\"UTC-05:00\",\"UTC-04:00\",\"UTC+10:00\",\"UTC+12:00\"],\"borders\":[\"CAN\",\"MEX\"],\"nativeName\":\"United States\",\"numericCode\":\"840\",\"currencies\":[{\"code\":\"USD\",\"name\":\"United States dollar\",\"symbol\":\"$\"}],\"languages\":[{\"iso639_1\":\"en\",\"iso639_2\":\"eng\",\"name\":\"English\",\"nativeName\":\"English\"}],\"translations\":{\"de\":\"Vereinigte Staaten von Amerika\",\"es\":\"Estados Unidos\",\"fr\":\"États-Unis\",\"ja\":\"アメリカ合衆国\",\"it\":\"Stati Uniti D'America\",\"br\":\"Estados Unidos\",\"pt\":\"Estados Unidos\",\"nl\":\"Verenigde Staten\",\"hr\":\"Sjedinjene Američke Države\",\"fa\":\"ایالات متحده آمریکا\"},\"flag\":\"https://restcountries.eu/data/usa.svg\",\"regionalBlocs\":[{\"acronym\":\"NAFTA\",\"name\":\"North American Free Trade Agreement\",\"otherAcronyms\":[],\"otherNames\":[\"Tratado de Libre Comercio de América del Norte\",\"Accord de Libre-échange Nord-Américain\"]}],\"cioc\":\"USA\"}";
        String countryList = producerTemplate.requestBody("http://localhost:8082/country", input, String.class);
        assertNotNull(countryList);
    }
}