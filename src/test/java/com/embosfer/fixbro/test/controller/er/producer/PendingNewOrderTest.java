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
package com.embosfer.fixbro.test.controller.er.producer;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.embosfer.fixbro.controller.er.producer.PendingNewOrder;
import com.embosfer.fixbro.model.messages.ExecutionReport;
import com.embosfer.fixbro.model.state.Order;
import com.embosfer.fixbro.model.state.OrderBean;
import com.embosfer.fixbro.model.tags.OrdStatus;

@RunWith(JUnit4.class)
public class PendingNewOrderTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void incompleteOrderShouldThrowNPE_NeedsClOrdID_And_LeavesQty() {
		Order order = new OrderBean();
		PendingNewOrder pnOrder = new PendingNewOrder(order);

		thrown.expect(NullPointerException.class);
		pnOrder.getExecutionReport();
	}
	
	@Test
	public void incompleteOrderShouldThrowNPE_NeedsLeavesQty() {
		Order order = new OrderBean();
		order.setClOrdID("id1");

		PendingNewOrder pnOrder = new PendingNewOrder(order);
		thrown.expect(NullPointerException.class);
		pnOrder.getExecutionReport();
	}
	
	@Test
	public void pendingNewOrder() {
		Order order = new OrderBean();
		order.setClOrdID("id1");
		order.setLeavesQty(100);
		ExecutionReport pendingEr = new PendingNewOrder(order).getExecutionReport();

		assertEquals(0, order.getAvgPx(), 0);
		assertEquals(0, pendingEr.getAvgPx(), 0);

		assertEquals(0, order.getCumQty(), 0);
		assertEquals(0, pendingEr.getCumQty(), 0);

		assertEquals(100, order.getLeavesQty(), 0);
		assertEquals(100, pendingEr.getLeavesQty(), 0);

		assertEquals(OrdStatus.PENDING_NEW, order.getOrdStatus());
		assertEquals(OrdStatus.PENDING_NEW, pendingEr.getOrdStatus());
	}

}
