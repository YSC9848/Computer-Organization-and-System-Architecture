/**
 * 模拟ALU进行整数和浮点数的四则运算
 * @author 161250189_虞圣呈
 *
 */

public class ALU {

	/**
	 * 生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	public String integerRepresentation (String number, int length) {
		String answer = "";
		int Number = Integer.parseInt(number);
		int[] ans = new int[length];
		if (Number >= 0) {
			for (int i = 0; i < length; i++) {
				if (Number%2==1) {
					ans[length-i-1] = 1;
				} else {
					ans[length-i-1] = 0;
				}
				Number = Number/2;
			}			
			for (int i = 0; i < ans.length; i++) {
				answer += String.valueOf(ans[i]);
			}
		} else {
			Number = Math.abs(Number);
			for (int i = 0; i < length; i++) {
				if (Number%2==1) {
					ans[length-i-1] = 0;
				} else {
					ans[length-i-1] = 1;
				}
				Number = Number/2;
			}			
			for (int i = ans.length-1; i >= 0; i--) {
				if (ans[i]==0) {
					ans[i] = 1;
					for (int j = ans.length-1; j > i; j--) {
						ans[j] = 0;
					}
					break;
				}
			}
			for (int i = 0; i < ans.length; i++) {
				answer += String.valueOf(ans[i]);
			}
		}
		return (answer);
	}
	
	/**
	 * 生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 *要求实现一个方法，把10进制小数表示成二进制，阶码的偏移量为2^(n-1)-1
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		if (number.equals("+Inf")) {
			String result = "0";
			for (int i = 0; i < eLength; i++) {
				result += "1";
			}
			for (int i = 0; i < sLength; i++) {
				result += "0";
			}
			return result;
		} else if (number.equals("-Inf")) {
			String result = "1";
			for (int i = 0; i < eLength; i++) {
				result += "1";
			}
			for (int i = 0; i < sLength; i++) {
				result += "0";
			}
			return result;
		} else if (number.equals("+0.0") || number.equals("0")) {
			String result = "";
			for (int i = 0; i < 1 + eLength + sLength; i++) {
				result += "0";
			}
			return result;
		} else if (number.equals("NaN")) {
			String result = "0";
			for (int i = 0; i < eLength; i++) {
				result += "1";
			}
			for (int i = 0; i < sLength - 1; i++) {
				result += "0";
			}
			return result + "1";
		} else if (number.equals("-0.0")) {
			String result = "";
			for (int i = 0; i < 1 + eLength + sLength - 1; i++) {
				result += "0";
			}
			return "1" + result;
		} else {
			double num = 0;
			long dec = 0;
			String decBin = "";
			double sig = 0;
			String sigBin = "";
			int exponent = 0;
			String exponentBin = "";
			String all = "";
			String[] temp = new String[2];
			String result = "";
			String s = "";
			String test = "";
			String precision = "";
			String all2 = "";
			int standard = (int) Math.pow(2, eLength - 1);
			num = Float.parseFloat(number);

			if (number.contains(".")) {
				temp = number.split("\\.");
				dec = Long.parseLong(temp[0]);
			} else {
				dec = Integer.parseInt(number);
			}
			sig = num - dec;
			if (sig < 0) {
				sig = -sig;
			}
			if (dec < 0) {
				dec = -dec;
			}
			decBin = integerRepresentation(dec + "", standard);
			while (sig > 0) {
				sig *= 2;
				if (sig >= 1) {
					sigBin += "1";
					sig -= 1;
				} else {
					sigBin += "0";
				}
			}
			if (decBin.contains("1")) {
				decBin = decBin.substring(decBin.indexOf("1"));
			} else {
				decBin = "0";
			}
			all = decBin + sigBin;
			exponent = (decBin.length() - 1) - all.indexOf("1") + standard - 1;
			for (int i = all.length(); i < sLength + 2; i++) {
				all += "0";
			}
			precision = all.substring(all.indexOf("1"));
			for (int i = precision.length(); i < sLength + 2; i++) {
				precision += "0";
			}
			precision = precision.substring(0, sLength + 2);
			if (precision.length() > sLength) {
				if (precision.lastIndexOf("1") == (sLength + 1)) {

					all2 = adder("00" + precision, "0", '1', sLength + 4)
							.substring(1, sLength + 4);
					if (all2.charAt(0) == '1') {
						precision = all2.substring(0, sLength + 1);
						exponent += 1;
					} else {
						precision = all2.substring(1, sLength + 2);
					}
				}
			}

			if (exponent >= (standard * 2 - 1)) {
				result = "";
				if (num < 0) {
					result += "1";
				} else {
					result += "0";
				}
				for (int i = 0; i < eLength; i++) {
					result += "1";
				}
				for (int i = 0; i < sLength; i++) {
					result += "0";
				}

			} else if (exponent < (standard * 2 - 1) && exponent > 0) {
				result = "";
				exponentBin = integerRepresentation(exponent + "", eLength);
				if (num < 0) {
					result += "1";
				} else {
					result += "0";
				}
				result += exponentBin;
				precision = precision.substring(1);
				for (int i = precision.length(); i < sLength; i++) {
					precision += "0";
				}
				s = precision.substring(0, sLength);

				result += s;

			} else if (exponent <= 0 && (exponent >= (-(standard - 1) - sLength))) {
				result = "";
				if (num < 0) {
					result += "1";
				} else {
					result += "0";
				}
				for (int i = 0; i < eLength; i++) {
					result += "0";
				}
				for (int i = 0; i < standard - 2; i++) {
					test += "0";
				}
				assert sigBin.contains(test);
				sigBin = sigBin.substring(standard - 2);
				if (sigBin.length() > sLength) {
					sigBin = sigBin.substring(0, sLength);
				} else {
					for (int j = sigBin.length(); j < sLength; j++) {
						sigBin += "0";
					}
				}
				result += sigBin;

			} else if (exponent < (-(standard - 1) - sLength)) {
				result = "";
				if (num < 0) {
					result += "1";
				} else {
					result += "0";
				}
				for (int i = 0; i < eLength; i++) {
					result += "0";
				}
				for (int i = 0; i < sLength; i++) {
					result += "0";
				}
			}
			return result;
		}
	}
	
	/**
	 * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
	 * 例：ieee754("11.375", 32)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String ieee754 (String number, int length) {
		String result = "";
		if (length == 32) { result = floatRepresentation(number, 8,23); }
		else if (length == 64) { result = floatRepresentation(number, 11, 52); }
		return result;
	}
	
	/**
	 * 计算二进制补码表示的整数的真值。<br/>
	 * 例：integerTrueValue("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 */
	public String integerTrueValue (String operand) {
		String result = "";
		int dec = 0;
		for (int i = 0; i < operand.length(); i++) {
			if (i == 0) {
				if (operand.charAt(i) == '1') {
					dec += (-1);
				}
			} else {
				dec = dec * 2 + (operand.charAt(i) - '0');
			}
		}
		return result + dec;
	}
	
