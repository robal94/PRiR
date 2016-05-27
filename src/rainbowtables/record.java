/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainbowtables;

/**
 *
 * @author Tobiasz
 */
public class record {
    private final String string;
    private String hash;
    
    public record(String string){
        this.string = string;
        this.hash = null;
    }
    
    public String getString(){
        return string;
    }
    public String getHash(){
        return hash;
    }
    public void setHash(String hash){
        this.hash = hash;
    }
    
}
