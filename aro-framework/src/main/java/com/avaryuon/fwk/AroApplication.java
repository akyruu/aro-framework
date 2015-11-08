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

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaryuon.commons.RuntimeUtils;
import com.avaryuon.commons.StringUtils;
import com.avaryuon.fwk.core.bean.BeanManager;
import com.avaryuon.fwk.core.bean.Exclude;
import com.avaryuon.fwk.core.bean.context.holder.ViewHolder;
import com.avaryuon.fwk.core.config.ConfigManager;
import com.avaryuon.fwk.javafx.beans.TitleProperty;
import com.avaryuon.fwk.javafx.collection.IconList;
import com.avaryuon.fwk.view.util.AlertUtils;

/**
 * <b>Define an ARO application</b>
 * <p>
 * Extends this class and use static method "Application.launch()".
 * </p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
@Exclude
public abstract class AroApplication extends Application {
	/* STATIC FIELDS ======================================================= */
	/* Singleton ----------------------------------------------------------- */
	private static AroApplication instance;

	/* Logging ------------------------------------------------------------- */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( AroApplication.class );

	/* FIELDS ============================================================== */
	/* Singleton ----------------------------------------------------------- */
	private RandomAccessFile lockRAF;
	private FileLock lock;

	/* Properties ---------------------------------------------------------- */
	@Getter
	private AroContext context;
	@Getter
	private TitleProperty title;
	@Getter
	private IconList icons;

	/* Java FX ------------------------------------------------------------- */
	@Getter
	private Stage mainStage;

	/* Beans --------------------------------------------------------------- */
	private ConfigManager configMgr;
	private ViewHolder viewHolder;

	/* CONSTRUCTORS ======================================================== */
	protected AroApplication() {
		super();

		/* Singleton */
		instance = this;
	}

	/* METHODS ============================================================= */
	/* Scene --------------------------------------------------------------- */
	public void loadScene( Scene scene ) {
		if( mainStage.getScene() != null ) {
			viewHolder.start();
		}
		if( scene == null ) {
			viewHolder.end();
		}
		mainStage.setScene( scene );
	}

	public Parent test() {
		return mainStage.getScene().getRoot();
	}

	/* Life-cycle ---------------------------------------------------------- */
	/* Initialization */
	@Override
	public final void init() throws Exception {
		LOGGER.info( "Initialize application..." );

		// Gets bean from manager (this is not instantiate via injection)
		BeanManager beanMgr = BeanManager.instance();
		configMgr = beanMgr.getBean( ConfigManager.class );
		viewHolder = beanMgr.getBean( ViewHolder.class );

		// Initialize properties
		initTitle();
		initIcon();

		context = beanMgr.getBean( AroContext.class );

		onInit();
		LOGGER.info( "Application is initialized !" );
	}

	private void initTitle() {
		String titleBase = configMgr.getProperty( AroPropKey.TITLE );
		title = new TitleProperty(
				StringUtils.isBlank( titleBase ) ? AroPropKey.TITLE
						.defaultValue() : titleBase );
	}

	private void initIcon() {
		icons = new IconList();

		String iconName = configMgr.getProperty( AroPropKey.ICON_NAME );
		if( iconName == null ) {
			LOGGER.info( "No application icon to load" );
			return;
		}
		String sizes = configMgr.getProperty( AroPropKey.ICON_SIZES );
		if( sizes == null ) {
			LOGGER.error( "Failed to load application icon: no icon size specified" );
			return;
		}
		for( String size : sizes.split( "," ) ) {
			try {
				icons.add( iconName, Integer.parseInt( size ) );
			} catch( NumberFormatException ex ) {
				LOGGER.error( "Size {} is not a number", size );
			}
		}
	}

	/**
	 * Called at the end of init(). Overrides this method for initialize your
	 * application.
	 */
	protected void onInit() throws Exception {
		// Nothing to do
	}

	/* Starting */
	/**
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public final void start( Stage primaryStage ) throws Exception {
		LOGGER.info( "Start {}...", title.getBase() );

		// Check if this application is not already started
		if( checkSingletonInstance() ) {
			// Prepare and show window
			mainStage = primaryStage;
			mainStage.setTitle( title.getValue() );
			title.addListener( ( observable, oldValue, newValue ) -> mainStage
					.setTitle( newValue ) );
			mainStage.getIcons().addAll( icons );
			mainStage.setOnCloseRequest( event -> {
				close();
			} );

			// Start and show
			viewHolder.start();
			onStart();
			mainStage.show();

			LOGGER.info( "{} is started !", title.getBase() );
		}
	}

	/**
	 * Called at the end of start(), before mainStage.show(). Overrides this
	 * method for start your application.
	 */
	protected void onStart() throws Exception {
		// Nothing to do
	}

	/* Stopping */
	/**
	 * @see javafx.application.Application#stop()
	 */
	@Override
	public final void stop() throws Exception {
		LOGGER.info( "Stop {}...", title.getBase() );
		onStop();
	}

	/**
	 * Called at the start of stop(). Overrides this method for stop your
	 * application.
	 */
	protected void onStop() throws Exception {
		// Nothing to do
	}

	/* Exit */
	public final void close() {
		boolean confirmed = onClose();
		if( confirmed ) {
			Platform.exit();
			System.exit( 0 );
		}
	}

	/**
	 * Called at the start of exit(). Overrides this method for add confirm
	 * message.
	 * 
	 * @return True if exit or false for stand alive.
	 */
	protected boolean onClose() {
		// Nothing to do
		return true;
	}

	/* Singleton ----------------------------------------------------------- */
	/* Instance */
	@SuppressWarnings("unchecked")
	public static < A extends AroApplication > A instance() {
		return (A) instance;
	}

	/* Locking */
	private boolean checkSingletonInstance() {
		boolean success = false;

		// Checks not already started and lock file
		try {
			File lockFile = new File( "./.lock" );
			lockRAF = new RandomAccessFile( lockFile, "rw" );
			lock = lockRAF.getChannel().tryLock();
			success = lock != null;
		} catch( IOException ex ) {
			LOGGER.error( "Lock application failed.", ex );
		}

		// If success to lock, add auto unlocking, else display message
		if( success ) {
			RuntimeUtils.addShutdownHook( ( ) -> {
				try {
					lock.release();
					lockRAF.close();
				} catch( IOException ex ) {
					LOGGER.error( "Unlock application failed.", ex );
				}
				LOGGER.info( "{} is stopped.", title.getBase() );
			} );
		} else {
			AlertUtils.showInfo( title.getBase(), null,
					"The application is already running." );
			LOGGER.info( "{} is stopped !", title.getBase() );
		}

		return success;
	}
}
