package com.test;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sylla.CodeChecker;
import com.sylla.Constants;
import com.sylla.Corp;
import com.sylla.Image;
import com.sylla.Info;
import com.sylla.Niffler;
import com.sylla.Ocr;
import com.sylla.OcrImage;
import com.sylla.RegexFileFilter;
import com.sylla.Samephore;
import com.sylla.WebClient;
import com.sylla.WebPage;
import com.sylla.service.Combiner;
import com.sylla.service.SyllaServiceUtil;
import com.sylla.util.CommonUtil;
import com.sylla.util.ImageUtil;

public class Test {

	private static final Log log = LogFactory.getLog(Test.class);

	public static void main(String[] args) throws Exception {
		try {
			// Myth.test17();
			// Bargain.test1();
			Niffler.main(args);
			// test25();
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			// System.exit(0);
		}
	}

	private static String readText(String file) throws Exception {
		File f = new File(file);
		InputStream is = new FileInputStream(f);
		StringBuffer sb = new StringBuffer();
		byte[] b = new byte[8192];
		int l = 0;
		while ((l = is.read(b)) > 0) {
			sb.append(new String(b, 0, l));
		}
		is.close();
		return sb.toString();
	}

	private static byte[] readBytes(String file) throws Exception {
		File f = new File(file);
		InputStream is = new FileInputStream(f);
		byte[] b = new byte[is.available()];
		is.read(b);
		is.close();
		return b;
	}

	private static void writeBytes(String file, byte[] b) throws Exception {
		File f = new File(file);
		OutputStream os = new FileOutputStream(f);
		os.write(b);
		os.close();
	}

	public static void test0() throws Exception {
		Bargain n = new Bargain();
		n.run();
	}

	/**
	 * format /CorpSel/CorpSearchs.aspx and /CorpSel/CorpSearchsG.aspx url
	 * @throws Exception
	 */
	private static void test1() throws Exception {
		String url = MessageFormat.format(Niffler.CORP_INFO_URL, 1150494);
		System.out.println(url);
		url = MessageFormat.format(Niffler.CORP_INFOG_URL, 1150494);
		System.out.println(url);
	}

	/**
	 * get title from /BaseInfo/Book.aspx page
	 * @throws Exception
	 */
	private static void test2() throws Exception {
		String text = readText("./pages/Book.html");
		System.out.println(text);
		Matcher m = Niffler.BOOK_PATTERN.matcher(text);
		if (m.find() && m.groupCount() == 2) {
			StringBuffer sb = new StringBuffer();
			sb.append("Login times: ").append(m.group(1)).append(". Search corps: ").append(m.group(2));
			log.info(sb.toString());
		}
		else log.info("NOT matched!!!");
	}

	/**
	 * get corps and max page from /CorpSel/CorpSearchs.aspx page
	 * @throws Exception
	 */
	private static void test3() throws Exception {
		String text = readText("./pages/CorpSearchs.html");
		Matcher m = Niffler.CORP_INFO_PATTERN.matcher(text);
		List<Corp> list = new ArrayList<Corp>();
		Corp corp = null;
		String name;
		int i = 0;
		long max;
		while(m.find()) {
			name = m.group(2);
			System.out.println(m.group(1) + ": " + name + ": " + m.group(3));
			corp = new Corp();
			corp.setId(Long.parseLong(m.group(1)));
			corp.setName1(name);
			corp.setCreateDate1(m.group(3));
			list.add(corp);
			i = m.end();
		}
		m = Niffler.CORP_TOTAL_PAGE_PATTERN.matcher(text.substring(i));
		if (m.find()) {
			max = Long.parseLong(m.group(1));
			System.out.println("Total page: " + max);
		}
		System.out.println("Total found " + list.size() + " corps!!!");
		/*
		for (Iterator<Corp> it = list.iterator(); it.hasNext(); ) {
			corp = it.next();
			System.out.println(corp.getId() + ": " + corp.getName1() + ": " + corp.getCreateDate1());
		}
		*/
	}

