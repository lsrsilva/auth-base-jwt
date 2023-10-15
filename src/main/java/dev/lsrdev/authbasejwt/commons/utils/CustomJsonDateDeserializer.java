package dev.lsrdev.authbasejwt.commons.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String date = jsonparser.getText();
        try {
            if (!date.isEmpty()) {
                return format.parse(date);
            }
            return null;
        } catch (ParseException e) {
            throw new JsonParseException("Message: " + e.getMessage() + ". Error offset: " + e.getErrorOffset());
        }
    }


}
