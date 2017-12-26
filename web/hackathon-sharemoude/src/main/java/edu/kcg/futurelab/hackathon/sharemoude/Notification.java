package edu.kcg.futurelab.hackathon.sharemoude;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/notification/{type}")
public class Notification {
	@OnOpen
	public void onOpen(Session session, @PathParam("type") String type){
		session.setMaxTextMessageBufferSize(500000);
		System.out.println(type);
		synchronized(Notification.class){
			if(type.equals("remotes")){
				remotes.put(session.getId(), session);
			} else{
				mobiles.put(session.getId(), session);
			}
		}
	}

	@OnMessage
	public void onMessage(Session session, String message){
		if(message.startsWith("img:")){
			notifyCapture(message);
		}
	}

	public static synchronized void notifyCapture(String message){
		Iterator<Map.Entry<String, Session>> it = remotes.entrySet().iterator();
		while(it.hasNext()){
			Session s = it.next().getValue();
			try {
				s.getBasicRemote().sendText(message);
			} catch (IOException | java.lang.IllegalStateException e) {
				it.remove();
			}
		}
	}

	public static synchronized void notifyRemove(int prayId){
		Iterator<Map.Entry<String, Session>> it = remotes.entrySet().iterator();
		while(it.hasNext()){
			Session s = it.next().getValue();
			try {
				s.getBasicRemote().sendText("{\"operation\": \"remove\", \"value\": " + prayId + "}");
			} catch (IOException | java.lang.IllegalStateException e) {
				it.remove();
			}
		}
	}

	private static Map<String, Session> remotes = new LinkedHashMap<>();
	private static Map<String, Session> mobiles = new LinkedHashMap<>();
}
