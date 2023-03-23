package controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Figura;
import model.Model;
import view.View;

/**
 *
 * @author alvar
 */
public class Controller {

    Model m;
    View v;

    public Controller(Model m, View v) {
        this.m = m;
        this.v = v;
    }

    public boolean readCSV() {
        try {
            m.readCSV();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean writeCSV() {
        try {
            m.writeCSV();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean writeHTML() {
        try {
            m.writeHTML();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Figura> getFigura() {
        return m.getFigura();
    }

    public String printTitle(int maxSize[]) {
        return m.printTitle(maxSize);
    }

    public int[] getMaxSize(List<Figura> figura) {
        return m.getMaxSize(figura);
    }

    public boolean sortFigure(int maxSize[], int option) {
        /*
        Para no hacer un método distinto con cada tipo de ordenación, con uno
        solo al que le pase la opción que quiero, y dentro de este método
        un switch para cada caso, utilizo menos código.
         */
        try {
            List<Figura> figura = null;
            switch (option) {
                case 1 ->
                    figura = m.sortIdentificador();
                case 2 ->
                    figura = m.sortYearIdentificador();
                case 3 ->
                    figura = m.sortFabricanteYear();
            }
            if (figura != null) {
                for (Figura fig : figura) {
                    v.printRow(fig.getAll(maxSize));
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void addFigure(String[] add) {
        m.addFigure(add);
    }

    public String consultFigure(String identificador, int maxSize[]) {
        return m.consultFigure(identificador, maxSize);
    }

    public boolean deleteFigure(String consult) {
        boolean flag;
        flag = m.deleteFigure(consult);
        return flag;
    }

    public void changeFigure(int option, String cambio, String consult) {
        m.changeFigure(option, cambio, consult);
    }

    public boolean checkBinary() {
        return m.checkBinary();
    }

    public boolean importDataBin() {
        return m.importDataBin();
    }

    public boolean exportBin() {
        return m.exportBin();
    }

    public String correctImports() {
        return m.correctImports();
    }

    public boolean idRepeat(String id) {
        return m.idRepeat(id);
    }

    public boolean checkFigure() {
        return m.checkFigure();
    }

    public boolean isNumeric(String cambio) {
        boolean devolver = false;
        if (m.isInt(cambio)) {
            devolver = true;
        }
        if (m.isFloat(cambio)) {
            devolver = true;
        }
        return devolver;
    }

    /*
    Los métodos debajo de este comentario cuyo nombre empieza por check,
    son las restricciones que tienen que cumplir los distintos campos de figura.
     */
    public int checkValidityStrings(String toCheck) {
        if (toCheck.isBlank()) {
            v.errorInput(1);
            return -1;
        } else if (!this.idRepeat(toCheck)) {
            v.errorInput(2);
            return -2;
        } else if (this.isNumeric(toCheck)) {
            if (m.isInt(toCheck)) {
                if (Integer.parseInt(toCheck) <= 0) {
                    v.errorInput(3);
                    return -3;
                }
            } else if (m.isFloat(toCheck)) {
                if (Float.parseFloat(toCheck) <= 0) {
                    v.errorInput(3);
                    return -3;
                }
            }
        }
        return 0;
    }

    public int checkValidityStrings2(String toCheck) {
        if (toCheck.isBlank()) {
            v.errorInput(1);
            return -1;
        } else if (this.isNumeric(toCheck)) {
            v.errorInput(5);
            return -5;
        }
        return 0;
    }

    public int checkValidityStringsFoto(String toCheck) {
        if (toCheck.isBlank()) {
            v.errorInput(1);
            return -1;
        } else if (this.isNumeric(toCheck)) {
            v.errorInput(5);
            return -5;
        } else if (!toCheck.endsWith(".png")) {
            v.errorInput(8);
            return -8;
        }
        return 0;
    }

    public int checkValidityFloat(String toCheck) {
        if (Float.parseFloat(toCheck) <= 0 || Float.parseFloat(toCheck) > 1.5) {
            v.errorInput(4);
            return -4;
        }
        return 0;
    }

    public int checkValidityIntegerCant(String toCheck) {
        if (Integer.parseInt(toCheck) < 1) {
            v.errorInput(6);
            return -6;
        }
        return 0;
    }

    public int checkValidityIntegerYear(String toCheck) {
        if (Integer.parseInt(toCheck) < 0 || Integer.parseInt(toCheck) > 2100) {
            v.errorInput(7);
            return -7;
        }
        return 0;
    }
}
