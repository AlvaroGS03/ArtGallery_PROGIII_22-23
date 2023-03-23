package model;

import static com.coti.tools.OpMat.*;
import static com.coti.tools.Rutas.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvar
 */
public class Model {

    //Declaro el ArrayList
    List<Figura> figura = new ArrayList<>();

    //Para el CSV
    String carpeta = "datos_figuras";
    String archivo = "figuras.csv";
    Path PATH = pathToFileInFolderOnDesktop(carpeta, archivo);
    String ruta = PATH.toString();
    File f = new File(ruta);
    String separador = "\t";
    String[][] matriz2d;
    int tamAnterior;
    int tamPosterior;

    //Para el bin
    String fileName = "figuras.bin";
    Path PATHBIN = pathToFileInFolderOnDesktop(carpeta, fileName);
    String rutabin = PATHBIN.toString();
    File b = new File(rutabin);

    //Para ordenar
    Comparator<Figura> sortIdentificador = Comparator.comparing(Figura::getIdentificador);
    Comparator<Figura> sortYear = Comparator.comparing(Figura::getYear);
    Comparator<Figura> sortYearReversed = sortYear.reversed();
    Comparator<Figura> sortYearIdentificador = sortYearReversed.thenComparing(sortIdentificador);
    Comparator<Figura> sortFabricanteYear = Comparator.comparing(Figura::getFabricante).thenComparing(sortYear);

    public void readCSV() throws Exception {
        /*
        Importo en un array bidimensional los datos que se encuentran en el CSV
        del escritorio, siempre y cuando exista.
         */
        matriz2d = importFromDisk(f, separador);
        /*
        Posteriormente guardo en un array nuevo los mismos datos quitando los
        posibles errores que pueda contener.
         */
        String[][] matrizSinErrores = buscarErrores(matriz2d);
        /*
        Guardo el tamaño anterior de figura por si tuviera datos antes de importar
        nueva información. Esto servirá para sacar el número de datos importados
        distintos que los que ya hubiera.
         */
        tamAnterior = figura.size();
        /*
        Recorro la matriz sin errores, y por cada fila, voy añadiendo una fila
        nueva a la lista de tipo figura.
         */
        for (String[] matriz : matrizSinErrores) {
            matriz[6] = matriz[6].toUpperCase().charAt(0) + matriz[6].substring(1, matriz[6].length()).toLowerCase();
            figura.add(new Figura(matriz[0], Float.parseFloat(matriz[1]),
                    matriz[2], Integer.parseInt(matriz[3]),
                    Integer.parseInt(matriz[4]), matriz[5],
                    matriz[6]));
        }
        this.repeats();
    }

    public void writeCSV() throws Exception {
        /*
        Para exportar la lista a un CSV necesito los datos en forma de array
        bidimensional, por lo que esto es parecido al paso anterior, solo que
        recorriendo la lista en vez de el array, y en cada vuelta, añadiendo
        cada dato a su campo correspondiente. Finalmente se guarda en un CSV
        con el mismo nombre que el que importamos, por lo que si existe,
        se sobreescribirá.
         */
        String[][] export = new String[figura.size()][7];
        int i = 0;
        for (Figura fig : figura) {
            export[i][0] = fig.getIdentificador();
            export[i][1] = String.valueOf(fig.getAltura());
            export[i][2] = fig.getMaterial();
            export[i][3] = String.valueOf(fig.getCantidad());
            export[i][4] = String.valueOf(fig.getYear());
            export[i][5] = fig.getFoto();
            export[i][6] = fig.getFabricante();
            i++;
        }
        exportToDisk(export, f, separador);
    }

