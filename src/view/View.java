package view;

import static com.coti.tools.Esdia.*;
import static com.coti.tools.DiaUtil.*;
import controller.Controller;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvar
 */
public class View {

    private Controller c;

    public void runMainMenu() {
        //Declaro el menú principal que luego se imprimirá por pantalla.
        String menu = ANSI_GREEN
                + "%n"
                + "  ____    _    _     _____ ____  ___    _      ____  _____      _    ____ _____ _____ %n"
                + " / ___|  / \\  | |   | ____|  _ \\|_ _|  / \\    |  _ \\| ____|    / \\  |  _ \\_   _| ____|%n"
                + "| |  _  / _ \\ | |   |  _| | |_) || |  / _ \\   | | | |  _|     / _ \\ | |_) || | |  _|  %n"
                + "| |_| |/ ___ \\| |___| |___|  _ < | | / ___ \\  | |_| | |___   / ___ \\|  _ < | | | |___ %n"
                + " \\____/_/   \\_\\_____|_____|_| \\_\\___/_/   \\_\\ |____/|_____| /_/   \\_\\_| \\_\\|_| |_____|"
                + "%n%n%n%n"
                + ANSI_RESET
                + "1. Archivos%n"
                + "2. Gestión de la galería%n"
                + "3. Listados%n%n"
                + "4. Salir%n"
                + "%n%nElija una opción: ";

        boolean salir = false;
        do {
            /*
            Limpio pantalla para mayor claridad y muestro el menú principal.
            Repite este proceso hasta que el usuario elija una opción correcta,
            si no, le indicará al usuario que la opción es incorrecta, y
            volverá a limpiar la pantalla mostrando el menú de nuevo.
            */
            clear();
            //Se lee por teclado lo que el usuario desea hacer

            String option = readString(menu).toLowerCase();
            //Dependiendo de la opción tecleada entra en un case u otro.
            switch (option) {
                case "1" ->
                    this.runFilesMenu();
                case "2" ->
                    this.runGalleryManagementMenu();
                case "3" ->
                    this.runListsMenu();
                case "4" -> {
                    salir = siOnoPropio("Desea salir de la aplicación?");
                    /*
                    Si realmente el usuario desea salir, se guardará la
                    información actual del programa en el mismo fichero
                    que hemos intentado leer al principio de la ejecución.
                    */
                    if (salir) {
                        this.exportBin();
                    }
                }
                default ->
                    this.incorrectOption();
            }
        } while (!salir);
    }

    private void runFilesMenu() {

        boolean esLetra = false;
        String menu = ANSI_GREEN
                + "%n"
                + "    _    ____   ____ _   _ _____     _____  ____  %n"
                + "   / \\  |  _ \\ / ___| | | |_ _\\ \\   / / _ \\/ ___| %n"
                + "  / _ \\ | |_) | |   | |_| || | \\ \\ / / | | \\___ \\ %n"
                + " / ___ \\|  _ <| |___|  _  || |  \\ V /| |_| |___) |%n"
                + "/_/   \\_\\_| \\_\\\\____|_| |_|___|  \\_/  \\___/|____/ "
                + "%n%n%n%n"
                + ANSI_RESET
                + "a. Importar CSV%n"
                + "b. Exportar CSV%n"
                + "c. Exportar HTML%n%n"
                + "g. Volver al menú principal%n"
                + "%n%nEliga una opción: ";

        do {
            clear();
            String option = readString(menu).toLowerCase();
            switch (option) {
                case "a" ->
                    this.readCSV();
                case "b" ->
                    this.writeCSV();
                case "c" ->
                    this.writeHTML();
                case "g" ->
                    esLetra = siOnoPropio("Desea volver al menú principal?");
                default ->
                    this.incorrectOption();
            }
        } while (!esLetra);
    }

