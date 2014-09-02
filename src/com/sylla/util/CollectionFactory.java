package com.sylla.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import EDU.oswego.cs.dl.util.concurrent.SyncMap;
import EDU.oswego.cs.dl.util.concurrent.SyncSet;
import EDU.oswego.cs.dl.util.concurrent.WriterPreferenceReadWriteLock;

public class CollectionFactory {

	public static Map getHashMap() {
		return new HashMap();
	}

	public static Map getHashMap(int capacity) {
		return new HashMap(capacity);
	}

	public static Set getHashSet() {
		return new HashSet();
	}

	public static Set getHashSet(int capacity) {
		return new HashSet(capacity);
	}

	public static List getLinkedList() {
		return new LinkedList();
	}

	public static Map getSyncHashMap() {
		return new SyncMap(getHashMap(), new WriterPreferenceReadWriteLock());
	}

	public static Map getSyncHashMap(int capacity) {
		return new SyncMap(getHashMap(capacity),
				new WriterPreferenceReadWriteLock());
	}

	public static Set getSyncHashSet() {
		return new SyncSet(getHashSet(), new WriterPreferenceReadWriteLock());
	}

	public static Set getSyncHashSet(int capacity) {
		return new SyncSet(getHashSet(capacity),
				new WriterPreferenceReadWriteLock());
	}

}