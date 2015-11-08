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
package com.avaryuon.fwk.core.bean.context.cache;

import java.util.LinkedList;
import java.util.List;

/**
 * Simple scoped cache (inspired by RequestScopedCache).
 * 
 * @see org.jboss.weld.context.cache.RequestScopedCache
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class SimpleScopedCache {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	private ThreadLocal< List< SimpleScopedItem >> lookup;

	/* CONSTRUCTORS ======================================================== */
	protected SimpleScopedCache() {
		lookup = new ThreadLocal<>();
	}

	/* METHODS ============================================================= */
	/* Activation ---------------------------------------------------------- */
	public boolean isActive() {
		return lookup.get() != null;
	}

	/* Access -------------------------------------------------------------- */
	public void addItem( final SimpleScopedItem item ) {
		final List< SimpleScopedItem > cache = lookup.get();
		checkCacheForAdding( cache );
		cache.add( item );
	}

	private void checkCacheForAdding( final List< SimpleScopedItem > cache ) {
		if( cache == null ) {
			throw new IllegalStateException(
					"Unable to add scoped cache item when request cache is not active" );
		}
	}

	public boolean addItemIfActive( final SimpleScopedItem item ) {
		final List< SimpleScopedItem > cache = lookup.get();
		if( cache != null ) {
			cache.add( item );
			return true;
		}
		return false;
	}

	public boolean addItemIfActive( final ThreadLocal< ? > item ) {
		final List< SimpleScopedItem > cache = lookup.get();
		if( cache != null ) {
			cache.add( ( ) -> item.remove() );
			return true;
		}
		return false;
	}

	/* Life-cycle ---------------------------------------------------------- */
	public void begin() {
		end();
		lookup.set( new LinkedList< SimpleScopedItem >() );
	}

	public void end() {
		final List< SimpleScopedItem > result = lookup.get();
		if( result != null ) {
			lookup.remove();
			for( final SimpleScopedItem item : result ) {
				item.invalidate();
			}
		}
	}

	public void invalidate() {
		if( isActive() ) {
			end();
			begin();
		}
	}

}
