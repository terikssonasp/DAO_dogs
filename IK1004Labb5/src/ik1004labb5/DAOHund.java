/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik1004labb5;

import java.util.List;

/**
 *
 * @author TErik
 */
public interface DAOHund {
    
    public void add(DTOHund dtoHund);
    
    public void delete(int id);
    
    public void update(DTOHund dtoHund);
    
    public List<DTOHund> getHundar();
}
