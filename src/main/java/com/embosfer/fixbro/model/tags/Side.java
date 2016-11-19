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
 * Represents the tag 54 in FIX
 * 
 * @author embosfer
 *
 */
public enum Side {

	BUY('1', "Buy"), SELL('2', "Sell"); // TODO add others

	private final char fixSide;
	private final String humanSide;

	Side(char fixSide, String humanSide) {
		this.fixSide = fixSide;
		this.humanSide = humanSide;
	}

	@Override
	public String toString() {
		return fixSide + " (" + humanSide + ")";
	}
	
	public char toChar() {
		return fixSide;
	}
	
	public static Side valueOf(char side) {
		for (Side s: Side.values()) {
			if (side == s.fixSide) return s;
		}
		return null;
	}
}
