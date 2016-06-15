/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rainbowtables.RainbowRemoteInterface;
import rainbowtables.Record;
import static rainbowtables.Sha1.toSha1;
import rainbowtables.GeneratorStringow;
/**
 *
 * @author przemek
 */
public class MakeRainbowTable {
    private static GeneratorStringow generatorStringow;
    private static ArrayList<Record> list;
    private int size;
    
    private static void nextRound() {//każda kolejna runda - redukcja, hash, zapis
        for (int i = list.size(); i < list.size(); i++) {
            try {
                list.get(i).setHash(toSha1(generatorStringow.redukcja(list.get(i).getHash())));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(MakeRainbowTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args){
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try{
            String name = "RainbowRemoteInterface";
            Registry registry = LocateRegistry.getRegistry(args[0]);
            RainbowRemoteInterface remoteObject = (RainbowRemoteInterface) registry.lookup(name);
            
            remoteObject.init(args);
            list = remoteObject.getFirstRound();
            remoteObject.generate();
            nextRound();
            nextRound();
            nextRound();
            nextRound();
            remoteObject.receive(list);
            remoteObject.printTable();
            
            
            
            
            
        }catch(Exception e){
            System.err.println("MakeRainbowTable exception:");
            e.printStackTrace();
        }
    }
}
