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
package com.avaryuon.fwk.javafx.beans;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;

import com.avaryuon.commons.StringUtils;

/**
 * Define composed title for windows.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class TitleProperty extends SimpleStringProperty {
	/* TYPES =============================================================== */
	public static enum TitleStatus {
		NONE( null ), NEW( " *" );

		/* FIELDS */
		@Getter
		private String suffix;

		/* CONSTRUCTORS */
		private TitleStatus( String suffix ) {
			this.suffix = suffix;
		}
	}

	/* STATIC FIELDS ======================================================= */
	private static final String BASE_EXTEND_SEPARATOR = " - ";

	/* FIELDS ============================================================== */
	@Getter
	private String base;
	@Getter
	private String extend;
	@Getter
	private TitleStatus status;

	/* CONSTRUCTORS ======================================================== */
	public TitleProperty( String title ) {
		base = title;
		extend = null;
		status = TitleStatus.NONE;
		update();
	}

	/* METHODS ============================================================= */
	/* Access -------------------------------------------------------------- */
	public void setBase( String base ) {
		this.base = base;
		update();
	}

	public void setExtend( String extend ) {
		this.extend = extend;
		update();
	}

	public void setStatus( TitleStatus status ) {
		this.status = status;
		update();
	}

	/* Updating ------------------------------------------------------------ */
	private void update() {
		StringBuilder value = new StringBuilder( base );
		if( StringUtils.isNotBlank( extend ) ) {
			value.append( BASE_EXTEND_SEPARATOR ).append( extend );
		}
		if( status != TitleStatus.NONE ) {
			value.append( status.getSuffix() );
		}

		setValue( value.toString() );
	}
}
