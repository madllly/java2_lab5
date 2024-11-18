package ru.yuminov.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    /**
     * Уникальный идентификатор сообщения.
     * Обязательный. Максимум 32 символа.
     */
    @NotBlank(message = "uid is mandatory")
    @Size(max = 32, message = "uid must be at most 32 characters long")
    private String uid;

    /**
     * Уникальный идентификатор операции.
     * Обязательный. Максимум 32 символа.
     */
    @NotBlank(message = "operationUid is mandatory")
    @Size(max = 32, message = "operationUid must be at most 32 characters long")
    private String operationUid;

    /**
     * Имя системы отправителя.
     * Необязательный параметр.
     */
    private Systems systemName;

    /**
     * Время создания сообщения.
     * Обязательный параметр.
     */
    @NotBlank(message = "systemTime is mandatory")
    private String systemTime;

    /**
     * Наименование ресурса.
     * Необязательный параметр.
     */
    private String source;

    /**
     * Уникальный идентификатор коммуникации.
     * Обязательный. Минимум 1, максимум 100000.
     */
    @Min(value = 1, message = "communicationId must be at least 1")
    @Max(value = 100000, message = "communicationId must be at most 100000")
    private int communicationId;

    /**
     * Уникальный идентификатор шаблона.
     * Необязательный параметр.
     */
    private int templateId;

    /**
     * Код продукта.
     * Необязательный параметр.
     */
    private int productCode;

    /**
     * Смс-код.
     * Необязательный параметр.
     */
    private int smsCode;

    /**
     * Время получения сообщения.
     * Параметр добавляется при обработке сообщения.
     */
    private Instant receivedTime;

    @Override
    public String toString() {
        return "{" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName='" + systemName + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communicationId='" + communicationId + '\'' +
                ", templateId='" + templateId + '\'' +
                ", productCode='" + productCode + '\'' +
                ", smsCode='" + smsCode + '\'' +
                ", receivedTime='" + receivedTime + '\'' +
                '}';
    }
}
