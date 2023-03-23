package model;

import static com.coti.tools.Esdia.*;
import java.io.Serializable;

/**
 *
 * @author alvar
 */
public class Figura implements Serializable {

    //Declaro todos los campos que tiene una figura.
    private String identificador;
    private float altura;
    private String material;
    private int cantidad;
    private int year;
    private String foto;
    private String fabricante;

    //Su constructor.
    public Figura(String identificador, float altura, String material, int cantidad, int year, String foto, String fabricante) {
        this.identificador = identificador;
        this.altura = altura;
        this.material = material;
        this.cantidad = cantidad;
        this.year = year;
        this.foto = foto;
        this.fabricante = fabricante;
    }

    //Getters y Setters
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getAll(int maxSize[]) {
        //Devuelve un string con formato de todos los campos de la fila.
        return String.format(ANSI_BLUE_BACKGROUND + ANSI_WHITE
                + "| " + ANSI_WHITE_BACKGROUND + ANSI_BLACK + "%" + maxSize[0] + "s " + "| %" + maxSize[1] + ".3f "
                + "| %" + maxSize[2] + "s " + "| %" + maxSize[3] + "d " + "| %"
                + maxSize[4] + "d " + "| %" + maxSize[5] + "s " + "| %" + maxSize[6] + "s "
                + ANSI_BLUE_BACKGROUND + ANSI_WHITE + "|" + ANSI_RESET,
                this.identificador, this.altura, this.material,
                this.cantidad, this.year, this.foto,
                this.fabricante);
    }

    public static String getHTMLRowHeader() {
        //Devuelve el encabezado de la tabla HTML
        return """
               <tr>
                   <th>Identificador</th>
                   <th>Altura</th>
                   <th>Material</th>
                   <th>Cantidad</th>
                   <th>Año</th>
                   <th>Foto</th>
                   <th>Fabricante</th>
                 </tr>""";
    }

    public String asHTMLRow3() {

        //Devuelve el contenido de cada fila con formato adecuado para una tabla HTML.
        return String.format("""
                             <tr>
                                 <td>%s</td>
                                 <td>%f</td>
                                 <td>%s</td>
                                 <td>%d</td>
                                 <td>%d</td>
                                 <td>%s</td>
                                 <td>%s</td>
                               </tr>""",
                this.identificador, this.altura, this.material,
                this.cantidad, this.year, this.foto,
                this.fabricante);
    }
}
