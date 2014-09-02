package com.sylla;

public class Samephore {

	private long value;
	private String name;

	public Samephore() {
		this(0);
	}

	/**
	 * construct the samephore
	 * @param value
	 */
	public Samephore(long value) {
		this(value, "");
	}

	public Samephore(long value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * P operation @ PV atom statement
	 */
	public synchronized void p() {
		p(1L);
	}

	public synchronized void p(long p) {
		value -= p;
		// System.out.println(name + " (P)= " + value);
		if(value < 0L) {
			try {
				this.wait();
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	/**
	 * V operation @ PV atom statement
	 */
	public synchronized void v() {
		v(1L);
	}

	public synchronized void v(long v) {
		value += v;
		// System.out.println(name + " (V)= " + value);
		if(value <= 0L) {
			this.notify(); 
		}
	}

	public long value() {
		return value;
	}
}