	/**
	 * get corps and max page from /CorpSel/CorpSearchsG.aspx page
	 * @throws Exception
	 */
	private static void test4() throws Exception {
		String text = readText("./pages/CorpSearchsG.html");
		Matcher m = Niffler.CORP_INFOG_PATTERN.matcher(text);
		List<Corp> list = new ArrayList<Corp>();
		Corp corp = null;
		String name;
		int i = 0;
		long max;
		while(m.find()) {
			name = m.group(2);
			System.out.println(m.group(1) + ": " + name + ": " + m.group(3));
			corp = new Corp();
			corp.setId(Long.parseLong(m.group(1)));
			corp.setName1(name);
			corp.setCreateDate1(m.group(3));
			list.add(corp);
			i = m.end();
		}
		m = Niffler.CORPG_TOTAL_PAGE_PATTERN.matcher(text.substring(i));
		if (m.find()) {
			max = Long.parseLong(m.group(1));
			System.out.println("Total page: " + max);
		}
		System.out.println("Total found " + list.size() + " corps!!!");
		/*
		for (Iterator<Corp> it = list.iterator(); it.hasNext(); ) {
			corp = it.next();
			System.out.println(corp.getId() + ": " + corp.getName1() + ": " + corp.getCreateDate1());
		}
		*/
	}

	/**
	 * get corp info(images) and scope from /CorpSel/Corp_info.aspx page
	 * @throws Exception
	 */
	private static void test5() throws Exception {
		String text = readText("./pages/Corp_info.html");
		Matcher m1 = Niffler.IMAGE_PATTERN.matcher(text);
		Matcher m2;
		int i = 0;
		while (m1.find()) {
			System.out.println(Niffler.INFO_TYPES[i++] + ": " + m1.group(1));
			if (i == Niffler.CORP_INFO_SCOPE_INDEX) {
				m2 = Niffler.CORP_INFO_SCOPE_PATTERN.matcher(text.substring(m1.end()));
				if (m2.find()) System.out.println("SCOPE: " + m2.group(1));
			}
		}
	}

	/**
	 * get corpG info(images) and scope from /CorpSel/Corp_infoG.aspx page
	 * @throws Exception
	 */
	private static void test6() throws Exception {
		String text = readText("./pages/Corp_infoG.html");
		Matcher m1 = Niffler.IMAGE_PATTERN.matcher(text);
		Matcher m2;
		int i = 0;
		while (m1.find()) {
			System.out.println(Niffler.INFO_TYPES[i++] + ": " + m1.group(1));
			if (i == Niffler.CORP_INFO_SCOPE_INDEX) {
				m2 = Niffler.CORP_INFO_SCOPE_PATTERN.matcher(text.substring(m1.end()));
				if (m2.find()) System.out.println("SCOPE: " + m2.group(1));
			}
		}
	}

	/**
	 * get the image(JPEG) from /CorpSel/InfoShowImg.aspx page
	 * @throws Exception
	 */
	private static void test7() throws Exception {
		Image image = new Image();
		String code = "E869DB3F24432121E8BA3E2A4BF8062783A0A5BA04E47512E1B6425DDFAD3BFE";
		image.setCode(code);
		byte[] b = Niffler.image(image);
		writeBytes(code + ".jpg", b);
	}

	/**
	 * get image from database
	 * @throws Exception
	 */
	private static void test9() throws Exception {
		Info info = new Info();
		info.setCorpId(9996778L);
		// info.setType(InfoPK.IT_INDUSTRY);
		// info.setCode("E869DB3F24432121E8BA3E2A4BF8062783A0A5BA04E47512E1B6425DDFAD3BFE");
		List<Image> images = SyllaServiceUtil.getImages(info);
		Image image;
		for (Iterator<Image> it = images.iterator(); it.hasNext(); ) {
			image = it.next();
			System.out.println(image.getCode() + ": " + image.getStatus());
		}
	}

	/**
	 * get /CorpSel/CorpSearchs.aspx page
	 * @throws Exception
	 */
	private static void test10() throws Exception {
		WebPage page = null;
		int c = 0;
		WebClient client = WebClient.create(Niffler.HOST);
		while (c < Constants.MAX_RETRY) {
			try {
				for (int i = 0; i < Niffler.LOGIN_STEPS.length; i++) page = Niffler.LOGIN_STEPS[i].work(client, page);
			} catch(Exception e) { }
			Matcher m = Niffler.BOOK_PATTERN.matcher(page.getResponseString());
			if (m.find() && m.groupCount() == 2) {
				StringBuffer sb = new StringBuffer();
				sb.append("Login times: ").append(m.group(1)).append(". Search corps: ").append(m.group(2));
				log.info(sb.toString());
				break;
			}
			c++;
		}
		if (c == Constants.MAX_RETRY) {
			log.error("Failed to login and get BaseInfo/Book.aspx page!!!");
			return;
		}
		Niffler.search(client, 1);
		page = Niffler.search(client, 2);
		WebClient.debug(page);
	}

