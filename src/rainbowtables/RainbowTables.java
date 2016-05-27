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
    private static int size = 1000;
    
    private static void nextRound(){
        for(int i = 0; i < size; i++){
            try {
                String x = generatorStringow.redukcja(list.get(i).getHash());
                list.get(i).setHash(toSha1(x));
                System.out.println(x + "-> "+ list.get(i).getHash());

            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(RainbowTables.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean bezParametrow = true;
        RainbowTables.generatorStringow = new GeneratorStringow();
        for (String s : args){
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
        if(bezParametrow){
            generatorStringow.setFlagNumerics();
        }
        for(int i = 0; i < size; i++){
            list.add(new record(generatorStringow.generateString()));
           // System.out.println(list.get(i).getString()+" -> "+list.get(i).getHash());
        }
        RainbowTables.nextRound();
        System.out.println("***");
        RainbowTables.nextRound();
        System.out.println("***");
        RainbowTables.nextRound();
        System.out.println("***");
        RainbowTables.nextRound();
        System.out.println("***");

    }
}
