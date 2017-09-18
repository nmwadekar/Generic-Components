package util;

import org.apache.log4j.*;

public class MessageLogger {
	
	private static final String pattern = "%d{dd MMM yyyy HH:mm:ss} %5p [%t] %l %x :%m%n";	
	private static final PatternLayout layout = new PatternLayout(pattern); 
	private static RollingFileAppender stdAppender = new RollingFileAppender();
	private static RollingFileAppender errorAppender = new RollingFileAppender(); 
	private static RollingFileAppender requestAppender = new RollingFileAppender();
	private static RollingFileAppender responseAppender = new RollingFileAppender();
	private static final String stdLogPath = "D:/Channels/Logs/stdLogs.out";
	private static final String errorLogPath = "D:/Channels/Logs/errorLogs.out";
	private static final String requestLogPath = "D:/Channels/Logs/requestLogs.out";
	private static final String responseLogPath = "D:/Channels/Logs/responseLogs.out";	
	
	public static Logger getStdLogger(String name) {
		Logger l = Logger.getLogger(name);		 	
		stdAppender.setFile(stdLogPath);
		stdAppender.setLayout(layout); 
		stdAppender.setImmediateFlush(true);
		stdAppender.setAppend(true);
		stdAppender.activateOptions();		
		l.addAppender(stdAppender);	
		return l;
	}
	
	
	public static Logger getErrorLogger(String name) {
		Logger l = Logger.getLogger(name);			
		errorAppender.setFile(errorLogPath);
		errorAppender.setThreshold(Level.ERROR);
		errorAppender.setLayout(layout); 
		errorAppender.setImmediateFlush(true);
		errorAppender.setAppend(true);
		errorAppender.activateOptions();
		l.addAppender(errorAppender);		
		l.setAdditivity(false);
		return l;
	}
	
	public static Logger getRequestLogger(String name) {
		Logger l = Logger.getLogger(name);		
		requestAppender.setFile(requestLogPath);		
		requestAppender.setLayout(layout); 
		requestAppender.setImmediateFlush(true);
		requestAppender.setAppend(true);
		requestAppender.activateOptions();
		l.addAppender(requestAppender);							
		l.setAdditivity(false);
		return l;
	}
	
	public static Logger getResponseLogger(String name){
		Logger l = Logger.getLogger(name);		
		responseAppender.setFile(responseLogPath);		
		responseAppender.setLayout(layout); 
		responseAppender.setImmediateFlush(true);
		responseAppender.setAppend(true);
		responseAppender.activateOptions();
		l.addAppender(responseAppender);			
		l.setAdditivity(false);
		return l;
	}
}