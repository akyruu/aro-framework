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
package com.avaryuon.fwk.javafx.scene.control;

import javafx.scene.control.Dialog;
import javafx.stage.Modality;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;

import com.avaryuon.commons.StringUtils;
import com.avaryuon.fwk.core.bean.BeanManager;
import com.avaryuon.fwk.core.i18n.I18nManager;
import com.avaryuon.fwk.core.i18n.MsgKey;

/**
 * Abstract dialog with utility methods.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public abstract class AroDialog< T > extends Dialog< T > {
	/* STATIC FIELDS ======================================================= */
	protected static final String GROUP_ARO = "aro";

	/* FIELDS ============================================================== */
	/* Beans --------------------------------------------------------------- */
	@Getter(AccessLevel.PROTECTED)
	@Inject
	private I18nManager i18nMgr;

	/* CONSTRUCTORS ======================================================== */
	public AroDialog() {
		this( null );

	}

	public AroDialog( String title ) {
		this( title, Modality.NONE );
	}

	public AroDialog( String title, Modality modality ) {
		this( title, modality, true );
	}

	public AroDialog( String title, Modality modality, boolean create ) {
		super();

		setTitle( title );
		initModality( modality );

		BeanManager.instance().injectFields( this );
		if( create ) {
			createModel();
			createUI();
		}
	}

	/* METHODS ============================================================= */
	/* Access -------------------------------------------------------------- */
	protected void setTitleIfBlank( String title ) {
		if( StringUtils.isBlank( getTitle() ) ) {
			setTitle( title );
		}
	}

	/* Create -------------------------------------------------------------- */
	protected abstract void createModel();

	protected abstract void createUI();

	/* I18n ---------------------------------------------------------------- */
	public String getBundleMessage( String msgKey, Object... args ) {
		return i18nMgr.getBundleMessage( GROUP_ARO, msgKey, args );
	}

	public String getBundleMessage( MsgKey msgKey, Object... args ) {
		return i18nMgr.getBundleMessage( msgKey, args );
	}
}
