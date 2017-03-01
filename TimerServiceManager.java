package nmw.ejb.timer;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TimedObject;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

/**
 * Session Bean implementation class TimerServiceManager
 */
@Singleton
@Startup
public class TimerServiceManager /*implements TimedObject*/{

	@Resource
    private TimerService timerService;

	
    /**
     * Default constructor. 
     */
    public TimerServiceManager() {
        // TODO Auto-generated constructor stub
    }
    
    @PostConstruct
    public void init(){
    	
    	System.out.println("IN SINGLETON INIT METHOD");
    	
    	Timer timer = timerService.createCalendarTimer(createSchedule(),new TimerConfig("EJB timer service timeout at ",false));
    	
    	System.out.println("SCHEDULED TIMER - "+timer.getSchedule());
    }
    
    @Timeout
    public void timerTimeout(/*Timer timer*/) {
        
    	System.out.println(new Date()+ " IN TIMEOUT METHOD - "/*+timer*/);
    }

    public void setTimerService(TimerService timerService) {
        this.timerService = timerService;
    }
    
    private ScheduleExpression createSchedule() {
        ScheduleExpression expression = new ScheduleExpression();
        expression.hour("*")
                .minute("*/1")
                .second("30");
        return expression;
    }

/*	@Override
	public void ejbTimeout(Timer arg0) {
		
		
		System.out.println("In timeout - "+arg0.getInfo());
		
	}*/
}
