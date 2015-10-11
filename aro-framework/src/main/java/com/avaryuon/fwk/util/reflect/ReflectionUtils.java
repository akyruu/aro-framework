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
package com.avaryuon.fwk.util.reflect;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaryuon.fwk.util.LogUtils;

/**
 * Contains utility methods for reflection. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class ReflectionUtils {
	/* STATIC FIELDS ======================================================= */
	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( ReflectionUtils.class );

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private ReflectionUtils() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/* Field --------------------------------------------------------------- */
	public static Field findField( Class< ? > objClass, String fieldName ) {
		Field field = null;

		if( objClass != null && !Object.class.equals( objClass ) ) {
			try {
				field = objClass.getDeclaredField( fieldName );
			} catch( NoSuchFieldException ex ) {
				Class< ? > superclass = objClass.getSuperclass();
				LogUtils.logTrace(
						LOGGER,
						"Field \"{}\" is not found for class {}. Continue to search with superclass of {}.",
						ex, fieldName, objClass.getCanonicalName(),
						superclass.getCanonicalName() );
				field = findField( superclass, fieldName );
			} catch( SecurityException ex ) {
				LogUtils.logError( LOGGER, "Field \"{}\" is not accessible.",
						ex, fieldName );
			}
		}

		return field;
	}

	public static Object getValue( Object obj, Field field ) {
		Object value = null;

		if( field != null ) {
			field.setAccessible( true );
			try {
				value = field.get( obj );
			} catch( IllegalArgumentException | IllegalAccessException ex ) {
				LOGGER.error( "", ex );
			}
		}

		return value;
	}

}
