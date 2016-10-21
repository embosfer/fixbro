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

import com.embosfer.fixbro.controller.sender.MessageSender;
import com.embosfer.fixbro.model.messages.NewOrderSingle;

import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.Message.Header;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;
import quickfix.field.SenderCompID;
import quickfix.field.TargetCompID;

/**
 * This component listens to FIX messages coming from clients, transforms them
 * into FixBro datamodel and sends them in for processing
 * 
 * @author embosfer
 *
 */
public class QFJApplicationIn extends quickfix.fix44.MessageCracker implements quickfix.Application {

	private static final Logger logger = LoggerFactory.getLogger(QFJApplicationIn.class);
	private final MessageSender messageSender;

	public QFJApplicationIn(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	// callback notifying of every "admin" message (eg. logon, heartbeat)
	// received from the outside world
	@Override
	public void fromAdmin(Message msg, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		if (logger.isDebugEnabled())
			logger.debug("fromAdmin on session {}. Msg {}", sessionID, msg);
	}

	// callback notifying of every "app" message (eg. NOS, OCR, OCRR) received
	// from the outside world
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
	public void onMessage(quickfix.fix44.NewOrderSingle qfjNos, SessionID sessionID)
			throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
		logger.info("NewOrderSingle received on session {} => {}", sessionID, qfjNos);
		NewOrderSingle.Builder builder = new NewOrderSingle.Builder();
		// mandatory fields
		Header header = qfjNos.getHeader();
		builder.senderCompID(header.getField(new SenderCompID()).getValue())
				.targetCompID(header.getField(new TargetCompID()).getValue()).clOrdID(qfjNos.getClOrdID().getValue())
				.orderQty(qfjNos.getOrderQty().getValue()).ordType(qfjNos.getOrdType().getValue())
				.side(qfjNos.getSide().getValue()).symbol(qfjNos.getInstrument().getSymbol().getValue())
				.transactTime(qfjNos.getTransactTime().getValue());

		// non mandatory fields
		if (qfjNos.isSetPrice())
			builder.price(qfjNos.getPrice().getValue());
		NewOrderSingle nos = builder.build();
		logger.info("Transformed {}", nos);
		messageSender.sendNewOrderSingle(nos);
	}

	@Override
	public void onMessage(quickfix.fix44.OrderCancelRequest qfjOcr, SessionID sessionID)
			throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
		logger.info("OrderCancelRequest received on session {} => {}", sessionID, qfjOcr);
	}
}
