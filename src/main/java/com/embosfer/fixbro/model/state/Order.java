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
package com.embosfer.fixbro.model.state;

import com.embosfer.fixbro.model.tags.OrdStatus;
import com.embosfer.fixbro.model.tags.OrdType;
import com.embosfer.fixbro.model.tags.Side;

/**
 * Mutable structure that holds the state of an order.
 * 
 * @author embosfer
 *
 */
public interface Order extends ReadOnlyOrder {

	void setSenderCompID(String senderCompID);

	void setTargetCompID(String targetCompID);
	
	void setOrderID(String ID);

	void setClOrdID(String clientID);

	void setOrigClOrdID(String origClientID);

	void setSymbol(String symbol);

	void setOrdStatus(OrdStatus status);

	void setTimeInForce(char tif);

	void setSide(Side side);

	void setOrdType(OrdType type);

	void setQty(double qty);

	void setAvgPx(double price);

	void setPrice(Double price);

	void setCumQty(double cumQty);

	void setLeavesQty(double leavesQty);
}
