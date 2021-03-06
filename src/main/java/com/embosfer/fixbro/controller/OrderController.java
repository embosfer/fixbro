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
package com.embosfer.fixbro.controller;

import com.embosfer.fixbro.model.state.Order;

/**
 * Controller part of the MVC model. Provides actions to be applied on an
 * {@link Order}
 * 
 * @author embosfer
 */
public interface OrderController {

	public void execute(Order order, double lastPx, double lastQty);

	public void reject(Order order, String reason);

	public void cancel(Order order, String reason);

	public void doneForDay(Order order);

	public void expire(Order order);

	public void replace(Order order);

	public void acknowledge(Order order);

	public void pendingNew(Order order);

	public void pendingCancel(Order order);

	public void pendingReplace(Order order);

}
