package edu.kcg.futurelab.hackathon.sharemoude;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;

@WebServlet(urlPatterns="/request")
public class RequestServlet extends HttpServlet{
	private static final long serialVersionUID = -1847128562664195255L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("post");
		Request r = JSON.decode(req.getInputStream(), Request.class);
		try{
			r.setPray(RSA.encrypt(r.getPray()));
			Requests.add(r);
			resp.getWriter().write("" + r.getId());
		} catch(Exception e){
			throw new ServletException(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		JSON.encode(Requests.list(), resp.getOutputStream());
	}
}
