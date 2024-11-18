package ru.yuminov.MySecondTestAppSpringBoot.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yuminov.MySecondTestAppSpringBoot.model.Response;
import ru.yuminov.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;


@Service("ModifySystemTimeResponseService")
public class ModifySystemTimeResponseService implements ModifyResponseService{
    @Override
    public Response modify(Response response) {
        response.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));
        return response;
    }
}
