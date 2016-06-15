/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainbowtables;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
/**
 *
 * @author przemek
 */
public interface RainbowRemoteInterface extends Remote{
    void generate() throws RemoteException;
    void receive(ArrayList<Record> clientRainbow) throws RemoteException;
    ArrayList<Record> getFirstRound() throws RemoteException;
    public void printTable() throws RemoteException;
    public void init(String[] args) throws RemoteException;
}
