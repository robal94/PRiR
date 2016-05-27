/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainbowtables;

import java.util.ArrayList;

/**
 *
 * @author Tobiasz
 */
public class RainbowTables {
    private static GeneratorStringow generatorStringow;
    private static ArrayList<record> list;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean bezParametrow = true;
        generatorStringow = new GeneratorStringow();
        list = new ArrayList<>(1000);
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
                    if(Integer.parseInt(s)>0){
                        generatorStringow.setSize(Integer.parseInt(s));
                    }
                    break;
            }
         }
        if(bezParametrow){
            generatorStringow.setFlagNumerics();
        }
        for(int i = 0; i < 1000; i++){
            list.add(new record(generatorStringow.generateString()));
            System.out.println(list.get(i).getString()+" "+list.get(i).getHash());
        }
        
        }
         

    
}
