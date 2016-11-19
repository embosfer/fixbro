/***********************************************************************************************************************
 *
 * FixBro - an open source FIX broker (sell side) simulator
 * =========================================================
 *
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
 * Represents the tag 150 in FIX
 * 
 * @author embosfer
 *
 */
public enum ExecType {

	NEW('0', "NEW"), DONE_FOR_DAY('3', "DONE FOR DAY"), CANCELED('4', "CANCELED"), REPLACE('5',
			"REPLACE"), PENDING_CANCEL('6', "PENDING CANCEL"), REJECTED('8', "REJECTED"), PENDING_NEW('A',
					"PENDING NEW"), PENDING_REPLACE('E', "PENDING REPLACE"), TRADE('F', "TRADE");

	private final char fixTag;
	private final String humanValue;

	ExecType(char fixTag, String humanValue) {
		this.fixTag = fixTag;
		this.humanValue = humanValue;
	}

	@Override
	public String toString() {
		return fixTag + " (" + humanValue + ")";
	}

	public char toChar() {
		return fixTag;
	}

}
