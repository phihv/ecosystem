package net.platform.services.ecosystem.adapter.inbound.restful;

import lombok.RequiredArgsConstructor;
import net.platform.services.ecosystem.adapter.inbound.facade.TestThread;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/thread")
public class ThreadController {
    private final TestThread testThread;
    @GetMapping("/virtual")
    public String runVirtual(@RequestParam(defaultValue = "100000") int count) throws Exception {
        return testThread.runThreads(true, count);
    }

    @GetMapping("/platform")
    public String runPlatform(@RequestParam(defaultValue = "1000") int count) throws Exception {
        return testThread.runThreads(false, count);
    }
}
