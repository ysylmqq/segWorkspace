package com.hm.bigdata.util;

public class UrlUtils {

	public static final String cleanupUrl(final String url) {

		if (url == null) {

			return null;

		}

		final int jsessionPosition = url.indexOf(";jsession");

		if (jsessionPosition == -1) {

			return url;

		}

		final int questionMarkPosition = url.indexOf("?");

		if (questionMarkPosition < jsessionPosition) {

			return url.substring(0, url.indexOf(";jsession"));

		}
		return url.substring(0, jsessionPosition) + url.substring(questionMarkPosition);

	}

}
