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
package com.avaryuon.fwk.core.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaryuon.commons.reflect.ReflectionUtils;

/**
 * <b>Manages bean in ARO application.</b>
 * <p>
 * Use injection annotations for declare beans.
 * </p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
@Exclude
public class BeanManager {
	/* STATIC FIELDS ======================================================= */
	/* Singleton ----------------------------------------------------------- */
	private static BeanManager instance;

	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( BeanManager.class );

	/* FIELDS ============================================================== */
	/* Injection ----------------------------------------------------------- */
	private Weld weld;
	private WeldContainer container;

	/* Extensions ---------------------------------------------------------- */
	@Getter(AccessLevel.PACKAGE)
	private BeanExtension extension;

	/* CONSTRUCTORS ======================================================== */
	private BeanManager() {
		LOGGER.info( "Initialize bean manager..." );

		// Initialize injection fields
		weld = new Weld();
		
		extension = new BeanExtension();
		weld.addExtension( extension );
		
		container = weld.initialize();

		// Add shutdown treatment
		Runtime.getRuntime().addShutdownHook( new Thread() {
			@Override
			public void run() {
				weld.shutdown();
			}
		} );

		LOGGER.info( "Bean manager is initialized !" );
	}

	/* METHODS ============================================================= */
	/* Beans --------------------------------------------------------------- */
	public < T > T getBean( Class< T > type, Annotation... qualifiers ) {
		return container.instance().select( type, qualifiers ).get();
	}

	/* Injection ----------------------------------------------------------- */
	public void injectFields( Object obj ) {
		Collection< Field > injectFields = ReflectionUtils.findFields(
				obj.getClass(), Inject.class );
		for( Field injectField : injectFields ) {
			ReflectionUtils.setValue( obj, injectField,
					getBean( injectField.getType() ) );
		}
	}

	/* Singleton ----------------------------------------------------------- */
	public static BeanManager instance() {
		if( instance == null ) {
			instance = new BeanManager();
		}
		return instance;
	}
}