	/**
	 * 计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		String result = "";
		String all = "";
		int exponent = 0;
		String s = "";
		String sLater = "";
		int temp = 0;
		double sig = 0;
		double sigT = 0;
		String sigS = "";

		int standard = (int) (Math.pow(2, eLength - 1));
		all = operand.substring(1);
		s = all.substring(eLength);
		exponent = Integer.parseInt(integerTrueValue("0" + all.substring(0, eLength))) - standard + 1;
		if (exponent >= standard) {
			if (s.contains("1")) {
				result = "NaN";
			} else {
				if (operand.charAt(0) == '0')
					result = "+Inf";
				else
					result = "-Inf";
			}
		} else if ((exponent > -(standard - 1)) && exponent < standard) {
			s = "01" + s;
			sLater = s.substring(0, s.lastIndexOf("1") + 1);
			temp = Integer.parseInt(integerTrueValue(sLater));
			sig = temp * Math.pow(2, -(sLater.length() - 2));
			sigT = sig * Math.pow(2, exponent);
			sigS = "" + sigT;
			for (int i = sigS.length() - 1; i >= 0; i--) {
				if (sigS.charAt(i) != '0') {
					if (sigS.charAt(i) == '.') {
						result = sigS.substring(0, i);
						break;
					} else {
						result = sigS.substring(0, i + 1);
						break;
					}
				}
				if (i == 0) {
					result = sigS.substring(0, 1);
					break;
				}
			}

			if (operand.charAt(0) == '1')
				result = "-" + result;

		} else if (exponent == -(standard - 1)) {
			if (s.contains("1")) {
				s = "01" + s;
				temp = Integer.parseInt(integerTrueValue(s));
				sig = temp * Math.pow(2, -sLength);
				sigT = sig - 1;
				sigT = sigT * Math.pow(2, -(standard - 2));
				result = "" + sigT;
				if (operand.charAt(0) == '1')
					result = "-" + result;
			} else {
				result = "0";
			}
		}

		if (!result.contains(".") && !result.equals("NaN") && !result.equals("+Inf") && !result.equals("-Inf")) {
			return result + ".0";
		} else {
			return result;
		}
	}
	
	/**
	 * 按位取反操作。<br/>
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
	 */
	public String negation (String operand) {
		String answer = "";
		for (int i = 0; i < operand.length(); i++) {
			if (operand.charAt(i) == '0') { answer = answer + "1"; }
			else { answer = answer + "0"; }
		}
		return answer;
	}
	
