package com.avaryuon.commons.collections;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * List iterator.
 * 
 * @see com.avaryuon.commons.collections.ListUtils#iterator(List)
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class ListIterator< T > implements Iterator< T > {
	/* FIELDS ============================================================== */
	private int currentIndex;
	private int previousIndex;
	private int nextIndex;

	private final List< T > list;

	/* CONSTRUCTORS ======================================================== */
	ListIterator( List< T > list ) {
		this.currentIndex = -1;
		this.nextIndex = 0;
		this.list = list;
	}

	/* METHODS ============================================================= */
	/* Previous ------------------------------------------------------------ */
	public boolean hasPrevious() {
		return previousIndex >= 0;
	}

	public T previous() {
		if( !hasPrevious() ) {
			throw new NoSuchElementException();
		}
		T item = list.get( previousIndex );
		setIndex( previousIndex );
		return item;
	}

	/* Current ------------------------------------------------------------- */
	public boolean hasCurrent() {
		return (currentIndex >= 0) && (currentIndex < list.size());
	}

	public T current() {
		if( !hasCurrent() ) {
			throw new NoSuchElementException();
		}
		return list.get( currentIndex );
	}

	public int currentIndex() {
		return currentIndex;
	}

	/* Next ---------------------------------------------------------------- */
	@Override
	public boolean hasNext() {
		return nextIndex < list.size();
	}

	@Override
	public T next() {
		if( !hasNext() ) {
			throw new NoSuchElementException();
		}
		T item = list.get( nextIndex );
		setIndex( nextIndex );
		return item;
	}

	/* Management ---------------------------------------------------------- */
	public void reset() {
		currentIndex = -1;
		nextIndex = 0;
	}

	private void setIndex( int idx ) {
		currentIndex = idx;
		previousIndex = currentIndex - 1;
		nextIndex = currentIndex + 1;
	}
}
