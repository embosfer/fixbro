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
package com.embosfer.fixbro.controller.qfj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.embosfer.fixbro.controller.er.observer.ExecutionReportObserver;
import com.embosfer.fixbro.model.messages.ExecutionReport;
import com.embosfer.fixbro.model.state.ReadOnlyOrder;

import quickfix.Session;
import quickfix.SessionNotFound;
import quickfix.field.AvgPx;
import quickfix.field.ClOrdID;
import quickfix.field.CumQty;
import quickfix.field.ExecID;
import quickfix.field.ExecType;
import quickfix.field.LastPx;
import quickfix.field.LastQty;
import quickfix.field.LeavesQty;
import quickfix.field.OrdStatus;
import quickfix.field.OrderID;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.fix44.component.Instrument;

/**
 * @author embosfer
 *
 */
public class QFJApplicationOut implements ExecutionReportObserver {

	private static final Logger logger = LoggerFactory.getLogger(QFJApplicationOut.class);

	@Override
	public void onExecutionReport(ExecutionReport er) {
		quickfix.fix44.ExecutionReport qfjER = new quickfix.fix44.ExecutionReport();
		ReadOnlyOrder order = er.getOrder();
		qfjER.set(new ClOrdID(er.getClOrdID()));
		qfjER.set(new OrderID(order.getOrderID()));
		qfjER.set(new ExecID(er.getExecID()));
		qfjER.set(new OrdStatus(er.getOrdStatus().toChar()));
		qfjER.set(new ExecType(er.getExecType().toChar()));
		qfjER.set(new CumQty(er.getCumQty()));
		qfjER.set(new AvgPx(er.getAvgPx()));
		qfjER.set(new LeavesQty(er.getLeavesQty()));
		qfjER.set(new Side(order.getSide().toChar()));
		qfjER.set(new Instrument(new Symbol(order.getSymbol())));

		// non mandatory
		if (er.getExecType() == com.embosfer.fixbro.model.tags.ExecType.TRADE) {
			qfjER.set(new LastPx(er.getLastPx()));
			qfjER.set(new LastQty(er.getLastQty()));
		}
		try {
			Session.sendToTarget(qfjER, order.getTargetCompID(), order.getSenderCompID()); // reverse
			logger.info("Sent to [SenderCompID={},TargetCompID={}] {}. Msg {}", order.getSenderCompID(),
					order.getTargetCompID(), qfjER);
		} catch (SessionNotFound e) {
			logger.error("Session to [SenderCompID={},TargetCompID={}] not found. Could not send msg {}",
					order.getSenderCompID(), order.getTargetCompID(), qfjER);
			// TODO implement rollback
		}
	}

}
