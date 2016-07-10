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
import com.embosfer.fixbro.model.state.Order;
import com.embosfer.fixbro.model.state.OrderBean;
import com.embosfer.fixbro.model.state.OrderBook;
import com.embosfer.fixbro.model.tags.OrdStatus;
import com.embosfer.fixbro.model.tags.OrdType;
import com.embosfer.fixbro.model.tags.Side;
import com.embosfer.fixbro.view.OrderView;

import javafx.application.Application;
import javafx.stage.Stage;
import quickfix.ConfigError;

/**
 * Starts the application and delegates to the actual JavaFX application. An
 * instance of this class will be created by JavaFX after having called
 * launch(args). FIXBro will create the actual application (view) and the
 * controller
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
		// start accepting connections
		final QFJAcceptor acceptor = new QFJAcceptor("fixbro_qfj.cfg");
		acceptor.start();

		// add some fake orders into the order book
		Order order1 = new OrderBean();
		order1.setAvgPx(0D);
		order1.setClOrdID("1234");
		order1.setCumQty(0D);
		order1.setLeavesQty(1000D);
		order1.setOrderID("myOrderID");
		order1.setOrdType(OrdType.LIMIT);
		order1.setOrdStatus(OrdStatus.PENDING_NEW);
		order1.setOrigClOrdID("orig1234");
		order1.setPrice(1.35D);
		order1.setQty(1000D);
		order1.setSide(Side.BUY);
		order1.setSymbol("EUR/USD");
		OrderBook.getInstance().addOrder(order1);

		Order order2 = new OrderBean();
		order2.setAvgPx(0D);
		order2.setClOrdID("1235");
		order2.setCumQty(0D);
		order2.setLeavesQty(2000D);
		order2.setOrderID("myOrderID2");
		order2.setOrdType(OrdType.MARKET);
		order2.setOrdStatus(OrdStatus.NEW);
		order2.setOrigClOrdID("orig1235");
		order2.setQty(2000D);
		order2.setSide(Side.SELL);
		order2.setSymbol("EUR/USD");
		OrderBook.getInstance().addOrder(order2);

		// launch JavaFX
		launch(args);

		acceptor.stop();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		view.start(primaryStage);
	}
}
