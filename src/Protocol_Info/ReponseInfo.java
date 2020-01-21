/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocol_Info;

import Interface.Reponse;
import java.io.Serializable;

/**
 *
 * @author Eliott
 */
public class ReponseInfo implements Reponse, Serializable{
    
    public static int CONVERT_OK = 100;
    public static int CONVERT_PASOK = 101;
    
    private int codeRet;
    private String charge; 

    ReponseInfo(int c, String str) {
        codeRet = c;
        setCharge(str);     
    }
    
    public int getCode() { return codeRet; } 
    public void setCharge(String s) { charge = s; } 
    public String getCharge() { return charge; } 
}

