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
package com.embosfer.fixbro.controller.er.processor;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.embosfer.fixbro.controller.er.observer.ExecutionReportObservable;
import com.embosfer.fixbro.controller.er.observer.ExecutionReportObserver;
import com.embosfer.fixbro.model.messages.ExecutionReport;

/**
 * Distributes the {@link ExecutionReport}s produced by FIXBro to all
 * {@link ExecutionReportObserver}s
 * 
 * @author embosfer
 *
 */
public class ExecutionReportProcessorImpl implements ExecutionReportProcessor, ExecutionReportObservable {
	
	private final Logger log = LoggerFactory.getLogger(ExecutionReportProcessorImpl.class);

	private final List<ExecutionReportObserver> erObserverList;
	private final ExecutorService executorService;

	public ExecutionReportProcessorImpl(List<ExecutionReportObserver> observers) {
		erObserverList = new CopyOnWriteArrayList<>(observers);
		executorService = Executors.newCachedThreadPool();
	}

	@Override
	public void process(ExecutionReport er) {
		erObserverList.forEach(observer -> {
			log.info("Sending {}", er);
			executorService.execute(() -> observer.onExecutionReport(er));
		});
	}

	@Override
	public void registerExecutionReportObservable(ExecutionReportObserver observer) {
		erObserverList.add(observer);
	}

	@Override
	public void unRegisterExecutionReportObservable(ExecutionReportObserver observer) {
		Iterator<ExecutionReportObserver> it = erObserverList.iterator();
		while (it.hasNext()) {
			ExecutionReportObserver o = it.next();
			if (o.equals(observer)) {
				it.remove();
				return;
			}
		}
	}

}
