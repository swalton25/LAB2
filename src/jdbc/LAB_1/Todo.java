////Samuel Walton
//LAB2


package jdbc.LAB_1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class todo
 */
@WebServlet(name="Servlets_Todo",
description = "",
urlPatterns = {"/*"})
public class Todo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ConcurrentHashMap<String, String> data;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Todo() {
        super();
        // TODO Auto-generated constructor stub
        data = new ConcurrentHashMap<String, String>();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    private void sendIdMsgJsonObject(PrintWriter out, String id, String msg) {
		System.out.println("{"); System.out.println("\"id\": \"" + id + "\","); System.out.println("\"message\": \"" + msg + "\"");
		System.out.println("}");
    }
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	resp.setContentType("application/json"); // return as json
        PrintWriter out = resp.getWriter();

		String id = req.getPathInfo();
		if (id.equals("/") || id.equals("") || id == null || id == "/*") {
			out.println("[");
			int count = 0;
			for( Map.Entry<String, String> entry : data.entrySet() ) {
				sendIdMsgJsonObject(out, entry.getKey(), entry.getValue());
				count++;
				if (count < data.size()) {
					out.println(",");
				}
			}
			out.println("]");
			out.close();
		}else {
			// Get a specific id
			id = id.substring(1); // remove the slash
			String message = data.get(id);
			sendIdMsgJsonObject(out, id, message);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    private void successJsonObject(PrintWriter out, boolean success) {
		out.println("{");
		out.println("\"success\": " + success);
		out.println("}");
    }
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
    	resp.setContentType("application/json"); // return as json
        PrintWriter out = resp.getWriter();
		String id = req.getParameter("id");
		String message = req.getParameter("message");
		if (data.get(id) == null) {
			data.put(id, message);
			successJsonObject(out, true);
		}else {
			successJsonObject(out, false);
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String line = br.readLine();
		String id = "", message = "";
	    String[] pairs = line.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
	        String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
	        if (key.equals("id")) {
	        	id = value;
	        }else if (key.equals("message")) {
	        	message = value;
	        }
	    }
		data.put(id, message);
//        PrintWriter out = resp.getWriter();
		//(out, id, message);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getPathInfo();
		id = id.substring(1); // remove the slash
		data.remove(id);
    	resp.setContentType("application/json"); // return as json
        PrintWriter out = resp.getWriter();
		if (data.get(id) == null) {
			successJsonObject(out, true);
		}else {
			successJsonObject(out, false);
		}
	}

}
