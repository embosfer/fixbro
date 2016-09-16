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
package com.embosfer.fixbro.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.embosfer.fixbro.controller.er.processor.ExecutionReportProcessor;
import com.embosfer.fixbro.controller.er.producer.ExecuteOrder;
import com.embosfer.fixbro.controller.er.producer.ExecutionReportProducer;
import com.embosfer.fixbro.controller.er.producer.PendingNewOrder;
import com.embosfer.fixbro.model.messages.ExecutionReport;
import com.embosfer.fixbro.model.state.Order;

/**
 * Applies the action asked by the view and sends the Execution out
 * 
 * @author embosfer
 *
 */
public class OrderControllerImpl implements OrderController {

	private final Logger log = LoggerFactory.getLogger(OrderControllerImpl.class);

	private final ExecutionReportProcessor erProcessor;

	public OrderControllerImpl(ExecutionReportProcessor erProcessor) {
		this.erProcessor = erProcessor;
	}

	private void processExecutionReport(ExecutionReportProducer executionProducer) {
		ExecutionReport er = executionProducer.getExecutionReport();
		erProcessor.process(er);
	}

	@Override
	public void execute(Order order, double lastPx, double lastQty) {
		log.info("Executing order {} with lastPx {} and lastQty {}",
				new Object[] { order.getOrderID(), lastPx, lastQty });
		processExecutionReport(new ExecuteOrder(order, lastPx, lastQty));
	}

	@Override
	public void reject(Order order, String reason) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancel(Order order, String reason) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doneForDay(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void expire(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void replace(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void acknowledge(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pendingNew(Order order) {
		log.info("To Pending New, order {}", order.getOrderID());
		processExecutionReport(new PendingNewOrder(order));
	}

	@Override
	public void pendingCancel(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pendingReplace(Order order) {
		// TODO Auto-generated method stub

	}

}
