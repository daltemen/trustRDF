package jena;
import java.util.ArrayList;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
/**
 *
 * @author Daniel
 */
public class Jena {
    
    public static ArrayList consultar()
    {   
        ArrayList<String> array = new ArrayList<String>();
        FileManager.get().addLocatorClassLoader(Jena.class.getClassLoader());
        Model model = FileManager.get().loadModel("C:/Users/Daniel/Documents/NetBeansProjects/tRDF/src/java/jena/data.rdf");
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
                return null;
            }            
    }       
}
