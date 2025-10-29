/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pvd.controller;

import pvd.model.Model;

/**
 *
 * @author denis
 */
public abstract class Controller {
    
    public Controller() {
    }
    
    public abstract boolean create(Model model);
    public abstract boolean update(Model model);
    public abstract boolean delete();
}
