package carry.ilearn.common.serializer;

import carry.ilearn.common.Constants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * @author Carry
 * @since 2021/4/12
 */
public class JodaDateTimeJsonDeserializer extends JsonDeserializer<DateTime> {
    
    @Override
    public DateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String dateString = p.readValueAs(String.class);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN);
        return DateTime.parse(dateString, dateTimeFormatter);
    }
    
}
