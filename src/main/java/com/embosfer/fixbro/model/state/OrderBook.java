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
package com.embosfer.fixbro.model.state;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Singleton keeping the orders in the system
 * 
 * @author embosfer
 *
 */
public class OrderBook implements OrderObservable {

	private static final OrderBook instance = new OrderBook();
	
	private final Map<String, Order> ordersByID;
//	private final Map<String, List<Order>> historyByOrder;
	private final List<OrderObserver> observers;

	private OrderBook() {
		ordersByID = new ConcurrentHashMap<>();
		observers = new CopyOnWriteArrayList<>();
//		historyByOrder = new ConcurrentHashMap<>();
	}

	public static OrderBook getInstance() {
		return instance;
	}

	public Order getOrderBy(String orderID) {
		return ordersByID.get(orderID);
	}

	public void addOrder(Order order) {
		ordersByID.put(order.getOrderID(), order);
		notifyObservers(order);
	}
	
	public Collection<Order> getAllOrders() {
		return ordersByID.values();
	}

	@Override
	public void registerOrderObserver(OrderObserver observer) {
		observers.add(observer);
	}

	@Override
	public void unRegisterOrderObserver(OrderObserver observer) {
		// TODO Auto-generated method stub
	}

	private void notifyObservers(Order order) {
		observers.forEach(obs -> obs.onNewOrder(order));
	}

}