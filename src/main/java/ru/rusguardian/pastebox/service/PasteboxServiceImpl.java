package ru.rusguardian.pastebox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.rusguardian.pastebox.api.PasteboxRequest;
import ru.rusguardian.pastebox.api.PublicStatus;
import ru.rusguardian.pastebox.api.response.PasteboxResponse;
import ru.rusguardian.pastebox.api.response.PasteboxUrlResponse;
import ru.rusguardian.pastebox.repository.PasteboxEntity;
import ru.rusguardian.pastebox.repository.PasteboxRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


@Service
@RequiredArgsConstructor
@ConfigurationProperties
public class PasteboxServiceImpl implements PasteboxService {

    private String host = "http://abc.ru";
    private int publicListSize = 10;

    private final PasteboxRepository repository;
    private AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public PasteboxResponse getByHash(String hash) {
        PasteboxEntity pasteboxEntity = repository.getByHash(hash);
        return new PasteboxResponse(pasteboxEntity.getData(), pasteboxEntity.isPublic());
    }

    @Override
    public List<PasteboxResponse> getFirstPublicPasteboxes() {

        List<PasteboxEntity> list =repository.getListOfPublicAndAlive(publicListSize);

        return list.stream().map(pasteboxEntity ->
             new PasteboxResponse(pasteboxEntity.getData(), pasteboxEntity.isPublic()))
                .collect(Collectors.toList());



    }

    @Override
    public PasteboxUrlResponse create(PasteboxRequest request) {

        int hash = generateId();
        PasteboxEntity pasteboxEntity = new PasteboxEntity();
        pasteboxEntity.setData(request.getData());
        pasteboxEntity.setId(hash);
        pasteboxEntity.setHash(Integer.toHexString(hash));
        pasteboxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);
        pasteboxEntity.setLifetime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));

            repository.add(pasteboxEntity);

            return new PasteboxUrlResponse(host + "/" + pasteboxEntity.getHash());
    }

    private int generateId() {
        return idGenerator.getAndIncrement();
    }
}
