package data;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by SilverNarcissus on 2017/3/5.
 */
class DataLogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(new Date()).append("--").append(record.getLevel()).append(":").append(record.getMessage()).append(System.lineSeparator());
        return stringBuilder.toString();
    }
}
