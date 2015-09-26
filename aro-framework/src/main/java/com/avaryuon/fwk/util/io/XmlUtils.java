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
package com.avaryuon.fwk.util.io;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Contains utility methods for XML. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class XmlUtils {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private XmlUtils() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/**
	 * Parses XML input stream with SAX.
	 * 
	 * @param is
	 *            Source stream.
	 * @param docHandler
	 *            Document handler to set in SAX parser.
	 * 
	 * @throws SAXException
	 *             Parsing error.
	 * @throws IllegalArgumentException
	 *             Document handler is null.
	 */
	public static void parseXmlFile( InputStream is, DefaultHandler docHandler )
			throws SAXException {
		if( docHandler == null ) {
			throw new IllegalArgumentException( "Target class is required" );
		}

		if( is != null ) {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware( true );
			factory.setXIncludeAware( true );

			try {
				SAXParser parser = factory.newSAXParser();
				parser.parse( is, docHandler );
			} catch( ParserConfigurationException | IOException ex ) {
				throw new SAXException( "Parsing of XML file failed", ex );
			}
		}
	}

}
