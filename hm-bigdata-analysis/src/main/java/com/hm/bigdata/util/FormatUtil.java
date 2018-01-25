package com.hm.bigdata.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;

public class FormatUtil {

    public final static int[] DAYSINMONTH = {
                                            31, 28, 31, 30, 31, 30, 31, 31, 30,
                                            31, 30, 31};
    protected Calendar cal = Calendar.getInstance();
    protected int today = cal.get(Calendar.DAY_OF_MONTH);
    protected int month = cal.get(Calendar.MONTH);
    protected int year = cal.get(Calendar.YEAR);
    public static String GBK="GBK";
    public static String UTF_8="UTF-8";

    public String getMonthMaxDay(int iyear, int imonth) {
        cal.set(iyear, imonth, 1);
        int maxday = DAYSINMONTH[imonth - 1];
        if (imonth == Calendar.FEBRUARY &&
            ((GregorianCalendar) cal).isLeapYear(iyear)) {
            maxday++;
        }
        return String.valueOf(maxday);
    }

    /*
    * 功能：判断当前时间是否超过指定时间
    * 参数：形如：2007-12-25 的日期字符串
    * 返回值：True 超过，False 不超过
    */
    public static boolean whetherExceedCurrentTime(String strDate) {
        Date currentTime = new Date();
        //String strDate = "2007-12-25";
        String[] date = strDate.split("-"); //将要转换的日期字符串拆分成年月日
        int year, month, day;
        year = Integer.parseInt(date[0]);
        month = Integer.parseInt(date[1]) - 1;
        day = Integer.parseInt(date[2]);
        GregorianCalendar d = new GregorianCalendar(year, month, day);
        d.add(Calendar.DATE, 0);
        Date dd = d.getTime();
        System.out.println("是否超过：" + (currentTime.after(dd)));
        return currentTime.after(dd);
    }

    //如果是形如：2007-12-15 00:00:00.0 的日期则截取为：2007-12-15 格式
    public static String getSortDateValue(String s){
        if(s.length()>10){
            s = s.substring(0,10);
        }
        return s;
    }

    //将形如“0000000180.00”的数字符串转换不整数
    public static int getIntValue(String s){
      int i=0;
      try {
        if(s.indexOf(".")>0){
          s = s.substring(0,s.indexOf("."));
        }
        i = Integer.parseInt(s);
      }
      catch (NumberFormatException ex) {
        ex.printStackTrace();
      }
      return i;
    }

    /**
     * 字符串按字节截取
     * @param str 原字符
     * @param len 截取长度
     * @param elide 省略符
     * @return String
     * @author kinglong
     * @since 2006.07.20
     */
    public static String interceptStringByByte(String str, int len, String elide) {
      if (str == null) {
        return "";
      }
      byte[] strByte = str.getBytes();
      int strLen = strByte.length;
      int elideLen = (elide.trim().length() == 0) ? 0 : elide.getBytes().length;
      if (len >= strLen || len < 1) {
        return str;
      }
      if (len - elideLen > 0) {
        len = len - elideLen;
      }
      int count = 0;
      for (int i = 0; i < len; i++) {
        int value = (int) strByte[i];
        if (value < 0) {
          count++;
        }
      }
      if (count % 2 != 0) {
        len = (len == 1) ? len + 1 : len - 1;
      }
      return new String(strByte, 0, len) + elide.trim();
    }
    /**
     * 字符串按字节截取
     * @param str 原字符
     * @param len 截取长度
     * @param elide 省略符
     * @return String
     * @author kinglong
     * @since 2006.07.20
     */
    public static String interceptStringByByte(String str, int len, String elide,String charset) {
      if (str == null) {
        return "";
      }
      try {
		byte[] strByte = str.getBytes(charset);
		  int strLen = strByte.length;
		  int elideLen = (elide.trim().length() == 0) ? 0 : elide.getBytes(charset).length;
		  if (len >= strLen || len < 1) {
		    return str;
		  }
		  if (len - elideLen > 0) {
		    len = len - elideLen;
		  }
		  int count = 0;
		  for (int i = 0; i < len; i++) {
		    int value = (int) strByte[i];
		    if (value < 0) {
		      count++;
		    }
		  }
		  if (count % 2 != 0) {
		    len = (len == 1) ? len + 1 : len - 1;
		  }
		  return new String(strByte, 0, len,charset) + elide.trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
      return null;
    }

    // 指定整数和小数位数的输出，整数部分不够位则左补0，小数部分四五五入
    // 本地默认格式输出数：3,333.333
    // 参数说明：d 要处理的数字 integerdigits 整数位数 decimaldigits小数位数
    public static String getAppointedNumberFormat(double d,int integerdigits,int decimaldigits) {
        //double d = 10000.0 / 3.0;
        //d = 333.335;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumIntegerDigits(integerdigits);
        numberFormat.setMinimumIntegerDigits(integerdigits);
        numberFormat.setMaximumFractionDigits(decimaldigits);
        numberFormat.setMinimumFractionDigits(decimaldigits);
        String numberString = numberFormat.format(d);
        return numberString;
    }

    // 指定整数和小数位数的输出，整数部分不够位则左补0，小数部分四五五入
    // 本地默认格式输出数：3,333.333
    // 参数说明：s 要处理的数字字符串 integerdigits 整数位数 decimaldigits小数位数
    public static String getAppointedNumberFormat(String s,int integerdigits,int decimaldigits) {
        double d = 10000.0 / 3.0;
        //d = 333.335;
        if((s!=null)&&(!"".equals(s))){
            d = Double.parseDouble(s);
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumIntegerDigits(integerdigits);
        numberFormat.setMinimumIntegerDigits(integerdigits);
        numberFormat.setMaximumFractionDigits(decimaldigits);
        numberFormat.setMinimumFractionDigits(decimaldigits);
        String numberString = numberFormat.format(d);
        return numberString;
    }

    // 指定整数和小数位数的输出，整数部分不够位则左补0，小数部分四五五入
    // 本地默认格式输出数：3333.333
    // 参数说明：s 要处理的数字字符串 integerdigits 整数位数 decimaldigits小数位数
    public static String getAppointedCurrencyFormat(String s, int integerdigits,
            int decimaldigits) {
        String reStr = "";
        if (s != null) {
            String[] tmpArr = s.split("\\.");
            if (tmpArr.length > 1) {
                reStr = leftAppendZero(tmpArr[0], integerdigits) + "." +
                        rightAppendZero(tmpArr[1], decimaldigits);
            } else {
                reStr = leftAppendZero(tmpArr[0], integerdigits) + "." +
                        rightAppendZero("0", decimaldigits);
            }
        }
        return reStr;
    }

    // 使用本地默认格式输出数
    // 本地默认格式输出数：3,333.333
    public static String getLocalizeNumberFormat(double d) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        //numberFormat.setMaximumFractionDigits(4);
        //numberFormat.setMinimumIntegerDigits(6);
        return numberFormat.format(d);
    }

    // 使用本地默认格式输出数
    // 本地默认格式输出数：3333.33
    public static String getLocalizeNumberFormat(String s) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        Number numb1 = null;
        try {
          //String s = "33,333.33";
          numb1 = numberFormat.parse(s);
          return numb1.toString();
        }
        catch (ParseException e1) {
          System.err.println(e1);
        }
        return "";
    }

    // 使用本地默认格式输出百分数
    // 本地默认格式输出百分数：333,333%
    public static String getLocalizePercentFormat(double d) {
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        System.out.println("本地默认格式输出百分数：" + percentFormat.format(d));
        return percentFormat.format(d);
    }

