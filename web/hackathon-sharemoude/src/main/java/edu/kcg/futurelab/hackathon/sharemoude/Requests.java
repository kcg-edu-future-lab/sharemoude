package edu.kcg.futurelab.hackathon.sharemoude;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Requests {
	public static Request[] list(){
		return reqs.toArray(new Request[]{});
	}
	public static void add(Request req){
		req.setId(seed++);
		if(reqs.size() == 40){
			reqs.remove(0);
		}
		reqs.add(req);
	}
	public static void remove(int id){
		Iterator<Request> it = reqs.iterator();
		while(it.hasNext()){
			if(it.next().getId() == id) it.remove();
		}
	}
	private static int seed = 2;
	private static List<Request> reqs = new ArrayList<>();
	static{
		try {
			reqs.add(new Request(0, RSA.encrypt("一年間健康で過ごせますように"), 1786));
			reqs.add(new Request(1, RSA.encrypt("就職できますように"), 200));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException | IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
