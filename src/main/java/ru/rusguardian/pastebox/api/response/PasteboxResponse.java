package ru.rusguardian.pastebox.api.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.rusguardian.pastebox.api.PublicStatus;

@Data
@RequiredArgsConstructor
public class PasteboxResponse {
    private final String data;
    private final boolean isPublic;
}
