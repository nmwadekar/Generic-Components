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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import nmw.util.SortCriteria;

/**
 * @author Nayan_Wadekar
 *
 * @param <T>
 */
public class GenericComparator<T> implements Comparator<T> {

	private List<SortCriteria> sortCriteriaList;
	
	private static final Logger logger = Logger.getLogger(GenericComparator.class.getName());
	
	private static final String numericCheckRegex = "[+-]?\\d*(\\.\\d+)?";
	
	public GenericComparator(List<SortCriteria> sortCriteriaList){
		
		super();
		
		this.sortCriteriaList = sortCriteriaList;
	}
	
	@Override
	public int compare(T o1, T o2) {
		
		int retVal = 0;

		Object value_1 = null;
		Object value_2 = null;

		Object[] noArguments = null;
		
		Method classMethod = null;
		
		char cValue;
		
		try {
			
			String methodName = null;
			String column = null;

			BigDecimal dVal_1 = new BigDecimal(0);
			BigDecimal dVal_2 = new BigDecimal(0);
			
			for(SortCriteria criteria : sortCriteriaList){

				column = criteria.getSortColumn();
				
				cValue = column.trim().charAt(0);
				
				methodName = "get" + String.valueOf(cValue).toUpperCase() + column.substring(1, column.length());

				classMethod = o1.getClass().getMethod(methodName, null);

				value_1 = classMethod.invoke(o1, noArguments);
				value_2 = classMethod.invoke(o2, noArguments);

				if(value_1 == null)
					return 1;
				else if(value_2 == null)
					return -1;
				
				
				if(value_1 instanceof Date && value_2 instanceof Date){
					
					retVal = ((Date)value_1).compareTo((Date)value_2);
					
				}
				else if(value_1 instanceof Timestamp && value_2 instanceof Timestamp){
					
					retVal = ((Timestamp)value_1).compareTo((Timestamp)value_2);
					
				}
				else if(isNumeric(value_1.toString()) && isNumeric(value_2.toString()) 
						&& value_1.toString().length() != 0 && value_2.toString().length() != 0){

					dVal_1 = new BigDecimal(value_1.toString());
					dVal_2 = new BigDecimal(value_2.toString());
				
					retVal = dVal_1.compareTo(dVal_2);
				
				}else{
					
					retVal = value_1.toString().trim().compareToIgnoreCase(value_2.toString().trim());
				}

				if("DESC".equalsIgnoreCase(criteria.getSortOrder()))
					retVal *= -1;

				if(retVal != 0){
					break;
				}
					
			}

		} catch (SecurityException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (IllegalAccessException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (NoSuchMethodException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (InvocationTargetException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
			
		return retVal;
	}
	
	private boolean isNumeric(String input)
	{
	    return input.matches(numericCheckRegex);
	}
}
