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

import javax.inject.Inject;
import javax.inject.Singleton;

import com.avaryuon.fwk.core.bean.context.bound.BoundViewContext;

/**
 * Holder view scoped implementation.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
@Singleton
public class ViewHolder extends SimpleScopedHolder< BoundViewContext > {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	@Inject
	private BoundViewContext context;

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Overriding - SimpleScopedHolder.class ------------------------------- */
	/**
	 * @see com.avaryuon.fwk.core.bean.context.holder.SimpleScopedHolder#getContext()
	 */
	@Override
	protected BoundViewContext getContext() {
		return context;
	}

}