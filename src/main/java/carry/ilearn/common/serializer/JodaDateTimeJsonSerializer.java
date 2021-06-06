package carry.ilearn.common.serializer;

import carry.ilearn.common.Constants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;

/**
 * @author Carry
 * @since 2021/4/12
 */
public class JodaDateTimeJsonSerializer extends JsonSerializer<DateTime> {
    
    @Override
    public void serialize(DateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString(DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN)));
    }
    
}
