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
    public static ArrayList consultar(String opcion) {
        String nombre = nombre(opcion);
        ArrayList<String> array = new ArrayList<String>(), enviar = new ArrayList<String>();
        FileManager.get().addLocatorClassLoader(Jena.class.getClassLoader());
        Model model = FileManager.get().loadModel("/rdf/" + opcion + ".rdf");
        String consultasparql
                = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
                + "SELECT * WHERE {"
                + " ?person foaf:name ?x ."
                + //" FILTER(?x = \"Roger\")"+
                //" ?person foaf:knows ?person2 ."+
                //" ?person2 foaf:name ?y ." +
                //"FILTER( ?y = \"Roger\")"+                 
                "}";

        Query query = QueryFactory.create(consultasparql);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                String name = soln.getLiteral("x").getString();
                //Literal name = soln.getLiteral("x");                    
                array.add(name);
            }
            enviar.add(nombre);
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i) == null ? nombre != null : !array.get(i).equals(nombre)) {
                    enviar.add(array.get(i));
                }
            }
            return enviar;
        } finally {
            qexec.close();
        }
    }

    public static String nombre(String opcion) {
        String nombre = null;
        //ArrayList<String> array = new ArrayList<String>();
        FileManager.get().addLocatorClassLoader(Jena.class.getClassLoader());
        Model model = FileManager.get().loadModel("/rdf/" + opcion + ".rdf");
        String consultasparql
                = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
                + "SELECT * WHERE {"
                + " ?person foaf:name ?x ."
                + " ?person foaf:knows ?person2 ."
                + "}";

        Query query = QueryFactory.create(consultasparql);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                nombre = soln.getLiteral("x").getString();
                //Literal name = soln.getLiteral("x");                    
                //array.add(name);
            }
            return nombre;
        } finally {
            qexec.close();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String opcion)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            ArrayList<String> relaciones = new ArrayList<>();
            relaciones = consultar(opcion);
            ArchivoJS ajs = new ArchivoJS(relaciones);
            ajs.escribir();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Resultado</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center><h1>Resultados del RDF</h1></center>");
            out.println("<a href=\"/tRDF/index.html\">Inicio</a><br>");
            out.println("<a href=\"/tRDF/js/dibujo2.html\">Ver grafica</a> ");
            out.println("<br>");
            out.println("<center>"+relaciones.get(0) + " esta relacionado con:");            
            out.println("<ul>");
            for (int i = 1; i <= relaciones.size(); i++) {
                out.println("<li>" + relaciones.get(i) + "</li>");
            }
            out.println("</ul></center>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    public void registrar(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCrvlet SparqServletTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Registrar RDF</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Nuevo RDF</h1>");
            out.println("<center><form action=\"SparqServlet\" method=\"POST\">");
            out.println("Nombre: <input type=\"text\" name=\"nombre\"><br>");
            out.println("<textarea name=\"textarea\" rows=\"20\" cols=\"100\">Inserte el codigo RDF</textarea><br>");
            out.println("<input type=\"submit\" value=\"Guardar\" name=\"guardar\">");
            out.println("</form></center>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    public void opciones(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Seleccionar RDF</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>Seleccionar ejemplo</center>");
            out.println("<br>");
            out.println("<center><form action=\"SparqServlet\" method=\"POST\">");
            out.println("<input type=\"radio\" name=\"opcion\" value=\"data1\">Data 1");
            out.println("<br>");
            out.println("<input type=\"radio\" name=\"opcion\" value=\"data2\">Data 2");
            out.println("<br>");
            out.println("<input type=\"submit\" value=\"Siguiente\" name=\"siguiente\">");
            out.println("</form></center>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    public void continuar(HttpServletRequest request, HttpServletResponse response, String nombre) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Continuar</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center><form action=\"SparqServlet\" method=\"POST\">");
            out.println("<input type=\"radio\" name=\"opcion\" value=\""+nombre+"\">"+nombre);
            out.println("<br>");
            out.println("<input type=\"submit\" value=\"Siguiente\" name=\"siguiente\">");
            out.println("</form></center>");
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
        processRequest(request, response, null);
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
        //String opcion = request.getParameter("opcion");
        //processRequest(request, response, opcion);        
        
        if ("Registrar".equals(request.getParameter("registrar"))) {
            registrar(request, response);
        } else if ("Seleccionar".equals(request.getParameter("seleccionar"))) {
            opciones(request, response);
        } else if ("Siguiente".equals(request.getParameter("siguiente"))){
            String opcion = request.getParameter("opcion");
            processRequest(request, response, opcion);
        } else if ("Guardar".equals(request.getParameter("guardar"))){
            String nombre = request.getParameter("nombre");
            String contenido = request.getParameter("textarea");
            ArchivoRDF ardf = new ArchivoRDF(nombre, contenido);
            ardf.escribir();
            continuar(request, response, nombre);            
        }
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