    private void runGalleryManagementMenu() {

        boolean esLetra = false;
        String menu = ANSI_GREEN
                + "%n"
                + "  ____ _____ ____ _____ ___ ___  _   _ %n"
                + " / ___| ____/ ___|_   _|_ _/ _ \\| \\ | |%n"
                + "| |  _|  _| \\___ \\ | |  | | | | |  \\| |%n"
                + "| |_| | |___ ___) || |  | | |_| | |\\  |%n"
                + " \\____|_____|____/ |_| |___\\___/|_| \\_|"
                + "%n%n%n%n"
                + ANSI_RESET
                + "a. Añadir una figura al inventario%n"
                + "b. Consultar datos de una figura%n"
                + "c. Modificar datos de una figura%n"
                + "d. Eliminar una figura del inventario%n%n"
                + "g. Volver al menú principal%n"
                + "%n%nEliga una opción: ";

        do {
            clear();
            String option = readString(menu).toLowerCase();
            switch (option) {
                case "a" ->
                    this.addFigure();
                case "b" ->
                    this.consultMain();
                case "c" ->
                    this.changeMain();
                case "d" ->
                    this.deleteMain();
                case "g" ->
                    esLetra = siOnoPropio("Desea volver al menú principal?");
                default ->
                    this.incorrectOption();
            }
        } while (!esLetra);
    }

    private void runListsMenu() {

        boolean esLetra = false;
        String menu = ANSI_GREEN
                + "%n"
                + " _     ___ ____ _____  _    ____   ___  ____  %n"
                + "| |   |_ _/ ___|_   _|/ \\  |  _ \\ / _ \\/ ___| %n"
                + "| |    | |\\___ \\ | | / _ \\ | | | | | | \\___ \\ %n"
                + "| |___ | | ___) || |/ ___ \\| |_| | |_| |___) |%n"
                + "|_____|___|____/ |_/_/   \\_\\____/ \\___/|____/ "
                + "%n%n%n%n"
                + ANSI_RESET
                + "a. Listado por identificador%n"
                + "b. Listado por año e identificador%n"
                + "c. Listado por fabricante y año%n%n"
                + "g. Volver al menú principal%n"
                + "%n%nEliga una opción: ";

        /*
        Obtengo el tamaño máximo de cada columna, para que así al imprimir los
        listados, cada campo tenga su tamaño mínimo y esté todo correctamente
        alineado.
        */
        int maxSize[] = c.getMaxSize(c.getFigura());
        do {
            clear();
            String option = readString(menu).toLowerCase();
            switch (option) {
                case "a" ->
                    this.sortIdentificador(maxSize);
                case "b" ->
                    this.sortYearIdentificador(maxSize);
                case "c" ->
                    this.sortFabricanteYear(maxSize);
                case "g" ->
                    esLetra = siOnoPropio("Desea volver al menú principal?");
                default ->
                    this.incorrectOption();
            }
        } while (!esLetra);
    }

    private void readCSV() {
        if (c.readCSV()) {
            underline2("Los datos se han importado correctamente.");
            System.out.println(c.correctImports());
        } else {
            underline2("Se ha producido un error al importar el CSV");
        }
        readString("%n%nPresione una tecla para continuar.....");
    }

    private void writeCSV() {
        /*
        Compruebo si existen los datos de alguna figura, es decir, si el ArrayList
        figura está vacío o no. Esto lo hago porque si no existe ninguna figura
        guardada, no tiene sentido que acceda a estas opciones.
        Aunque haya implementado un try catch, prefiero que el programa quede
        más "limpio", y que la excepción solo salte si de verdad se ha producido
        un error al exportar los datos, no porque el usuario quiera exportar
        algo que no existe.
        */
        if (this.checkFigure()) {
            if (c.writeCSV()) {
                System.out.println("Los datos se han exportado a un CSV correctamente.");
            } else {
                System.out.println("Fallo al exportar los datos a un CSV.");
            }
        }
        readString("%n%nPresione una tecla para continuar.....");
    }

    private void writeHTML() {
        if (this.checkFigure()) {
            if (c.writeHTML()) {
                underline2("Los datos se han exportado a HTML correctamente.");
            } else {
                underline2("Fallo al exportar los datos a HTML.");
            }
        }
        readString("%n%nPresione una tecla para continuar.....");
    }

    private void consultMain() {
        if (this.checkFigure()) {
            String consult = readString("%nIntroduzca el identificador de "
                    + "la figura: ");
            boolean repeatOrNo = this.consultFigure(consult);
            if (repeatOrNo) {
                underline2("Datos consultados correctamente.");
            } else {
                underline2("Error al consultar datos.");
            }
        }
        readString("%n%nPresione una tecla para continuar.....");
    }

