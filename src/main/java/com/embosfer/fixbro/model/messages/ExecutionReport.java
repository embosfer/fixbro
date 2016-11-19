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
package com.embosfer.fixbro.model.messages;

import com.embosfer.fixbro.model.state.ReadOnlyOrder;
import com.embosfer.fixbro.model.tags.ExecType;
import com.embosfer.fixbro.model.tags.OrdStatus;

/**
 * Immutable class. This execution structure is based on the structure
 * ExecutionReport from FIX 4.4. "Never changing" fields are available through
 * the Order object, like for instance Side and Symbol. The rest of the fields
 * are available via at the root level of this structure because they are
 * potentially different at each execution
 * 
 * @author embosfer
 *
 */
public class ExecutionReport {

	private final ReadOnlyOrder order;

	private final String clOrdID;
	private final String execID;
	private final ExecType execType;
	private final OrdStatus ordStatus;
	private final double avgPx;
	private final double cumQty;
	private final double leavesQty;

	// non mandatory
	private Double lastQty;
	private Double lastPx;

	public static class Builder {
		// mandatory fields
		private final ReadOnlyOrder order;
		private ExecType execType;

		// optional fields
		private double lastQty;
		private double lastPx;

		public Builder(ExecType execType, ReadOnlyOrder order) {
			this.execType = execType;
			this.order = order;
		}

		public Builder lastPx(double lastPx) {
			this.lastPx = lastPx;
			return this;
		}

		public Builder lastQty(double lastQty) {
			this.lastQty = lastQty;
			return this;
		}

		public ExecutionReport build() {
			return new ExecutionReport(this);
		}

	}

	private ExecutionReport(Builder builder) {
		this.clOrdID = builder.order.getClOrdID();
		this.execID = "E" + System.currentTimeMillis(); // ensure unicity
		this.order = builder.order;
		this.avgPx = builder.order.getAvgPx();
		this.cumQty = builder.order.getCumQty();
		this.execType = builder.execType;
		this.leavesQty = builder.order.getLeavesQty();
		this.ordStatus = builder.order.getOrdStatus();

		// non mandatory: prevent filling them with null values
		if (this.execType == ExecType.TRADE) {
			this.lastQty = builder.lastQty;
			this.lastPx = builder.lastPx;
		}
	}

	public String getExecID() {
		return execID;
	}

	public double getAvgPx() {
		return avgPx;
	}

	public double getCumQty() {
		return cumQty;
	}

	public ExecType getExecType() {
		return execType;
	}

	public Double getLastPx() {
		return lastPx;
	}

	public Double getLastQty() {
		return lastQty;
	}

	public double getLeavesQty() {
		return leavesQty;
	}

	public ReadOnlyOrder getOrder() {
		return order;
	}

	public OrdStatus getOrdStatus() {
		return ordStatus;
	}

	public String getClOrdID() {
		return clOrdID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[ExecutionReport ");
		builder.append("clOrdID: ").append(getClOrdID()).append(" execID:").append(execID).append(" execType:")
				.append(execType).append(" ordStatus:").append(ordStatus).append(" lastQty:").append(lastQty)
				.append(" lastPx:").append(lastPx).append(" leavesQty:").append(leavesQty).append(" cumQty:")
				.append(cumQty).append(" avgPx:").append(avgPx).append(" ").append(order).append("]");
		return builder.toString();
	}

}
