/**
 * Copyright 2016 Ambud Sharma
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.srotya.tau.nucleus.processor;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.lmax.disruptor.EventHandler;
import com.srotya.tau.nucleus.DisruptorUnifiedFactory;
import com.srotya.tau.wraith.Event;
import com.srotya.tau.wraith.MutableInt;

/**
 * @author ambudsharma
 *
 */
public class FineCountingProcessor extends AbstractProcessor {
	
	private static final Logger logger = Logger.getLogger(FineCountingProcessor.class.getName());

	public FineCountingProcessor(DisruptorUnifiedFactory factory, int parallelism, int bufferSize,
			Map<String, String> conf, AbstractProcessor[] outputProcessors) {
		super(factory, parallelism, bufferSize, conf, outputProcessors);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<EventHandler<Event>> getInitializedHandlers(MutableInt parallelism, Map<String, String> conf,
			DisruptorUnifiedFactory factory) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConfigPrefix() {
		return "fcagg";
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

}