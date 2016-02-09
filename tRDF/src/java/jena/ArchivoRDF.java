/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jena;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author alex
 */
public class ArchivoRDF {

    String nombre = "", contenido = "";

    public ArchivoRDF(String nombre, String contenido) {
        this.nombre = nombre;
        this.contenido = contenido;
    }

    public void escribir() throws IOException {
        File archivo = new File("D:/Octavo Semestre/Redes3/trustRDF-master/tRDF/src/java/rdf/" + nombre + ".rdf");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(contenido);
            bw.close();
        } catch (FileNotFoundException fnfe) {
        } catch (IOException ioe) {
        }
    }
}
