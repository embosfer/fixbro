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

import com.embosfer.fixbro.controller.OrderController;
import com.embosfer.fixbro.model.OrderBean;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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

	public OrderView(OrderController controller) {
		this.controller = controller;
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
		applyStyleTo(vbox);
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
		applyStyleTo(hbox);

		Button buttonAck = new Button("Acknowledge");
		buttonAck.setOnAction(event -> {
			System.out.println(event);
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

	private void applyStyleTo(Pane pane) {
		pane.setStyle("-fx-background-color: #336699;");
		pane.setPadding(new Insets(10));
	}

	private void disableIfNoSelection(Button... buttons) {
		for (Button button : buttons) {
			button.disableProperty().bind(orderTableView.getSelectionModel().selectedItemProperty().isNull());
		}
	}

	private void createOrShowExecuteWindow(String orderID) {
		if (executeOrderStage == null) {
			executeOrderStage = new ExecuteOrderStage(orderID);
		}
		executeOrderStage.show();
	}

	private class ExecuteOrderStage extends Stage {
		private TextField txtLastQty;
		private TextField txtLastPx;

		ExecuteOrderStage(String orderID) {
			BorderPane borderPaneRoot = new BorderPane();
			borderPaneRoot.setPadding(new Insets(10));
			final Scene scene = new Scene(borderPaneRoot, 300, 150);

			GridPane grid = new GridPane();
			applyStyleTo(grid);
			grid.setHgap(10D);
			grid.setVgap(10D);

			// last qty
			Label lblQty = new Label("Last qty");
			lblQty.setTextFill(Color.WHITE);
			GridPane.setHalignment(lblQty, HPos.RIGHT);
			grid.add(lblQty, 0, 0);
			txtLastQty = new TextField();
			txtLastQty.setMaxWidth(Double.MAX_VALUE);
			GridPane.setHgrow(txtLastQty, Priority.ALWAYS);
			GridPane.setHalignment(txtLastQty, HPos.LEFT);
			grid.add(txtLastQty, 1, 0);

			// last price
			Label lblPx = new Label("Last px");
			lblPx.setTextFill(Color.WHITE);
			grid.add(lblPx, 0, 1);
			GridPane.setHalignment(lblPx, HPos.RIGHT);
			txtLastPx = new TextField();
			txtLastPx.setMaxWidth(Double.MAX_VALUE);
			GridPane.setHgrow(txtLastPx, Priority.ALWAYS);
			GridPane.setHalignment(txtLastPx, HPos.LEFT);
			grid.add(txtLastPx, 1, 1);

			// button
			Button buttonExecute = new Button("Execute");
			buttonExecute.setOnAction(event -> {
				OrderBean order = orderTableView.getSelectionModel().getSelectedItem();
				controller.execute(order, Double.valueOf(txtLastPx.getText()), Double.valueOf(txtLastQty.getText()));
				ExecuteOrderStage.this.hide();
			});
			buttonExecute.disableProperty()
					.bind(Bindings.isEmpty(txtLastPx.textProperty()).or(Bindings.isEmpty(txtLastQty.textProperty())));

			buttonExecute.setAlignment(Pos.BASELINE_RIGHT);
			GridPane.setHalignment(buttonExecute, HPos.RIGHT);
			grid.add(buttonExecute, 1, 2);
			borderPaneRoot.setCenter(grid);

			setScene(scene);
			setTitle("Execute order " + orderID);
		}
	}

}
