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
package com.embosfer.fixbro.main;

import com.embosfer.fixbro.controller.OrderController;
import com.embosfer.fixbro.controller.OrderControllerImpl;
import com.embosfer.fixbro.controller.qfj.QFJAcceptor;
import com.embosfer.fixbro.model.OrdStatus;
import com.embosfer.fixbro.model.Order;
import com.embosfer.fixbro.model.OrderBean;
import com.embosfer.fixbro.model.OrderBook;
import com.embosfer.fixbro.view.OrderView;

import javafx.application.Application;
import javafx.stage.Stage;
import quickfix.ConfigError;

/**
 * Starts the application
 * 
 * @author embosfer
 *
 */
public class FIXBro extends Application {

	private Application view;

	public FIXBro() {
		OrderController controller = new OrderControllerImpl();
		view = new OrderView(controller);
	}

	public static void main(String[] args) throws ConfigError {
		// add some fake orders into the order book
		Order order1 = new OrderBean();
		order1.setAvgPx(0D);
		order1.setClOrdID("1234");
		order1.setCumQty(0D);
		order1.setLeavesQty(1000D);
		order1.setOrderID("myOrderID");
		order1.setOrdStatus(OrdStatus.PENDING_NEW);
		order1.setOrigClOrdID("orig1234");
		order1.setPrice(1.35D);
		order1.setQty(1000D);
		order1.setSymbol("EUR/USD");
		OrderBook.getInstance().addOrder(order1);

		Order order2= new OrderBean();
		order2.setAvgPx(0D);
		order2.setClOrdID("1235");
		order2.setCumQty(0D);
		order2.setLeavesQty(2000D);
		order2.setOrderID("myOrderID2");
		order2.setOrdStatus(OrdStatus.NEW);
		order2.setOrigClOrdID("orig1235");
		order2.setPrice(1.344D);
		order2.setQty(2000D);
		order2.setSymbol("EUR/USD");
		OrderBook.getInstance().addOrder(order2);
		
		// launch JavaFX
		launch(args);
		
		// start accepting connections
		final QFJAcceptor acceptor = new QFJAcceptor("fixbro_qfj.cfg");
		acceptor.start();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		view.start(primaryStage);
	}
}
