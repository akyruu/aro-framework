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
package com.avaryuon.fwk.core.bean;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

import lombok.AccessLevel;
import lombok.Getter;

import org.jboss.weld.bean.builtin.BeanManagerProxy;
import org.jboss.weld.bootstrap.events.AbstractContainerEvent;

import com.avaryuon.fwk.core.bean.context.bound.BoundViewContext;
import com.avaryuon.fwk.core.bean.context.bound.BoundViewContextImpl;

/**
 * ARO bean extension for ARO contexts (inspired by WeldSEBeanRegistrant).
 * 
 * @see org.jboss.weld.environment.se.WeldSEBeanRegistrant
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class BeanExtension implements Extension {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	@Getter(AccessLevel.PACKAGE)
	private BoundViewContext viewContext;

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	/* Scope --------------------------------------------------------------- */
	public void registerScopedContexts( @Observes AfterBeanDiscovery event,
			BeanManager manager ) {
		if( ignoreEvent( event ) ) {
			return;
		}

		// Build contexts
		viewContext = new BoundViewContextImpl( BeanManagerProxy.unwrap(
				manager ).getContextId() );

		// Add contexts
		event.addContext( viewContext );
	}

	private static boolean ignoreEvent( Object event ) {
		return !(event instanceof AbstractContainerEvent);
	}
}