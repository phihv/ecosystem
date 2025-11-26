package net.platform.services.ecosystem.adapter.inbound.facade;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TestThread {
    public String runThreads(boolean virtual, int n) throws InterruptedException, ExecutionException {
        long memBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long start = System.currentTimeMillis();

        ExecutorService executor = virtual ?
                Executors.newVirtualThreadPerTaskExecutor() :
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ignored) {}
                return 1;
            }, executor));
        }

        int sum = 0;
        for (var f : futures) sum += f.get();

        executor.close();
        long end = System.currentTimeMillis();
        long memAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        return String.format(
                "%s Threads: count=%d, sum=%d, time=%d ms, memory used=%.2f MB",
                virtual ? "Virtual" : "Platform",
                n, sum, (end - start),
                (memAfter - memBefore) / 1024.0 / 1024.0
        );
    }
}
