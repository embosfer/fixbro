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

import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.field.AvgPx;
import quickfix.field.ClOrdID;
import quickfix.field.CumQty;
import quickfix.field.ExecID;
import quickfix.field.ExecType;
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

	// FIXME keep the session ID on the ER message
	private SessionID currentSessionID = new SessionID("FIX.4.4:FIXB.TRD->BANZAI");

	@Override
	public void onExecutionReport(ExecutionReport er) {
		quickfix.fix44.ExecutionReport qfjER = new quickfix.fix44.ExecutionReport();
		qfjER.set(new ClOrdID(er.getClOrdID()));
		qfjER.set(new OrderID(er.getOrder().getOrderID()));
		qfjER.set(new ExecID(er.getExecID()));
		qfjER.set(new OrdStatus(er.getOrdStatus().toChar()));
		qfjER.set(new ExecType(er.getExecType().toChar()));
		qfjER.set(new CumQty(er.getCumQty()));
		qfjER.set(new AvgPx(er.getAvgPx()));
		qfjER.set(new LeavesQty(er.getLeavesQty()));
		qfjER.set(new Side(er.getOrder().getSide().toChar()));
		qfjER.set(new Instrument(new Symbol(er.getOrder().getSymbol())));
		try {
			Session.sendToTarget(qfjER, currentSessionID);
			logger.info("Sent to session {}. Msg {}", currentSessionID, qfjER);
		} catch (SessionNotFound e) {
			logger.error("Session {} not found. Could not send msg {}", currentSessionID, qfjER);
			// TODO implement rollback
		}
	}

}
