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
package com.avaryuon.fwk.config;

import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * <b>title</b>
 * <p>
 * description
 * </p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
class ConfigHandler extends DefaultHandler {
	/* STATIC FIELDS ======================================================= */
	private static final String PROPERTY_SEPARATOR = ".";

	/* FIELDS ============================================================== */
	/* General ------------------------------------------------------------- */
	private Map< String, Properties > propertyGroups;

	/* Variables ----------------------------------------------------------- */
	private int level;
	private String propertyGroup;
	private Properties properties;
	private Queue< String > propertyNames;
	private String propertyValue;

	/* CONSTRUCTORS ======================================================== */
	public ConfigHandler( Map< String, Properties > propertyGroups ) {
		this.level = 0;
		this.propertyGroups = propertyGroups;
		this.propertyNames = new LinkedList<>();
	}

	/* METHODS ============================================================= */
	/**
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement( String uri, String localName, String qName,
			Attributes attributes ) throws SAXException {
		if( level > 1 ) {
			propertyNames.add( localName );
		} else if( level == 1 ) {
			propertyGroup = localName;
			properties = new Properties();
			propertyNames.clear();
			propertyValue = null;
		}
		level++;
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters( char[] ch, int start, int length )
			throws SAXException {
		propertyValue = new String( ch, start, length );
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement( String uri, String localName, String qName )
			throws SAXException {
		level--;
		if( (level > 1) && (propertyValue != null) ) {
			// Build property name
			String lastPropertyName = propertyNames.poll();
			StringBuilder propertyNameBuilder = new StringBuilder();
			for( String propertyName : propertyNames ) {
				propertyNameBuilder.append( propertyName ).append(
						PROPERTY_SEPARATOR );
			}
			propertyNameBuilder.append( lastPropertyName );

			// Set property to group
			properties.setProperty( propertyNameBuilder.toString(),
					propertyValue );
		} else if( level == 1 ) {
			propertyGroups.put( propertyGroup, properties );
		}
	}
}
