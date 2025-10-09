/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastro_de_clientes.view.helper;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author denis
 */
public class Helper {
    public static void centralize(Container window) {
            Toolkit tool = Toolkit.getDefaultToolkit();
            Dimension d = tool.getScreenSize();
            
            int x = (d.width - window.getWidth()) / 2;
            int h = (d.height - window.getWidth()) / 2;
            
            window.setLocation(x, h);
    }
}