	/**
	 * 左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
	 */
	public String leftShift (String operand, int n) {
		if (n > operand.length()) {
			String answer = "";
			while (answer.length() != operand.length()) {
				answer += "0";
			}
			return answer;
		} else {
			String answer = "";
			answer = operand.substring(n);
			while (answer.length() != operand.length()) {
				answer = answer + "0";
			}
			return answer;
		}
	}
	
	/**
	 * 逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public String logRightShift (String operand, int n) {
		if (n > operand.length()) {
			String answer = "";
			while (answer.length() != operand.length()) {
				answer += "0";
			}
			return answer;
		} else {
			String answer = "";
			answer = operand.substring(0, operand.length() - n);
			while (answer.length() != operand.length()) {
				answer = "0" + answer;
			}
			return answer;
		}
	}
	
	/**
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
	 */
	public String ariRightShift (String operand, int n) {
		if (n > operand.length()) {
			String answer = "";
			while (answer.length() != operand.length()) {
				answer += operand.substring(0, 1);
			}
			return answer;
		} else {
			String answer = "";
			answer = operand.substring(0, operand.length() - n);
			while (answer.length() != operand.length()) {
				answer = operand.substring(0, 1) + answer;
			}
			return answer;
		}
	}
	
	/**
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
	 */
	public String fullAdder (char x, char y, char c) {
		String answer = "";
		int temp = (x - '0') + (y - '0') + (c - '0');
		switch (temp) {
		case 0: answer = "00"; break;
		case 1: answer = "01"; break;
		case 2: answer = "10"; break;
		case 3: answer = "11"; break;
		default: break;
		}
		return answer;
	}
	
