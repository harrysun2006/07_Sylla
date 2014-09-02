package com.sylla.service;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.sylla.Corp;
import com.sylla.Image;
import com.sylla.Info;
import com.sylla.Page;
import com.sylla.util.SpringUtil;

public class SyllaServiceUtil {

	public static final String BEAN_NAME = "syllaServiceUtil";

	private SyllaService syllaService;

	public static SyllaService getSyllaService() {
		ApplicationContext ctx = SpringUtil.getContext();
		SyllaServiceUtil util = (SyllaServiceUtil) ctx.getBean(BEAN_NAME);
		SyllaService service = util.syllaService;
		return service;
	}

	public void setSyllaService(SyllaService syllaService) {
		this.syllaService = syllaService;
	}

	public static List<Corp> getCorps(Long count, String[] ss) {
		return getSyllaService().getCorps(count, ss);
	}

	public static List<Corp> getVoidCorps(Long count) {
		return getSyllaService().getVoidCorps(count);
	}

	public static Corp getNextCorp(Long idx, String[] ss) {
		return getSyllaService().getNextCorp(idx, ss);
	}

	public static Corp getVoidCorp(Long idx) {
		return getSyllaService().getVoidCorp(idx);
	}

	public static Corp getCorp(Long id) {
		return getSyllaService().getCorp(id);
	}

	public static void addCorp(Corp corp) {
		getSyllaService().addCorp(corp);
	}

	public static void saveCorp(Corp corp) {
		getSyllaService().saveCorp(corp);
	}

	public static void updateCorp(Corp corp) {
		getSyllaService().updateCorp(corp);
	}

	public static int updateCorpPage(Corp corp) {
		return getSyllaService().updateCorpPage(corp);
	}

	public static void saveCorpAndInfos(Corp corp, List<Info> infos) {
		getSyllaService().saveCorpAndInfos(corp, infos);
	}

	public static void verifyCorpAndInfos(Corp corp, List<Info> infos) {
		getSyllaService().verifyCorpAndInfos(corp, infos);
	}

	public static List<Image> getImages(Long corpId) {
		return getSyllaService().getImages(corpId);
	}

	public static List<Image> getImages(Info info) {
		return getSyllaService().getImages(info);
	}

	public static List<Image> getImages(Long count, String[] ss) {
		return getImages(count, ss, null);
	}

	public static List<Image> getImages(Long count, String[] ss, Long idx) {
		return getSyllaService().getImages(count, ss, idx);
	}

	public static List<Image> getImages(Long[] idxs) {
		return getSyllaService().getImages(idxs);
	}

	public static List<Image> getImages(Long beginIdx, Long endIdx) {
		return getSyllaService().getImages(beginIdx, endIdx);
	}

	public static List<Image> getImages(Long count, String criteria, Long idx) {
		return getSyllaService().getImages(count, criteria, idx);
	}

	public static Long getImageCount(Long beginIdx, Long endIdx) {
		return getSyllaService().getImageCount(beginIdx, endIdx);
	}

	public static Image getNextImage(Long idx, String[] ss) {
		return getSyllaService().getNextImage(idx, ss);
	}

	public static Image getImage(String code) {
		return getSyllaService().getImage(code);
	}

	public static void addImage(Image image) {
		getSyllaService().addImage(image);
	}

	public static void updateImage(Image image) {
		getSyllaService().updateImage(image);
	}

	public static int updateImageStatus(Image image) {
		return getSyllaService().updateImageStatus(image);
	}

	public static int updateImageText(Image image) {
		return getSyllaService().updateImageText(image);
	}

	public static Long getInfoCount(Long corpId) {
		return getSyllaService().getInfoCount(corpId);
	}

	public static List<Page> getPages(Long count, String[] ss) {
		return getSyllaService().getPages(count, ss);
	}

	public static Page getNextPage(Long idx, String[] ss) {
		return getSyllaService().getNextPage(idx, ss);
	}

	public static Page getPage(Long pageId, String type) {
		return getPage(pageId, type);
	}

	public static void updatePage(Page page) {
		getSyllaService().updatePage(page);
	}

	public static void savePageAndCorps(Page page, List<Corp> corps) {
		getSyllaService().savePageAndCorps(page, corps);
	}

	public static void verifyPageAndCorps(Page page, List<Corp> corps) {
		getSyllaService().verifyPageAndCorps(page, corps);
	}

}