    // 使用本地默认格式输出货币值
    // 本地默认格式输出货币值：￥3,333.33
    public static String getLocalizeCurrencyFormat(double d) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        System.out.println("本地默认格式输出货币值：" + currencyFormat.format(d));
        return currencyFormat.format(d);
    }

    //日期自动补零程序
    public static String appendZero(String n) {
        return (("00" + n).substring(("00" + n).length() - 2));
    }
    //任意字符串按指定长度左补0
    public static String leftAppendZero(String instr,int length) {
        for(int i=instr.getBytes().length; i<length; i++) {
              instr = "0"+instr;
        }
        return instr;
    }
    //任意字符串按指定长度左补空格
    public static String leftAppendBlank(String instr,int length) {
        for(int i=instr.getBytes().length; i<length; i++) {
              instr = " "+instr;
        }
        return instr;
    }
    //任意字符串按指定长度右补0
    public static String rightAppendZero(String instr,int length) {
        for(int i=instr.getBytes().length; i<length; i++) {
              instr = instr+"0";
        }
        return instr;
    }
    //任意字符串按指定长度右补空格(以字节算)
    public static String rightAppendBlank(String instr,int length) {
        if(instr.getBytes().length>length) instr = interceptStringByByte(instr,length,"");
        for(int i=instr.getBytes().length; i<length; i++) {
              instr = instr+" ";
        }
        return instr;
    }
    
    //任意字符串按指定长度右补空格(以字节算)
    public static String rightAppendBlank(String instr,int length,String charset) {
        try {
			if(instr.getBytes(charset).length>length){
				instr = interceptStringByByte(instr,length,"",charset);
			}
			for(int i=instr.getBytes(charset).length; i<length; i++) {
			      instr = instr+" ";
			}
			return instr;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return null;
    }
    /*
      功能：四舍五入运算
      输入：double   value   原数据
                  int   n   保留小数位数
      返回：四舍五入后的结果double
     */
    public static double roundHalf(double value, int n) {
      BigDecimal bDec = new BigDecimal(value);
      if (n < 0) {
        n = 0;
      }
      bDec = bDec.setScale(n, BigDecimal.ROUND_HALF_EVEN); //四舍五入
      value = bDec.doubleValue();
      return value;
    }

    /**
     *   千分位格式化数据
     *   @param   str   String
     *   @return   String
     */
    public static String formatDec(String str) {
      int iPoint = str.indexOf(".");
      int iLen = str.length();
      String temp = "";
      if (iLen < 4) {
        return str;
      }
      if (iPoint < 0) {
        iPoint = 0;
      }else {
        iLen = iPoint;
      }

      for (int i = 3; i < iLen; i = i + 3) {
        temp = str.substring(iLen - i);
        str = str.substring(0, iLen - i) + ",";
        str += temp;
        i++;
        iLen++;
      }
      return str;
    }

    /**
     *   取消千分位格式化,返回实际值,如123,12.00   应返回12312.00
     *   @param   str   String
     *   @return   String
     */
    public static String unFormatDec(String str) {
        str = str.replaceAll(",", "");
        return str;
    }
    //替换掉字符串中的小数点 “.”是一个正则的特殊字符所以在前面要加“\\”
    public static String replaceRadixPoint(String str) {
        str = str.replaceAll("\\.", "");
        return str;
    }

    //计算两个日期间的天数
    public static long compareDate(String startDate,String endDate){
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date sdate = null;
        Date edate = null;
        long day =0l;
        try {
            sdate = myFormatter.parse(startDate);
            edate = myFormatter.parse(endDate);
            day = (edate.getTime() - sdate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
        	e.printStackTrace();
        }

        return day;
    }

    public static String[] DealStringTokenizer(String source, String delim){
        //String source = "2007-03-01 02:03:04";
        //String delim = "- :";
        StringTokenizer tokenizer;
        tokenizer = new StringTokenizer(source, delim);
        String tmpStr = "";
        while (tokenizer.hasMoreTokens()) {
          tmpStr = tmpStr + tokenizer.nextToken()+"/";
        }
        return tmpStr.split("/");
    }

    //计算两个日期间的天数
    public static int compareCalendar(String startdate,String endDate){
        //String date1 = "2006/07/18/11/42/30";
        //String date2 = "2006/07/20/13/07/19";
        String[] theDate1 = DealStringTokenizer(startdate,"- :"); //分隔字符串
        String[] theDate2 = DealStringTokenizer(endDate,"- :");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.set(Integer.parseInt(theDate1[0]), Integer.parseInt(theDate1[1]),
               Integer.parseInt(theDate1[2]), Integer.parseInt(theDate1[3]),
               Integer.parseInt(theDate1[4]), Integer.parseInt(theDate1[5]));

        c2.set(Integer.parseInt(theDate2[0]), Integer.parseInt(theDate2[1]),
               Integer.parseInt(theDate2[2]), Integer.parseInt(theDate2[3]),
               Integer.parseInt(theDate2[4]), Integer.parseInt(theDate2[5]));

        long ll = c2.getTimeInMillis() - c1.getTimeInMillis(); //获得总毫秒数

        int h = (int) (ll / (60 * 60 * 1000)); //获得小时数

        int m = (int) ( (ll % (60 * 60 * 1000)) / (60 * 1000)); //获得分钟数

        int s = (int) ( ( (ll % (60 * 60 * 1000)) % (60 * 1000)) / 1000); //获得秒数

        int day = (int) (h / 24); //天数

        if (h / 24 > 1) {
            h = h % 24;
        } else {
            h = h - 24;
        }

        //判断天数

        System.out.println("总共时间为:" + day + "天" + h + "小时" + m + "分" + s + "秒");

        return day;
    }


    public static String encodePassword(String password, String algorithm) {
        byte[] unencodedPassword = password.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            return password;
        }
        md.reset();
        md.update(unencodedPassword);
        byte[] encodedPassword = md.digest();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public String str_replace(String from, String to, String source) {
        StringBuffer bf = new StringBuffer("");
        StringTokenizer st = new StringTokenizer(source, from, true);
        while (st.hasMoreTokens()) {
            String tmp = st.nextToken();
            System.out.println("*" + tmp);
            if (tmp.equals(from)) {
                bf.append(to);
            } else {
                bf.append(tmp);
            }
        }
        return bf.toString();
    }

    /*
         Description ： 格式化显示日期型数据

         @param Date dt_in ：日期型数据
         boolean bShowTimePart_in ： 是否显示时间部分
         @return String 格式化后的日期格式
     */

    public String DoFormatDate(java.util.Date dt_in, boolean bShowTimePart_in) {
        if (bShowTimePart_in) {
            return (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(dt_in);
        } else {
            return (new SimpleDateFormat("yyyy-MM-dd")).format(dt_in);
        }
    }

    //将形如:2005-05-25 12:10:01.0的日期转换成Timestamp时间
    public Timestamp getTimestamp(String longdate) {
        return Timestamp.valueOf(longdate);
    }

    //将形如:2005-05-25的日期转换成：2005-05-25 00:00:00.0格式的Timestamp时间
    public Timestamp getStartTimestamp(String thisdate) {
        if (thisdate.length() > 11) {
            thisdate = thisdate.substring(0, 11);
        }
        return Timestamp.valueOf(thisdate + " 00:00:00.0");
    }

    //将形如:2005-05-25的日期转换成：2005-05-25 23:59:59.0格式的Timestamp时间
    public Timestamp getEndTimestamp(String thisdate) {
        if (thisdate.length() > 11) {
            thisdate = thisdate.substring(0, 11);
        }
        return Timestamp.valueOf(thisdate + " 23:59:59.0");
    }

    //生成长时间字符串，形如：2005-01-01 01:01:01
    public static String getLongTimeString() {
        java.util.Date myDate = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //计算前一年
        //long myTime = (myDate.getTime() / 1000) - 60 * 60 * 24 * 365;
        //myDate.setTime(myTime * 1000);
        String mDate = formatter.format(myDate);
        return mDate;
    }

    public static String getDateString(String longtime) {
        if (longtime.length() > 11) {
            longtime = longtime.substring(0, 11);
        }
        return longtime;
    }

    public static String getShortDateString(String longtime) {
        if (longtime.length() > 11) {
            longtime = longtime.substring(0, 11);
        }
        return longtime;
    }

    //生成指定小时前的长时间字符串，形如：2005-01-01 01:01:01
    public static String getPrevLongTimeString(int offsethour) {
        java.util.Date myDate = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //long myTime = (myDate.getTime() / 1000) - 60 * 60 * 24 * 365 - 60 * 60 * offsethour;
        long myTime = (myDate.getTime() / 1000) - 60 * 60 * offsethour;
        myDate.setTime(myTime * 1000);
        String mDate = formatter.format(myDate);
        return mDate;
    }

    /**
     * 方法描述：页面中字符串转化为日期类型
     * 在Oracle数据库中时间的存储和读取(读取时、分、秒)
     * 输入参数：从页面中得到的以字符串形式表示的日期数据
     * 输出参数：转换后的日期
     */
    public static Date transToDate(String strDateFromPage) {
        Date date = null; //此处为yyyy-MM-dd 则为2005-1-13
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //建立一个一定格式的 SimpleDateFormat
        if (strDateFromPage == null) {
            return new Date();
        }
        if (strDateFromPage.length() > 10) {
            sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //此处时间方式为2005-1-13 20:00:12
        }
        try {
            date = sdf.parse(strDateFromPage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return date;
    }

    //将字符串转换为形如：2004年6月16日的当前日期
    public String getChineseDate(String sdate) {
        sdate = sdate.trim();
        if (sdate.indexOf(" ") > 0) {
            sdate = sdate.substring(0, sdate.length() - sdate.indexOf(" "));
        } else {
            sdate = sdate.trim();
        }
        String[] tmpArr = sdate.split("-");
        sdate = tmpArr[0] + "年" + tmpArr[1] + "月" + tmpArr[2] + "日";
        return sdate;
    }

    //到当前的中文 日期形如：2004年6月16日的当前日期
    public String getCurChineseDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年M日d");
        java.util.Date currentTime_1 = new java.util.Date();
        String dateString = formatter.format(currentTime_1);
        return dateString;
    }

    //获取Oracle数据库要求格式的日期字符串
    public static String getOracleDate(java.sql.Date dt, boolean istodate) {
        String sDt = "";
        String restr = "";
        String temp = "yyyy-MM-dd";
        try {
            java.text.SimpleDateFormat formatter = new java.text.
                    SimpleDateFormat(temp);
            restr = formatter.format(dt);
            sDt = "TO_DATE('" + restr + "','" + temp + "')";
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (istodate) {
            restr = sDt;
        }
        return restr;
    }

    //获取Oracle数据库要求格式的日期字符串(一般用在between后)
    public static String getOracleBeginDate(java.sql.Date dt, boolean istodate) {
        String sDt = "";
        String restr = "";
        String shortFormat = "yyyy-MM-dd";
        String longFormat = "yyyy-MM-dd hh24:mi:ss";
        try {
            java.text.SimpleDateFormat formatter = new java.text.
                    SimpleDateFormat(shortFormat);
            restr = formatter.format(dt) + " 00:00:00";
            sDt = "TO_DATE('" + restr + "','" + longFormat + "')";
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (istodate) {
            restr = sDt;
        }
        return restr;
    }

    //获取Oracle数据库要求格式的日期字符串(一般用在between end 后)
    public static String getOracleEndDate(java.sql.Date dt, boolean istodate) {
        String sDt = "";
        String restr = "";
        String shortFormat = "yyyy-MM-dd";
        String longFormat = "yyyy-MM-dd hh24:mi:ss";
        try {
            java.text.SimpleDateFormat formatter = new java.text.
                    SimpleDateFormat(shortFormat);
            restr = formatter.format(dt) + " 00:00:00";
            sDt = "TO_DATE('" + restr + "','" + longFormat + "')+1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (istodate) {
            restr = sDt;
        }
        return restr;
    }
    //将形如：20060107的日期串转换为形如：2006-01-07形式的字符串
    public static String getDateStr(String dateString) {
        String date = dateString;
        if(dateString.length()>=8){
            date = dateString.substring(0,4)+"-"+dateString.substring(4,6)+"-"+dateString.substring(6,8);
        }
        return date;
    }
    //保留小数点后两位小数,返回String型字符串
    public static String getdoubleString(String s) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(2);
        double aa = Double.parseDouble(s);
        String ss = nf.format(aa);
        return ss;
    }

    //保留小数点后两位小数,返回Double型数值
    public static double getDouble2Number(String s) {
        DecimalFormat df1 = new DecimalFormat("###0.00");
        double d = Double.parseDouble(s);
        double dd = Double.parseDouble(df1.format(d));
        return dd;
    }

    //保留小数点后两位小数,返回Double型数值
    public static String getDouble2String(String s) {
        DecimalFormat df1 = new DecimalFormat("###0.00");
        double d = Double.parseDouble(s);
        double dd = Double.parseDouble(df1.format(d));
        return String.valueOf(dd);
    }

    public static Timestamp DelphiDateToJavaDate(double dl) {
        long ll = System.currentTimeMillis();
        double timePara = dl; //38530.427181713; //传进来的参数
        double timeBase = 25569.3333333333; //1970-01-01 08:00:00.0;
        double dd = timePara - timeBase;
        dd = dd * 24 * 60 * 60 * 1000;
        DecimalFormat nf = new DecimalFormat("#0");
        String myStr = nf.format(dd);
        ll = Long.parseLong(myStr);
        Timestamp ts = new Timestamp(ll);
        return ts;
    }

    //得到百分比数据,返回字符串
    public String getPercentNumber(String s) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        double d = Double.parseDouble(s);
        String dd = nf.format(d);
        return dd;
    }

    /*
      判断是否是数字字符串
     */
    public boolean isDate(String s) {
        //return s.matches("^(-?\\d+)(\\.\\d+)?$");
        try {
            java.sql.Date.valueOf(s);
            return true;
        } catch (NumberFormatException sqo) {
            return false;
        }
    }

//判断是否为整形数字
    public boolean isIntNumeric(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException sqo) {
            return false;
        }
    }

//判断是否为Double形数字
    public boolean isDoubleNumeric(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException sqo) {
            return false;
        }
    }

    /*
      判断是否是数字字符串
     */
    public boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException sqo) {
            return false;
        }

    }

    //判断一个字符串里面全是数字
    public boolean IsNumber(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否为汉字
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        byte[] b;
        int temp;
        for (int i = 0; i < str.length(); i++) {
            b = str.substring(i, i + 1).getBytes();
            temp = b[0];
            if (temp > 0) {
                return false;
            }
        }
        return true;
    }

    /*
     去掉返回的Vector中的不良数据
     */
    public Vector getReturnData(Vector vecData) {
        Vector revect = new Vector();
        for (int i = 0; i < vecData.size(); i++) {
            String[] sdata = (String[]) vecData.elementAt(i);
            //去除形如：2004-10-14 01:12:21.0的时间并转换为年月日的格式
            for (int jj = 0; jj < sdata.length; jj++) {
                if (sdata[jj] == null) {
                    sdata[jj] = "";
                } else if ((sdata[jj].length() == 21) &&
                           (sdata[jj].substring(sdata[jj].length() -
                                                2).equalsIgnoreCase(".0"))) {
                    sdata[jj] = sdata[jj].substring(0, 10);
                    String[] tmpArr = sdata[jj].split("-");
                    sdata[jj] = tmpArr[0] + "年" + tmpArr[1] + "月" + tmpArr[2] +
                                "日";
                }
            }
            //去掉空值、0、null、0.0%、等不良数据
            for (int k = 0; k < sdata.length; k++) {
                if ((sdata[k] == null) ||
                    ("0.0".equals(sdata[k])) ||
                    ("null".equals(sdata[k])) ||
                    ("0.0%".equals(sdata[k])) ||
                    ("0".equals(sdata[k]))) {
                    sdata[k] = "";
                }
                if (sdata[k].endsWith(".0")) {
                    sdata[k] = sdata[k].substring(0, sdata[k].length() - 2);
                }
                if ((sdata[k].indexOf(".") >= 0) &&
                    ((sdata[k].length() - sdata[k].indexOf(".")) > 2) &&
                    (isDoubleNumeric(sdata[k]))) {
                    sdata[k] = getdoubleString(sdata[k]);
                }
            }
            revect.add(sdata);
        }
        return revect;
    }

    /*
     去掉返回的Vector中的不良数据
     */
    public Vector getReturnEnData(Vector vecData) {
        Vector revect = new Vector();
        for (int i = 0; i < vecData.size(); i++) {
            String[] sdata = (String[]) vecData.elementAt(i); //去除形如：2004-10-14 01:12:21.0的时间并转换为年月日的格式
            for (int jj = 0; jj < sdata.length; jj++) {
                if (sdata[jj] == null) {
                    sdata[jj] = "";
                } else if ((sdata[jj].length() == 21) &&
                           (sdata[jj].substring(sdata[jj].length() -
                                                2).equalsIgnoreCase(".0"))) {
                    sdata[jj] = sdata[jj].substring(0, 10);
                }
            }
            //去掉空值、0、null、0.0%、等不良数据
            for (int k = 0; k < sdata.length; k++) {
                if ((sdata[k] == null) ||
                    ("0.0".equals(sdata[k])) ||
                    ("null".equals(sdata[k])) ||
                    ("0.0%".equals(sdata[k])) ||
                    ("0".equals(sdata[k]))) {
                    sdata[k] = "";
                }
                if (sdata[k].endsWith(".0")) {
                    sdata[k] = sdata[k].substring(0, sdata[k].length() - 2);
                }
                if ((sdata[k].indexOf(".") >= 0) &&
                    ((sdata[k].length() - sdata[k].indexOf(".")) > 2) &&
                    (isDoubleNumeric(sdata[k]))) {
                    sdata[k] = getdoubleString(sdata[k]);
                }
            }
            revect.add(sdata);
        }
        return revect;
    }

    /*
     对数据做纵列统计
     */
    public Vector getColumCountData(Vector vect) {
        double[] ddata = new double[50];
        String[] tmpArr = new String[50];
        if (vect.size() > 1) {
            ddata = new double[((String[]) vect.elementAt(0)).length];
            tmpArr = new String[((String[]) vect.elementAt(0)).length];
        }
        Vector tmpvect = new Vector();
        for (int i = 0; i < vect.size(); i++) {
            String[] sdata = (String[]) vect.elementAt(i);
            for (int j = 0; j < sdata.length; j++) {
                if ((sdata[j] != null) && (isDoubleNumeric(sdata[j]))) {
                    ddata[j] += Double.parseDouble(sdata[j]);
                }
            }
            for (int j = 0; j < ddata.length; j++) {
                tmpArr[j] = String.valueOf(ddata[j]);
            }
            tmpArr[0] = "合计";
            tmpvect.add(sdata);
        }
        tmpvect.add(tmpArr);
        tmpvect = getReturnData(tmpvect);
        return tmpvect;
    }

    /*
     去掉Vector(String[])中的空格或null值
     */
    public static Vector removeNull(Vector vect) {
        int i = 0;
        Vector tmpvect = new Vector();
        for (i = 0; i < vect.size(); i++) {
            String[] tmpStr = (String[]) vect.elementAt(i);
            for (int j = 0; j < tmpStr.length; j++) {
                if ((tmpStr[j] == null) || (tmpStr[j].equals("null"))) {
                    tmpStr[j] = "";
                }
            }
            tmpvect.add(tmpStr);
        }
        return vect;
    }

    /**
     * 获得当前时间
     * @param parrten 输出的时间格式
     * @return 返回时间
     */
    public static String getTime(String parrten) {
        String timestr;
        if (parrten == null || parrten.equals("")) {
            parrten = "yyyyMMddHHmmss";
        }
        java.text.SimpleDateFormat sdf = new SimpleDateFormat(parrten);
        java.util.Date cday = new Date();
        timestr = sdf.format(cday);
        return timestr;
    }

