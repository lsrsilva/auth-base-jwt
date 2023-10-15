package dev.lsrdev.authbasejwt.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class JsonResponseDTO {
    private Object result;
    protected String message;
    private List<String> messages;

    public JsonResponseDTO() {
    }

    public JsonResponseDTO(Object result, List<String> messages) {
        this.result = result;
        this.messages = messages;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public static JsonResponseDTO ok() {
        return ok(null, null);
    }

    public static JsonResponseDTO ok(String message) {
        return ok(null, message);
    }

    public static JsonResponseDTO ok(Object result) {
        return ok(result, null);
    }

    public static JsonResponseDTO ok(List<?> result, Page<?> page, String message) {
        return ok(
                new PageImpl<>(
                        result,
                        page.getPageable(),
                        page.getTotalElements()
                ),
                message
        );
    }

    public static JsonResponseDTO ok(Object result, String message) {
        JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
        jsonResponseDTO.setResult(result);
        jsonResponseDTO.setMessage(message);
        return jsonResponseDTO;
    }

    public static JsonResponseDTO otherReponse(String message) {
        return otherReponse(null, message, null);
    }

    public static JsonResponseDTO otherReponse(String message, List<String> messages) {
        return otherReponse(null, message, messages);
    }

    public static JsonResponseDTO otherReponse(Object result, String message, List<String> messages) {
        JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
        jsonResponseDTO.setResult(result);
        jsonResponseDTO.setMessage(message);
        jsonResponseDTO.setMessages(messages);
        return jsonResponseDTO;
    }

}