	/**
	 * test the code checker
	 * @throws Exception
	 */
	private static void test11() throws Exception {
		Runnable[] r = new Runnable[] {
				new CodeTester("./vcodes/yedn.gif"), 
				new CodeTester("./vcodes/2zut.gif"),
				new CodeTester("./vcodes/4bkb.gif"), 
				new CodeTester("./vcodes/fqjq.gif"),
				new CodeTester("./vcodes/r59h.gif"), 
				new CodeTester("./vcodes/uap8.gif"),
		};
		Thread t;
		for (int i = 0; i < r.length; i++) {
			t = new Thread(r[i]);
			// if (i > 0) Thread.sleep(RR.nextInt(2000));
			t.start();
		}
	}

	private static final class CodeTester implements Runnable {
		private String file;

		public CodeTester(String file) {
			this.file = file;
		}

		public void run() {
			try {
				byte[] b = readBytes(file);
				System.out.println("Read: " + file);
				System.out.println("Code[" + file + "]: " + CodeChecker.getCode(b));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * test multiple-thread on static function
	 * @throws Exception
	 */
	private static void test12() throws Exception {
		SynTester.create(1L);
		SynTester.create(2L);
	}

	private static final class SynTester implements Runnable {
		
		private static Set<Long> ids = new HashSet<Long>();

		private Long id;
	
		private SynTester(Long id) {
			this.id = id;
		}

		public static boolean full() {
			return (ids.size() >= 10);
		}

		public static void create(Long id) {
			SynTester st = null;
			Thread t = null;
			try {
				synchronized(ids) {
					if (!ids.contains(id)) {
						if (!full()) {
							st = new SynTester(id);
							System.out.println("Created: " + id);
						} else {
							System.out.println("Queued: " + id);
						}
					}
				}
				if (st != null) {
					t = new Thread(st);
					t.start();
				}
			} catch (Throwable tt) {
				synchronized(ids) {
					ids.remove(id);
				}
			}
		}

		public void run() {
			try {
				System.out.println("OKEY: " + id);
			} catch (Throwable tt) {
				System.err.println("ERROR: " + id);
			} finally {
				synchronized(ids) {
					ids.remove(id);
				}
			}
			SynTester.create(id);
		}
	}

	private static void test13() throws Exception {
		Corp corp;
		for (long i = 1; i < 10; i++) {
			corp = SyllaServiceUtil.getCorp(13325972L);
			System.out.println(i + ": " + corp);
		}
	}

	private static void test14() throws Exception {
		// String text = readText("./pages/CorpSearchsG_978.html");
		// String text = readText("./pages/CorpSearchsG_656.html");
		String text = readText("./pages/CorpSearchs_9.html");
		Matcher m = Niffler.CORP_INFO_PATTERN.matcher(text);
		int i = 0;
		while(m.find()) {
			System.out.println(m.group(1) + ": " + m.group(2) + ": " + m.group(3));
			i++;
		}
		System.out.println("Total found " + i + " corps!!!");
	}

	private static void test15() throws Exception {
		Corp corp = new Corp();
		corp.setId(889920L);
		corp.setPage(11L);
		System.out.println(SyllaServiceUtil.updateCorpPage(corp));
	}

	private static final Samephore ZIP_SAMEPHORE = new Samephore(0, "ZIP");
	private static final Samephore ZIP_MUTEX = new Samephore(1, "MUTEX");
	private static final Samephore ZIP_CHECK = new Samephore(0, "CHECK");
	private static final int BUFFER_SIZE = 2048;

	/**
	 * zip files into a package using multiple threads, it's OKEY!!!
	 * @throws Exception
	 */
	private static void test16() throws Exception {
		OutputStream os = new FileOutputStream("test.zip");
    ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(os));
    File f = new File("./pages");
    // File files[] = f.listFiles(new RegexFileFilter("CorpSearchs\\.html"));
    File files[] = f.listFiles(new RegexFileFilter("Corp.*"));
    Runnable r;
    Thread t;
    for (int i = 0; i < files.length; i++) {
    	System.out.println(files[i].getName());
    	r = new ZipThread(files[i], zip);
    	t = new Thread(r);
    	t.start();
    }
    // Thread.sleep(1000);
    ZIP_SAMEPHORE.p(files.length);
    while (ZIP_SAMEPHORE.value() < 0) {
    	ZIP_CHECK.p();
    }
    zip.close();
	}

	private static final class ZipThread implements Runnable {

		private File file;
		private ZipOutputStream zip;

		private ZipThread(File file, ZipOutputStream zip) {
			this.file = file;
			this.zip = zip;
		}

		public void run() {
      byte data[] = new byte[BUFFER_SIZE];
      try {
	      FileInputStream fi = new FileInputStream(file);
	      InputStream is = new BufferedInputStream(fi, BUFFER_SIZE);
	      ZipEntry entry = new ZipEntry(file.getName());
	      int count;
	      ZIP_MUTEX.p();
	      zip.putNextEntry(entry);
	      while ((count = is.read(data, 0, BUFFER_SIZE)) != -1) {
	          zip.write(data, 0, count);
	      }
	      zip.flush();
	      ZIP_MUTEX.v();
	      is.close();
      } catch(IOException ioe) {
      	ioe.printStackTrace();
      } finally {
      	ZIP_SAMEPHORE.v();
      	ZIP_CHECK.v();
      }
		}
	}

	/**
	 * Zip files into a package using single thread, it's OKEY!!!
	 * @throws Exception
	 */
	private static void test17() throws Exception {
		byte data[] = new byte[BUFFER_SIZE];
		OutputStream os = new FileOutputStream("test.zip");
    ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(os));
    File f = new File("./pages");
    // File files[] = f.listFiles(new RegexFileFilter("CorpSearchs\\.html"));
    File files[] = f.listFiles(new RegexFileFilter("Corp.*"));
    for (int i = 0; i < files.length; i++) {
    	System.out.println(files[i].getName());
      FileInputStream fi = new FileInputStream(files[i]);
      InputStream is = new BufferedInputStream(fi, BUFFER_SIZE);
      ZipEntry entry = new ZipEntry(files[i].getName());
      int count;
      zip.putNextEntry(entry);
      while ((count = is.read(data, 0, BUFFER_SIZE)) != -1) {
          zip.write(data, 0, count);
      }
      is.close();
    }
    zip.close();
	}

