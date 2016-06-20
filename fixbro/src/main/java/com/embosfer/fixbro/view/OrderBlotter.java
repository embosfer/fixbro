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

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.embosfer.fixbro.model.Order;
import com.embosfer.fixbro.model.OrderBean;
import com.embosfer.fixbro.model.OrderBook;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * {@code TableView} displaying the current status of all orders in the system
 * 
 * @author embosfer
 *
 */
public class OrderBlotter {

	private final TableView<OrderBean> tableView;

	OrderBlotter() {
		// get all orders and transform them into order beans
		Collection<Order> orders = OrderBook.getInstance().getAllOrders();
		List<OrderBean> orderBeans = orders.stream().map(order -> new OrderBean(order)).collect(Collectors.toList());
		// add all properties from the table to be updated
		ObservableList<OrderBean> observableList = FXCollections.observableList(orderBeans,
				order -> new Observable[] { order.getAvgPxProperty(), order.getLeavesProperty(),
						order.getClOrdIDProperty(), order.getCumQtyProperty(), order.getPriceProperty(),
						order.getOrdStatusProperty(), order.getAvgPxProperty() });
		observableList.addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				System.out.println("Paaaam"); //
			}
		});
		tableView = new TableView<>(observableList);
		getTableView().setEditable(false);
		// With the table defined, we define now the data model: we'll be using
		// a ObservableList
		createTableColumns();
	}

	@SuppressWarnings("unchecked")
	private void createTableColumns() {
		TableColumn<OrderBean, String> orderID = new TableColumn<>("orderID");
		orderID.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
		TableColumn<OrderBean, String> origClOrdID = new TableColumn<>("origClOrdID");
		origClOrdID.setCellValueFactory(cellData -> cellData.getValue().getOrigClOrdIDProperty());
		TableColumn<OrderBean, String> clOrdID = new TableColumn<>("clOrdID");
		clOrdID.setCellValueFactory(cellData -> cellData.getValue().getClOrdIDProperty());
		TableColumn<OrderBean, String> symbol = new TableColumn<>("symbol");
		symbol.setCellValueFactory(cellData -> cellData.getValue().getSymbolProperty());
		TableColumn<OrderBean, Double> price = new TableColumn<>("price");
		price.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
		TableColumn<OrderBean, String> ordStatus = new TableColumn<>("ordStatus");
		ordStatus.setCellValueFactory(cellData -> cellData.getValue().getOrdStatusProperty());
		TableColumn<OrderBean, Double> leavesQty = new TableColumn<>("leavesQty");
		leavesQty.setCellValueFactory(cellData -> cellData.getValue().getLeavesProperty().asObject());
		TableColumn<OrderBean, Double> cumQty = new TableColumn<>("cumQty");
		cumQty.setCellValueFactory(cellData -> cellData.getValue().getCumQtyProperty().asObject());
		TableColumn<OrderBean, Double> avgPx = new TableColumn<>("avgPx");
		avgPx.setCellValueFactory(cellData -> cellData.getValue().getAvgPxProperty().asObject());
		// TODO fill all values

		getTableView().getColumns().setAll(orderID, origClOrdID, clOrdID, symbol, price, ordStatus, leavesQty, cumQty,
				avgPx);
	}

	public TableView<OrderBean> getTableView() {
		return tableView;
	}

}