    private void changeMain() {
        if (this.checkFigure()) {
            String consult = readString("%nIntroduzca el identificador de "
                    + "la figura: ");
            int repeatOrNo = this.changeFigure(consult);
            switch (repeatOrNo) {
                case 0 ->
                    underline2("La figura se ha modificado correctamente.");
                case -1 ->
                    underline2("Error al modificar la figura.");
                case 1 ->
                    underline2("Figura no modificada.");
            }
        }
        readString("%n%nPresione una tecla para continuar.....");
    }

    private void deleteMain() {
        if (this.checkFigure()) {
            String consult = readString("%nIntroduzca el identificador de "
                    + "la figura: ");
            int repeatOrNo = this.deleteFigure(consult);
            switch (repeatOrNo) {
                case 0 ->
                    underline2("La figura se ha eliminado correctamente");
                case -1 ->
                    underline2("Error al eliminar la figura.");
                case 1 ->
                    underline2("Figura no eliminada.");
            }
        }
        readString("%n%nPresione una tecla para continuar.....");
    }

    private void addFigure() {
        String[] add = new String[7];
        int puedesono;
        /*
        Guardo los datos de la nueva figura en un array de strings para pasarselo
        al model más fácilmente. Podría hacerlo pasandole cada dato con su formato
        y así no tendría que hacer la conversión a string aquí, y luego en el model
        a su formato original, pero me pareció más cómodo así.
        */
        underline2("DATOS DE LA NUEVA FIGURA");
        /*
        Cada opción está envuelta en su propio do while, del que no saldrá si
        alguna de las restricciones impuestas no se cumple.
        */
        do {
            add[0] = readString("%nIntroduzca identificador: ");
            puedesono = c.checkValidityStrings(add[0]);
        } while (puedesono != 0);

        do {
            add[1] = String.valueOf(readFloat("%nIntroduzca altura (0-1.5): "));
            puedesono = c.checkValidityFloat(add[1]);
        } while (puedesono != 0);

        do {
            add[2] = readString("%nIntroduzca material: ");
            puedesono = c.checkValidityStrings2(add[2]);
        } while (puedesono != 0);

        do {
            add[3] = String.valueOf(readInt("%nIntroduzca cantidad (>=1): "));
            puedesono = c.checkValidityIntegerCant(add[3]);
        } while (puedesono != 0);

        do {
            add[4] = String.valueOf(readInt("%nIntroduzca año (0-2100): "));
            puedesono = c.checkValidityIntegerYear(add[4]);
        } while (puedesono != 0);

        do {
            add[5] = readString("%nIntroduzca nombre de foto (.png): ");
            puedesono = c.checkValidityStringsFoto(add[5]);
        } while (puedesono != 0);

        do {
            add[6] = readString("%nIntroduzca fabricante: ");
            add[6] = add[6].toUpperCase().charAt(0) + add[6].substring(1, add[6].length()).toLowerCase();
            puedesono = c.checkValidityStrings2(add[6]);

        } while (puedesono != 0);
        /*
        Finalmente, le paso al controller el array que contiene los nuevos datos,
        y en el controller los paso al model, donde crearé una nueva "fila" de
        tipo figura con los datos insertados.
        */
        c.addFigure(add);
        readString("%n%nPresione una tecla para continuar.....");
    }

    private boolean consultFigure(String consult) {
        int maxSize[] = c.getMaxSize(c.getFigura());
        String imprimir = c.consultFigure(consult, maxSize);
        if (imprimir == null) {
            underline2("El identificador no existe.");
            return false;
        } else {
            System.out.println("");
            System.out.printf(ANSI_BLUE_BACKGROUND + ANSI_WHITE);
            underline2("CONSULTA");
            underline2(c.printTitle(maxSize));
            System.out.printf(ANSI_WHITE_BACKGROUND + ANSI_BLACK);
            try {
                System.out.println(imprimir);
                System.out.printf(ANSI_RESET);
                return true;

            } catch (Exception ex) {
                Logger.getLogger(View.class
                        .getName()).log(Level.SEVERE, null, ex);
                System.out.printf(ANSI_RESET);
                return false;
            }
        }
    }

    private int deleteFigure(String consult) {
        boolean exists = consultFigure(consult);
        if (exists) {
            boolean confirm;
            boolean flag;
            confirm = siOnoPropio("Está seguro de borrar la figura?");
            if (confirm) {
                flag = c.deleteFigure(consult);
                if (!flag) {
                    underline2("El identificador no existe.");
                } else {
                    return 0;
                }
            } else {
                return 1;
            }
        } else {
            return -1;
        }
        return -1;
    }

