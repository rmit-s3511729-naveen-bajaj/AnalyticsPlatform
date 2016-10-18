package com.bda.analyticsplatform.realtime;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;

@ServerEndpoint(value = "/channel/task", configurator = SpringConfigurator.class)
public class TaskWebSocketHandler {
	@Autowired
	private KafkaConsumer consumer = null;
	
	  
	  @OnOpen
	  public void onOpen(Session session) {

	            consumer = new KafkaConsumer();
	        }
	  
  
	  
		  @OnClose
		    public void onClose(final Session session) {
		        if (consumer != null) {
		            consumer.stop();
		        }
		    }
		  

}


