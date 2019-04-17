package com.amarmodi.cameldemo.processors;

import com.amarmodi.cameldemo.domain.InputPost;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RowProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        List<Map<String, Object>> rows = (List<Map<String, Object>>) exchange.getIn().getBody();
        List<InputPost> list= new ArrayList<>();
        for(int i=0; i< rows.size();i++){
            Map<String,Object> row = rows.get(i);
            InputPost inputPost = new InputPost();
            BigDecimal id = (BigDecimal) row.get("id");
            inputPost.setId(id.intValueExact());
            inputPost.setName((String)row.get("name"));
            list.add(inputPost);
        }
        exchange.getOut().setBody(list);

    }
}