    public void writeHTML() throws Exception {
        /*
        Para exportar la lista a HTML, escribo varias sentencias de formato
        en CSS para darle color a la tabla. Finalmente se guarda en un archivo
        llamado figuras.hmtl.
         */
        String filename = "figuras.html";
        String folder = "datos_figuras";
        String htmlDocString = """
                               <!DOCTYPE html>
                               <html>
                               %%INTERNAL_CSS%%<head>
                               <title>%%title%%</title>
                               </head>
                               <body>
                               
                               <h1>%%Header1%%</h1>
                               <p>%%description1%%</p>
                               %%TABLE%%
                               
                               </body>
                               </html>""";

        htmlDocString = htmlDocString.replace("%%title%%", "Galería de Arte");
        htmlDocString = htmlDocString.replace("%%Header1%%", "Galería de Arte");
        htmlDocString = htmlDocString.replace("%%description1%%", "Estas son las figuras disponibles en la galería:");

        String CSS = """
                     <style>
                     table {
                       text-align: left;
                       position: relative;
                       border-collapse: collapse; 
                       background-color: #f6f6f6;
                     }
                     td, th {
                       border: 1px solid #999;
                       padding: 20px;
                     }
                     th {
                       background: blue;
                       color: white;
                       border-radius: 0;
                       position: sticky;
                       top: 0;
                       padding: 10px;
                     }
                     </style>""";

        htmlDocString = htmlDocString.replace("%%INTERNAL_CSS%%", CSS);

        StringBuilder tableStringBuilder = new StringBuilder();
        tableStringBuilder.append("<table>");

        tableStringBuilder.append(Figura.getHTMLRowHeader());
        for (Figura fig : figura) {
            tableStringBuilder.append(fig.asHTMLRow3());
        }
        tableStringBuilder.append("</table>");

        htmlDocString = htmlDocString.replace("%%TABLE%%", tableStringBuilder);

        writeToFileOnDesktop(filename, htmlDocString, folder);
    }

    public static void writeToFileOnDesktop(String filename, String html, String folder) throws IOException {

        var h = pathToFileInFolderOnDesktop(folder, filename);

        Files.writeString(h, html,
                Charset.forName("UTF-8"),
                StandardOpenOption.CREATE);

    }

    public String[][] buscarErrores(String[][] matriz2d) {
        /*
        Recorro la matriz importada, y para cada columna, utilizo el switch. Así,
        en cada opción buscará posibles errores. Por ejemplo en los strings, que
        no pueda estar vacío, o los enteros que no sea float o string...
        Si encuentra un fallo, eliminaré esa fila entera, creando un nuevo array
        con los mismos datos que la matriz original, pero saltándome esa fila.
        Posteriormente, disminuye la variable "i", pues la nueva fila 0, será
        la que originalmente era la 1 (así sucesivamente en todo el array),
        y si no disminuimos la variable, nos quedarán filas sin comprobar.
         */
        for (int i = 0; i < matriz2d.length; i++) {
            for (int j = 0; j < matriz2d[i].length; j++) {
                switch (j) {
                    case 0:
                    case 2:
                    case 6:
                        if (matriz2d[i][0].isEmpty() || matriz2d[i][2].isEmpty() || matriz2d[i][6].isEmpty()) {
                            matriz2d = eliminarFila(matriz2d, i);
                            i--;
                            break;
                        } else if (isInt(matriz2d[i][0])) {
                            if (Integer.parseInt(matriz2d[i][0]) <= 0) {
                                matriz2d = eliminarFila(matriz2d, i);
                                i--;
                                break;
                            }
                        } else if (isFloat(matriz2d[i][0])) {
                            if (Float.parseFloat(matriz2d[i][0]) <= 0) {
                                matriz2d = eliminarFila(matriz2d, i);
                                i--;
                                break;
                            }
                        }
                    case 5:
                        if (matriz2d[i][5].isEmpty() || !matriz2d[i][5].endsWith(".png")) {
                            matriz2d = eliminarFila(matriz2d, i);
                            i--;
                            break;
                        }
                    case 1:
                        if (!isFloat(matriz2d[i][1]) || Float.parseFloat(matriz2d[i][1]) <= 0 || Float.parseFloat(matriz2d[i][1]) > 1.5) {
                            matriz2d = eliminarFila(matriz2d, i);
                            i--;
                            break;
                        }
                    case 3:
                        if (!isInt(matriz2d[i][3]) || Integer.parseInt(matriz2d[i][3]) < 1) {
                            matriz2d = eliminarFila(matriz2d, i);
                            i--;
                            break;
                        }
                    case 4:
                        if (!isInt(matriz2d[i][4]) || Integer.parseInt(matriz2d[i][4]) < 0 || Integer.parseInt(matriz2d[i][3]) > 2100) {
                            matriz2d = eliminarFila(matriz2d, i);
                            i--;
                            break;
                        }
                }
            }
        }
        return matriz2d;
    }

