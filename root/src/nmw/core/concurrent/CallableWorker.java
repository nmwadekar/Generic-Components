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

package nmw.core.concurrent;

import java.util.concurrent.Callable;
import java.util.logging.Logger;
import nmw.core.concurrent.IWorker;
/**
 * @author Nayan_Wadekar
 */

public class CallableWorker<T,E> implements Callable<T>{

	private static final Logger logger = Logger.getLogger(CallableWorker.class.getName());
	
	private IWorker<T,E> iWorker;
	private E e;
	private int workerId;
	
	/**
	 * @param iWorker The class to invoke for processing
	 * @param e The type of Input for the process
	 * @param workerId The task identifier
	 */
	public CallableWorker(IWorker<T,E> iWorker, E e, int workerId) {
		
		super();
		
		this.iWorker = iWorker;
		this.e = e;
		this.workerId = workerId;
	}

	/** 
	 * @return Task output
	 */
	//@Override
	public T call(){
		
		T t = iWorker.performTask(e);

		logger.info("CallableWorker["+workerId+"] OUTPUT : " + t);
		
		return t;
	}
}
