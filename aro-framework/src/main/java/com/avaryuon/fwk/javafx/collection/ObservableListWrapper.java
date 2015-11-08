/*
 * Copyright 2015 Akyruu (akyruu@hotmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.avaryuon.fwk.javafx.collection;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ModifiableObservableListBase;

/**
 * Icons collection .
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class ObservableListWrapper< T > extends
		ModifiableObservableListBase< T > {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	private List< T > lookup;

	/* CONSTRUCTORS ======================================================== */
	public ObservableListWrapper() {
		lookup = new ArrayList<>();
	}

	/* METHODS ============================================================= */
	/* Overriding - ObservableList.class ----------------------------------- */
	/** @see java.util.List#get(int) */
	@Override
	public T get( int index ) {
		return lookup.get( index );
	}

	/**
	 * @see javafx.collections.ModifiableObservableListBase#doAdd(int,
	 *      java.lang.Object)
	 */
	@Override
	protected void doAdd( int index, T element ) {
		lookup.add( index, element );
	}

	/**
	 * @see javafx.collections.ModifiableObservableListBase#doSet(int,
	 *      java.lang.Object)
	 */
	@Override
	protected T doSet( int index, T element ) {
		return lookup.set( index, element );
	}

	/**
	 * @see javafx.collections.ModifiableObservableListBase#doRemove(int)
	 */
	@Override
	protected T doRemove( int index ) {
		return lookup.remove( index );
	}

	/** @see java.util.AbstractCollection#size() */
	@Override
	public int size() {
		return lookup.size();
	}
}
