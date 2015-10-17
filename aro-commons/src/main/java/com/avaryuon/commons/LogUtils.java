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
package com.avaryuon.commons;

import org.slf4j.Logger;

/**
 * Contains utility methods for log messages. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class LogUtils {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private LogUtils() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/* Logging ------------------------------------------------------------- */
	public static < L extends Logger > void logTrace( L logger, String message,
			Throwable t, Object... args ) {
		if( logger.isTraceEnabled() ) {
			logger.trace( formatMessage( message, args ), t );
		}
	}

	public static < L extends Logger > void logError( L logger, String message,
			Throwable t, Object... args ) {
		if( logger.isErrorEnabled() ) {
			logger.error( formatMessage( message, args ), t );
		}
	}

	/* Tools --------------------------------------------------------------- */
	public static String formatMessage( String message, Object... args ) {
		String formattedMessage = message;
		for( Object arg : args ) {
			formattedMessage = formattedMessage.replaceFirst( "\\{\\}",
					String.valueOf( arg ) );
		}
		return formattedMessage;
	}
}
