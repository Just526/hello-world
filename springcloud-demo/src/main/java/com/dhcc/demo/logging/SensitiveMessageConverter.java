package com.dhcc.demo.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.google.common.collect.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 敏感信息脱敏处理
 *
 * @author guogang
 */
public class SensitiveMessageConverter extends MessageConverter {
    private static volatile List<Config> configList;
    private final Logger log = LoggerFactory.getLogger(SensitiveMessageConverter.class);

    @Override
    public String convert(ILoggingEvent event) {
        init();
        return sensitiveConvert(event);
    }

    /**
     * 初始化规则配置
     */
    private void init() {
        if (configList == null) {
            synchronized (SensitiveMessageConverter.class) {
                if (configList == null) {
                    configList = Lists.newArrayList();
                    Map<String, String> propertyMap = ((LoggerContext) LoggerFactory.getILoggerFactory()).getCopyOfPropertyMap();
                    for (String s : propertyMap.keySet()) {
                        String[] array = propertyMap.get(s).split("&");
                        if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                            configList.add(new Config(array[0], array[1]));
                        }
                    }
                    log.info("desensitization rule config init end ! ");
                }
            }
        }
    }

    /**
     * 日志内容脱敏转换
     *
     * @param event
     * @return
     */
    private String sensitiveConvert(ILoggingEvent event) {
        String message = event.getFormattedMessage();
        // 超长报文截取
        if (message.length() > 8192) {
            message = message.substring(0, 8192).concat("<<- more:" + message.length()+" char");
        }
        if (configList != null) {
            for (Config config : configList) {
                message = config.apply(message);
            }
        } else {
            message = super.convert(event);
        }
        return message;
    }

    private class Config {
        private String reg;
        private String replacement;

        Config(String reg, String replacement) {
            this.reg = reg;
            this.replacement = replacement;
        }

        String apply(String message) {
            return Pattern.compile(reg).matcher(message).replaceAll(replacement);
        }

        public String getReg() {
            return reg;
        }

        public void setReg(String reg) {
            this.reg = reg;
        }

        public String getReplacement() {
            return replacement;
        }

        public void setReplacement(String replacement) {
            this.replacement = replacement;
        }
    }
}