	/**
	 * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	public String claAdder (String operand1, String operand2, char c) {
		char[] op1 = operand1.toCharArray();
		char[] op2 = operand2.toCharArray();
		char[] answer = new char[5];
		String an = "";
		char[] c_and_f = new char[2];

		for (int i = 4; i > 0; i--) {
			an = fullAdder(op1[i-1], op2[i-1], c);
			c_and_f = an.toCharArray();
			answer[i] = c_and_f[1];
			c = c_and_f[0];
		}
		answer[0] = c_and_f[0];
		
		String finalAnswer = String.valueOf(answer);
		return finalAnswer;
	}
	
	/**
	 * 加一器，实现操作数加1的运算。
	 * 需要采用与门、或门、异或门等模拟，
	 * 不可以直接调用{@link #fullAdder(char, char, char) fullAdder}、
	 * {@link #claAdder(String, String, char) claAdder}、
	 * {@link #adder(String, String, char, int) adder}、
	 * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
	 * 例：oneAdder("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
	 */
	public String oneAdder (String operand) {
		int i = 1;
		int a = 0;
		int x = Integer.parseInt(integerTrueValue(operand));
		do {
			a = x&i;
			x ^= i;
			i <<= 1;
		} while (a != 0);
		String answer = integerRepresentation(String.valueOf(x), operand.length() + 1);
		if (!answer.contains("1")) {
			return answer;
		} else {
			if (operand.charAt(0) == answer.charAt(1)) {
				return "0" + answer.substring(1);
			} else {
				return "1" + answer.substring(1);
			}
		}
	}
	
