package com.chinagps.driverbook.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.content.res.AXmlResourceParser;

public class AndroidUtil {
	private static final float[] RADIX_MULTS = { 0.0039063F, 3.051758E-005F, 1.192093E-007F, 4.656613E-010F };
	private static final String[] DIMENSION_UNITS = { "px", "dip", "sp", "pt", "in", "mm", "", "" };
	private static final String[] FRACTION_UNITS = { "%", "%p", "", "", "", "", "", "" };
	private static final String DEFAULT_XML = "AndroidManifest.xml";
	

	public static List<String> getInfoFromAPK(File apkFile) {
		ZipFile file = null;
		
		ArrayList<String> result = new ArrayList<String>();
		try {
			file = new ZipFile(apkFile, ZipFile.OPEN_READ);
			ZipEntry entry = file.getEntry(DEFAULT_XML);
			AXmlResourceParser parser = new AXmlResourceParser();
			parser.open(file.getInputStream(entry));
			StringBuilder indent = new StringBuilder(10);
			while (true) {
				int type = parser.next();
				if (type == 1) {
					break;
				}
				switch (type) {
				case 0:
					log("<?xml version=\"1.0\" encoding=\"utf-8\"?>", new Object[0]);
					break;
				case 2:
					log("%s<%s%s",
							new Object[] { indent,
									getNamespacePrefix(parser.getPrefix()),
									parser.getName() });
					indent.append("\t");

					int namespaceCountBefore = parser.getNamespaceCount(parser
							.getDepth() - 1);
					int namespaceCount = parser.getNamespaceCount(parser
							.getDepth());
					for (int i = namespaceCountBefore; i != namespaceCount; i++) {
						log("%sxmlns:%s=\"%s\"",
								new Object[] { indent,
										parser.getNamespacePrefix(i),
										parser.getNamespaceUri(i) });
					}

					for (int i = 0; i != parser.getAttributeCount(); i++) {
						String a = log("%s%s%s=\"%s\"", new Object[] { indent, getNamespacePrefix(parser.getAttributePrefix(i)), parser.getAttributeName(i), getAttributeValue(parser, i) });
						if (a != null) {
							result.add(a);
						}
					}
					log("%s>", new Object[] { indent });
					break;
				case 3:
					indent.setLength(indent.length() - "\t".length());
					log("%s</%s%s>",
							new Object[] { indent, getNamespacePrefix(parser.getPrefix()), parser.getName() });
					break;
				case 4:
					log("%s%s", new Object[] { indent, parser.getText() });
					break;
				case 1:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (file != null) {
					file.close();
				}
			} catch (Exception e) {}
		}
		return result;
	}

	private static String getNamespacePrefix(String prefix) {
		if ((prefix == null) || (prefix.length() == 0)) {
			return "";
		}
		return prefix + ":";
	}

	private static String getAttributeValue(AXmlResourceParser parser, int index) {
		int type = parser.getAttributeValueType(index);
		int data = parser.getAttributeValueData(index);
		if (type == 3) {
			return parser.getAttributeValue(index);
		}
		if (type == 2) {
			return String.format("?%s%08X", new Object[] { getPackage(data), Integer.valueOf(data) });
		}
		if (type == 1) {
			return String.format("@%s%08X", new Object[] { getPackage(data), Integer.valueOf(data) });
		}
		if (type == 4) {
			return String.valueOf(Float.intBitsToFloat(data));
		}
		if (type == 17) {
			return String.format("0x%08X", new Object[] { Integer.valueOf(data) });
		}
		if (type == 18) {
			return data != 0 ? "true" : "false";
		}
		if (type == 5) {
			return Float.toString(complexToFloat(data))
					+ DIMENSION_UNITS[(data & 0xF)];
		}
		if (type == 6) {
			return Float.toString(complexToFloat(data))
					+ FRACTION_UNITS[(data & 0xF)];
		}
		if ((type >= 28) && (type <= 31)) {
			return String.format("#%08X",
					new Object[] { Integer.valueOf(data) });
		}
		if ((type >= 16) && (type <= 31)) {
			return String.valueOf(data);
		}
		return String.format("<0x%X, type 0x%02X>",
				new Object[] { Integer.valueOf(data), Integer.valueOf(type) });
	}

	private static String getPackage(int id) {
		if (id >>> 24 == 1) {
			return "android:";
		}
		return "";
	}

	private static String log(String format, Object[] arguments) {
		String s = String.format(format, arguments);
		Pattern p1 = Pattern.compile("(android:versionCode|android:versionName|package){1}=\"(.+)\"");
		Matcher m = p1.matcher(s);
		if (m.find()) {
			return m.group(0).replaceAll("\"", "");
		}
		return null;
	}

	private static float complexToFloat(int complex) {
		return (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4 & 0x3)];
	}
}