    public boolean isInt(String cadena) {
        //Comprueba si el string recibido es un int.
        try {
            Integer.valueOf(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public boolean isFloat(String cadena) {
        //Comprueba si el string recibido es un float.
        try {
            Float.valueOf(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private static String[][] eliminarFila(String[][] data, int c) {
        /*
        Entra en este método cuando encuentra un error en algún campo de cualquier
        fila del array. Genera un nuevo array sin esa fila.
         */
        if (data.length == 0) {
            return data; //Ya estaba vacia
        }
        String[][] resultado = new String[data.length - 1][];
        System.arraycopy(data, 0, resultado, 0, c);
        for (int j = c; j < resultado.length; j++) {
            resultado[j] = data[j + 1];
        }
        return resultado;
    }

    public List<Figura> getFigura() {
        return figura;
    }

    public String printTitle(int maxSize[]) {
        //Devuelve con formato el título de la tabla, para que el view lo pueda imprimir.
        String identificador = "IDENTIFICADOR";
        String altura = "ALTURA";
        String material = "MATERIAL";
        String cantidad = "CANTIDAD";
        String year = "AÑO";
        String foto = "FOTO";
        String fabricante = "FABRICANTE";

        String tabla = String.format("| %" + maxSize[0] + "s " + "| %" + maxSize[1] + "s "
                + "| %" + maxSize[2] + "s " + "| %" + maxSize[3] + "s " + "| %"
                + maxSize[4] + "s " + "| %" + maxSize[5] + "s " + "| %" + maxSize[6] + "s |",
                identificador, altura, material, cantidad, year, foto, fabricante);

        return tabla;
    }

    public int[] getMaxSize(List<Figura> figura) {
        /*
        Comprueba cual es el valor de cada campo con mayor longitud, para así
        obtener el tamaño mínimo de cada columna.        
         */
        int maxSize[] = new int[7];
        String identificador = "IDENTIFICADOR";
        String altura = "ALTURA";
        String material = "MATERIAL";
        String cantidad = "CANTIDAD";
        String year = "AÑO";
        String foto = "FOTO";
        String fabricante = "FABRICANTE";
        maxSize[0] = identificador.length();
        maxSize[1] = altura.length();
        maxSize[2] = material.length();
        maxSize[3] = cantidad.length();
        maxSize[4] = year.length();
        maxSize[5] = foto.length();
        maxSize[6] = fabricante.length();

        for (Figura fig : figura) {
            if (fig.getIdentificador().length() > maxSize[0]) {
                maxSize[0] = fig.getIdentificador().length();
            }
            if (String.valueOf(fig.getAltura()).length() > maxSize[1]) {
                maxSize[1] = String.valueOf(fig.getAltura()).length();
            }
            if (fig.getMaterial().length() > maxSize[2]) {
                maxSize[2] = fig.getMaterial().length();
            }
            if (String.valueOf(fig.getCantidad()).length() > maxSize[3]) {
                maxSize[3] = String.valueOf(fig.getCantidad()).length();
            }
            if (String.valueOf(fig.getYear()).length() > maxSize[4]) {
                maxSize[4] = String.valueOf(fig.getYear()).length();
            }
            if (fig.getFoto().length() > maxSize[5]) {
                maxSize[5] = fig.getFoto().length();
            }
            if (fig.getFabricante().length() > maxSize[6]) {
                maxSize[6] = fig.getFabricante().length();
            }
        }
        return maxSize;
    }

    public List<Figura> sortIdentificador() throws Exception {
        figura.sort(sortIdentificador);
        return figura;
    }

    public List<Figura> sortYearIdentificador() throws Exception {
        figura.sort(sortYearIdentificador);
        return figura;
    }

    public List<Figura> sortFabricanteYear() throws Exception {
        figura.sort(sortFabricanteYear);
        return figura;
    }

    public void addFigure(String[] add) {
        //Para añadir un nuevo campo de figura en la lista.
        figura.add(new Figura(add[0], Float.parseFloat(add[1]), add[2],
                Integer.parseInt(add[3]), Integer.parseInt(add[4]),
                add[5], add[6]));
    }

    public String consultFigure(String identificador, int maxSize[]) {
        boolean flag = false;
        /*
        Comprueba si existe el identificador recibido, si es así, devuelve los
        datos en forma de string con formato para que el view lo pueda mostrar
        por pantalla.
         */
        for (Figura fig : figura) {
            if (identificador.equals(fig.getIdentificador())) {
                return fig.getAll(maxSize);
            }
        }
        if (!flag) {
            return null;
        }
        return null;
    }

    public boolean deleteFigure(String consult) {
        boolean flag = false;
        int i = 0;
        //Si existe el identificador elimina la figura.
        for (Figura fig : figura) {
            if (consult.equals(fig.getIdentificador())) {
                figura.remove(i);
                flag = true;
                break;
            } else {
                i++;
            }
        }
        if (flag == false) {
            return flag;
        }
        return flag;
    }

    public void changeFigure(int option, String cambio, String consult) {
        /*
        Mediante los setters de la clase Figura podemos hacer cambios en la
        figura que coincida con el identificador recibido.
         */
        for (Figura fig : figura) {
            if (consult.equals(fig.getIdentificador())) {
                switch (option) {
                    case 2 ->
                        fig.setAltura(Float.parseFloat(cambio));
                    case 3 ->
                        fig.setMaterial(cambio);
                    case 4 ->
                        fig.setCantidad(Integer.parseInt(cambio));
                    case 5 ->
                        fig.setYear(Integer.parseInt(cambio));
                    case 6 ->
                        fig.setFoto(cambio);
                    case 7 ->
                        fig.setFabricante(cambio);
                }
            }
        }
    }

    public boolean checkBinary() {
        //Comprueba si existe el fichero binario.
        return b.exists();
    }

    public boolean importDataBin() {
        if (b.exists()) {

            FileInputStream fis;
            BufferedInputStream bis;
            ObjectInputStream ois = null;

            try {
                fis = new FileInputStream(b);

                bis = new BufferedInputStream(fis);
                ois = new ObjectInputStream(bis);

                this.figura = (ArrayList<Figura>) ois.readObject();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, "No se encuentra el fichero", ex);
                return false;
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } finally {

                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            return true;

        } else {
            return false;
        }
    }

    public boolean exportBin() {

        FileOutputStream fos;
        BufferedOutputStream bos;
        ObjectOutputStream oos = null;

        try {

            fos = new FileOutputStream(b);

            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);

            if (this.figura != null) {
                oos.writeObject(this.figura);
            } else {
                return false;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, "No se encuentra el fichero", ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        }
        return true;
    }

    public String correctImports() {
        //Calcula los datos iniciales y los que han podido importarse correctamente.
        int initDates = matriz2d.length;
        int finalDates = tamPosterior - tamAnterior;
        float porcentaje = (float) (finalDates * 100) / initDates;

        String correct = String.format("%nNumero total de registros leídos: %d %n"
                + "Numero total de registros importados: %d %n"
                + "Porcentaje de registros importados: %.2f%%",
                initDates, finalDates, porcentaje);
        return correct;
    }

    public void repeats() {
        /*
        Guarda en un array todos los identificadores de cada figura, para
        posteriormente buscar repetidos, y en la posición que lo encuentre,
        es la posición que borra de la lista.
        Esto lo hace una vez haya sido importada la figura con identificadores
        correctos, pues hacerlo con datos incorrectos daría error, o algún
        comportamiento aleatorio no deseado.
         */
        String repes[] = new String[figura.size()];
        int i = 0;
        for (Figura fig : figura) {
            repes[i++] = fig.getIdentificador();
        }
        for (i = 0; i < figura.size() - 1; i++) {
            for (int j = i + 1; j < figura.size(); j++) {
                if (repes[i].equals(repes[j])) {
                    figura.remove(j--);
                }
            }
        }
        tamPosterior = figura.size();
    }

    public boolean idRepeat(String id) {
        /*
        Muy parecido al anterior, solo que esta vez, este método se usa 
        exclusivamente para los apartador de añadir/modificar datos,
        cuando se introduce un identificador, y comprueba si está repetido o no.
         */
        String repes[] = new String[figura.size()];
        int i = 0;
        for (Figura fig : figura) {
            repes[i++] = fig.getIdentificador();
        }
        for (i = 0; i < figura.size(); i++) {
            if (repes[i].equals(id)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkFigure() {
        //Comprueba si la lista está vacía.
        return !figura.isEmpty();
    }
}
