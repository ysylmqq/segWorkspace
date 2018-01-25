package com.hm.bigdata.util;
/**
 * @Package:com.chinagps.fee.util
 * @ClassName:MoneyUtil
 * @Description:将阿拉伯数字转换成人民币大写形式的JAVA工具类
 * @author:zfy
 * @date:2014-6-6 下午3:07:32
 */
public class MoneyUtil {
		private final static String[] CN_Digits = { "零", "壹", "貳", "叁", "肆", "伍",
				"陆", "柒", "捌", "玖", };

		/**
		 * 将数字型货币转换为中文型货币 <br/>
		 * 作者：wallimn　时间：2009-4-10　下午09:59:26<br/>
		 * 博客：http://blog.csdn.net/wallimn<br/>
		 * 参数：<br/>
		 * 
		 * @param moneyValue
		 *            　字符串形式的金额，小数部分，将多于3位部分舍去，不做四舍五入
		 * @return
		 */
		public static String CNValueOf(String moneyValue) {
			//使用正则表达式，去除前面的零及数字中的逗号
			String value = moneyValue.replaceFirst("^0+", "");
			value = value.replaceAll(",", "");
			//分割小数部分与整数部分
			int dot_pos = value.indexOf('.');
			String int_value;
			String fraction_value;
			if (dot_pos == -1) {
				int_value = value;
				fraction_value = "00";
			} else {
				int_value = value.substring(0, dot_pos);
				fraction_value = value.substring(dot_pos + 1, value.length())
						+ "00".substring(0, 2);//也加两个0，便于后面统一处理
			}

			int len = int_value.length();
			if (len>16) return "值过大";
			StringBuffer cn_currency = new StringBuffer();
			String[] CN_Carry = new String[] { "", "万", "亿", "万" };
			//数字分组处理，计数组数
			int cnt = len/4+(len%4==0?0:1);
			//左边第一组的长度
			int partLen = len-(cnt-1)*4;
			String partValue=null;
			boolean bZero=false;//有过零
			String curCN=null;
			for(int i =0; i<cnt; i++){
				partValue = int_value.substring(0,partLen);
				int_value=int_value.substring(partLen);
				curCN = Part2CN(partValue,i!=0&&!"零".equals(curCN));
				//System.out.println(partValue+":"+curCN);
				//若上次为零，这次不为零，则加入零			
				if(bZero && !"零".equals(curCN)){
					cn_currency.append("零");
					bZero=false;
				}
				if("零".equals(curCN))bZero=true;
				//若数字不是零，加入中文数字及单位
				if(!"零".equals(curCN)){
					cn_currency.append(curCN);
					cn_currency.append(CN_Carry[cnt-1-i]);
				}
				//除最左边一组长度不定外，其它长度都为4
				partLen=4;
				partValue=null;
			}
			cn_currency.append("元");
			//处理小数部分
			int fv1 = Integer.parseInt(fraction_value.substring(0,1));
			int fv2 = Integer.parseInt(fraction_value.substring(1,2));
			if(fv1+fv2==0){
				cn_currency.append("整");
			}
			else{
				cn_currency.append(CN_Digits[fv1]).append("角");
				cn_currency.append(CN_Digits[fv2]).append("分");
			}
			return cn_currency.toString();
		}

		/**
		 * 将一组数字（不多于四个）转化成中文表示 <br/>
		 * 作者：wallimn　时间：2009-4-11　下午07:41:25<br/>
		 * 博客：http://wallimn.iteye.com<br/>
		 * 参数：<br/>
		 * 
		 * @param partValue 字符串形式的数字
		 * @param bInsertZero 是否在前面添加零
		 * @return
		 */
		private static String Part2CN(String partValue,boolean bInsertZero) {
			//使用正则表达式，去除前面的0
			partValue = partValue.replaceFirst("^0+", "");
			int len = partValue.length();
			if (len == 0)
				return "零";
			StringBuffer sbResult = new StringBuffer();
			int digit;
			String[] CN_Carry = new String[] { "", "拾", "佰", "仟" };
			for (int i = 0; i < len; i++) {
				digit = Integer.parseInt(partValue.substring(i, i + 1));
				if (digit != 0) {
					sbResult.append(CN_Digits[digit]);
					sbResult.append(CN_Carry[len - 1 - i]);
				} else {
					// 若不是最后一位，且下不位不为零，追加零
					if (i != len - 1
							&& Integer.parseInt(partValue.substring(i + 1, i + 2)) != 0)
						sbResult.append("零");
				}
			}
			if(bInsertZero && len!=4)sbResult.insert(0, "零");
			return sbResult.toString();
		}
		
		public static void main(String[] args) {
			System.out.println(CNValueOf("7895563.21"));
		}

}

