/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import rainbowtables.GeneratorStringow;
import rainbowtables.Record;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rainbowtables.RainbowRemoteInterface;
import static rainbowtables.Sha1.toSha1;

/**
 *
 * @author Tobiasz
 */
public class RainbowTables implements RainbowRemoteInterface {

    private static GeneratorStringow generatorStringow;
    private static ArrayList<Record> list;
    private static int size = 1000; //domyślnie tablica wynikowa = 1000

    public void generate() throws RemoteException {
        RainbowTables.nextRound();//runda kończąca
        RainbowTables.nextRound();
        RainbowTables.nextRound();
        RainbowTables.nextRound();
    }

    public void receive(ArrayList<Record> clientRainbow) throws RemoteException {
        for (int i = 0; i < clientRainbow.size(); i++) {
            list.set(i, clientRainbow.get(i));
        }

    }

    //przeprowadzenie pierwszej rundy i przekazanie polowy wynikow do dalszych oblieczen do klienta
    public ArrayList getFirstRound() throws RemoteException {
        RainbowTables.firstRound();
        ArrayList<Record> half = null;
        for (int i = 0; i < size / 2; i++) {
            half.add(list.get(i));
        }
        return half;
    }
    
    public void printTable() throws RemoteException {//metoda wyświetlająca tablicę wynikową
        for (int i = 0; i < size; i++) {
            System.out.println(list.get(i).getString() + " " + list.get(i).getHash());
        }
    }
    
    public void init(String[] args) throws RemoteException{
        boolean bezParametrow = true;
        RainbowTables.generatorStringow = new GeneratorStringow();
        for (String s : args) { //pobranie parametrów = a-małe litery, A - duże litery, 0 - cyfry, 1-10 - długość hasła, >10 - wielkość tablicy wynikowej
            switch (s) {
                case "a":
                    generatorStringow.setFlagSmallAlpha();
                    bezParametrow = false;
                    break;
                case "A":
                    generatorStringow.setFlagBigAlpha();
                    bezParametrow = false;
                    break;
                case "0":
                    generatorStringow.setFlagNumerics();
                    bezParametrow = false;
                    break;
                default:
                    int x = Integer.parseInt(s);
                    if (x > 0 && x <= 10) {
                        generatorStringow.setSize(Integer.parseInt(s));
                    } else if (x > 10) {
                        size = x;
                    }
                    break;
            }
        }
        RainbowTables.list = new ArrayList<>(size);
        if (bezParametrow) {//jak nie podano alfabetu - domyślnie same cyfry
            generatorStringow.setFlagNumerics();
        }
    }

    private static void firstRound() { //pierwsza runda - tworzenie haseł i pierwszych hashów
        for (int i = 0; i < size; i++) {
            list.add(new Record(generatorStringow.generateString()));
        }

    }

    private static void nextRound() {//każda kolejna runda - redukcja, hash, zapis
        for (int i = size / 2; i < size; i++) {
            try {
                list.get(i).setHash(toSha1(generatorStringow.redukcja(list.get(i).getHash())));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(RainbowTables.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try{
            String name = "RainbowRemoteInterface";
            RainbowRemoteInterface halfRainbowGenerator = new RainbowTables();
            RainbowRemoteInterface stub = (RainbowRemoteInterface) UnicastRemoteObject.exportObject(halfRainbowGenerator, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("RemoteTables bound");
        }catch(Exception e){
            System.err.println("RemoteTables exception:");
            e.printStackTrace();
        }
        
      

    }
}
