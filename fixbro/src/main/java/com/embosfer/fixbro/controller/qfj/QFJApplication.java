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

import com.embosfer.fixbro.controller.ExecutionNotifier;
import com.embosfer.fixbro.model.ExecutionReport;

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
import quickfix.fix44.component.Instrument;

public class QFJApplication implements quickfix.Application, ExecutionNotifier {

	@Override
	public void fromAdmin(Message arg0, SessionID arg1)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		// TODO Auto-generated method stub

	}

	@Override
	public void fromApp(Message arg0, SessionID arg1)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreate(SessionID session) {

	}

	@Override
	public void onLogon(SessionID arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLogout(SessionID arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toAdmin(Message arg0, SessionID arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toApp(Message arg0, SessionID arg1) throws DoNotSend {
		// TODO Auto-generated method stub

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
		// TODO send
//		Session.sendToTarget(qfjER, currentSession);
	}
}
