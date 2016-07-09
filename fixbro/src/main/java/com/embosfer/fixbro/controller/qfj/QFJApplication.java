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

import com.embosfer.fixbro.controller.ExecutionReportNotifier;
import com.embosfer.fixbro.model.messages.ExecutionReport;

import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;
import quickfix.field.AvgPx;
import quickfix.field.CumQty;
import quickfix.field.ExecType;
import quickfix.field.LeavesQty;
import quickfix.field.OrdStatus;
import quickfix.field.OrderID;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.fix44.NewOrderSingle;
import quickfix.fix44.component.Instrument;

public class QFJApplication extends quickfix.fix44.MessageCracker
		implements quickfix.Application, ExecutionReportNotifier {

	private static final Logger logger = LoggerFactory.getLogger(QFJApplication.class);

	@Override
	public void fromAdmin(Message msg, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		logger.info("fromAdmin on session {}. Msg {}", sessionID, msg);
	}

	@Override
	public void fromApp(Message msg, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		if (logger.isDebugEnabled())
			logger.debug("fromApp on session {}. Msg {}", sessionID, msg);
		crack(msg, sessionID);
	}

	@Override
	public void onCreate(SessionID session) {
		logger.info("creating {}", session);
	}

	@Override
	public void onLogon(SessionID sessionID) {
		logger.info("loging on {}", sessionID);
	}

	@Override
	public void onLogout(SessionID sessionID) {
		logger.info("loging out {}", sessionID);
	}

	@Override
	public void toAdmin(Message msg, SessionID sessionID) {
		logger.info("toAdmin {}", sessionID);
	}

	@Override
	public void toApp(Message msg, SessionID sessionID) throws DoNotSend {
		logger.info("toApp on session {}. Msg {}", sessionID, msg);
	}

	@Override
	public void onMessage(NewOrderSingle nos, SessionID sessionID)
			throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
		logger.info("NewOrderSingle received on session {} => {}", sessionID, nos);
	}

	@Override
	public void notify(ExecutionReport er) {
		quickfix.fix44.ExecutionReport qfjER = new quickfix.fix44.ExecutionReport();
		qfjER.set(new OrderID(er.getOrder().getOrderID()));
		qfjER.set(new OrdStatus(er.getOrdStatus().toChar()));
		qfjER.set(new ExecType(er.getExecType()));
		qfjER.set(new CumQty(er.getCumQty()));
		qfjER.set(new AvgPx(er.getAvgPx()));
		qfjER.set(new LeavesQty(er.getLeavesQty()));
		qfjER.set(new Side(er.getOrder().getSide()));
		qfjER.set(new Instrument(new Symbol(er.getOrder().getSymbol())));
		// logger.info("Sending to session {}. Msg {}", sessionID, msg);
		// TODO send
		// Session.sendToTarget(qfjER, currentSession);
	}
}
