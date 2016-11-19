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
package com.embosfer.fixbro.controller.er.producer;

import com.embosfer.fixbro.model.messages.ExecutionReport;
import com.embosfer.fixbro.model.state.Order;
import com.embosfer.fixbro.model.tags.ExecType;
import com.embosfer.fixbro.model.tags.OrdStatus;

/**
 * Changes the order given to PENDING NEW state and generates an
 * {@link ExecutionReport} accordingly
 * 
 * @author embosfer
 *
 */
public class PendingNewOrder implements ExecutionReportProducer {
	
	private final Order order;

	public PendingNewOrder(Order targetOrder) {
		this.order = targetOrder;
		order.setOrdStatus(OrdStatus.PENDING_NEW);
	}

	@Override
	public ExecutionReport getExecutionReport() {
		return new ExecutionReport.Builder(ExecType.PENDING_NEW,  order).build();
	}

}
