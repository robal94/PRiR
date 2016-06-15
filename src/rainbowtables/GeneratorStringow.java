/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainbowtables;

import java.util.Random;

/**
 *
 * @author Tobiasz
 */
public class GeneratorStringow {

    private boolean smallAlpha, bigAlpha, numeric; //flagi dotyczące alfabetu haseł
    private final char[] smallAlphaList, bigAlphaList, numericList; //alfabety haseł
    private int size;  //rozmiar haseł
    private final Random generator; //generator liczb losowych

    public GeneratorStringow() { //konstruktor
        this.generator = new Random();
        this.smallAlphaList = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'w', 'x', 'y', 'z'};
        this.bigAlphaList = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'W', 'X', 'Y', 'Z'};
        this.numericList = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',};
        this.numeric = false;
        this.bigAlpha = false;
        this.smallAlpha = false;
        this.size = 5; //rozmiar hasła - domyślnie 5
    }

    private char generateChar() { //generuje pojedyńczy znak zgodnie z ustawieniami urzytkownika

        while (true) {
            int x = generator.nextInt(3);
            switch (x) {
                case 0:
                    if (smallAlpha) {
                        return smallAlphaList[generator.nextInt(25)];
                    }
                case 1:
                    if (bigAlpha) {
                        return bigAlphaList[generator.nextInt(25)];
                    }
                case 2:
                    if (numeric) {
                        return numericList[generator.nextInt(10)];
                    }
            }
        }
    }

    public String generateString() {//generuje hasło początkowe - losowe
        String wynik = "";
        for (int i = 0; i < this.size; i++) {
            wynik = wynik + generateChar();
        }
        return wynik;
    }

    private boolean ok(char x) {//sprawdza czy podany znak jest akceptowalny przez wybrany przez użytkownika alfabet
        if (smallAlpha) {
            for (int i = 0; i < 25; i++) {
                if (smallAlphaList[i] == x) {
                    return true;
                }
            }
        }
        if (bigAlpha) {
            for (int i = 0; i < 25; i++) {
                if (bigAlphaList[i] == x) {
                    return true;
                }
            }
        }
        if (numeric) {
            for (int i = 0; i < 10; i++) {
                if (numericList[i] == x) {
                    return true;
                }
            }
        }
        return false;

    }

    public String redukcja(String hash) { //funkcja redukująca
        char[] string = hash.toCharArray();
        String wynik = "";
        for (int i = 0; i < this.size; i++) {
            int j = i % 40;
            if (ok(string[i])) {
                wynik = wynik + string[i];
            }

        }
        return wynik;
    }

    public void setFlagSmallAlpha() {
        smallAlpha = true;
    }

    public void setFlagBigAlpha() {
        bigAlpha = true;
    }

    public void setFlagNumerics() {
        numeric = true;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
