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
package com.avaryuon.fwk;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AccessLevel;
import lombok.Getter;

import com.avaryuon.fwk.util.StringUtils;

/**
 * Define title of ARO application.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class AroTitle {
	/* STATIC FIELDS ======================================================= */
	private static final String DEFAULT_BASE = "ARO application";

	private static final String BASE_EXTEND_SEPARATOR = " - ";
	private static final String NEW_SUFFIX = " *";

	/* FIELDS ============================================================== */
	/* Components ---------------------------------------------------------- */
	@Getter
	private String base;

	@Getter
	private String extend;

	@Getter
	private boolean changed;

	/* Title --------------------------------------------------------------- */
	@Getter(AccessLevel.PACKAGE)
	private StringProperty property;

	/* CONSTRUCTORS ======================================================== */
	AroTitle( String titleBase ) {
		/* Components */
		base = titleBase;
		if( base == null ) {
			base = DEFAULT_BASE;
		}
		extend = null;
		changed = false;

		/* Title */
		property = new SimpleStringProperty( base );
	}

	/* METHODS ============================================================= */
	/* Access -------------------------------------------------------------- */
	public String getValue() {
		return property.getValue();
	}

	public void setBase( String newBase ) {
		base = StringUtils.isBlank( newBase ) ? DEFAULT_BASE : newBase;
		updateTitle();
	}

	public void setExtend( String newExtend ) {
		extend = newExtend;
		updateTitle();
	}

	public void setChanged( boolean newChanged ) {
		changed = newChanged;
		updateTitle();
	}

	/* Life-cycle ---------------------------------------------------------- */
	private void updateTitle() {
		StringBuilder title = new StringBuilder();
		title.append( base );
		if( StringUtils.isNotBlank( extend ) ) {
			title.append( BASE_EXTEND_SEPARATOR ).append( extend );
		}
		if( changed ) {
			title.append( NEW_SUFFIX );
		}
		property.setValue( title.toString() );
	}

	/* Overriding - Object.class ------------------------------------------- */
	@Override
	public String toString() {
		return getValue();
	}

}
