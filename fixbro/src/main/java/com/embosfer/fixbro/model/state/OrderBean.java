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

import com.embosfer.fixbro.model.tags.OrdStatus;
import com.embosfer.fixbro.model.tags.OrdType;
import com.embosfer.fixbro.model.tags.Side;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Implementation for the {@link com.embosfer.fixbro.view.OrderBlotter}
 * 
 * @author embosfer
 *
 */
public class OrderBean implements Order {

	private StringProperty orderID;
	private StringProperty origClOrdID;
	private StringProperty clOrdID;
	private StringProperty symbol;
	private DoubleProperty price;
	private DoubleProperty leavesQty;
	private DoubleProperty cumQty;
	private DoubleProperty avgPx;
	private StringProperty ordStatus;
	private StringProperty ordType;
	private StringProperty side;
	private OrdStatus ordStatusEnum;
	private OrdType ordTypeEnum;
	private Side sideEnum;

	public OrderBean() {
	}

	public OrderBean(Order from) {
		setAvgPx(from.getAvgPx());
		setClOrdID(from.getClOrdID());
		setCumQty(from.getCumQty());
		setLeavesQty(from.getLeavesQty());
		setOrderID(from.getOrderID());
		setOrdStatus(from.getOrdStatus());
		setOrdType(from.getOrdType());
		setPrice(from.getPrice());
		setOrigClOrdID(from.getClOrdID());
		setQty(from.getQty());
		setSide(from.getSide());
		setSymbol(from.getSymbol());
		setTimeInForce(from.getTimeInForce());
	}

	@Override
	public String getOrderID() {
		return orderID.get();
	}

	public StringProperty getIDProperty() {
		return orderID;
	}

	@Override
	public String getClOrdID() {
		return clOrdID.get();
	}

	public StringProperty getClOrdIDProperty() {
		return clOrdID;
	}

	@Override
	public String getOrigClOrdID() {
		return origClOrdID.get();
	}

	public StringProperty getOrigClOrdIDProperty() {
		return origClOrdID;
	}

	@Override
	public String getSymbol() {
		return symbol.get();
	}

	public StringProperty getSymbolProperty() {
		return symbol;
	}

	@Override
	public OrdStatus getOrdStatus() {
		return ordStatusEnum;
	}

	public StringProperty getOrdStatusProperty() {
		return ordStatus;
	}

	@Override
	public char getTimeInForce() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Side getSide() {
		return sideEnum;
	}

	public StringProperty getSideProperty() {
		return side;
	}

	@Override
	public OrdType getOrdType() {
		return ordTypeEnum;
	}

	public StringProperty getOrdTypeProperty() {
		return ordType;
	}

	@Override
	public double getQty() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAvgPx() {
		return avgPx.get();
	}

	public DoubleProperty getAvgPxProperty() {
		return avgPx;
	}

	@Override
	public double getPrice() {
		return price.get();
	}

	public DoubleProperty getPriceProperty() {
		return price;
	}

	@Override
	public double getCumQty() {
		return cumQty.get();
	}

	public DoubleProperty getCumQtyProperty() {
		return cumQty;
	}

	@Override
	public double getLeavesQty() {
		return leavesQty.get();
	}

	public DoubleProperty getLeavesProperty() {
		return leavesQty;
	}

	@Override
	public void setOrderID(String ID) {
		if (orderID == null)
			orderID = new SimpleStringProperty(this, "orderID");
		this.orderID.set(ID);
	}

	@Override
	public void setClOrdID(String clientID) {
		if (clOrdID == null)
			clOrdID = new SimpleStringProperty(this, "clOrdID");
		this.clOrdID.set(clientID);
	}

	@Override
	public void setOrigClOrdID(String origClientID) {
		if (origClOrdID == null)
			origClOrdID = new SimpleStringProperty(this, "origClOrdID");
		this.origClOrdID.set(origClientID);
	}

	@Override
	public void setSymbol(String sym) {
		if (symbol == null)
			symbol = new SimpleStringProperty(this, "symbol");
		this.symbol.set(sym);
	}

	@Override
	public void setOrdStatus(OrdStatus status) {
		this.ordStatusEnum = status;
		if (ordStatus == null)
			ordStatus = new SimpleStringProperty(this, "ordStatus");
		this.ordStatus.set(status.toString());
	}

	@Override
	public void setTimeInForce(char tif) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSide(Side s) {
		this.sideEnum = s;
		if (side == null)
			side = new SimpleStringProperty(this, "side");
		this.side.set(s.toString());
	}

	@Override
	public void setOrdType(OrdType type) {
		this.ordTypeEnum = type;
		if (ordType == null)
			ordType = new SimpleStringProperty(this, "ordType");
		this.ordType.set(type.toString());
	}

	@Override
	public void setQty(double qty) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAvgPx(double price) {
		if (avgPx == null)
			avgPx = new SimpleDoubleProperty(this, "avgPx");
		this.avgPx.set(price);
	}

	@Override
	public void setPrice(double px) {
		if (price == null)
			price = new SimpleDoubleProperty(this, "price");
		this.price.set(px);
	}

	@Override
	public void setCumQty(double cum) {
		if (cumQty == null)
			cumQty = new SimpleDoubleProperty(this, "clOrdID");
		this.cumQty.set(cum);
	}

	@Override
	public void setLeavesQty(double lvsQty) {
		if (leavesQty == null)
			leavesQty = new SimpleDoubleProperty(this, "leavesQty");
		leavesQty.set(lvsQty);
	}

}
