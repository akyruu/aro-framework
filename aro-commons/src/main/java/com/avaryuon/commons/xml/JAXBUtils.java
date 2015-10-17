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
package com.avaryuon.commons.xml;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.avaryuon.commons.ArrayUtils;

/**
 * Contains utility methods for JAXB. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class JAXBUtils {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private JAXBUtils() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/**
	 * Parses XML input stream with JAXB unmarshalling.
	 * 
	 * @param is
	 *            Source stream.
	 * @param targetClass
	 *            Class to transform result for return.
	 * @param classes
	 *            Classes to bounds (necessary in specific cases).
	 * 
	 * @return Object from parsing or null if an error occurred.
	 * @throws JAXBException
	 *             Parsing error.
	 * @throws IllegalArgumentException
	 *             Target class is null.
	 */
	public static < T > T parseXmlFile( InputStream is, Class< T > targetClass,
			Class< ? >... classes ) throws JAXBException {
		Object result = null;

		if( targetClass == null ) {
			throw new IllegalArgumentException( "Target class is required" );
		}

		if( is != null ) {
			Class< ? >[] classesToBeBound = ArrayUtils.push( classes,
					targetClass );
			JAXBContext context = JAXBContext.newInstance( classesToBeBound );
			Unmarshaller unmarshaller = context.createUnmarshaller();
			result = unmarshaller.unmarshal( is );
		}

		return (result == null) ? null : targetClass.cast( result );
	}

}
