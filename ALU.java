/**
 * ģ��ALU���������͸���������������
 * @author 161250189_��ʥ��
 *
 */

public class ALU {

	/**
	 * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
	 * ����integerRepresentation("9", 8)
	 * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʋ����ʾ�ĳ���
	 * @return number�Ķ����Ʋ����ʾ������Ϊlength
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
	 * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
	 * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
	 * �������Ϊ��0���롣<br/>
	 * ����floatRepresentation("11.375", 8, 11)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 *Ҫ��ʵ��һ����������10����С����ʾ�ɶ����ƣ������ƫ����Ϊ2^(n-1)-1
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
	 * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
	 * ����ieee754("11.375", 32)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
	 * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String ieee754 (String number, int length) {
		String result = "";
		if (length == 32) { result = floatRepresentation(number, 8,23); }
		else if (length == 64) { result = floatRepresentation(number, 11, 52); }
		return result;
	}
	
	/**
	 * ��������Ʋ����ʾ����������ֵ��<br/>
	 * ����integerTrueValue("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
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
	 * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
	 * ����floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
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
	 * ��λȡ��������<br/>
	 * ����negation("00001001")
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @return operand��λȡ���Ľ��
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
	 * ���Ʋ�����<br/>
	 * ����leftShift("00001001", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand����nλ�Ľ��
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
	 * �߼����Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand�߼�����nλ�Ľ��
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
	 * �������Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand��������nλ�Ľ��
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
	 * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
	 * ����fullAdder('1', '1', '0')
	 * @param x ��������ĳһλ��ȡ0��1
	 * @param y ������ĳһλ��ȡ0��1
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
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
	 * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
	 * ����claAdder("1001", "0001", '1')
	 * @param operand1 4λ�����Ʊ�ʾ�ı�����
	 * @param operand2 4λ�����Ʊ�ʾ�ļ���
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ�����
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
	 * ��һ����ʵ�ֲ�������1�����㡣
	 * ��Ҫ�������š����š�����ŵ�ģ�⣬
	 * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
	 * {@link #claAdder(String, String, char) claAdder}��
	 * {@link #adder(String, String, char, int) adder}��
	 * {@link #integerAddition(String, String, int) integerAddition}������<br/>
	 * ����oneAdder("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
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
	 * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
	 * ����adder("0100", "0011", ��0��, 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param c ���λ��λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
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
	 * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerAddition("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		return adder(operand1, operand2, '0', length);
	}
	
	/**
	 * �����������ɵ���{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerSubtraction("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		operand2 = negation(operand2);
		operand2 = adder(operand2, "01", '0', length).substring(1);
		String valueOfAnswer = adder(operand1, operand2, '0', length);
		return valueOfAnswer;
	}
	
	/**
	 * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
	 * ����integerMultiplication("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
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
	 * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
	 * ����integerDivision("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
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
	 * �����������ӷ������Ե���{@link #adder(String, String, char, int) adder}�ȷ�����
	 * ������ֱ�ӽ�������ת��Ϊ�����ʹ��{@link #integerAddition(String, String, int) integerAddition}��
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}��ʵ�֡�<br/>
	 * ����signedAddition("1100", "1011", 8)
	 * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
	 * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
	 * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
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
	 * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
	 * ����floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
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
	 * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
	 * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
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
	 * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
	 * ����floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
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
	 * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
	 * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}//////////////
}
