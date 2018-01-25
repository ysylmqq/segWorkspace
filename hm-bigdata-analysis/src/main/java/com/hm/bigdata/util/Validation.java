package com.hm.bigdata.util;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.regex.Matcher;

public class Validation {

    public Validation() {

    }

    //�ж��Ƿ��������ַ���
    public static boolean isNumericalString(String str) {
        str = str.toLowerCase();
        String strTemp = str;

        char c = str.charAt(0);
        if (!(c == '+' || c == '-' || Character.isDigit(c))) {
            return false;
        } else if (c == '+' || c == '-') {
            strTemp = str.substring(1);
        }

        int size = strTemp.length();
        for (int i = 0; i < size; i++) {
            char tempChar = strTemp.charAt(i);
            if (!(Character.isDigit(tempChar) || tempChar == '.' ||
                  tempChar == 'e')) {
                return false;
            } else {
                if (tempChar == '.') {
                    if (i == 0 || i == size - 1) {
                        return false;
                    }
                }
                if (tempChar == 'e') {
                    tempChar = str.charAt(i + 1);
                    if (!(tempChar == '+' || tempChar == '-' ||
                          Character.isDigit(tempChar))) {
                        return false;
                    } else {
                        if (tempChar == '+' || tempChar == '-') {
                            strTemp = str.substring(i + 2);
                        } else {
                            strTemp = str.substring(i + 1);
                        }
                    }
                    for (int ii = 0; ii < strTemp.length(); ii++) {
                        tempChar = strTemp.charAt(ii);
                        if (!Character.isDigit(tempChar)) {
                            return false;
                        }
                    }
                    return true;
                }

            }
        }
        return true;
    }

    /**
     * support Integer format:<br>
     * "33" "003300" "+33" " -0000 "
     * @param str String
     * @return boolean
     */
    public static boolean isInteger(String str) {
        int begin = 0;
        if (str == null || str.trim().equals("")) {
            return false;
        }
        str = str.trim();
        if (str.startsWith("+") || str.startsWith("-")) {
            if (str.length() == 1) {
                // "+" "-"
                return false;
            }
            begin = 1;
        }
        for (int i = begin; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * use Exception
     * support Numeric format:<br>
     * "33" "+33" "033.30" "-.33" ".33" " 33." " 000.000 "
     * @param str String
     * @return boolean
     */
    public static boolean isNumericEx(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * use Exception
     * support less than 11 digits(<11)<br>
     * support Integer format:<br>
     * "33" "003300" "+33" " -0000 " "+ 000"
     * @param str String
     * @return boolean
     */
    public static boolean isIntegerEx(String str) {
        str = str.trim();
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex) {
            if (str.startsWith("+")) {
                return isIntegerEx(str.substring(1));
            }
            return false;
        }
    }
    public static boolean isDate(String strDate) {
      try {
        Pattern datePattern = Pattern.compile("([0-9]{1,4})-([0-9]{1,2})-([0-9]{1,2})");
        Matcher dateParts = datePattern.matcher(strDate);
        if (dateParts.find()) {
          int iYear = getIntValue(dateParts.group(1));
          int iMonth = getIntValue(dateParts.group(2));
          int iDay = getIntValue(dateParts.group(3));

          if (checkDate(iYear, iMonth, iDay)) {
            return true;
          }
          else {
            return false;
          }
        }
        else {
          return false;
        }
      }
      catch (PatternSyntaxException pse) {
        return false;
      }
    }

    public static boolean checkDate(int iYear, int iMonth, int iDay) {
      int[] amonths = {1, 3, 5, 7, 8, 10, 12};
      int[] bmonths = {4, 6, 9, 11};

      for (int i = 0; i < amonths.length; i++) {
        if (iMonth == amonths[i] && iDay < 32) {
          return true;
        }
      }
      for (int i = 0; i < bmonths.length; i++) {
        if (iMonth == bmonths[i] && iDay < 31) {
          return true;
        }
      }
      if (iMonth == 2) {
        if (isLeapYear(iYear) && iDay < 30) {
          return true;
        }
        else if (iDay < 29) {
          return true;
        }
      }
      return false;
    }

    public static boolean isLeapYear(int iYear) {
      if (iYear % 4 == 0) {
        if ( (iYear % 100 == 0) && (iYear % 400 != 0)) {
          return false;
        }
        else {
          return true;
        }
      }
      else {
        return false;
      }
    }

    public static int getIntValue(String strInt) {
      try {
        return Integer.parseInt(strInt);
      }
      catch (Exception e) {
        return 0;
      }
  }
    public static void main(String[] args) {
        //Validation validation = new Validation();
    }
}
