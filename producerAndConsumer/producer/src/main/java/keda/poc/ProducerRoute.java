package keda.poc;

import org.apache.camel.Endpoint;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.rabbitmq;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.timer;

@Component
public class ProducerRoute extends RouteBuilder {

	@Value("${producer.routeId}")
	private String routeId;
	
	@Value("${producer.description}")
	private String routeDescription;
	
	@Value("${producer.logName}")
	private String logName;
	
    @Override
    public void configure() throws Exception {
    	log.debug("configure in route %d", routeId);

        from("timer:hello")
        	.description( routeDescription )
        	.routeId( routeId )
        	
            .transform(simple("ID${random(0,10000)}"))            
            .log(LoggingLevel.DEBUG, logName, "${exchangeId}, I have generated the following body, ${body}")            
            
            .to("rabbitmq:foo?queue=foo")
            
            .log(LoggingLevel.DEBUG, logName, "${exchangeId}, Send was OK");
    }

}
