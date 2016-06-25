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

import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.FileStoreFactory;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;

/**
 * This class manages the life cycle of a QFJ acceptor. It requires a .cfg file
 * specifying the QFJ properties, like senderCompID, targetCompID, etc
 * 
 * @author embosfer
 *
 */
public class QFJAcceptor {

	private static final Logger LOG = LoggerFactory.getLogger(QFJAcceptor.class);

	private final SocketAcceptor acceptor;

	public QFJAcceptor(String settingsFileName) throws ConfigError {
		final SessionSettings settings = new SessionSettings(settingsFileName);
		acceptor = new SocketAcceptor(new QFJApplication(), new FileStoreFactory(settings), settings,
				new DefaultMessageFactory());
	}

	public void start() throws ConfigError {
		LOG.info("Starting");
		acceptor.start();
		LOG.info("Started");
	}

	public void stop() {
		LOG.info("Stopping");
		acceptor.stop();
		LOG.info("Stopped");
	}

}
