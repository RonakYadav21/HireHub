package com.apigateway.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class IntervalBackpressureExample {
    public static void main(String[] args) throws InterruptedException {

        // Emit 1 item per second
        Flux.interval(Duration.ofSeconds(1))
            .onBackpressureBuffer(
                10, // buffer size
                dropped -> System.out.println("⚠️ Dropped Tick: " + dropped)
            )
            .publishOn(Schedulers.boundedElastic()) // Subscriber runs on a different thread
            .subscribe(tick -> {
                System.out.println("✅ Received Tick: " + tick);

                // Simulate a slow subscriber that takes 3 seconds to process one tick
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        // Keep the main thread alive to observe output
        Thread.sleep(30000); // Run for 30 seconds
    }
}