	/**
	 * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
	 * 例：adder("0100", "0011", ‘0’, 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param c 最低位进位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		String sign1 = operand1.substring(0, 1);
		while (operand1.length() != length) { operand1 = sign1 + operand1; }
		String sign2 = operand2.substring(0, 1);
		while (operand2.length() != length) { operand2 = sign2 + operand2; }
		
		int groups = length / 4;
		String[] operands1 = new String[groups];
		String[] operands2 = new String[groups];
		for (int i = 0; i < groups; i++) {
			operands1[i] = operand1.substring(4*i, 4*i+4);
			operands2[i] = operand2.substring(4*i, 4*i+4);
		}//operands are divided into groups
		
		String answer = "";
		String temp = "";
		for (int i = groups - 1; i >= 0; i--) {
			temp = claAdder(operands1[i], operands2[i], c);
			answer = temp.substring(1) + answer;
			c = temp.charAt(0);
		}
		if (operand1.charAt(0) != operand2.charAt(0)) {
			return "0" + answer;
		} else {
			if (answer.charAt(0) == operand1.charAt(0)) {
				return "0" + answer;
			} else {
				return "1" + answer;
			}
		}
	}
	
	/**
	 * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		return adder(operand1, operand2, '0', length);
	}
	
	/**
	 * 整数减法，可调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		operand2 = negation(operand2);
		operand2 = adder(operand2, "01", '0', length).substring(1);
		String valueOfAnswer = adder(operand1, operand2, '0', length);
		return valueOfAnswer;
	}
	
	/**
	 * 整数乘法，使用Booth算法实现，可调用{@link #adder(String, String, char, int) adder}等方法。<br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		String num1 = adder(operand1, "00", '0', length).substring(1);
		String num2 = adder(operand2, "00", '0', length).substring(1);
		String num1Neg = adder(negation(operand1), "01", '0', length).substring(1);

		char y_1 = '0';
		String new_num2 = num2;
		for (int i = 0; i < length; i++) {
			new_num2 = "0" + new_num2;
		}
		
		for(int i = 0;i < length; i++){
			String temp = "";
			temp = temp + String.valueOf(new_num2.charAt(new_num2.length()-1));
			temp = temp + String.valueOf(y_1);
			if (temp.equals("00")) {
				y_1 = '0';
			} else if (temp.equals("11")) {
				y_1 = '1';
			} else if (temp.equals("01")) {
				y_1 = '0';
				new_num2 = adder(new_num2.substring(0, length), num1, '0', length).substring(1) + new_num2.substring(length, new_num2.length());
			} else if (temp.equals("10")) {
				y_1 = '1';
				new_num2 = adder(new_num2.substring(0, length), num1Neg, '0', length).substring(1) + new_num2.substring(length, new_num2.length());
			}
			new_num2=ariRightShift(new_num2,1);
		}

		char[] num2Array = new_num2.toCharArray();
		int count = 0;
		for(int i = 0; i < length; i++) {
			count += (num2Array[i] ^ num2Array[i+1]) & 0xf;
		}
		
		String zero = "";
		for (int i = 0; i < length; i++) { zero += "0"; }
		String overflow = "1" + zero.substring(1);
		String one = zero.substring(0, zero.length()) + "1";
		
		if (num1.equals(overflow) && !(num2.equals(zero) || num2.equals(one))) {
			new_num2 = "1" + new_num2;
		} else if (num2.equals(overflow) && !(num1.equals(zero) || num1.equals(one))) {
			new_num2 = "1" + new_num2;
		} else if (num1.equals(overflow) || num2.equals(overflow)) {
			new_num2 = "0" + new_num2;
		} else {
			if (count != 0) {
				new_num2 = "1" + new_num2;
			} else {
				new_num2 = "0" + new_num2;
			}
		}
		
		 return String.valueOf(new_num2.charAt(0)) + new_num2.substring(length + 1);
	}
	
	/**
	 * 整数的不恢复余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		String sign1 = operand1.substring(0, 1); 
		while (operand1.length() != length) {operand1 = sign1 + operand1;}
		String sign2 = operand2.substring(0, 1); 
		while (operand2.length() != length) {operand2 = sign2 + operand2;}
		int value1 = Math.abs(Integer.parseInt(integerTrueValue(operand1)));
		int value2 = Math.abs(Integer.parseInt(integerTrueValue(operand1)));
		
		if (value1 == 0 && value2 == 0) {
			return "NaN";
		} else if ((value1 == 0 && value2 != 0) || (value1 < value2)) {
			String zero = "";
			while (zero.length() != length + 1) {zero += "0";}
			return zero + operand1;
		} else if (value1 != 0 && value2 == 0) {
			if (sign1.equals("1")) {
				return "-Inf";
			} else {
				return "+Inf";
			}
		} else {
			String dividendPart="";
			String Q="";
			String overflow="";
			for (int i = 0; i < length; i++) {
				dividendPart += sign1;
			}
			String divisor = operand2;
			String divisorNeg = oneAdder(negation(operand2)).substring(1);
			if (dividendPart.substring(0, 1).equals(divisor.substring(0, 1))) {
				dividendPart = integerAddition(dividendPart, divisorNeg, length).substring(1);
			} else {
				dividendPart = integerAddition(dividendPart, divisor, length).substring(1);
			}
			
			String dividend = dividendPart + operand1;
			for (int i = 0; i < length; i++) {
				if (dividendPart.substring(0, 1).equals(divisor.substring(0, 1))) {
					Q = "1";
					dividend = leftShift(dividend, 1).substring(0, 2*length - 1) + Q;
					dividendPart = dividend.substring(0, length);
					dividendPart = integerAddition(dividendPart, divisorNeg, length).substring(1);
					dividend = dividendPart + dividend.substring(length);
				} else {
					Q = "0";
					dividend = leftShift(dividend, 1).substring(0, 2*length - 1) + Q;
					dividendPart = dividend.substring(0, length);
					dividendPart = integerAddition(dividendPart, divisor, length).substring(1);
					dividend = dividendPart + dividend.substring(length);
				}
			}
			
			String Str = dividend.substring(length);
			String string1 = Str.substring(0, 1);
			String string2 = "";
			if (dividendPart.substring(0, 1).equals(divisor.substring(0, 1))) {
				string2 = "1";
			} else {
				string2 = "0";
			}
			
			if ((sign1.equals(sign2) && string1.equals("1")) || (!(sign1.equals(sign2)) && string1.equals("0"))) {
				overflow = "1";
			} else {
				overflow = "0";
			}
			
			if (sign1.equals(sign2)) {
				Str = leftShift(Str, 1).substring(0, length - 1) + string2;
			} else {
				Str = oneAdder(leftShift(Str, 1).substring(0, length - 1) + string2).substring(1);
			}
			
			if (!(sign1.equals(dividendPart.substring(0, 1)))) {
				if (sign1.equals(sign2)) {
					dividendPart = integerAddition(dividendPart, divisor, length).substring(1);
				} else {
					dividendPart = integerAddition(dividendPart, divisorNeg, length).substring(1);
				}
			}
			
			String aString = "";
			for (int i = 0; i < length; i++) {
				aString += "0";
			}
			
			if (dividendPart.equals(divisor)) {
				Str = oneAdder(Str);
				if (Str.charAt(0) == '1') {
					overflow = "1";
				}
				Str = Str.substring(1);
				return overflow + Str + aString;
			} else if (dividendPart.equals(divisorNeg)) {
				int num = Integer.parseInt(integerTrueValue(Str)) - 1;
				Str = integerRepresentation(String.valueOf(num), length);
				return overflow + Str + aString;
			} else {
				dividend = Str + dividendPart;
				return overflow + dividend;
			}
		}
	}
	
	/**
	 * 带符号整数加法，可以调用{@link #adder(String, String, char, int) adder}等方法，
	 * 但不能直接将操作数转换为补码后使用{@link #integerAddition(String, String, int) integerAddition}、
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}来实现。<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		char sign1 = operand1.charAt(0);
		String zero = "";
		for (int i = 0; i < length - operand1.length() + 1; i++) { zero += "0"; }
		String op1 = zero + operand1.substring(1);
		
		char sign2 = operand2.charAt(0);
		String op2 = zero + operand2.substring(1);
		
		char OF = '0';
		String result = "";
		if (sign1 == sign2) {
			result = adder("0" + op1, "0" + op2, '0', length*2).substring(1);
			if (result.charAt(length - 1) == '1') {
				OF = '1';
			}
			result = sign1 + result.substring(length);
		} else {
			op2 = negation(op2);
			result = adder("0" + op1, "0" + op2, '1', length*2).substring(1);
			if (result.charAt(length - 1) == '1') {
				result = sign1 + result.substring(length);
			} else {
				result = result.substring(length);
				result = oneAdder(negation(result)).substring(1);
				String Sign = sign1 == '1' ? "0" : "1";
				result = Sign + result;
			}
		}
		return OF+result;
	}	

	/**
	 * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		int E1 = Integer.parseInt(integerTrueValue(operand1.substring(1, eLength + 1)));
		int E2 = Integer.parseInt(integerTrueValue(operand2.substring(1, eLength + 1)));
		String S1 = "1" + operand1.substring(eLength + 1);
		String S2 = "1" + operand2.substring(eLength + 1);
		if (E1 == 0) {
			S1 = S1.replaceAll("1", "0");
		} else if (E2 == 0) {
			S2 = S2.replaceAll("1", "0");
		}
		
		StringBuffer answer = new StringBuffer();
		String guard = "";
		while (guard.length() != gLength) { guard += "0"; }
		
		if (E1 >= E2) {
			int deltaE = E1 - E2;
			S2 = logRightShift(S2 + guard, deltaE);
			int bias = ((sLength + gLength + 2) / 4 + 1) * 4 - (2 + sLength + gLength);
			S1 += guard;
			String result = signedAddition(operand1.charAt(0) + S1, operand2.charAt(0) + S2, 
					2 + sLength + gLength + bias).substring(1);
			answer.append(result.charAt(0));
			result = result.substring(bias + 1);

			int count = 0;
			int len = result.length();
			for ( ; count < len; count++) {
				if (result.charAt(count) == '1') {
					break;
				}
			}

			E1 = E1 - (count - 1);
			if (E1 < 0) {
				
				String zero = "";
				while (zero.length() != eLength) { zero += "0"; }
				answer.append(zero);
				answer.insert(0,"0");
				for(int i = E1 + 1; i < 0 && i <= sLength;i++) {
					answer.append("0");
				}
				answer.append(result.substring(count));
				zero = "";
				while (zero.length() != eLength) { zero += "0"; }
				answer.append(zero);
				answer.setLength(2 + eLength + sLength);
				return new String(answer);
			} else if (E1 >= (1 << (eLength)) - 1) {
				String one = "";
				while (one.length() != eLength) { one += "1"; }
				answer.append(one);
				answer.insert(0,"1");
				String zero = "";
				while (zero.length() != eLength) { zero += "0"; }
				answer.append(zero);
				return new String(answer);
			} else {
				answer.insert(0,'0');
				answer.append(integerRepresentation(String.valueOf(E1), eLength / 4 * 4 + 4)
						.substring(eLength / 4 * 4 + 4 - eLength));
				answer.append(result.substring(count + 1));
				String Zero = "";
				while (Zero.length() != eLength) { Zero += "0"; }
				answer.append(Zero);
				answer.setLength(2 + eLength + sLength);
				return new String(answer);
			}
		} else {
			return floatAddition(operand2, operand1, eLength, sLength, gLength);
		}
	}
	
	/**
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		if (operand2.charAt(0) == '0') {
			operand2 = "1" + operand2.substring(1);
			return floatAddition(operand1, operand2, eLength, sLength, gLength);
		} else {
			operand2 = "0" + operand2.substring(1);
			return floatAddition(operand1, operand2, eLength, sLength, gLength);
		}
	}
	
	/**
	 * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int) integerMultiplication}等方法实现。<br/>
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		int E1 = Integer.parseInt(integerTrueValue(operand1.substring(1, eLength + 1)));
		int E2 = Integer.parseInt(integerTrueValue(operand2.substring(1, eLength + 1)));
		String S1 = "1" + operand1.substring(eLength + 1);
		String S2 = "1" + operand2.substring(eLength + 1);
		if (E1 == 0) {
			S1 = S1.replaceAll("1", "0");
		} else if (E2 == 0) {
			S2 = S2.replaceAll("1", "0");
		}
		
		StringBuffer answer = new StringBuffer();

		int bias = ((2 * S2.length() + 1) / 4 + 1) * 4 - (2 * S2.length() + 1);
		String result = integerMultiplication("0" + S1, "0" + S2, 2 * S2.length() + 1 + bias).substring(1);
		result = result.substring(result.length() - 2 * S2.length(), result.length());
		answer.append(String.valueOf(Integer.parseInt(String.valueOf(operand1.charAt(0)))
				^ Integer.parseInt(String.valueOf(operand2.charAt(0)))));

		int count = 0;
		int len = result.length();
		for ( ; count < len; count++) {
			if (result.charAt(count) == '1') {
				break;
			}
		}
		E1 = E1 + E2 - ((1 << (eLength-1)) - 1);
		E1 = E1 - (count - 1);
		if (E1 < 0) {
			
			String zero = "";
			while (zero.length() != eLength) { zero += "0"; }
			answer.append(zero);
			answer.insert(0,"0");
			for(int i = E1 + 1; i < 0 && i <= sLength;i++) {
				answer.append("0");
			}
			answer.append(result.substring(count));
			zero = "";
			while (zero.length() != eLength) { zero += "0"; }
			answer.append(zero);
			answer.setLength(2 + eLength + sLength);
			return new String(answer);
		} else if (E1 >= (1 << (eLength)) - 1) {
			String one = "";
			while (one.length() != eLength) { one += "1"; }
			answer.append(one);
			answer.insert(0,"1");
			String zero = "";
			while (zero.length() != eLength) { zero += "0"; }
			answer.append(zero);
			return new String(answer);
		} else {
			answer.insert(0,'0');
			answer.append(integerRepresentation(String.valueOf(E1), eLength / 4 * 4 + 4)
					.substring(eLength / 4 * 4 + 4 - eLength));
			answer.append(result.substring(count + 1));
			String Zero = "";
			while (Zero.length() != eLength) { Zero += "0"; }
			answer.append(Zero);
			answer.setLength(2 + eLength + sLength);
			return new String(answer);
		}
	}
	
	/**
	 * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}//////////////
}
