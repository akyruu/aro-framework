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
import java.util.Map;

import javax.enterprise.context.spi.Context;

import org.jboss.weld.context.AbstractBoundContext;
import org.jboss.weld.context.beanstore.MapBeanStore;
import org.jboss.weld.context.beanstore.NamingScheme;
import org.jboss.weld.context.beanstore.SimpleNamingScheme;

import com.avaryuon.fwk.core.bean.context.cache.SimpleScopedCache;

/**
 * <b>title</b>
 * <p>
 * description
 * </p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public abstract class SimpleBoundContext extends
		AbstractBoundContext< Map< String, Object >> {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	private NamingScheme namingScheme;

	/* CONSTRUCTORS ======================================================== */
	public SimpleBoundContext( String contextId,
			Class< ? extends Context > contextType ) {
		super( contextId, false );
		namingScheme = new SimpleNamingScheme( contextType.getName() );
	}

	/* METHODS ============================================================= */
	/* Overriding - BoundContext.class ------------------------------------- */
	@Override
	public abstract Class< ? extends Annotation > getScope();

	public abstract SimpleScopedCache getCache();

	public boolean associate( Map< String, Object > storage ) {
		if( getBeanStore() == null ) {
			setBeanStore( new MapBeanStore( namingScheme, storage, true ) );
			getBeanStore().attach();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void activate() {
		super.activate();
		getCache().begin();
	}

	@Override
	public void deactivate() {
		try {
			getCache().end();
		} finally {
			super.deactivate();
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		getBeanStore().detach();
	}
}
