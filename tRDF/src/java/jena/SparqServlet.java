/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jena;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

/**
 *
 * @author Daniel
 */
public class SparqServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static ArrayList consultar()
    {   
        ArrayList<String> array = new ArrayList<String>();
        FileManager.get().addLocatorClassLoader(Jena.class.getClassLoader());
        Model model = FileManager.get().loadModel("C:/Users/Daniel/Documents/NetBeansProjects/tRDF/src/java/jena/data2.rdf");
        String consultasparql = 
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
                "SELECT * WHERE {" +
                " ?person foaf:name ?x ."+
                //" FILTER(?x = \"Roger\")"+
                //" ?person foaf:knows ?person2 ."+
                //" ?person2 foaf:name ?y ." +
                //"FILTER( ?y = \"Roger\")"+                
                "}"
                ;        
            Query query = QueryFactory.create(consultasparql);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            try {
                ResultSet results = qexec.execSelect();
                while(results.hasNext()){
                    QuerySolution soln = results.nextSolution();
                    String name = soln.getLiteral("x").getString();
                    //Literal name = soln.getLiteral("x");
                    System.out.println(name);
                    array.add(name);
                }
                return array;
            } finally {
                qexec.close();                
            }            
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SparqServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SparqServlet at " + request.getContextPath() + "</h1>");
            out.println(consultar());
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
