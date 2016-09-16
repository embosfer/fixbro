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
package com.embosfer.fixbro.view;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.embosfer.fixbro.controller.OrderController;
import com.embosfer.fixbro.model.state.OrderBean;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * View part of the MVC model
 * 
 * @author embosfer
 *
 */
public class OrderView extends Application {

	private final OrderController controller;
	private ExecuteOrderStage executeOrderStage;
	private TableView<OrderBean> orderTableView;
	private final Executor backgroundExecutor;

	public OrderView(OrderController controller) {
		this.controller = controller;
		this.backgroundExecutor = Executors.newCachedThreadPool();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final BorderPane root = new BorderPane();
		final Scene scene = new Scene(root, 1000, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		root.setTop(buildTopPane());
		root.setCenter(buildCenterPane());
		root.setBottom(buildBottomPane());

		primaryStage.setScene(scene);
		primaryStage.setTitle("FIXBro");
		primaryStage.show();
	}

	private Node buildTopPane() {
		VBox vbox = new VBox();
		DisplayUtils.applyStyleTo(vbox);
		vbox.setSpacing(8);
		Text title = new Text("ORDER BLOTTER");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		title.setFill(Color.WHITE);
		vbox.getChildren().addAll(title);
		return vbox;
	}

	// build Order blotter
	private Node buildCenterPane() {
		OrderBlotter blotter = new OrderBlotter();
		orderTableView = blotter.getTableView();
		return orderTableView;
	}

	private Node buildBottomPane() {
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		DisplayUtils.applyStyleTo(hbox);

		Button buttonAck = new Button("Pending New");
		buttonAck.setOnAction(event -> {
			OrderBean selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
			controller.pendingNew(selectedOrder);
		});

		Button buttonExecute = new Button("Execute");
		buttonExecute.setOnAction(event -> {
			OrderBean selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
			createOrShowExecuteWindow(selectedOrder.getOrderID());
		});
		hbox.getChildren().addAll(buttonAck, buttonExecute);
		disableIfNoSelection(buttonAck, buttonExecute);

		return hbox;
	}

	private void disableIfNoSelection(Button... buttons) {
		for (Button button : buttons) {
			button.disableProperty().bind(orderTableView.getSelectionModel().selectedItemProperty().isNull());
		}
	}

	private void createOrShowExecuteWindow(String orderID) {
		if (executeOrderStage == null) {
			executeOrderStage = new ExecuteOrderStage(controller, backgroundExecutor);
		}
		OrderBean order = orderTableView.getSelectionModel().getSelectedItem();
		executeOrderStage.setTargetOrder(order);
		executeOrderStage.show();
	}

}