    private int changeFigure(String consult) {
        boolean exists = consultFigure(consult);
        /*
        Este método se parece mucho al de añadir una nueva figura, lo único
        que cambia es que al principio te muestra los datos de la figura que
        quieres modificar, y que te pregunta que opción quieres modificar,
        no estando obligado a modificarla entera. Finalmente te pregunta si
        deseas modificar algun dato más, y cuando la respuesta es no, actua igual
        que el método de añadir figura, pasa los datos al controller, y este
        al model.
        */
        if (exists) {
            boolean confirm;
            boolean salir = false;
            boolean repeat;
            String cambio = null;
            String option = null;

            confirm = siOnoPropio("Quiere modificar algún dato?");
            if (confirm) {
                String options = "%n2. Altura"
                        + "%n3. Material"
                        + "%n4. Cantidad"
                        + "%n5. Año"
                        + "%n6. Foto"
                        + "%n7. Fabricante"
                        + "%n%nQue desea modificar? ";
                do {
                    do {
                        int puedesono;
                        option = readString(options).toLowerCase();
                        switch (option) {
                            case "2" -> {
                                do {
                                    cambio = String.valueOf(readFloat("Introduzca nueva altura (0-1.5): "));
                                    puedesono = c.checkValidityFloat(cambio);
                                } while (puedesono != 0);
                                salir = true;
                            }
                            case "3" -> {
                                do {
                                    cambio = readString("Introduzca nuevo material: ");
                                    puedesono = c.checkValidityStrings2(cambio);
                                } while (puedesono != 0);
                                salir = true;
                            }
                            case "4" -> {
                                do {
                                    cambio = String.valueOf(readInt("Introduzca nueva cantidad (>=1): "));
                                    puedesono = c.checkValidityIntegerCant(cambio);
                                } while (puedesono != 0);
                                salir = true;
                            }
                            case "5" -> {
                                do {
                                    cambio = String.valueOf(readInt("Introduzca nuevo año (0-2100): "));
                                    puedesono = c.checkValidityIntegerYear(cambio);
                                } while (puedesono != 0);
                                salir = true;
                            }
                            case "6" -> {
                                do {
                                    cambio = readString("Introduzca nueva foto (.png): ");
                                    puedesono = c.checkValidityStringsFoto(cambio);
                                } while (puedesono != 0);
                                salir = true;
                            }
                            case "7" -> {
                                do {
                                    cambio = readString("Introduzca nuevo fabricante: ");
                                    cambio = cambio.toUpperCase().charAt(0) + cambio.substring(1, cambio.length()).toLowerCase();
                                    puedesono = c.checkValidityStrings2(cambio);
                                } while (puedesono != 0);
                            }
                            default -> {
                                this.incorrectOption();
                            }
                        }
                    } while (!salir);
                    c.changeFigure(Integer.parseInt(option), cambio, consult);
                    repeat = siOnoPropio("Desea modificar alguna otra opción?");
                } while (repeat);
            } else {
                return 1;
            }
            return 0;
        } else {
            return -1;
        }
    }

    public void checkBinary() {
        //Comprueba si existe el fichero binario.
        boolean exists = c.checkBinary();
        //Si existe pregunta al usuario si desea importarlo.
        if (exists) {
            boolean confirm = siOnoPropio("Se ha detectado un archivo binario."
                    + "Desea importarlo?");
            if (confirm) {
                /*
                Si la respuesta es si, importa los datos, teniendo dos posibilidades,
                que la importación haya tenido éxito o no.
                */
                if (c.importDataBin()) {
                    underline2("Importación realizada con éxito.");
                } else {
                    underline2("Error en la importación.");
                }
            } else {
                underline2("Archivo binario no importado.");
            }
        } else {
            underline2("Archivo binario no encontrado.");
            System.out.printf("%n");
            /*
            Si no se encuentra fichero.bin le indica al usuario que debe
            importar un CSV para poder realizar la mayoría de opciones que
            proporciona el programa.
            Si no lo hiciera, solo podría añadir una figura manualmente.
            */
            underline2("Importe un CSV para comenzar.");
            System.out.printf("%n");
        }
        readString("%n%nPresione una tecla para continuar......");
    }

    private void exportBin() {
        if (c.exportBin()) {
            underline2("El archivo binario se ha guardado correctamente.");
        } else {
            underline2("Se ha producido un error al guardar el fichero binario.");
        }
        readString("%n%nPresione una tecla para salir.");
    }

