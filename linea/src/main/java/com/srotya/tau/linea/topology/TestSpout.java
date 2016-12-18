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
package com.srotya.tau.linea.topology;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import com.srotya.tau.linea.ft.Collector;
import com.srotya.tau.linea.processors.Spout;
import com.srotya.tau.wraith.Event;

import io.netty.util.internal.ConcurrentSet;

/**
 * @author ambud.sharma
 */
public class TestSpout extends Spout {

	private static final int COUNT = 50000;
	private static final long serialVersionUID = 1L;
	private transient Collector collector;
	private transient Set<Long> emittedEvents;
	private transient int taskId;
	private transient AtomicBoolean processed;

	@Override
	public void configure(Map<String, String> conf, int taskId, Collector collector) {
		this.taskId = taskId;
		this.processed = new AtomicBoolean(false);
		this.collector = collector;
		emittedEvents = new ConcurrentSet<>();
	}

	@Override
	public String getProcessorName() {
		return "testSpout";
	}

	@Override
	public void ready() {
		System.out.println("Running spout:" + taskId);
		for (int i = 0; i < COUNT; i++) {
			Event event = collector.getFactory().buildEvent();
			event.getHeaders().put("uuid", taskId + "host" + i);
			emittedEvents.add(event.getEventId());
			collector.spoutEmit("jsonbolt", event);
		}
		System.out.println("Emitted all events");
		processed.set(true);
		while (true) {
			if (emittedEvents.size() == 0) {
				System.out.println("Completed processing " + COUNT + " events" + "\ttaskid:" + taskId);
			} else {
				System.out.println("Dropped data:" + emittedEvents.size() + "\ttaskid:" + taskId);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void ack(Long eventId) {
//		boolean removed = false;
		emittedEvents.remove(eventId);
//		if (!removed) {
//			System.err.println("Misrouted event:" + eventId + "\t" + emittedEvents.size());
//		} else {
//			counter = counter + 1;
//		}
	}

	@Override
	public void fail(Long eventId) {
		System.out.println("Spout failing event:" + eventId);
	}

}
