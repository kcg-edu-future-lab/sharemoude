package edu.kcg.futurelab.hackathon.sharemoude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/decrypt")
public class DecryptServlet extends HttpServlet{
	private static final long serialVersionUID = -1847128562664195255L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		System.out.println("decrypt");
		String dec = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8")).readLine();
		System.out.println(dec);
		try {
			String decrypted = RSA.decrypt(dec);
			System.out.println(decrypted);
			resp.getWriter().write(decrypted);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException | ClassNotFoundException e) {
			throw new ServletException(e);
		}
	}
}
