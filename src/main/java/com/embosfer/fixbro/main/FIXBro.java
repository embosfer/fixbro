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
package com.embosfer.fixbro.main;

import java.util.Arrays;
import java.util.List;

import com.embosfer.fixbro.controller.OrderController;
import com.embosfer.fixbro.controller.OrderControllerImpl;
import com.embosfer.fixbro.controller.er.observer.ExecutionReportObserver;
import com.embosfer.fixbro.controller.er.processor.ExecutionReportProcessor;
import com.embosfer.fixbro.controller.er.processor.ExecutionReportProcessorImpl;
import com.embosfer.fixbro.controller.qfj.QFJAcceptor;
import com.embosfer.fixbro.controller.qfj.QFJApplicationIn;
import com.embosfer.fixbro.controller.qfj.QFJApplicationOut;
import com.embosfer.fixbro.controller.sender.DirectMessageSender;
import com.embosfer.fixbro.controller.sender.MessageSender;
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
	private QFJAcceptor acceptor;

	public FIXBro() throws ConfigError {
		// sending strategy: directly
		MessageSender directSender = new DirectMessageSender();
		acceptor = new QFJAcceptor("fixbro_qfj.cfg", new QFJApplicationIn(directSender));

		List<ExecutionReportObserver> erObservers = Arrays.asList(new QFJApplicationOut());
		ExecutionReportProcessor erProcessor = new ExecutionReportProcessorImpl(erObservers);
		OrderController controller = new OrderControllerImpl(erProcessor);
		view = new OrderView(controller);
	}

	public static void main(String[] args) {
		// TEST DATA
		// add some fake orders into the order book
		// Order order1 = new OrderBean();
		// order1.setAvgPx(0D);
		// order1.setClOrdID("1234");
		// order1.setCumQty(0D);
		// order1.setLeavesQty(1000D);
		// order1.setOrderID("myOrderID");
		// order1.setOrdType(OrdType.LIMIT);
		// order1.setOrdStatus(OrdStatus.PENDING_NEW);
		// order1.setOrigClOrdID("orig1234");
		// order1.setPrice(1.35D);
		// order1.setQty(1000D);
		// order1.setSide(Side.BUY);
		// order1.setSymbol("EUR/USD");
		// order1.setSenderCompID("BANZAI");
		// order1.setTargetCompID("FIXB");
		// OrderBook.getInstance().addOrder(order1);
		//
		// Order order2 = new OrderBean();
		// order2.setAvgPx(0D);
		// order2.setClOrdID("1235");
		// order2.setCumQty(0D);
		// order2.setLeavesQty(2000D);
		// order2.setOrderID("myOrderID2");
		// order2.setOrdType(OrdType.MARKET);
		// order2.setOrdStatus(OrdStatus.NEW);
		// order2.setOrigClOrdID("orig1235");
		// order2.setQty(2000D);
		// order2.setSide(Side.SELL);
		// order2.setSymbol("EUR/USD");
		// order2.setSenderCompID("BANZAI2");
		// order2.setTargetCompID("FIXB");
		// OrderBook.getInstance().addOrder(order2);

		// launch JavaFX
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		acceptor.start(); // start accepting connections
		view.start(primaryStage);
	}

	@Override
	public void stop() throws Exception {
		view.stop();
		acceptor.stop();
	}
}
