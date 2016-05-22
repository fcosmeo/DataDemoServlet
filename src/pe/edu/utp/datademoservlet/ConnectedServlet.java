package pe.edu.utp.datademoservlet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by GrupoUTP on 21/05/2016.
 */
@WebServlet(name = "ConnectedServlet",urlPatterns = "/connected")
public class ConnectedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = null;
        out = response.getWriter();
        out.println("<p>Hello World!</p>");
        //JDBC DataSource Connection Test
        try {
            InitialContext ctx = new InitialContext();
            out.println("Before Lookup");
            DataSource ds = (DataSource) ctx.lookup("jdbc/MySQLDataSource");
            out.println("Before Connection");
            Connection con = ds.getConnection();
            out.println("Before Statement");
            Statement stmt = con.createStatement();
            out.println("Before Query");
            ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
            out.println("After Query");
            if(rs != null){
                out.println("Right!");
                while(rs.next()){
                    out.println(rs.getString("last_name"));
                }
            }else{
                out.println("Not lucky this time");
            }
        }catch(NamingException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
