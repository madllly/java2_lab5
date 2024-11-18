package ru.yuminov.MySecondTestAppSpringBoot.service;

import ru.yuminov.MySecondTestAppSpringBoot.model.Response;

import java.util.UUID;

public class ModifyOperationUidResponseService implements ModifyResponseService {
    @Override
    public Response modify(Response response) {
        UUID uuid = UUID.randomUUID();

        response.setOperationUid(uuid.toString());

        return response;
    }
}
