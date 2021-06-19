package keda.poc;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ConsumerRoute extends RouteBuilder {

	@Value("${consumer.routeId}")
	private String routeId;
	
	@Value("${consumer.description}")
	private String routeDescription;
	
	@Value("${consumer.logName}")
	private String logName;
	
    @Override
    public void configure() throws Exception {
    	log.debug("configure in route %d", routeId);
        	
        from("rabbitmq:foo?queue=foo")
    		.description( routeDescription )
    		.routeId( routeId )
    		
    		.log(LoggingLevel.DEBUG, logName, "${exchangeId}, Consumed, ${body}");
        }//configure

}//class
