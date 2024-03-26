package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.OrdineDao;

/**
 * Servlet implementation class OrdiniAdminServlet
 */
@WebServlet("/ordiniAdmin")
public class OrdiniAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		OrdineDao ord = new OrdineDao();
		String action = request.getParameter("action");
		
		try{
			if(action!=null) {
	
				if(action.equalsIgnoreCase("data")) {
					String data1 =  request.getParameter("dal");
					String data2 =  request.getParameter("al");
					request.getSession().setAttribute("adminOrdini" ,ord.doRetrieveByDate(data1,data2));
					
				}
				else if(action.equalsIgnoreCase("nominativo")) {
					String nome =  request.getParameter("nome");
					String cognome =  request.getParameter("cognome");
					request.getSession().setAttribute("adminOrdini" ,ord.doRetrieveByNominativo(nome,cognome));
					
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
		
		
		response.sendRedirect(request.getContextPath() +"/admin/ViewOrdini.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
