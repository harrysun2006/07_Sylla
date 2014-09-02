package com.sylla;

public class OcrImage {

	private String text;

	private double accurate;

	public OcrImage() {
	}

	public OcrImage(String text, double accurate) {
		setText(text);
		setAccurate(accurate);
	}

	public double getAccurate() {
		return accurate;
	}

	public void setAccurate(double accurate) {
		this.accurate = accurate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
