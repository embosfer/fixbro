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
package com.embosfer.fixbro.model.messages;

import java.util.Date;

import com.embosfer.fixbro.model.tags.OrdType;
import com.embosfer.fixbro.model.tags.Side;

/**
 * Class representing a 35=D message
 * 
 * @author embosfer
 *
 */
public class NewOrderSingle {

	// MANDATORY FIELDS
	private final String clOrdID; // 11
	private final String symbol; // 55
	private final Side side; // 54
	private final Date transactTime; // 60
	private final double orderQty; // 38
	private final OrdType ordType; // 40

	// non mandatory
	private double price; // 44

	private NewOrderSingle(Builder builder) {
		this.clOrdID = builder.clOrdID;
		this.symbol = builder.symbol;
		this.side = builder.side;
		this.transactTime = builder.transactTime;
		this.orderQty = builder.orderQty;
		this.ordType = builder.ordType;
		this.price = builder.price;
	}

	public String getClOrdID() {
		return clOrdID;
	}

	public String getSymbol() {
		return symbol;
	}

	public Side getSide() {
		return side;
	}

	public Date getTransactTime() {
		return transactTime;
	}

	public double getOrderQty() {
		return orderQty;
	}

	public OrdType getOrdType() {
		return ordType;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[NewOrderSingle ");
		builder.append("clOrdID:").append(clOrdID).append(" symbol:").append(symbol).append(" side:").append(side)
				.append(" transactTime:").append(transactTime).append(" orderQty:").append(orderQty).append(" ordType:")
				.append(ordType).append("]");
		return builder.toString();
	}

	public final static class Builder {

		private String clOrdID;
		private String symbol;
		private Side side;
		private Date transactTime;
		private double orderQty;
		private OrdType ordType;

		// non mandatory
		private double price;

		public Builder() {
		}

		public Builder clOrdID(String clOrdID) {
			this.clOrdID = clOrdID;
			return this;
		}

		public Builder side(char side) {
			this.side = Side.valueOf(side);
			return this;
		}

		public Builder symbol(String sym) {
			this.symbol = sym;
			return this;
		}

		public Builder transactTime(Date tt) {
			this.transactTime = (Date) tt.clone();
			return this;
		}

		public Builder orderQty(double qty) {
			this.orderQty = qty;
			return this;
		}

		public Builder ordType(char type) {
			this.ordType = OrdType.valueOf(type);
			return this;
		}

		public Builder price(double px) {
			this.price = px;
			return this;
		}

		public NewOrderSingle build() {
			return new NewOrderSingle(this);
		}
	}

}
