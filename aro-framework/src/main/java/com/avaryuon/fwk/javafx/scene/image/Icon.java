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
package com.avaryuon.fwk.javafx.scene.image;

import java.net.URL;

import javafx.beans.NamedArg;
import javafx.scene.image.Image;
import lombok.Getter;

import com.avaryuon.fwk.core.bean.BeanManager;
import com.avaryuon.fwk.core.style.ThemeManager;

/**
 * Specific image with cube size.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class Icon extends Image {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	@Getter
	private String name;
	@Getter
	private int size;

	/* CONSTRUCTORS ======================================================== */
	public Icon( URL iconURL ) {
		super( iconURL.toExternalForm() );
	}

	public Icon( @NamedArg("name") String name, @NamedArg("size") int size ) {
		this( name, size, false, false );
	}

	public Icon( @NamedArg("name") String name, @NamedArg("size") int size,
			@NamedArg("smooth") boolean smooth ) {
		this( name, size, smooth, false );
	}

	public Icon( @NamedArg("name") String name, @NamedArg("size") int size,
			@NamedArg(value = "smooth", defaultValue = "true") boolean smooth,
			@NamedArg("backgroundLoading") boolean backgroundLoading ) {
		super( buildURL( name, size ), size, size, true, smooth,
				backgroundLoading );
		this.name = name;
		this.size = size;
	}

	/* METHODS ============================================================= */
	/* Access -------------------------------------------------------------- */
	private static String buildURL( String name, int size ) {
		ThemeManager themeMgr = BeanManager.instance().getBean(
				ThemeManager.class );
		URL iconURL = themeMgr.getIconURL( name, size );
		return (iconURL == null) ? null : iconURL.toExternalForm();
	}
}
