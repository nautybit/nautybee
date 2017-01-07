package com.nautybit.nautybee.common.filter;


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class DbNoConneInfoFilter extends Filter<ILoggingEvent> {

  @Override
  public FilterReply decide(ILoggingEvent event) {
    if (event.getMessage().startsWith("ooo Using Connection [")) {
      return FilterReply.DENY;
    } else {
      return FilterReply.NEUTRAL;
    }
  }

}
