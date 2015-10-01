/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mecanismos.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import mecanismos.View.MainView;

/**
 *
 * @author giovannirojas
 */
public class MainController implements ActionListener, KeyListener {

    private MainView view;

    public MainController(MainView view) {
        this.view = view;

        // Menu 
        view.getSalirItem().addActionListener(this);
        view.getBarras4Select().addActionListener(this);

        // 4 Barras 
        view.getA4Barras().addKeyListener(this);
        view.getB4Barras().addKeyListener(this);
        view.getC4Barras().addKeyListener(this);
        view.getD4Barras().addKeyListener(this);
        view.getO24Barras().addKeyListener(this);
        view.getO34BarrasAbierto().addKeyListener(this);
        view.getO44BarrasAbierto().addKeyListener(this);
        view.getO34BarrasCruzado().addKeyListener(this);
        view.getO44BarrasCruzado().addKeyListener(this);
        view.getA4Barras().addActionListener(this);
        view.getB4Barras().addActionListener(this);
        view.getC4Barras().addActionListener(this);
        view.getD4Barras().addActionListener(this);
        view.getO24Barras().addActionListener(this);
        view.getO34BarrasAbierto().addActionListener(this);
        view.getO44BarrasAbierto().addActionListener(this);
        view.getO34BarrasCruzado().addActionListener(this);
        view.getO44BarrasCruzado().addActionListener(this);
        view.getRad024Barras().addActionListener(this);
        view.getRadAbierto4Barras().addActionListener(this);
        view.getRadCruzado4Barras().addActionListener(this);
        view.getRadTrans4Barras().addActionListener(this);
        view.getRadExtremos4Barras().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == view.getSalirItem()) {
            System.exit(0); // No errors to exit. 

        } else if (evt.getSource() == view.getBarras4Select()) {
            view.getContainerPanel().setSelectedIndex(1);

        } else if (evt.getSource() == view.getA4Barras() || evt.getSource() == view.getB4Barras()
                || evt.getSource() == view.getC4Barras() || evt.getSource() == view.getD4Barras()
                || evt.getSource() == view.getO24Barras() || evt.getSource() == view.getO34BarrasAbierto()
                || evt.getSource() == view.getO44BarrasAbierto() || evt.getSource() == view.getO34BarrasCruzado()
                || evt.getSource() == view.getO44BarrasCruzado()
                || evt.getSource() == view.getRad024Barras()
                || evt.getSource() == view.getRadAbierto4Barras()
                || evt.getSource() == view.getRadCruzado4Barras()
                || evt.getSource() == view.getRadTrans4Barras() 
                || evt.getSource() == view.getRadExtremos4Barras()
                ) {
            String digitGood = isGoodDigit();
            if (digitGood.equalsIgnoreCase("")) {
                double a, b, c, d, o2;
                a = Double.parseDouble(view.getA4Barras().getText());
                b = Double.parseDouble(view.getB4Barras().getText());
                c = Double.parseDouble(view.getC4Barras().getText());
                d = Double.parseDouble(view.getD4Barras().getText());
                o2 = Double.parseDouble(view.getO24Barras().getText());
                if (!view.getRad024Barras().isSelected()) {
                    o2 = Math.toRadians(o2);
                }
                calculate4Barras(a, b, c, d, o2);

            } else {
                JOptionPane.showMessageDialog(null, "Error en datos : \n" + digitGood, "Error en datos ", 0);
            }

        }
    }

    @Override
    public void keyTyped(KeyEvent evt) {
        if (evt.getSource() == view.getA4Barras() || evt.getSource() == view.getB4Barras()
                || evt.getSource() == view.getC4Barras() || evt.getSource() == view.getD4Barras()
                || evt.getSource() == view.getO24Barras() || evt.getSource() == view.getO34BarrasAbierto()
                || evt.getSource() == view.getO44BarrasAbierto() || evt.getSource() == view.getO34BarrasCruzado()
                || evt.getSource() == view.getO44BarrasCruzado()) {

            char c = evt.getKeyChar();
            if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_PERIOD) || (c == KeyEvent.VK_DELETE))) {
                view.getToolkit().beep();
                evt.consume();
            }

        }
    }

    /**
     * Receive the parameters, and gives the result and put it on the JTextField
     * as corresponds.
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param o2
     */
    public void calculate4Barras(double a, double b, double c, double d, double o2) {
        // o2 debe estar en radianes. 
        double k1, k2, k3, k4, k5, A, B, C, D, E, F, o3pos, o4pos, o3neg, o4neg, atrans, min, max;
        try {
            k1 = d / a;
            k2 = d / c;
            k3 = (Math.pow(a, 2) - Math.pow(b, 2) + Math.pow(c, 2) + Math.pow(d, 2)) / (2 * a * c);
            k4 = d / b;
            k5 = (Math.pow(c, 2) - Math.pow(d, 2) - Math.pow(a, 2) - Math.pow(b, 2)) / (2 * a * b);

            A = (Math.cos(o2)) - k1 - (k2 * Math.cos(o2)) + k3;
            B = -2 * Math.sin(o2);
            C = k1 - (k2 + 1) * Math.cos(o2) + k3;
            D = Math.cos(o2) - k1 + k4 * Math.cos(o2) + k5;
            E = -2 * Math.sin(o2);
            F = k1 + (k4 - 1) * Math.cos(o2) + k5;

            // Abierto
            o3neg = 2 * Math.atan((-E - Math.sqrt(Math.pow(E, 2) - 4 * D * F)) / (2 * D));
            o4neg = 2 * Math.atan((-B - Math.sqrt(Math.pow(B, 2) - 4 * A * C)) / (2 * A));
            if (!view.getRadAbierto4Barras().isSelected()) {
                o3neg = (Math.toDegrees(o3neg));
                o4neg = (Math.toDegrees(o4neg));
            }
            // Mostrar el resultado. 
            view.getO34BarrasAbierto().setText("" + to2DecimalFormat(o3neg));
            view.getO44BarrasAbierto().setText("" + to2DecimalFormat(o4neg));

            // Cerrado
            o3pos = 2 * Math.atan((-E + Math.sqrt(Math.pow(E, 2) - 4 * D * F)) / (2 * D));
            o4pos = 2 * Math.atan((-B + Math.sqrt(Math.pow(B, 2) - 4 * A * C)) / (2 * A));
            if (!view.getRadCruzado4Barras().isSelected()) {
                o3pos = (Math.toDegrees(o3pos));
                o4pos = (Math.toDegrees(o4pos));
            }
            // Mostrar el resultado. 
            view.getO34BarrasCruzado().setText("" + to2DecimalFormat(o3pos));
            view.getO44BarrasCruzado().setText("" + to2DecimalFormat(o4pos));

            // Angulo de transmisión 
            if (view.getRadAbierto4Barras().isSelected()) {
                atrans = Math.abs(o3neg - o4neg);
            } else {
                atrans = Math.abs(o3neg - o4neg);
                atrans = Math.toRadians(atrans); // Convierto a rads antes de seguir.

            }

            if (atrans > Math.PI / 2) {
                atrans = Math.PI - atrans;
            }
            if (!view.getRadTrans4Barras().isSelected()) {
                atrans = Math.toDegrees(atrans); // Para ver como mostrar el resultado
            }

            // Mostrar el resultado
            view.getAngTrans4barrasField().setText("" + to2DecimalFormat(atrans));
            
            // Valores Extremos 
            // Minimo
            min = Math.acos((Math.pow(b, 2)+Math.pow(c, 2)-Math.pow((d-a), 2))/(2*b*c));
            // Máximo
            max = Math.PI - Math.acos((Math.pow(b, 2)+Math.pow(c, 2)-Math.pow((d+a), 2))/(2*b*c));
            // Rads o no 
            if (!view.getRadExtremos4Barras().isSelected()){
                min = Math.toDegrees(min);
                max = Math.toDegrees(max);
            }
            // Mostrar datos
            view.getMin4BarrasField().setText(""+to2DecimalFormat(min));
            view.getMax4BarrasField().setText(""+to2DecimalFormat(max));
            
        } catch (Exception e) {
            System.err.println("Error en calcular 4 Barras " + e);
        }

    }

    public double to2DecimalFormat(double value) {

        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(value));

    }

    /**
     *
     * @return is a good digit True of False
     */
    public String isGoodDigit() {
        double a, b, c, d, o2;
        String errorMsj = "";

        try {
            a = Double.parseDouble(view.getA4Barras().getText());
        } catch (Exception e) {
            a = 0.0;
            view.getA4Barras().setText("" + a);
            errorMsj += "Error en valor de A";

        }
        try {
            b = Double.parseDouble(view.getB4Barras().getText());
        } catch (Exception e) {
            b = 0.0;
            view.getB4Barras().setText("" + b);
            errorMsj += "\nError en valor de B";

        }
        try {
            c = Double.parseDouble(view.getC4Barras().getText());
        } catch (Exception e) {
            c = 0.0;
            view.getC4Barras().setText("" + c);
            errorMsj += "\nError en valor de C";

        }
        try {
            d = Double.parseDouble(view.getD4Barras().getText());
        } catch (Exception e) {
            d = 0.0;
            view.getD4Barras().setText("" + d);
            errorMsj += "\nError en valor de D";

        }
        try {
            o2 = Double.parseDouble(view.getO24Barras().getText());
        } catch (Exception e) {
            o2 = 0.0;
            view.getO24Barras().setText("" + o2);
            errorMsj += "\nError en valor de ø2";

        }

        if (a != 0.0 && b != 0.0 && c != 0.0 && d != 0.0 && o2 != 0.0) {
            return "";
        }
        return errorMsj;

    }

    @Override
    public void keyPressed(KeyEvent evt) {
    }

    @Override
    public void keyReleased(KeyEvent evt) {

    }

}
