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

public enum OrdStatus {

	// TODO finish statuses
	NONE_YET('.', "None yet"), // this status is only added for display in
								// blotter (for newly arrived orders)
	PENDING_NEW('A', "Pending New"), NEW('0', "New"), PARTIALLY_FILLED('1', "Partially Filled"), FILLED('2',
			"Filled"), DONE_FOR_DAY('3', "Done for Day"), CANCELED('4', "Canceled");

	private final char fixStatus;
	private final String humanStatus;

	OrdStatus(char status, String humanStatus) {
		this.fixStatus = status;
		this.humanStatus = humanStatus;
	}

	@Override
	public String toString() {
		return fixStatus + " (" + humanStatus + ")";
	}

	public char toChar() {
		return fixStatus;
	}

}
