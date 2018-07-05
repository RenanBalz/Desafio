package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CursoServlet extends HttpServlet {

    Connection connection;
    
    public CursoServlet() throws SQLException {
        connection = DriverManager.getConnection("jdbc:derby://localhost:1527/erpcolegio", "sa", "sa");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        resp.setContentType("text/html");
        req.setCharacterEncoding("UTF-8");
        
        PrintWriter saida = resp.getWriter();
        
        String cursoId = req.getParameter("curso_id");
        String nomeCurso = req.getParameter("nome_curso");
        String turno = req.getParameter("turno");
        String qtdeEstudantes = req.getParameter("qtde_estudantes");
        String dataCadastro = req.getParameter("data_cadastro");
        
        try {
            PreparedStatement p = connection.prepareStatement("insert into curso values (?, ?, ?, ?, ?)");
            p.setInt(1, Integer.parseInt(cursoId));
            p.setString(2, nomeCurso);
            p.setString(3, turno);
            p.setInt(4, Integer.parseInt(qtdeEstudantes));
            p.setDate(5, Date.valueOf(dataCadastro));
            p.execute();
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
        
        saida.write("Concluído!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        resp.setContentType("text/html");
        req.setCharacterEncoding("UTF-8");
        
        PrintWriter saida = resp.getWriter();
        
        try {
            PreparedStatement p = connection.prepareStatement("select * from curso");
            p.execute();
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                saida.println("<ul>");
                    saida.println("<li>" + (rs.getInt("curso_id")) + "</li>");
                    saida.println("<li>" + (rs.getString("nome_curso")) + "</li>");
                    saida.println("<li>" + (rs.getString("turno")) + "</li>");
                    saida.println("<li>" + (rs.getInt("qtde_estudantes")) + "</li>");
                    saida.println("<li>" + (rs.getDate("data_cadastro")) + "</li>");
                saida.println("</ul>");
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
}