//取得设定时间的前一小时
    public static java.sql.Timestamp getPreHour(java.sql.Timestamp stamp) {
        long totalstamp = stamp.getTime();
        totalstamp -= 3600000;
        java.sql.Timestamp nextstamp = new java.sql.Timestamp(totalstamp);
        return nextstamp;
    }

//取得设定时间的后一小时
    public static java.sql.Timestamp getNextHour(java.sql.Timestamp stamp) {
        long totalstamp = stamp.getTime();
        totalstamp += 3600000;
        java.sql.Timestamp nextstamp = new java.sql.Timestamp(totalstamp);
        return nextstamp;
    }

//取得设定时间的前一天
    public static java.sql.Timestamp getPreDate(java.sql.Timestamp stamp) {
        long totalstamp = stamp.getTime();
        totalstamp -= 86400000;
        java.sql.Timestamp nextstamp = new java.sql.Timestamp(totalstamp);
        return nextstamp;
    }

//取得设定时间的后一天
    public static java.sql.Timestamp getNextDate(java.sql.Timestamp stamp) {
        long totalstamp = stamp.getTime();
        totalstamp += 86400000;
        java.sql.Timestamp nextstamp = new java.sql.Timestamp(totalstamp);
        return nextstamp;
    }

    /**
     * 比较两个字符串时间的大小
     * @param t1 时间1
     * @param t2 时间2
     * @param parrten 时间格式 :yyyy-MM-dd
     * @return 返回long =0相等，>0 t1>t2，<0 t1<t2
     */
    public static long compareStringTime(String t1, String t2, String parrten) {
        SimpleDateFormat formatter = new SimpleDateFormat(parrten);
        ParsePosition pos = new ParsePosition(0);
        ParsePosition pos1 = new ParsePosition(0);
        Date dt1 = formatter.parse(t1, pos);
        Date dt2 = formatter.parse(t2, pos1);
        long l = dt1.getTime() - dt2.getTime();
        return l;
    }

    public static String gettime() {
        String datestr = "";
        try {
            java.text.DateFormat df = new java.text.SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            datestr = df.format(new java.util.Date());
        } catch (Exception ex) {

        }

        return datestr;
    }

    public static String getHour(java.sql.Timestamp stamp) {
        String datestr = "";
        try {
            java.text.DateFormat df = new java.text.SimpleDateFormat("H");
            datestr = df.format(stamp);
        } catch (Exception ex) {

        }
        return datestr;
    }

    public static String getStrDay(java.sql.Timestamp stamp) {
        String datestr = "";
        try {
            java.text.DateFormat df = new java.text.SimpleDateFormat("d");
            datestr = df.format(stamp);
        } catch (Exception ex) {

        }
        return datestr;
    }

    public static String getDay() {
        String datestr = "";
        try {
            java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-M-d");
            datestr = df.format(new java.util.Date());
        } catch (Exception ex) {

        }
        return datestr;
    }

    public static String getWeek(java.sql.Timestamp stamp) {
        String datestr = "";
        try {
            java.text.DateFormat df = new java.text.SimpleDateFormat("E");
            datestr = df.format(stamp);
        } catch (Exception ex) {

        }
        return datestr;
    }

    public  static String getStrMonth() {
        String datestr = "";
        try {
            java.text.DateFormat df = new java.text.SimpleDateFormat("M");
            datestr = df.format(new java.util.Date());
        } catch (Exception ex) {

        }
        return datestr;
    }

    public  static String getMonth() {
        String datestr = "";
        try {
            java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-M");
            datestr = df.format(new java.util.Date());
        } catch (Exception ex) {

        }

        return datestr;
    }

    public  static  Date GetNowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date currentTime_1 = new java.util.Date();
        String dateString = formatter.format(currentTime_1);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    //返回格式：2004-05-14 14:10:32
    public  static String GetStringDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date currentTime_1 = new java.util.Date();
        String dateString = formatter.format(currentTime_1);
        return dateString;
    }

    //返回格式：2004-05-14
    public  static  String getDayDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date currentTime_1 = new java.util.Date();
        String dateString = formatter.format(currentTime_1);
        return dateString;
    }

    public String getNowWeek() {
        String Targetstr = GetStringDate();
        String tempStrI[] =
                new String[3];
        tempStrI[0] = Targetstr.substring(0, 4);
        tempStrI[1] = Targetstr.substring(5, 7);
        tempStrI[2] = Targetstr.substring(8, 10);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(tempStrI[0]) - 1900,
                Integer.parseInt(tempStrI[1]),
                Integer.parseInt(tempStrI[2]));
        int NowWeek = cal.get(Calendar.WEEK_OF_MONTH);
        cal.add(Calendar.DAY_OF_YEAR, -NowWeek);
        int stryear = cal.get(Calendar.YEAR);
        int strmonth = cal.get(Calendar.MONTH);
        int strday = cal.get(Calendar.DAY_OF_MONTH);
        return new String("" + (stryear + 1900) + "-" + strmonth + "-" + strday);
    }

