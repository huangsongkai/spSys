package com.xunj.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Eval {
	private static String [] symbols = {"+", "-", "*","×","/", "{","}","(", ")"};
	private static char [] symbolsChar = {'+', '-', '*','×','/', '{','}','(',')',' ','\t','#'};
	public String evalString(String exp){
		if(exp!=null && !"".equals(exp)){
			//如果是负数，直接转
			if(exp.indexOf("-")!=-1){
				exp="("+exp.substring(exp.indexOf("-")+1,exp.length())+")";
			}
			return exp;
		}
		return "";
		
	}
	public Double evalDouble(String exp) {
		if(exp!=null && !"".equals(exp)){
			//如果是负数，直接转
			if(exp.indexOf("-")==0){
				return new Double(exp);
			}
			exp += "#";
			List<String> list = infixExpToPostExp(exp);// 转化成后缀表达式
			if(list!=null && list.size()>0)
				return doEval(list);// 真正求值
		}
			
		return new Double(0);
		
	}
	public Long evalLong(String exp) {
		//如果是负数，直接转
		if(exp.indexOf("-")==0){
			return new Long(exp);
		}
		exp += "#";
		List<String> list = infixExpToPostExp(exp);// 转化成后缀表达式
		if(list!=null && list.size()>0)
			return new Long((int)Math.ceil(doEval(list).doubleValue()));// 真正求值
		else
			return new Long(0);
	}

	// 遇到操作符压栈，遇到表达式从后缀表达式中弹出两个数，计算出结果，压入堆栈
	private Double doEval(List<String> list) {
		Stack<String> stack = new Stack<String>();
		String element;
		Double n1, n2, result;
		try {
			for (int i = 0; i < list.size(); i++) {
				element = list.get(i);
				if (isOperator(element)) {
					n1 = Double.parseDouble(stack.pop());
					n2 = Double.parseDouble(stack.pop());
					result = doOperate(n1, n2, element);
					stack.push(new Double(result).toString());
				} else {
					stack.push(element);
				}
			}
			return Double.parseDouble(stack.pop());
		} catch (RuntimeException e) {
			return new Double(0);
		}
	}

	private Double doOperate(Double n2, Double n1, String operator) {
		//+
		if (operator.equals(symbols[0]))
			return n1 + n2;
		//-
		else if (operator.equals(symbols[1]))
			return n1 - n2;
		//*||×
		else if (operator.equals(symbols[2]) || operator.equals(symbols[3]))
			return n1 * n2;
		// /
		else
			return n1 / n2;
	}

	private boolean isOperator(String str) {
		//0+ 1- *2 ×3 /4 
		return str.equals(symbols[0]) || str.equals(symbols[1]) || str.equals(symbols[2])
				|| str.equals(symbols[3]) || str.equals(symbols[4]);
	}

	private List<String> infixExpToPostExp(String exp) {// 将中缀表达式转化成为后缀表达式
		List<String> postExp = new ArrayList<String>();// 存放转化的后缀表达式的链表
		StringBuffer numBuffer = new StringBuffer();// 用来保存一个数的
		Stack<Character> opStack = new Stack<Character>();// 操作符栈
		char ch, preChar;
		opStack.push(symbolsChar[11]);
		try {
			for (int i = 0; i < exp.length();) {
				ch = exp.charAt(i);
				//0+ 1- *2 ×3 /4 
				if(ch ==symbolsChar[0] || ch== symbolsChar[1] || ch== symbolsChar[2] || ch== symbolsChar[3] || ch== symbolsChar[4] ){
				
					//返回位于stack栈顶部的对象。
					preChar = opStack.peek();
					// 如果栈里面的操作符优先级比当前的大，则把栈中优先级大的都添加到后缀表达式列表中
					while (priority(preChar) >= priority(ch)) {
						if(priority(preChar)==9 || priority(ch)==9){
							return null;
						}
						postExp.add("" + preChar);
						//返回位于stack栈顶部的对象，并移除.
						opStack.pop();
						preChar = opStack.peek();
					}
					//将ch压入栈顶部.
					opStack.push(ch);
					i++;
					//{
				}else if (ch ==symbolsChar[5]){
					// 左花括号直接压栈
					opStack.push(ch);
					i++;
					//}
				}else if (ch ==symbolsChar[6]){
					// 右花括号则直接把栈中左花括号前面的弹出，并加入后缀表达式链表中
					char hc = opStack.pop();
					while (hc != symbolsChar[5]) {
						postExp.add("" + hc);
						hc = opStack.pop();
					}
					i++;
					//(
				}else if (ch ==symbolsChar[7]){
					// 左括号直接压栈
					opStack.push(ch);
					i++;
					//)
				}else if (ch ==symbolsChar[8]){
					// 右括号则直接把栈中左括号前面的弹出，并加入后缀表达式链表中
					char c = opStack.pop();
					while (c != symbolsChar[7]) {
						postExp.add("" + c);
						c = opStack.pop();
					}
					i++;
					//#
				}else if (ch ==symbolsChar[11]){
				// #号，代表表达式结束，可以直接把操作符栈中剩余的操作符全部弹出，并加入后缀表达式链表中
					char c1;
					while (!opStack.isEmpty()) {
						c1 = opStack.pop();
						if (c1 != symbolsChar[11])
							postExp.add("" + c1);
					}
					i++;
					// ' ' || '/t'
				}else if (ch ==symbolsChar[9]|| ch == symbolsChar[10]){
					// 过滤空白符
					i++;
				}else{
				// 数字则凑成一个整数，加入后缀表达式链表中
					if (Character.isDigit(ch)) {
						while (Character.isDigit(ch)) {
							numBuffer.append(ch);
							ch = exp.charAt(++i);
						}
						postExp.add(numBuffer.toString());
						numBuffer = new StringBuffer();
					} else {
						return null;
					}
				}
			}
		} catch (RuntimeException e) {
			return null;
		}
		return postExp;
	}

	private int priority(char op) {// 定义优先级
		if(op ==symbolsChar[0] || op== symbolsChar[1]){
			return 1;
		}else if(op ==symbolsChar[2] || op== symbolsChar[4] || op ==symbolsChar[3] ){
			return 2;
		}else if(op ==symbolsChar[7] || op== symbolsChar[5] || op ==symbolsChar[11] ){
			return 0;
		}else{
			return 9;
		}
		
	}

	public static void main(String[] args) {
		Eval eval = new Eval();
		Double result = eval.evalDouble("1+(2+2)*{1*2}×3-9");
		System.out.println(result);
		Double a = new Double("3");
		Long b = new Long("1");
		System.out.println(new Long((int)Math.ceil(a.doubleValue())));
		System.out.println(Double.parseDouble("-1"));
		System.out.println(Long.parseLong("-1"));
		System.out.println("(-1".indexOf("-1"));
	}

}
