package com.mysite.sbb;

import java.util.Locale;

import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.util.StringUtils;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class P6spyPrettySqlFormatter implements MessageFormattingStrategy {
	private String getServiceNameFromStackTrace() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		for (StackTraceElement element : stackTrace) {
			// 예: "com.example.service" 패키지의 클래스를 서비스로 가정
			if (element.getClassName().contains("service")) {
				return element.getClassName() + "." + element.getMethodName();
			}
		}
		return "UnknownService";
	}

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
			String sql, String url) {
		String serviceInfo = getServiceNameFromStackTrace();

		sql = formatSql(category, sql);
//	    Date currentDate = new Date();
//	    SimpleDateFormat format1 = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
//	    return now + "|" + elapsed + "ms|" + category + "|connection " + connectionId + "|" + P6Util.singleLine(prepared) + sql;
//	    return format1.format(currentDate) + " | " + "OperationTime : " + elapsed + "ms" + sql;
		return String.format("%s | took %dms | category: %s | connection: %d | %s | SQL: %s", serviceInfo, elapsed,
				category, connectionId, now, sql);
	}

	private String formatSql(String category, String sql) {
		if (sql == null || sql.trim().isEmpty()) {
			return sql;
		}

		// Only format Statement, distinguish DDL And DML
		if (Category.STATEMENT.getName().equals(category)) {
			String tmpsql = sql.trim().toLowerCase(Locale.ROOT);
			if (tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment")) {
				sql = FormatStyle.DDL.getFormatter().format(sql);
			} else {
				sql = FormatStyle.BASIC.getFormatter().format(sql);
			}

			if (StringUtils.hasText(sql)) {
				sql = FormatStyle.HIGHLIGHT.getFormatter().format(sql);
			}

			sql = "|\nHeFormatSql(P6Spy sql,Hibernate format):" + sql;
		}

		return sql;
	}

}