//当前时间之前的多少天的日期 LastNum:指要偏移的天数
    public static  String getLastNumWeek(int LastNum) {
        String Targetstr = GetStringDate();
        String tempStrI[] = new String[3];
        tempStrI[0] = Targetstr.substring(0, 4);
        tempStrI[1] = Targetstr.substring(5, 7);
        tempStrI[2] = Targetstr.substring(8, 10);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(tempStrI[0]) - 1900,
                Integer.parseInt(tempStrI[1]),
                Integer.parseInt(tempStrI[2]));
        int NowWeek = cal.get(Calendar.WEEK_OF_MONTH);
        cal.add(Calendar.WEEK_OF_MONTH, -LastNum);
        cal.add(Calendar.DAY_OF_YEAR, -NowWeek);
        int stryear = cal.get(Calendar.YEAR);
        int strmonth = cal.get(Calendar.MONTH);
        int strday = cal.get(Calendar.DAY_OF_MONTH);
        return new String("" + (stryear + 1900) + "-" + strmonth + "-" + strday);
    }

    public static  String getNowDay() {
        String Targetstr = GetStringDate();
        String tempStrI[] = new String[3];
        tempStrI[0] = Targetstr.substring(0, 4);
        tempStrI[1] = Targetstr.substring(5, 7);
        tempStrI[2] = Targetstr.substring(8, 10);
        return new String("" + tempStrI[0] + "-" + tempStrI[1] + "-" +
                          tempStrI[2]);
    }

