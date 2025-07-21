package com.mysite.sbb;

import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.util.StringUtils;

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
//		String serviceInfo = getServiceNameFromStackTrace();
//
//		sql = formatSql(category, sql);
//
//		if (sql.trim().isEmpty()) { // sql 이 없다면 출력하지 않아도 됨
//			return "";
//		}
////		// stack 을 구성하는 Format을 만든다
////		sql = sql + createStack(connectionId, elapsed);
//
////	    Date currentDate = new Date();
////	    SimpleDateFormat format1 = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
////	    return now + "|" + elapsed + "ms|" + category + "|conneckition " + connectionId + "|" + P6Util.singleLine(prepared) + sql;
////	    return format1.format(currentDate) + " | " + "OperationTime : " + elapsed + "ms" + sql;
//		return String.format("%s | took %dms | category: %s | connection: %d | %s | SQL: %s", serviceInfo, elapsed,
//				category, connectionId, now, sql);

		StringBuilder sb = new StringBuilder();
		sb.append(category).append(" ").append(elapsed).append("ms");
		if (StringUtils.hasText(sql)) {
			sb.append(highlight(format(sql)));
		}
		return sb.toString();
	}

	private String format(String sql) {
		if (isDDL(sql)) {
			return FormatStyle.DDL.getFormatter().format(sql);
		} else if (isBasic(sql)) {
			return FormatStyle.BASIC.getFormatter().format(sql);
		}
		return sql;
	}

	private String highlight(String sql) {
		return FormatStyle.HIGHLIGHT.getFormatter().format(sql);
	}

	private boolean isDDL(String sql) {
		return sql.startsWith("create") || sql.startsWith("alter") || sql.startsWith("comment");
	}

	private boolean isBasic(String sql) {
		return sql.startsWith("select") || sql.startsWith("insert") || sql.startsWith("update")
				|| sql.startsWith("delete");
	}
//	private String formatSql(String category, String sql) {
//		if (sql == null || sql.trim().isEmpty()) {
//			return sql;
//		}
//
//		// Only format Statement, distinguish DDL And DML
//		if (Category.STATEMENT.getName().equals(category)) {
//
//			String tmpsql = sql.trim().toLowerCase(Locale.ROOT);
//			if (tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment")) {
//				sql = FormatStyle.DDL.getFormatter().format(sql);
//			} else {
//				sql = FormatStyle.BASIC.getFormatter().format(sql);
//			}
//
//			if (StringUtils.hasText(sql)) {
//				sql = FormatStyle.HIGHLIGHT.getFormatter().format(sql);
//			}
//
//			sql = "|\nHeFormatSql(P6Spy sql,Hibernate format):" + sql;
//		}
//
//		return sql;
//	}
//	// stack 콘솔 표기
//	private String createStack(int connectionId, long elapsed) {
//		Stack<String> callStack = new Stack<>();
//		StackTraceElement[] stackTrace = new Throwable().getStackTrace();
//
//		for (StackTraceElement stackTraceElement : stackTrace) {
//			String trace = stackTraceElement.toString();
//
//			// trace 항목을 보고 내게 맞는 것만 필터
//			if (trace.startsWith(ALLOW_FILTER) && !filterDenied(trace)) {
//				callStack.push(trace);
//			}
//		}
//
//		StringBuffer sb = new StringBuffer();
//		int order = 1;
//		while (callStack.size() != 0) {
//			sb.append("\n\t\t" + (order++) + "." + callStack.pop());
//		}
//
//		return new StringBuffer().append("\n\n\tConnection ID:").append(connectionId).append(" | Excution Time:")
//				.append(elapsed).append(" ms\n").append("\n\tExcution Time:").append(elapsed).append(" ms\n")
//				.append("\n\tCall Stack :").append(sb).append("\n").append("\n--------------------------------------")
//				.toString();
//	}

}