

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package mecanismos;

import mecanismos.Controller.MainController;
import mecanismos.View.MainView;

/**
 *
 * @author giovannirojas
 */
public class Mecanismos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        MainView mv = new MainView();
        mv.setLocationRelativeTo(null);
        MainController mc = new MainController(mv);
        
        mv.setVisible(true);
    }
    
}
