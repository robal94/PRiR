/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainbowtables;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static rainbowtables.sha1.toSha1;

/**
 *
 * @author Tobiasz
 */
public class RainbowTables {
    private static GeneratorStringow generatorStringow;
    private static ArrayList<record> list;
    private static int size = 1000; //domyślnie tablica wynikowa = 1000
    
    private static void firstRound(){ //pierwsza runda - tworzenie haseł i pierwszych hashów
          for(int i = 0; i < size; i++){
            list.add(new record(generatorStringow.generateString()));
        }
       
    }
    
    private static void nextRound(){//każda kolejna runda - redukcja, hash, zapis
        for(int i = 0; i < size; i++){
            try {
                list.get(i).setHash(toSha1(generatorStringow.redukcja(list.get(i).getHash())));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(RainbowTables.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static void print(){//metoda wyświetlająca tablicę wynikową
        for(int i = 0; i < size; i++){
            System.out.println(list.get(i).getString() + " " + list.get(i).getHash());
    }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean bezParametrow = true;
        RainbowTables.generatorStringow = new GeneratorStringow();
        for (String s : args){ //pobranie parametrów = a-małe litery, A - duże litery, 0 - cyfry, 1-10 - długość hasła, >10 - wielkość tablicy wynikowej
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
                    if(x>0 && x <= 10){
                        generatorStringow.setSize(Integer.parseInt(s));
                    }else if(x>10){
                        size=x;
                    }
                    break;
            }
         }
        RainbowTables.list = new ArrayList<>(size);
        if(bezParametrow){//jak nie podano alfabetu - domyślnie same cyfry
            generatorStringow.setFlagNumerics();
        }
        RainbowTables.firstRound();//runda inicjująca
        RainbowTables.nextRound();//runda kończąca
        RainbowTables.nextRound();
        RainbowTables.nextRound();
        RainbowTables.nextRound();
        RainbowTables.print();

    }
}
