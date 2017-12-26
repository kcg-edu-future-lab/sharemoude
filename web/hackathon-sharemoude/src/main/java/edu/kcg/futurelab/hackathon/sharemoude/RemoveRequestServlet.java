package edu.kcg.futurelab.hackathon.sharemoude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/remove")
public class RemoveRequestServlet extends HttpServlet{
	private static final long serialVersionUID = -1847128562664195255L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("remove");
		String ids = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8")).readLine();
		int id = Integer.parseInt(ids);
		Requests.remove(id);
		Notification.notifyRemove(id);
	}
}
