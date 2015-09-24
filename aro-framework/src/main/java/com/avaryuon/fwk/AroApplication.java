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

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;

import com.avaryuon.fwk.bean.BeanManager;
import com.avaryuon.fwk.config.ConfigManager;
import com.avaryuon.fwk.resource.FileManager;

/**
 * <b>Define an ARO application</b>
 * <p>
 * Extends this class and use static method "Application.launch()".
 * </p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public abstract class AroApplication extends Application {
	/* STATIC FIELDS ======================================================= */
	/* Singleton ----------------------------------------------------------- */
	private static AroApplication instance;

	/* FIELDS ============================================================== */
	/* Java FX ------------------------------------------------------------- */
	@Getter(AccessLevel.PACKAGE)
	private Stage mainStage;

	/* Beans --------------------------------------------------------------- */
	private ConfigManager configMgr;
	private FileManager fileMgr;

	/* CONSTRUCTORS ======================================================== */
	protected AroApplication() {
		super();

		// Initialize singleton field
		instance = this;
	}

	/* METHODS ============================================================= */
	/* Life-cycle ---------------------------------------------------------- */
	@Override
	public final void init() throws Exception {
		// Gets bean from manager (this is not instantiate via injection)
		BeanManager beanMgr = BeanManager.instance();
		configMgr = beanMgr.getBean( ConfigManager.class );
		fileMgr = beanMgr.getBean( FileManager.class );
	}

	/**
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start( Stage primaryStage ) throws Exception {
		mainStage = primaryStage;
		mainStage.show();
	}

	/* Singleton ----------------------------------------------------------- */
	public static AroApplication instance() {
		return instance;
	}
}
