package com.amarmodi.cameldemo.processors;

        import com.amarmodi.cameldemo.domain.InputPost;
        import org.apache.camel.Exchange;
        import org.springframework.stereotype.Component;

@Component
public class BuildSQLProcessor implements org.apache.camel.Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        InputPost input  = (InputPost) exchange.getIn().getBody();
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO INPUT_POSTS (ID, NAME) VALUES (" + input.getId() + ",'" + input.getName() + "');");
        exchange.getIn().setBody(query.toString());
        exchange.getIn().setHeader("id",input.getId());

    }
}
