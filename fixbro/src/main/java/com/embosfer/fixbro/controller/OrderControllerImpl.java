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

import com.embosfer.fixbro.controller.actions.ExecuteOrder;
import com.embosfer.fixbro.model.Execution;
import com.embosfer.fixbro.model.Order;

/**
 * Applies the action asked by the view and sends the Execution out
 * 
 * @author embosfer
 *
 */
public class OrderControllerImpl implements OrderController {

	private void sendExecution(ExecutionProducer executionProducer) {
		Execution execution = executionProducer.getExecution();
		// TODO send Execution out
	}

	@Override
	public void execute(Order order, double lastPx, double lastQty) {
		sendExecution(new ExecuteOrder(order, lastPx, lastQty));
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
		// TODO Auto-generated method stub

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
