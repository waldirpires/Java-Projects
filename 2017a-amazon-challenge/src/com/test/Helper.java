package com.test;

import java.io.IOException;
import java.io.InputStream;

class Helper {

	private enum State { READ, NEXT, END }

	Helper.State state = State.NEXT;
	InputStream in = System.in;
	
	public int limit() {
		int limit = Integer.parseInt(next());
		state = State.NEXT;
		return limit;
	}
	
	public boolean hasNext() {
		return state == State.NEXT;
	}
	
	public String next() {
		if(state == State.END) { 
			return null;
		}
		StringBuilder sb = new StringBuilder();
		state = State.READ;
		while(state == State.READ) {
			try {
				char c = (char) in.read();
				switch (c) {
					case '\r':
						in.read();
					case '\n':
						state = State.END;
						break;
					case ' ':
						state = State.NEXT;
						break;
					default:
						sb.append(c);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if(sb.length() == 0) {
			return null;
		}
		return sb.toString();
	}
}