package ru.yuminov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.yuminov.MySecondTestAppSpringBoot.model.Request;

@Service
public interface ModifyRequestService {
    void modify(Request request);
}
