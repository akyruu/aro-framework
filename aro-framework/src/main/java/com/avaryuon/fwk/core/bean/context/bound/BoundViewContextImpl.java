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
package com.avaryuon.fwk.core.bean.context.bound;

import java.lang.annotation.Annotation;

import com.avaryuon.fwk.core.bean.context.ViewScoped;
import com.avaryuon.fwk.core.bean.context.cache.SimpleScopedCache;
import com.avaryuon.fwk.core.bean.context.cache.ViewScopedCache;

/**
 * Bound view context implementation.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class BoundViewContextImpl extends SimpleBoundContext implements
		BoundViewContext {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	public BoundViewContextImpl( String contextId ) {
		super( contextId, BoundViewContext.class );
	}

	/* METHODS ============================================================= */
	/**
	 * @see com.avaryuon.fwk.core.bean.context.bound.SimpleBoundContext#getScope()
	 */
	@Override
	public Class< ? extends Annotation > getScope() {
		return ViewScoped.class;
	}
	
	/**
	 * @see com.avaryuon.fwk.core.bean.context.bound.SimpleBoundContext#getCache()
	 */
	@Override
	public SimpleScopedCache getCache() {
		return ViewScopedCache.instance();
	}
}
