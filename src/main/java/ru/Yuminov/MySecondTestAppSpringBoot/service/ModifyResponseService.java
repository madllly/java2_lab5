package ru.yuminov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.yuminov.MySecondTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}
