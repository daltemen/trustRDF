/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jena;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author alex
 */
public class ArchivoJS {

    ArrayList<String> relaciones;

    public ArchivoJS(ArrayList relaciones) throws IOException {
        this.relaciones = relaciones;
    }

    public void escribir() throws IOException {
        Grafo grafo = new Grafo(relaciones);        
        //String sFichero = "/home/alex/NetBeansProjects/trustRDF-master/tRDF/web/js/dibujo2.html";
        String sFichero = "D:/Octavo Semestre/Redes3/trustRDF-master/tRDF/web/js/dibujo2.html";        
        String guardar = grafo.grafostring();
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero));
            bw.write(guardar);
            bw.close();
        } catch (FileNotFoundException fnfe) {
        } catch (IOException ioe) {
        }
    }
}
