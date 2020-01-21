/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FECOP;

import Interface.Reponse;
import java.io.Serializable;

/**
 *
 * @author Pierre
 */
public class ReponseFecop implements Reponse, Serializable {
    public static int LOG_OK = 201; 
    public static int LOG_PAS_OK = 202; 
    public static int DEMANDE_OK = 203; 
    public static int DEMANDE_PAS_OK = 204;
    public static int REPONSE_OK = 205; 
    public static int REPONSE_PAS_OK = 206; 
    public static int EVENT_OK = 207; 
    public static int EVENT_PAS_OK = 208; 
    
    public static int CLOSE = 250; 
    
    private int code;
    private String charge;
    
    public ReponseFecop(int c, String chu)
    {
        code = c;
        setCharge(chu);
    }
    
    @Override
    public int getCode() { return code; }
    public String getCharge() { return charge; }
    public void setCharge(String chargeUtile) { this.charge = chargeUtile; } 
}
