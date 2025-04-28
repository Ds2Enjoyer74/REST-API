package ru.rusguardian.pastebox.service;


import ru.rusguardian.pastebox.api.PasteboxRequest;
import ru.rusguardian.pastebox.api.response.PasteboxResponse;
import ru.rusguardian.pastebox.api.response.PasteboxUrlResponse;

import java.util.List;


public interface PasteboxService {
    PasteboxResponse getByHash(String hash);
    List<PasteboxResponse> getFirstPublicPasteboxes();
    PasteboxUrlResponse create(PasteboxRequest request);

}
