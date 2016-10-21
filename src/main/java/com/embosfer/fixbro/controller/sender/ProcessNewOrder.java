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
package com.embosfer.fixbro.controller.sender;

import com.embosfer.fixbro.model.messages.NewOrderSingle;
import com.embosfer.fixbro.model.state.Order;
import com.embosfer.fixbro.model.state.OrderBean;
import com.embosfer.fixbro.model.state.OrderBook;
import com.embosfer.fixbro.model.tags.OrdStatus;

/**
 * Processes the incoming 35=D messages (NewOrderSingle messages) and adds them
 * into the order book
 * 
 * @author embosfer
 *
 */
public class ProcessNewOrder {

	public ProcessNewOrder(NewOrderSingle inNos) {
		// TODO validate order
		Order order = new OrderBean();
		order.setSenderCompID(inNos.getSenderCompID());
		order.setTargetCompID(inNos.getTargetCompID());
		order.setClOrdID(inNos.getClOrdID());
		order.setAvgPx(0.0D);
		order.setCumQty(0.0D);
		order.setLeavesQty(inNos.getOrderQty());
		order.setOrderID("O" + System.nanoTime());
		order.setOrdStatus(OrdStatus.NONE_YET);
		order.setOrdType(inNos.getOrdType());
		order.setOrigClOrdID(inNos.getClOrdID());
		Double price = inNos.getPrice();
		if (price != null)
			order.setPrice(price);
		order.setQty(inNos.getOrderQty());
		order.setSide(inNos.getSide());
		order.setSymbol(inNos.getSymbol());
		// order.setTimeInForce(inNos.getT); TODO
		// TODO transactTime
		OrderBook.getInstance().addOrder(order);
	}
}
