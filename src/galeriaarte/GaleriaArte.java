package galeriaarte;

import static com.coti.tools.DiaUtil.*;
import controller.Controller;
import model.Model;
import view.View;

/**
 *
 * @author alvar
 */
public class GaleriaArte {

    public static void main(String[] args) {

        /*
        Creo el Model y el View antes que el Controller, para luego al crear
        el Controller, pasarselos a su constructor. Así tendremos un "canal" de
        doble sentido de comunicación, es decir, la vista con el controlador y
        viceversa, y el controlador con el modelo y viceversa.
        Así consigo realizar algunos de los métodos que he implementado en este
        programa.
        */
        Model m = new Model();
        View v = new View();

        Controller c = new Controller(m, v);

        //Limpio pantalla para más claridad.
        clear();
        v.setController(c);
        /*
        Mediante el siguiente método compruebo si existe un archivo binario, y 
        doy la opción al usuario para importarlo o no.
        */
        v.checkBinary();
        //A partir de aquí inicia el menú principal con todas sus opciones.
        v.runMainMenu();
        /*
        Finalmente muestra el nombre de usuario que ha ejecutado el programa
        y muestra la hora de finalización.
        */
        showFinalTime();
    }

}
