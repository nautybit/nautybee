package com.yuanpin.flora.register;

import com.yuanpin.flora.common.utils.DateUtils;

import java.text.*;
import java.util.Date;

/**
 * Created by Minutch on 15/6/13.
 */
public class SmartDateFormat extends SimpleDateFormat {

	private static final long serialVersionUID = -7511895186890117827L;

	@Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
        return new StringBuffer(DateUtils.formatDate(date, DateUtils.Y_M_D_HMS));
    }

    @Override
    public Date parse(String text) throws ParseException {
        return DateUtils.smartFormat(text);
    }
}
