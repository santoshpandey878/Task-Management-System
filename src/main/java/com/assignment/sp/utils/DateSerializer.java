package com.assignment.sp.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.assignment.sp.constant.ApplicationConstant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * utility class to format date in json response
 */
public class DateSerializer extends JsonSerializer<Date>{
	@Override
	public final void serialize(Date date, JsonGenerator jgen, SerializerProvider provider) throws IOException{
		if(null != date){
			SimpleDateFormat sdf = new SimpleDateFormat(ApplicationConstant.API_DATE_FORMAT);
			jgen.writeString(sdf.format(date));
		}else{
			jgen.writeString("--");
		}
	}
}

