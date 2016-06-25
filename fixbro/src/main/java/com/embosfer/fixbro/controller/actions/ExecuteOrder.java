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
package com.embosfer.fixbro.controller.actions;

import com.embosfer.fixbro.controller.ExecutionReportProducer;
import com.embosfer.fixbro.model.ExecutionReport;
import com.embosfer.fixbro.model.OrdStatus;
import com.embosfer.fixbro.model.Order;

/**
 * Applies the execution on the current order and produces the corresponding
 * {@link ExecutionReport} report
 * 
 * @author embosfer
 *
 */
public class ExecuteOrder implements ExecutionReportProducer {

	private final Order order;
	private final double lastPx;
	private final double lastQty;

	public ExecuteOrder(Order order, double lastPx, double lastQty) {
		this.order = order;
		this.lastPx = lastPx;
		this.lastQty = lastQty;

		// update order
		double newLeavesQty = order.getLeavesQty() - lastQty;
		order.setLeavesQty(newLeavesQty);
		OrdStatus ordStatus; // should be same as execType
		if (newLeavesQty > 0) {
			ordStatus = OrdStatus.PARTIALLY_FILLED;
		} else {
			ordStatus = OrdStatus.FILLED;
		}
		order.setOrdStatus(ordStatus);
		// calculate average price
		if (noFillsYet()) {
			order.setAvgPx(lastPx);
		} else if (needsAvgPxCalculation()) {
			// AvgPx = [(AvgPx * CumQty) + (LastShares * LastPx)] / (LastShares
			// + CumQty)
			double newAvgPx = ((order.getAvgPx() * order.getCumQty()) + (lastQty * lastPx))
					/ (lastQty + order.getCumQty());
			order.setAvgPx(newAvgPx);
		}
		order.setCumQty(order.getCumQty() + lastQty);
	}

	private boolean needsAvgPxCalculation() {
		return Double.compare(order.getAvgPx(), lastPx) != 0;
	}

	private boolean noFillsYet() {
		return Double.compare(order.getCumQty(), 0D) == 0;
	}

	@Override
	public ExecutionReport getExecutionReport() {
		// FIXME execType to be added
		ExecutionReport.Builder builder = new ExecutionReport.Builder(order);
		builder
				// execType(ordStatus
				.lastPx(lastPx).lastQty(lastQty);
		return builder.build();
	}

}