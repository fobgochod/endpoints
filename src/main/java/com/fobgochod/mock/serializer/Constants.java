package com.fobgochod.mock.serializer;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * 日期格式话
 *
 * @author fobgochod
 */
public interface Constants {

    String DATE_PATTERN = "yyyy-MM-dd";
    String TIME_PATTERN = "HH:mm:ss";
    String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(Constants.DATE_PATTERN);
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);
    DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
}
