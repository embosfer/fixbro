/***********************************************************************************************************************
 *
 * FixBro - an open source FIX broker (sell side) simulator
 * =========================================================
 *
 * Copyright (C) 2016 by Emilio Bosch Ferrando
 * https://github.com/embosfer
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************/
package com.embosfer.fixbro.model.tags;

/**
 * Represents the tag 40 in FIX
 * 
 * @author embosfer
 *
 */
public enum OrdType {

	MARKET('1', "Market"), LIMIT('2', "Limit"), STOP('3', "Stop"); // TODO add other types

	private final char fixOrdType;
	private final String humanOrdType;

	OrdType(char fixOrdType, String humanOrdType) {
		this.fixOrdType = fixOrdType;
		this.humanOrdType = humanOrdType;
	}
	
	@Override
	public String toString() {
		return fixOrdType + " (" + humanOrdType + ")";
	}
	
	public static OrdType valueOf(char type) {
		for (OrdType t : OrdType.values()) {
			if (t.fixOrdType == type) return t;
		}
		return null;
	}
}
