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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;

import com.avaryuon.fwk.AppConfig;

/**
 * Configuration of ARO application.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "config", namespace = AroConfig.NAMESPACE,
		propOrder = { "application" })
public class AroConfig {
	/* STATIC FIELDS ======================================================= */
	public static final String NAMESPACE = "http://www.avaryuon.com/config";

	/* FIELDS ============================================================== */
	@Getter
	@XmlElement(required = false)
	private AppConfig application;

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	// Nothing here
}
