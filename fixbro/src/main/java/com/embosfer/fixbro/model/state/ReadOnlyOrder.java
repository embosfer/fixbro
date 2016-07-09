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
package com.embosfer.fixbro.model.state;

import com.embosfer.fixbro.model.tags.OrdStatus;
import com.embosfer.fixbro.model.tags.OrdType;
import com.embosfer.fixbro.model.tags.Side;

public interface ReadOnlyOrder {

	String getOrderID();

	String getClOrdID();

	String getOrigClOrdID();

	String getSymbol();

	OrdStatus getOrdStatus();

	char getTimeInForce();

	Side getSide();

	OrdType getOrdType();

	double getQty();

	double getAvgPx();

	Double getPrice();
	
	double getCumQty();
	
	double getLeavesQty();
}
