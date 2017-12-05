/**
 * Copyright (C) 2014  Nayan Wadekar
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package nmw.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Nayan_Wadekar
 */

public class ExecutionManager<T, E> {

	private static final Logger logger = Logger.getLogger(ExecutionManager.class.getName());
	
	private List<CallableWorker<T, E>> taskList;
	private int thresholdValue;
	
	/**
	 * @param taskList Tasks to be performed of type - CallableWorker
	 * @param thresholdValue In seconds. If values <= 0, then the execution will wait till all tasks gets completed.
	 *  For value > 0, it will wait maximum till that duration & will try to cancel pending tasks.
	 */
	public ExecutionManager(List<CallableWorker<T, E>> taskList, int thresholdValue) {
		super();
		this.taskList = taskList;
		this.thresholdValue = thresholdValue;
	}

	/**
	 * @return List of results of the enqueued tasks
	 */
	public List<T> perform() {
		
		logger.info("[ExecutionManager - INITIALIZING] with thresholdValue :  "+thresholdValue);
		
		long startTime = System.currentTimeMillis();
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		List<T> results = new ArrayList<T>();
		List<Future<T>> futureList = null;
		
		try {
			 
			if(thresholdValue > 0)
				futureList = executorService.invokeAll(taskList, thresholdValue, TimeUnit.SECONDS);
			else 
				futureList = executorService.invokeAll(taskList);
			
			for(Future<T> future : futureList){
				
					try {
						
						results.add(future.get());
						
					} catch (InterruptedException e) {
						logger.log(Level.WARNING, e.getMessage());
					} catch (ExecutionException e) {
						logger.log(Level.WARNING, e.getMessage());
					} catch (Exception e) {
						logger.log(Level.WARNING, e.getMessage());
					}
			}
			
		} catch (InterruptedException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage());
		}finally {
			
			logger.info("[ExecutionManager - SHUTTING DOWN]");
			executorService.shutdownNow();
		}
		long endTime = System.currentTimeMillis();
		logger.info(" ExecutionManager perform() : ExecuteTime(ms): "+ (endTime - startTime));
		return results;
	}
	
}