	/**
	 * no way to intercept the JVM stop
	 * if something (e.g. close zip output stream) must be done before application stop, we can use this way
	 * @throws Exception
	 */
	private static void test18() throws Exception {
		Runnable r = new ListenThread();
		Thread t = new Thread(r);
		t.start();
		BufferedReader _input = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			String choice = _input.readLine().trim();
			try {
				if("?".equals(choice)) ;
				else if("q".equals(choice)) {
					ListenThread.stop();
					System.exit(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static final class ListenThread implements Runnable {

		public void run() {
			Calendar cal = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			while(true) {
				System.out.println(df.format(cal.getTime()));
				try {
					Thread.sleep(1000);
				} catch(Exception e) {}
				cal = Calendar.getInstance();
			}
		}

		public static void stop() {
			System.out.println("FINALIZED!!!");
		}
	}

	/**
	 * miscellaneous testing
	 * @throws Exception
	 */
	private static void test19() throws Exception {
		System.out.println(SyllaServiceUtil.getInfoCount(118281L));
		System.out.println(SyllaServiceUtil.getInfoCount(3712820L));
		System.out.println(CommonUtil.formatNumber("0000000", 123));
		System.out.println(MessageFormat.format("{0,number,0000000}", 123));
		List<Corp> corps = SyllaServiceUtil.getVoidCorps(10L);
		System.out.println(corps.size());
		Corp corp = SyllaServiceUtil.getVoidCorp(0L);
		System.out.println(corp.getName1());
	}

	/**
	 * combine the image and save it to jpg file
	 * @throws Exception
	 */
	private static void test20() throws Exception {
    File f = new File("./temp");
    File[] files = f.listFiles(new RegexFileFilter("i\\d{7}\\.jpg"));
    BufferedImage bi = (BufferedImage) ImageUtil.combine(files);
    OutputStream out = new FileOutputStream("bi.jpg"); 
    JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(out);
    enc.getDefaultJPEGEncodeParam(bi).setQuality(100, true);
    enc.encode(bi);
    out.close();
	}

	/**
	 * multithread JNI call COM dll, the second call will crash
	 * @throws Exception
	 */
	private static void test21() throws Exception {
		System.loadLibrary("ocr");
		Runnable[] rs = {new OcrThread("./temp/b0000013.jpg"), 
				new OcrThread("./temp/b0000029.jpg"),
				new OcrThread("./temp/b0000042.jpg")};
		Thread t;
		for (int i = 0; i < rs.length; i++) {
			t = new Thread(rs[i]);
			t.start();
			// Thread.sleep(1000);
		}

		/**
		 * if add the following codes, the ocr.dll will be blocked!!!
		 */
		/*
		BufferedReader _input = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			help();
			String choice = _input.readLine().trim();
			try {
				if("?".equals(choice)) help();
				else if("q".equals(choice)) {
					Packer.stopAll();
					System.exit(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		*/

	}

	private static final class OcrThread implements Runnable {

		private String name;

		public OcrThread(String name) {
			this.name = name;
		}

		public void run() {
			try {
				File f = new File(name);
		    System.out.println("name: " + f.getName() + ", path: " + f.getPath()
		    		+ ", canRead: " + f.canRead() + ", absolutePath: " + f.getAbsolutePath());
		    OcrImage[] images = Ocr.winocr(f.getAbsolutePath());
		    // OcrImage[] images = Ocr.ocr(f.getAbsolutePath());
				for (int i = 0; i < images.length; i++) {
					System.out.println(i + "(" + images[i].getAccurate() + "): " + images[i].getText());
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * JNI call COM dll all in main thread is OKEY!!!
	 * @throws Exception
	 */
	private static void test22() throws Exception {
		System.loadLibrary("ocr");
		Runnable[] rs = {
			// new OcrThread("./temp/b0000013.jpg"), 
			// new OcrThread("./tmep/b0000029.jpg"),
			// new OcrThread("./temp/b0000042.jpg"),
			new OcrThread("./temp/b0170527.jpg"),
			new OcrThread("./temp/b1404750.jpg"),
			new OcrThread("./temp/b0033030.jpg"),
		};
		for (int i = 0; i < rs.length; i++) {
			rs[i].run();
		}
	}

	/**
	 * multithread JNI call C++ dll, it's OKEY!!!
	 * @throws Exception
	 */
	private static void test23() throws Exception {
		System.loadLibrary("winocr");
		for (int i = 0; i < 10; i++) {
			new Thread(new JniThread(i)).start();
			Thread.sleep(1000);
		}
	}

	private static final class JniThread implements Runnable {

		private int index;

		public JniThread(int index) {
			this.index = index;
		}

		public void run() {
			try {
				Random r = new Random();
				double x = r.nextDouble();
				double y = r.nextDouble();
				System.out.println(index + ": x = " + x + ", y = " + y + ", x + y = " + Ocr.add(x, y));
				System.out.println(index + ": x = " + x + ", y = " + y + ", x - y = " + Ocr.sub(x, y));
				System.out.println(index + ": x = " + x + ", y = " + y + ", x * y = " + Ocr.mul(x, y));
				if (y != 0) System.out.println(index + ": x = " + x + ", y = " + y + ", x / y = " + Ocr.div(x, y));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * multithread MODI using jawin.dll, it's OKEY!!!
	 * @throws Exception
	 */
	private static void test24() throws Exception {
		System.loadLibrary("ocr");
		// HelloDll.main(null);
		// CreatePpt.main(null);
		// HelloJava.main(null);
		Runnable[] rs = {new OcrThread("./temp/b0000013.jpg"), 
			new OcrThread("./temp/b0000029.jpg"),
			new OcrThread("./temp/b0000042.jpg"),
			new OcrThread("./temp/b0023991.jpg"),
			new OcrThread("./temp/b0029601.jpg"),
			new OcrThread("./temp/b0029680.jpg"),
			new OcrThread("./temp/b0029681.jpg"),
			new OcrThread("./temp/b0029732.jpg"),
			new OcrThread("./temp/b0029733.jpg"),
			new OcrThread("./temp/b0029792.jpg"),
		};
		for (int i = 0; i < rs.length; i++) {
			new Thread(rs[i]).start();
			// rs[i].run();
			Thread.sleep(1000);
		}

		/**
		 * if add the following codes, the jawin.dll will be blocked!!!
		 */
		/*
		BufferedReader _input = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			help();
			String choice = _input.readLine().trim();
			try {
				if("?".equals(choice)) help();
				else if("q".equals(choice)) System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		*/
	}

	private static void test25() throws Exception {
		System.loadLibrary("ocr");
		// Long[] idxs = {111755L, 338253L, 155282L, 613713L, 8683L, 146550L, 340929L, 219378L, 337563L, 390642L, 616596L, };
		// Long[] idxs = {338253L, };
		// Long[] idxs = {1950L, 2896L, 2915L, 2952L, };
		Long[] idxs = {266656L, };
		List<Image> images = SyllaServiceUtil.getImages(idxs);
		Combiner r = Combiner.combine(images);
		BufferedImage bi = r.getImage();
		File bif = Combiner.save(bi, "bi2.jpg");
		OcrImage[] items = Ocr.winocr(bif.getAbsolutePath());
		for (int i = 0; i < items.length; i++) {
			System.out.println(i + "(" + items[i].getAccurate() + "): " + items[i].getText());
		}
	}

}