//当前时间之后的多少天的日期 LastNum:指要偏移的天数
    public static  String getLastNumDay(int LastNum) {
        String Targetstr = GetStringDate();
        String tempStrI[] = new String[3];
        tempStrI[0] = Targetstr.substring(0, 4);
        tempStrI[1] = Targetstr.substring(5, 7);
        tempStrI[2] = Targetstr.substring(8, 10);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(tempStrI[0]) - 1900,
                Integer.parseInt(tempStrI[1]),
                Integer.parseInt(tempStrI[2]));
        cal.add(Calendar.DAY_OF_YEAR, -LastNum);
        int stryear = cal.get(Calendar.YEAR);
        int strmonth = cal.get(Calendar.MONTH);
        int strday = cal.get(Calendar.DAY_OF_MONTH);
        return new String("" + (stryear + 1900) + "-" + strmonth + "-" + strday);
    }

    public static  String getNowMonth() {
        String Targetstr = GetStringDate();
        String tempStrI[] = new String[3];
        tempStrI[0] = Targetstr.substring(0, 4);
        tempStrI[1] = Targetstr.substring(5, 7);
        tempStrI[2] = Targetstr.substring(8, 10);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(
                tempStrI[0]) - 1900, Integer.parseInt(tempStrI[1]),
                Integer.parseInt(tempStrI[2]));
        int stryear = cal.get(Calendar.YEAR);
        int strmonth = cal.get(Calendar.MONTH);
        return new String("" + (stryear + 1900) + "-" + strmonth + "-" + 1);
    }

    public static  String getLastNumMonth(int LastNum) {
        String Targetstr = GetStringDate();
        String tempStrI[] = new String[3];
        tempStrI[0] = Targetstr.substring(0, 4);
        tempStrI[1] = Targetstr.substring(5, 7);
        tempStrI[2] = Targetstr.substring(8, 10);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(tempStrI[0]) - 1900,
                Integer.parseInt(tempStrI[1]),
                Integer.parseInt(tempStrI[2]));
        cal.add(Calendar.MONTH, -1);
        int stryear = cal.get(Calendar.YEAR);
        int strmonth = cal.get(Calendar.MONTH);
        int strday = cal.get(Calendar.DAY_OF_MONTH);
        return new String("" + (stryear + 1900) + "-" + strmonth + "-" + 1);
    }

    public static  String getNowYear() {
        String Targetstr = GetStringDate();
        String tempStrI[] = new String[3];
        tempStrI[0] = Targetstr.substring(0, 4);
        tempStrI[1] = Targetstr.substring(5, 7);
        tempStrI[2] = Targetstr.substring(8, 10);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(tempStrI[0]) - 1900,
                Integer.parseInt(tempStrI[1]),
                Integer.parseInt(tempStrI[2]));
        int stryear = cal.get(Calendar.YEAR);
        return new String("" + (stryear + 1900) + "-" + 1 + "-" + 1);
    }

    public static String getNowYear(int LastNum) {
        String Targetstr = GetStringDate();
        String tempStrI[] = new String[3];
        tempStrI[0] = Targetstr.substring(0, 4);
        tempStrI[1] = Targetstr.substring(5, 7);
        tempStrI[2] = Targetstr.substring(8, 10);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(tempStrI[0]) - 1900,
                Integer.parseInt(tempStrI[1]),
                Integer.parseInt
                (tempStrI[2]));
        cal.add(Calendar.YEAR, -1);
        int stryear = cal.get(Calendar.YEAR);
        return new String("" + (stryear + 1900) + "-" + 1 + "-" + 1);
    }

    public String ChangeDate(String Targetstr, String year, String month,
                             String day) {
        String tempStrI[] = new String[3];
        tempStrI[0] = Targetstr.substring(0, 4);
        tempStrI[1] = Targetstr.substring(5, 7);
        tempStrI[2] = Targetstr.substring(8, 10);
        Calendar cal = Calendar.getInstance();
        int temp = 0;
        cal.set(Integer.parseInt(tempStrI[0]) - 1900,
                Integer.parseInt(tempStrI[1]),
                Integer.parseInt(tempStrI[2]));
        if ((temp = year.indexOf('-')) > 0) {
            year = year.substring(temp);
        }
        if ((temp = month.indexOf('-')) > 0) {
            month = month.substring(temp);
        }
        if ((temp = day.indexOf('-')) > 0) {
            day = day.substring(temp);
        }
        cal.add(Calendar.YEAR, Integer.parseInt(year));
        cal.add(Calendar
                .MONTH, Integer.parseInt(month));
        cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        int stryear = cal.get(Calendar.YEAR);
        int strmonth = cal.get(Calendar.MONTH);
        int strday = cal.get(Calendar.DAY_OF_MONTH);
        stryear += 1900;
        return new String("" + stryear + "-" + strmonth + "-" + strday);
    }

    //计算两日期之间有多少天
    public static long getTotalDays(java.sql.Date staDate,
                                    java.sql.Date endDate) {
        long totalDays = endDate.getTime();
        totalDays -= staDate.getTime();
        totalDays /= 86400000; ////86400000 = 1024*84375;
        return (totalDays + 1);
    }

    //计算下一天的同一时间
    public static java.sql.Date getNextDay(java.sql.Date today) {
        long totalDays = today.getTime();
        totalDays += 86400000;
        java.sql.Date nextDay = new java.sql.Date(totalDays);
        return nextDay;
    }

    //计算下一月的同一时间
    public static java.sql.Date getNextMonth(java.sql.Date today) {
        long totalDays = today.getTime();
        totalDays += 86400000*30.5;
        java.sql.Date nextMonth = new java.sql.Date(totalDays);
        return nextMonth;
    }


    //计算两日期字符串的相差天数
    public String CompareDate(String SourceDate, String TargetDate) {
        String newSource = "";
        int tempI = 0;
        int tempII = 0;
        String newTarget = "";
        tempI = SourceDate.indexOf(' ');
        tempII = TargetDate.indexOf(' ');
        if (tempI > 0 && tempII > 0) {
            newSource = SourceDate.substring(0, tempI);
            newTarget = TargetDate.substring(0, tempII);
            newSource = newSource.trim();
            newTarget = newTarget.trim();
        } else {
            newSource = SourceDate;
            newTarget = TargetDate;
        }
        if (newSource.equals("") || newTarget.equals("")) {
            return null;
        } else {
            String tempStrI[] = new String[3];
            String tempStrII[] = new String[3];
            int tempIII = 0;
            int tempIIII = 0;
            int temp = 0;
            tempStrI[0] = newSource.substring(0, 4);
            tempStrI[1] = newSource.substring(5, 7);
            tempStrI[2] = newSource.substring(8, 10);
            tempStrII[0] = newTarget.substring(0, 4);
            tempStrII[1] = newTarget.substring(5, 7);
            tempStrII[2] = newTarget.substring(8, 10);
            tempIII = Integer.parseInt(tempStrI[0]);
            tempIIII = Integer.parseInt(tempStrII[0]);
            if (tempIII != tempIIII) {
                temp = tempIII - tempIII;
                temp = temp * 360;
            }
            tempIII = Integer.parseInt(tempStrI[1]);
            tempIIII = Integer.parseInt(tempStrII[1]);
            if (tempIII != tempIIII) {
                temp = temp + (tempIII - tempIIII) * 30;
            }
            tempIII = Integer.parseInt(tempStrI[2]);
            tempIIII = Integer
                       .parseInt(tempStrII[2]);
            if (tempIII != tempIIII) {
                temp = temp + (tempIII - tempIIII);
            }
            if (temp < 0) {
                temp = 0 - temp;
                return new String("" + temp);
            } else {
                return new String("" + temp);
            }
        }
    }

    /**
       /**
      * @param Calendar
      * @return String 2001/12/13 Format
      */
     public static String CalendarToStr(Calendar cal) {
         SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
         if (cal != null) {
             Date date = cal.getTime();
             return format.format(date);
         } else {
             return "";
         }
     }

    /**
     * @param Calendar
     * @return a Sunday Calendar in the Week
     */
    public static Calendar starCalOfWeek(Calendar day) {
        int temp = day.get(Calendar.DAY_OF_WEEK);
        switch (temp) {
        case 1:
            return day;
        case 2:
            day.add(Calendar.DATE, -1);
            return day;
        case 3:
            day.add(Calendar.DATE, -2);
            return day;
        case 4:
            day.add(Calendar.DATE, -3);
            return day;
        case 5:
            day.add(Calendar.DATE, -4);
            return day;
        case 6:
            day.add(Calendar.DATE, -5);
            return day;
        case 7:
            day.add(Calendar.DATE, -6);
            return day;
        default:
            return day;
        }
    }

    /**
     * @param Calendar
     * @return a Satday Calendar in the Week
     */
    public static Calendar endCalOfWeek(Calendar day) {
        int temp = day.get(Calendar.DAY_OF_WEEK);
        switch (temp) {
        case 1:
            day.add(Calendar.DATE, 6);
            return day;
        case 2:
            day.add(Calendar.DATE, 5);
            return day;
        case 3:
            day.add(Calendar.DATE, 4);
            return day;
        case 4:
            day.add(Calendar.DATE, 3);
            return day;
        case 5:
            day.add(Calendar.DATE, 2);
            return day;
        case 6:
            day.add(Calendar.DATE, 1);
            return day;
        case 7:
            return day;
        default:
            return day;
        }
    }

    public static Date StrToDate(String str) {
        if (str.length() == 0) {
            return null;
        }
        int start = str.indexOf('/');
        String year = str.substring(0, start);
        start++;
        int start1 = str.indexOf('/', start);
        String month = str.substring(start, start1);
        String day = str.substring(start1 + 1);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        cal.set(Calendar.DATE, Integer.parseInt(day));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println(sdf.format(cal.getTime()));
        return cal.getTime();
    }

    public static Calendar StrToCalendar(String str) {
        if (str.length() == 0) {
            return null;
        }
        int start = str.indexOf('/');
        String year = str.substring(0, start);
        start++;
        int start1 = str.indexOf('/', start);
        String month = str.substring(start, start1);
        String day = str.substring(start1 + 1);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        cal.set(Calendar.DATE, Integer.parseInt(day));
        return cal;
    }

    /**
     * @param Calendar
     * @return String Sun Mon etc. Format
     */
    public static String dayOfWeek(Calendar day) {
        int temp = day.get(Calendar.DAY_OF_WEEK);
        switch (temp) {
        case 1:
            return "Sun";
        case 2:
            return "Mon";
        case 3:
            return "Tue";
        case 4:
            return "Wed";
        case 5:
            return "Thu";
        case 6:
            return "Fri";
        case 7:
            return "Sat";
        default:
            return "";
        }
    }

    /**
     * @param String Date Format 2001/12/13
     * @return String Sun Mon etc. Format
     */
    public static String dayOfWeek(String inDay) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(StrToDate(inDay));
        int temp = cal.get(Calendar.DAY_OF_WEEK);
        switch (temp) {
        case 1:
            return "Sun";
        case 2:
            return "Mon";
        case 3:
            return "Tue";
        case 4:
            return "Wed";
        case 5:
            return "Thu";
        case 6:
            return "Fri";
        case 7:
            return "Sat";
        default:
            return "";
        }
    }

    public static Timestamp StrToTimestamp(String timestampStr, String pattern) throws
            ParseException {
        java.util.Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            date = format.parse(timestampStr);
        } catch (ParseException e) {
            throw e;
        }
        return date == null ? null : new Timestamp(date.getTime());
    }

    public static String StringToTimestamp(String timestampStr, String pattern) {
        String date = "";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        date = df.format(timestampStr);
        return date;
    }

    public static String iso2gbk(String isostr) {
        /*
                 try {
            byte[] byteStr = isostr.getBytes("ISO8859-1");
            String gbkStr = new String(byteStr, GBK);
            return gbkStr;
                 } catch (Exception e) {
            return null;
                 }
         */
        return isostr;
    }

    public static String UTF2gbk(String isostr) {
        try {
            byte[] byteStr = isostr.getBytes(UTF_8);
            String gbkStr = new String(byteStr, GBK);
            return gbkStr;
        } catch (Exception e) {
            return null;
        }
    }

    public static String gbk2iso(String gbkstr) {
        try {
            byte[] byteStr = gbkstr.getBytes(GBK);
            String isoStr = new String(byteStr, "ISO8859-1");
            return isoStr;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 做编码的转换，转换成GBK。
     * @param str  需要做转换的字符串
     * @return  String   返回转换后的字符串
     */
    public static String toGBK(String str) {
        String temp = str;
        try {
            if ((temp == null) || (temp.equals("null"))) {
                temp = "";
            }
            byte[] b = temp.getBytes("ISO8859-1"); //按字节将串拆分
            str = new String(b, GBK); //按GBK编码方式将字节组合
        } catch (Exception ex) {
        }
        return str;
    }

    /**
     * 做编码的转换，转换成GB2312。
     * @param str  需要做转换的字符串
     * @return  String   返回转换后的字符串
     */
    public static String toGB2312(String str) {
        String temp = str;
        try {
            if ((temp == null) || (temp.equals("null"))) {
                temp = "";
            }
            byte[] b = temp.getBytes("ISO8859-1"); //按字节将串拆分
            str = new String(b, "GB2312"); //按GB2312编码方式将字节组合
        } catch (Exception ex) {
        }
        return str;
    }
    /**
     * 做编码的转换，转换成GB2312。
     * @param str  需要做转换的字符串
     * @return  String   返回转换后的字符串
     */
    public static String toGB2312(String sourceFormat,String str) {
        String temp = str;
        try {
            if ((temp == null) || (temp.equals("null"))) {
                temp = "";
            }
            byte[] b = temp.getBytes(sourceFormat); //按字节将串拆分
            str = new String(b, "GB2312"); //按GB2312编码方式将字节组合
        } catch (Exception ex) {
        }
        return str;
    }

    /**
     * 做编码的转换，转换成ISO8859-1。
     * @param str  需要做转换的字符串
     * @return  String   返回转换后的字符串
     */
    public static String toISO(String str) {
        String temp = str;
        try {
            if ((temp == null) || (temp.equals("null"))) {
                temp = "";
            }
            byte[] b = temp.getBytes(GBK); //按双字节将串拆分
            str = new String(b, "ISO8859-1"); //按ISO8859-1编码方式将字节组合
        } catch (Exception ex) {
        }
        return str;
    }

    /**
     * 做编码的转换，转换成Utf8。
     * @param str  需要做转换的字符串
     * @return  String   返回转换后的字符串
     */
    public static String toUtf8(String str) {
        byte[] b = str.getBytes();
        char[] c = new char[b.length];
        for (int i = 0; i < b.length; i++) {
            c[i] = (char) (b[i] & 0x00FF);
        }
        return new String(c);
    }

    public static String defaultToUtf8(String str) {
        try {
            if ((str == null) || (str.equals("null"))) {
                str = "";
            }
            str = new String(str.getBytes(), UTF_8);
        } catch (Exception ex) {
        }
        return str;
    }

    /**
     * 做编码的转换，转换成Utf8。
     * @param str  需要做转换的字符串
     * @return  String   返回转换后的字符串
     */
    public static String GBK2Utf8(String str) {
        String temp = str;
        try {
            if ((temp == null) || (temp.equals("null"))) {
                temp = "";
            }
            byte[] b = temp.getBytes(GBK); //按双字节将串拆分
            str = new String(b, UTF_8); //按ISO8859-1编码方式将字节组合
        } catch (Exception ex) {
        }
        return str;
    }

    public static String ISO2Utf8(String str) {
        String temp = str;
        try {
            if ((temp == null) || (temp.equals("null"))) {
                temp = "";
            }
            byte[] b = temp.getBytes("ISO8859-1"); //按双字节将串拆分
            str = new String(b, UTF_8); //按ISO8859-1编码方式将字节组合
        } catch (Exception ex) {
        }
        return str;
    }

    public static String Utf2ISO(String str) {
        String temp = str;
        try {
            if ((temp == null) || (temp.equals("null"))) {
                temp = "";
            }
            byte[] b = temp.getBytes(UTF_8); //按双字节将串拆分
            str = new String(b, "ISO-8859-1"); //按ISO8859-1编码方式将字节组合
        } catch (Exception ex) {
        }
        return str;
    }

    public static String encoding(String str) {
        try {
            str = new String(str.getBytes("ISO-8859-1"), "MS950");
        } catch (UnsupportedEncodingException uee) {
            System.out.println("UnsupportedEncodingException：" + uee.getMessage());
        }
        return str;
    }

    public String ex_chinese(String str) {
        if (str == null) {
            str = "";
        } else {
            try {
                str = new String(str.getBytes("iso-8859-1"), "gb2312");
            } catch (Exception ex) {
            }
        }
        return str;
    }

    //html代码屏蔽
    public static String Htmlfilter(String str) {
        if (str == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        int n = str.length();
        for (int i = 0; i < n; i++) {
            char c = str.charAt(i);
            switch (c) {
            case ' ':
                sb.append("&nbsp;");
                break;
            case '\r':
                sb.append("<br/>");
                break;
            case '\'':
                sb.append("'");
                break;

            case '<':
                sb.append("<");
                break;
            case '>':
                sb.append(">");
                break;
            case '&':
                sb.append("&");
                break;
            case '"':
                sb.append("");
                break;
            default:
                sb.append(c);
            }
        }
        return sb.toString();
    }

    //按字节位截取（即一个汉字算两个字节）,参数说明：instr--原串 byteNum--要取得的字节位数
    public static String InterceptStringByByte(String instr, int byteNum) {
        byte bt[] = instr.getBytes();
        //System.out.println("Length of this String ===>" + bt.length);
        String substrx = "";
        if(bt.length<byteNum){
            return instr + "...";
        }
        if (byteNum > 1) {
            if (bt[byteNum] < 0) {
                substrx = new String(bt, 0, --byteNum);
            } else {
                substrx = new String(bt, 0, byteNum);
            }
        } else {
            if (byteNum == 1) {
                if (bt[byteNum] < 0) {
                    substrx = new String(bt, 0, ++byteNum);
                } else {
                    substrx = new String(bt, 0, byteNum);
                }
            } else {
                System.out.println("输入错误！！！请输入大于零的整数：");
            }
        }
        return substrx + "...";
    }

    //按字节位截取（即一个汉字算一个字节）,参数说明：instr--原串 byteNum--要取得的字节位数
    public static String InterceptStringByChar(String instr, int charNum) {
        if ((instr != null) && (instr.length() > charNum)) {
            return instr.substring(0, charNum) + "...";
        } else {
            return instr;
        }
    }

    /**
     * 取字符串的前toCount个字符
     *
     * @param str
     * @param toCount
     * @return
     */
    public static String substring(String str, int toCount) {
        int reInt = 0;
        String reStr = "";
        if (str == null) {
            return "";
        }
        char[] tempChar = str.toCharArray();
        for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
            String s1 = str.valueOf(tempChar[kk]);
            byte[] b = s1.getBytes();
            reInt += b.length;
            reStr += tempChar[kk];
        }
        if (toCount == reInt || (toCount == reInt - 1)) {
            reStr += "..";
        }
        return reStr;
    }
    //精确截取字符串(补一个字节截取 即“中国人”取三个字节则是“中国”)
    public static String getLimitString(String src, int len) {

        StringBuilder sb = new StringBuilder();
        char[] c = src.toCharArray();
        for (int x = 0; x < c.length; x++) {
            sb.append(c[x]);
            if (sb.toString().getBytes().length > len) {
                return sb.substring(0, x) + "..";
            }
        }
        return src;
    }
    //精确截取字符串(减一个字节截取 即“中国人”取三个字节则是“中”)
    public static String getMinLimitString(String src, int len) {
        StringBuilder sb = new StringBuilder();
        char[] c = src.toCharArray();
        for (int x = 0; x < c.length; x++) {
            sb.append(c[x]);
            if (sb.toString().getBytes().length > len)
                return sb.substring(0, x)+"..";
        }
        return src;
    }

    /**
     * 字符串按字节截取
     * @param str 原字符
     * @param len 截取长度
     * @return String
     * @author kinglong
     * @since 2006.07.20
     */
    public static String splitString(String str, int len) {
        return splitString(str, len, "...");
    }

    /**
     * 字符串按字节截取
     * @param str 原字符
     * @param len 截取长度
     * @param elide 省略符
     * @return String
     * @author kinglong
     * @since 2006.07.20
     */
    public static String splitString(String str, int len, String elide) {
        if (str == null) {
            return "";
        }
        byte[] strByte = str.getBytes();
        int strLen = strByte.length;
        int elideLen = (elide.trim().length() == 0) ? 0 :
                       elide.getBytes().length;
        if (len >= strLen || len < 1) {
            return str;
        }
        if (len - elideLen > 0) {
            len = len - elideLen;
        }
        int count = 0;
        for (int i = 0; i < len; i++) {
            int value = (int) strByte[i];
            if (value < 0) {
                count++;
            }
        }
        if (count % 2 != 0) {
            len = (len == 1) ? len + 1 : len - 1;
        }
        return new String(strByte, 0, len) + elide.trim();
    }
    //将数字转换为汉字
    public static String getChineseNum(int i) { //只处理到万位
        String sValue = new String("");
        int iFir = 0;
        int iValue = 0;
        int i0 = 0;

        if (i > 9999) {
            sValue = "超过万元数字无效";
        } else {
            iFir = i;
            iValue = iFir / 1000;
            if (iValue > 0) {
                sValue = sValue + getChineseValue(iValue) + "仟";
            }
            iFir = iFir - iValue * 1000;
            iValue = iFir / 100;
            if (iValue > 0) {
                sValue = sValue + getChineseValue(iValue) + "佰";
            } else if (iValue == 0) {
                if ((sValue.compareTo("") != 0) && (iFir != 0)) {
                    sValue = sValue + "零";
                    i0++;
                }
            }
            iFir = iFir - iValue * 100;
            iValue = iFir / 10;
            if (iValue > 0) {
                sValue = sValue + getChineseValue(iValue) + "拾";
            } else if (iValue == 0) {
                if ((sValue.compareTo("") != 0) && (iFir != 0) && (i0 == 0)) {
                    sValue = sValue + "零";
                }
            }
            iFir = iFir - iValue * 10;
            if (iFir > 0) {
                sValue = sValue + getChineseValue(iFir);
            }
        }
        sValue = sValue + "圆整";
        return sValue;
    }

    public static String getChineseValue(int i) {
        String s = new String("");
        switch (i) {
        case 0:
            s = "零";
            break;
        case 1:
            s = "壹";
            break;
        case 2:
            s = "贰";
            break;
        case 3:
            s = "叁";
            break;
        case 4:
            s = "肆";
            break;
        case 5:
            s = "伍";
            break;
        case 6:
            s = "陆";
            break;
        case 7:
            s = "柒";
            break;
        case 8:
            s = "捌";
            break;
        case 9:
            s = "玖";
            break;
        }
        return s;
    }

    public static String toRMB(double value) {
        char[] hunit = {'拾', '佰', '仟'}; //段内位置表示
        char[] vunit = {'万', '亿'}; //段名表示
        char[] digit = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'}; //数字表示
        long midVal = (long) (value * 100); //转化成整形
        String valStr = String.valueOf(midVal); //转化成字符串
        String head = valStr.substring(0, valStr.length() - 2); //取整数部分
        String rail = valStr.substring(valStr.length() - 2); //取小数部分

        String prefix = ""; //整数部分转化的结果
        String suffix = ""; //小数部分转化的结果
        //处理小数点后面的数
        if (rail.equals("00")) { //如果小数部分为0
            suffix = "整";
        } else {
            suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) -
                     '0'] + "分"; //否则把角分转化出来
        }
        //处理小数点前面的数
        char[] chDig = head.toCharArray(); //把整数部分转化成字符数组
        char zero = '0'; //标志'0'表示出现过0
        byte zeroSerNum = 0; //连续出现0的次数
        for (int i = 0; i < chDig.length; i++) { //循环处理每个数字
            int idx = (chDig.length - i - 1) % 4; //取段内位置
            int vidx = (chDig.length - i - 1) / 4; //取段位置
            if (chDig[i] == '0') { //如果当前字符是0
                zeroSerNum++; //连续0次数递增
                if (zero == '0') { //标志
                    zero = digit[0];
                } else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
                    prefix += vunit[vidx - 1];
                    zero = '0';
                }
                continue;
            }
            zeroSerNum = 0; //连续0次数清零
            if (zero != '0') { //如果标志不为0,则加上,例如万,亿什么的
                prefix += zero;
                zero = '0';
            }
            prefix += digit[chDig[i] - '0']; //转化该数字表示
            if (idx > 0) {
                prefix += hunit[idx - 1];
            }
            if (idx == 0 && vidx > 0) {
                prefix += vunit[vidx - 1]; //段结束位置应该加上段名如万,亿
            }
        }

        if (prefix.length() > 0) {
            prefix += '圆'; //如果整数部分存在,则有圆的字样
        }
        return prefix + suffix; //返回正确表示
    }

    public String getOracleInString(String[] Arr) {
        String reStr = "";
        if (Arr == null) {
            return "";
        }
        for (int i = 0; i < Arr.length; i++) {
            reStr += "'" + Arr[i] + "',";
        }
        if (reStr.length() > 0) {
            reStr = reStr.substring(0, reStr.length() - 1);
        }
        return reStr;
    }

    //整数到字节数组的转换
    public static byte[] intToByte(int number) {
        int temp = number;
        byte[] b = new byte[4];
        for (int i = b.length - 1; i > -1; i--) {
            b[i] = new Integer(temp & 0xff).byteValue(); //将最高位保存在最低位
            temp = temp >> 8; //向右移8位
        }
        return b;
    }

    //字节数组到整数的转换
    public static int byteToInt(byte[] b) {
        int s = 0;
        for (int i = 0; i < 3; i++) {
            if (b[i] >= 0) {
                s = s + b[i];
            } else {

                s = s + 256 + b[i];
            }
            s = s * 256;
        }
        if (b[3] >= 0) { //最后一个之所以不乘，是因为可能会溢出
            s = s + b[3];
        } else {
            s = s + 256 + b[3];
        }
        return s;
    }

    //字符到字节转换
    public static byte[] charToByte(char ch) {
        int temp = (int) ch;
        byte[] b = new byte[2];
        for (int i = b.length - 1; i > -1; i--) {
            b[i] = new Integer(temp & 0xff).byteValue(); //将最高位保存在最低位
            temp = temp >> 8; //向右移8位
        }
        return b;
    }

    //字节到字符转换

    public static char byteToChar(byte[] b) {
        int s = 0;
        if (b[0] > 0) {
            s += b[0];
        } else {
            s += 256 + b[0];
        }
        s *= 256;
        if (b[1] > 0) {
            s += b[1];
        } else {
            s += 256 + b[1];
        }
        char ch = (char) s;
        return ch;
    }

    //浮点到字节转换
    public static byte[] doubleToByte(double d) {
        byte[] b = new byte[8];
        long l = Double.doubleToLongBits(d);
        for (int i = 0; i < b.length; i++) {
            b[i] = new Long(l).byteValue();
            l = l >> 8;

        }
        return b;
    }

    //字节到浮点转换
    public static double byteToDouble(byte[] b) {
        long l;

        l = b[0];
        l &= 0xff;
        l |= ((long) b[1] << 8);
        l &= 0xffff;
        l |= ((long) b[2] << 16);
        l &= 0xffffff;
        l |= ((long) b[3] << 24);
        l &= 0xffffffffl;
        l |= ((long) b[4] << 32);
        l &= 0xffffffffffl;

        l |= ((long) b[5] << 40);
        l &= 0xffffffffffffl;
        l |= ((long) b[6] << 48);

        l |= ((long) b[7] << 56);
        return Double.longBitsToDouble(l);
    }

    public static String floatToStr2Deci(float value){
    	DecimalFormat fnum = new DecimalFormat("##0.00"); 
    	String dd=fnum.format(value); 
    	return dd;
    }
    public static String doubleToStr2Deci(double value){
    	DecimalFormat fnum = new DecimalFormat("##0.00"); 
    	String dd=fnum.format(value); 
    	return dd;
    }
    
    /**
     * @Title:tructZeroBefore
     * @Description:去掉数字前面的0 如000015800060.0
     * @param str
     * @return
     * @throws
     */
    public static String tructZeroBefore(String str){
    	if(StringUtils.isNotBlank(str)){
    		return str.replaceFirst("^0*", "");
    	}
    	return null;
    }
    public static void main(String[] args) {
     // compareCalendar("2007-03-01 02:03:04","2007-03-02 02:03:04");
      
      //System.out.println(","+getAppointedCurrencyFormat("600.23",10, 0)+",");
      
      try {
    	/*  String str="00000122      12                            1周强      492625894561265870 00000070000000111111111                                                                                           ";
         System.out.println(str.getBytes(GBK).length);*/
    	 //System.out.println(rightAppendBlank(str, 10,GBK)+",");
    	 String str = FormatUtil.rightAppendBlank("E07", 5);
    	 System.err.println("str "+str);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
}
