/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pvd.view.helpers;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author denis
 */
public class WindowHelper {

    public static void centralize(Container window) {
        Toolkit tool = Toolkit.getDefaultToolkit();
        Dimension d = tool.getScreenSize();

        int x = (d.width - window.getWidth()) / 2;
        int y = (d.height - window.getHeight()) / 2;

        window.setLocation(x, y);
    }
}
