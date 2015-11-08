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
package com.avaryuon.fwk.core.bean.context.holder;

import java.util.HashMap;
import java.util.Map;

import org.jboss.weld.context.BoundContext;
import org.jboss.weld.context.ManagedContext;

/**
 * <b>title</b>
 * <p>
 * description
 * </p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public abstract class SimpleScopedHolder< C extends ManagedContext& BoundContext< Map< String, Object >>> {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	private Map< String, Object > storage;

	/* CONSTRUCTORS ======================================================== */
	public SimpleScopedHolder() {
		storage = new HashMap< String, Object >();
	}

	/* METHODS ============================================================= */
	/* Access -------------------------------------------------------------- */
	protected abstract C getContext();

	/* Life-cycle ---------------------------------------------------------- */
	public void start() {
		C context = getContext();
		storage.clear();
		context.associate( storage );
		context.activate();
	}

	public void end() {
		C context = getContext();
		try {
			context.invalidate();
			context.deactivate();
		} finally {
			storage.clear();
			context.dissociate( storage );
		}
	}
}
