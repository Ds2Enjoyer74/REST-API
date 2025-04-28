package ru.rusguardian.pastebox.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rusguardian.pastebox.api.PasteboxRequest;
import ru.rusguardian.pastebox.api.response.PasteboxResponse;
import ru.rusguardian.pastebox.api.response.PasteboxUrlResponse;
import ru.rusguardian.pastebox.service.PasteboxService;

import java.util.Collection;



@RestController
@RequiredArgsConstructor
public class PasteboxController {
    private final PasteboxService pasteboxService;

    @GetMapping("/")
    public Collection<PasteboxResponse> getPublicPasteList() {
        return pasteboxService.getFirstPublicPasteboxes();
    }

    @GetMapping("/{hash}")
    public PasteboxResponse getByHash(@PathVariable String hash) {
        return pasteboxService.getByHash(hash);
    }

    @PostMapping("/")
    public PasteboxUrlResponse add(@RequestBody PasteboxRequest request) {
        return pasteboxService.create(request);
    }

}
