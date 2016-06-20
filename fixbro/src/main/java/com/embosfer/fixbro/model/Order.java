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
package com.embosfer.fixbro.model;

/**
 * Mutable structure that holds the state of an order.
 * 
 * @author embosfer
 *
 */
public interface Order extends ReadOnlyOrder {

	void setOrderID(String ID);

	void setClOrdID(String clientID);

	void setOrigClOrdID(String origClientID);

	void setSymbol(String symbol);

	void setOrdStatus(OrdStatus status);

	void setTimeInForce(char tif);

	void setSide(char side);

	void setOrdType(char type);

	void setQty(double qty);

	void setAvgPx(double price);

	void setPrice(double price);

	void setCumQty(double cumQty);

	void setLeavesQty(double leavesQty);
}
