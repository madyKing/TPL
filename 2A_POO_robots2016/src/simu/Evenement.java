package simu;
import donnees.*;
import donnees.robot.*;

abstract public class Evenement {
    private long date;
    private Robot rob;
    
    public Evenement(long d,Robot r){
        this.date = d;
        this.rob = r;
    }
    
    public Robot getRobot(){
	return this.rob;
    }
    
    public long getDate(){
        return this.date;
    }
    
    abstract public void execute();
}