    public void setController(Controller c) {
        this.c = c;
    }

    public void printRow(String toPrint) {
        /*
        Imprimo cada fila del ArrayList pasada desde controller, pues para
        recorrer dicha lista, se necesitaba un import de Figura, cosa que
        en la vista no está permitido hacer, por eso la creación de view y model
        antes que controller explicada en el archivo principal.
        */
        System.out.println(toPrint);
    }

    /*
    Hago un siOno propio porque el que viene en la biblioteca no te da la opción
    si el usuario introduce algo distinto a lo que pide.
    Si se introducía un valor distinto, el comportamiento era aleatorio, aunque la
    mayoría de veces funcionaba como un "no", pero prefiero asegurarme el buen
    funcionamiento.
    */
    private static boolean siOnoPropio(String prompt) {
        boolean salir = false;
        String b;
        do {
            b = readString(prompt + "[SsNn] ");
            String toLowerCase = b.toLowerCase();
            /*
            Lee de teclado la opción introducida por el usuario y lo pasa a
            minúsculas. Posteriormente comprueba si es algun valor distinto o no
            a "s" o "n", mostrando por pantalla un mensaje de error si la opción
            no es válida.
            */
            if (!"s".equals(toLowerCase) || !"n".equals(toLowerCase)) {
                salir = false;
            }
            if ("s".equals(toLowerCase) || "n".equals(toLowerCase)) {
                salir = true;
            }
            if (!salir) {
                underline2("Opción no válida.");
            }
        } while (!salir);
        return b.equalsIgnoreCase("s");
    }

    private boolean checkFigure() {
        /*
        Si no existe ningún dato introducido en el ArrayList, y el usuario
        intenta acceder a otra opción que no sea añadir una nueva figura o importar
        un CSV, este es el mensaje que mostrarápor pantalla.
        */
        if (!c.checkFigure()) {
            underline2("No hay datos. Importe un CSV o añada una figura manualmente.");
            return false;
        } else {
            return true;
        }
    }

    private void incorrectOption() {
        //Mostrará este mensaje cada vez que el usuario tecleé algo que no es correcto.
        out.printf("%n%nOpción incorrecta%n%n");
        readString("%n%nPresione una tecla para continuar.....");
    }

    private void sortIdentificador(int[] maxSize) {
        if (this.checkFigure()) {
            System.out.printf(ANSI_BLUE_BACKGROUND + ANSI_WHITE);
            underline2(c.printTitle(maxSize));
            if (!c.sortFigure(maxSize, 1)) {
                underline2("Ha ocurrido un problema al ordenar la lista.");
            }
        }
        readString("%n%nPresione una tecla para continuar.....");
    }

    private void sortYearIdentificador(int[] maxSize) {
        if (this.checkFigure()) {
            System.out.printf(ANSI_BLUE_BACKGROUND + ANSI_WHITE);
            underline2(c.printTitle(maxSize));
            if (!c.sortFigure(maxSize, 2)) {
                underline2("Ha ocurrido un problema al ordenar la lista.");
            }
        }
        readString("%n%nPresione una tecla para continuar.....");
    }

    private void sortFabricanteYear(int[] maxSize) {
        if (this.checkFigure()) {
            System.out.printf(ANSI_BLUE_BACKGROUND + ANSI_WHITE);
            underline2(c.printTitle(maxSize));
            if (!c.sortFigure(maxSize, 3)) {
                underline2("Ha ocurrido un problema al ordenar la lista.");
            }
        }
        readString("%n%nPresione una tecla para continuar.....");
    }

    public void errorInput(int option) {
        /*
        Método con los distintos errores que puede dar la inserción/modificación
        de datos en la lista.
        */
        switch (option) {
            case 1 ->
                underline2("El campo no puede estar vacío.");
            case 2 ->
                underline2("El identificador está repetido.");
            case 3 ->
                underline2("Identificador no válido.");
            case 4 ->
                underline2("Valor no válido. Debe estar entre 0 y 1.5 metros.");
            case 5 ->
                underline2("No puede ser un numero.");
            case 6 ->
                underline2("El valor debe ser 1 como mínimo.");
            case 7 ->
                underline2("El valor debe estar comprendido entre 0 y 2100");
            case 8 ->
                underline2("Formato de imágen no válido.");
        }
    }
}
