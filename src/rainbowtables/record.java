/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainbowtables;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tobiasz
 */
public class record {
    private final String string;
    private String hash;
    
    public record(String string){//konstruktor potrzebujący hasło, automatycznie wylicza dla niego sha1
        this.string = string;
        try {
            this.hash = sha1.toSha1(string);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(record.class.getName()).log(Level.SEVERE, null, ex);
        }
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
