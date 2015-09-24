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
package com.avaryuon.fwk.bean;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * <b>Manages bean in ARO application.</b>
 * <p>
 * Use injection annotations for declare beans.
 * </p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class BeanManager {
	/* STATIC FIELDS ======================================================= */
	/* Singleton ----------------------------------------------------------- */
	private static BeanManager instance;

	/* FIELDS ============================================================== */
	/* Injection ----------------------------------------------------------- */
	private final Weld weld;
	private final WeldContainer container;

	/* CONSTRUCTORS ======================================================== */
	private BeanManager() {
		// Initialize injection fields
		weld = new Weld();
		container = weld.initialize();

		// Add shutdown treatment
		Runtime.getRuntime().addShutdownHook( new Thread() {
			@Override
			public void run() {
				weld.shutdown();
			}
		} );
	}

	/* METHODS ============================================================= */
	/* Beans --------------------------------------------------------------- */
	public < T > T getBean( Class< T > type ) {
		return container.instance().select( type ).get();
	}

	/* Singleton ----------------------------------------------------------- */
	public static BeanManager instance() {
		if( instance == null ) {
			instance = new BeanManager();
		}
		return instance;
	}
}
