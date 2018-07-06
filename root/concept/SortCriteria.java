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

package nmw.core.concept;

/**
 * @author Nayan_Wadekar
 */

public class SortCriteria {

	private String sortColumn;
	private String sortOrder;

	public SortCriteria(String sortColumn, String sortOrder) {
		super();
		this.sortColumn = sortColumn;
		this.sortOrder = sortOrder;
	}
	/**
	 * @return the sortColumn
	 */
	public String getSortColumn() {
		return sortColumn;
	}
	/**
	 * @param sortColumn the sortColumn to set
	 */
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	/**
	 * @return the sortOrder
	 */
	public String getSortOrder() {
		return sortOrder;
	}
	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SortCriteria [sortColumn=" + sortColumn + ", sortOrder="
				+ sortOrder + "]";
	}

}
