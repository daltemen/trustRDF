/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jena;

import java.util.ArrayList;

/**
 *
 * @author alex
 */
public class Grafo {

    ArrayList<String> relaciones;

    public Grafo(ArrayList relaciones) {
        this.relaciones = relaciones;
    }

    public String arista() {
        int numero = (int) (Math.random() * 4 + 1);
        String arista = "";
        switch (numero) {
            case 1:
                arista = "seguro";
                break;
            case 2:
                arista = "normal";
                break;
            case 3:
                arista = "inseguro";
                break;
            case 4:
                arista = "indeterminado";
                break;
        }
        return arista;
    }

    public String nodo() {
        String nodos = "";

        for (int i = 1; i < relaciones.size(); i++) {
            nodos += "  {source: \"" + relaciones.get(0) + "\", target: \"" + relaciones.get(i) + "\", type: \"" + arista() + "\"},\n";
        }
        return nodos;
    }

    public String grafostring() {
        String guardar = "<!DOCTYPE html>\n"
                + "<meta charset=\"utf-8\">\n"
                + "<style>\n"
                + "\n"
                + ".link {\n"
                + "  fill: none;\n"
                + "  stroke: black;\n"
                + "  stroke-width: 1.5px;\n"
                + "}\n"
                + "\n"
                + "#seguro {\n"
                + "  fill: green;\n"
                + "}\n"
                + "\n"
                + ".link.seguro {\n"
                + "  stroke: green;\n"
                + "}\n"
                + "\n"
                + ".link.indeterminado {\n"
                + "  stroke-dasharray: 0,2 1;\n"
                + "}\n"
                + "\n"
                + "#inseguro {\n"
                + "  fill: red;\n"
                + "}\n"
                + "\n"
                + ".link.inseguro {\n"
                + "  stroke: red;\n"
                + "}\n"
                + "\n"
                + "circle {\n"
                + "  fill: #91e31c;\n"
                + "  stroke: #333;\n"
                + "  stroke-width: 1.5px;\n"
                + "}\n"
                + "\n"
                + "text {\n"
                + "  font: 10px sans-serif;\n"
                + "  pointer-events: none;\n"
                + "  text-shadow: 0 1px 0 #fff, 1px 0 0 #fff, 0 -1px 0 #fff, -1px 0 0 #fff;\n"
                + "}\n"
                + "\n"
                + "</style>\n"
                + "<head>\n"
                + "<title>Grafo</title>"
                + "</head>\n"
                + "<body>\n"
                + "<script src=\"d3.min.js\"></script>\n"
                + "<center><h1>Grafo</h1></center>\n"
                + "<script>\n"
                + "\n"
                + "// http://blog.thomsonreuters.com/index.php/mobile-patent-normals-graphic-of-the-day/\n"
                + "var links = [\n"
                + nodo()
                + "];\n"
                + "\n"
                + "var nodes = {};\n"
                + "\n"
                + "// Compute the distinct nodes from the links.\n"
                + "links.forEach(function(link) {\n"
                + "  link.source = nodes[link.source] || (nodes[link.source] = {name: link.source});\n"
                + "  link.target = nodes[link.target] || (nodes[link.target] = {name: link.target});\n"
                + "});\n"
                + "\n"
                + "var width = 960,\n"
                + "    height = 500;\n"
                + "\n"
                + "var force = d3.layout.force()\n"
                + "    .nodes(d3.values(nodes))\n"
                + "    .links(links)\n"
                + "    .size([width, height])\n"
                + "    .linkDistance(120)\n"
                + "    .charge(-300)\n"
                + "    .on(\"tick\", tick)\n"
                + "    .start();\n"
                + "\n"
                + "var svg = d3.select(\"body\").append(\"svg\")\n"
                + "    .attr(\"width\", width)\n"
                + "    .attr(\"height\", height);\n"
                + "\n"
                + "// Per-type markers, as they don't inherit styles.\n"
                + "svg.append(\"defs\").selectAll(\"marker\")\n"
                + "    .data([\"normal\", \"seguro\", \"indeterminado\", \"inseguro\"])\n"
                + "  .enter().append(\"marker\")\n"
                + "    .attr(\"id\", function(d) { return d; })\n"
                + "    .attr(\"viewBox\", \"0 -5 10 10\")\n"
                + "    .attr(\"refX\", 30)\n"
                + "    .attr(\"refY\", -3)\n"
                + "    .attr(\"markerWidth\", 6)\n"
                + "    .attr(\"markerHeight\", 6)\n"
                + "    .attr(\"orient\", \"auto\")\n"
                + "  .append(\"path\")\n"
                + "    .attr(\"d\", \"M0,-5L10,0L0,5\");\n"
                + "\n"
                + "var path = svg.append(\"g\").selectAll(\"path\")\n"
                + "    .data(force.links())\n"
                + "  .enter().append(\"path\")\n"
                + "    .attr(\"class\", function(d) { return \"link \" + d.type; })\n"
                + "    .attr(\"marker-end\", function(d) { return \"url(#\" + d.type + \")\"; });\n"
                + "\n"
                + "var circle = svg.append(\"g\").selectAll(\"circle\")\n"
                + "    .data(force.nodes())\n"
                + "  .enter().append(\"circle\")\n"
                + "    .attr(\"r\", 18)\n"
                + "    .call(force.drag);\n"
                + "\n"
                + "var text = svg.append(\"g\").selectAll(\"text\")\n"
                + "    .data(force.nodes())\n"
                + "  .enter().append(\"text\")\n"
                + "    .attr(\"x\", 8)\n"
                + "    .attr(\"y\", \".31em\")\n"
                + "    .text(function(d) { return d.name; });\n"
                + "\n"
                + "// Use elliptical arc path segments to doubly-encode directionality.\n"
                + "function tick() {\n"
                + "  path.attr(\"d\", linkArc);\n"
                + "  circle.attr(\"transform\", transform);\n"
                + "  text.attr(\"transform\", transform);\n"
                + "}\n"
                + "\n"
                + "function linkArc(d) {\n"
                + "  var dx = d.target.x - d.source.x,\n"
                + "      dy = d.target.y - d.source.y,\n"
                + "      dr = Math.sqrt(dx * dx + dy * dy);\n"
                + "  return \"M\" + d.source.x + \",\" + d.source.y + \"A\" + dr + \",\" + dr + \" 0 0,1 \" + d.target.x + \",\" + d.target.y;\n"
                + "}\n"
                + "\n"
                + "function transform(d) {\n"
                + "  return \"translate(\" + d.x + \",\" + d.y + \")\";\n"
                + "}\n"
                + "\n"
                + "</script>\n"
                + "</body>";

        return guardar;
    }
}
