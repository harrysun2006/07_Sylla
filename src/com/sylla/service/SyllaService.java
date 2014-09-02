package com.sylla.service;

import java.util.Iterator;
import java.util.List;

import com.sylla.Corp;
import com.sylla.Image;
import com.sylla.Info;
import com.sylla.Page;

class SyllaService {

	private SyllaDAOHibernate syllaDAO;

	public void setSyllaDAO(SyllaDAOHibernate syllaDAO) {
		this.syllaDAO = syllaDAO;
	}

	public List<Corp> getCorps(Long count, String[] ss) {
		return syllaDAO.getCorps(count, ss);
	}

	public List<Corp> getVoidCorps(Long count) {
		return syllaDAO.getVoidCorps(count);
	}

	public Corp getNextCorp(Long idx, String[] ss) {
		return syllaDAO.getNextCorp(idx, ss);
	}

	public Corp getVoidCorp(Long idx) {
		return syllaDAO.getVoidCorp(idx);
	}

	public Corp getCorp(Long id) {
		return syllaDAO.getCorp(id);
	}

	public void addCorp(Corp corp) {
		syllaDAO.addCorp(corp);
	}

	public void saveCorp(Corp corp) {
		syllaDAO.saveCorp(corp);
	}

	public void updateCorp(Corp corp) {
		syllaDAO.updateCorp(corp);
	}

	public int updateCorpPage(Corp corp) {
		return syllaDAO.updateCorpPage(corp);
	}

	public void saveCorpAndInfos(Corp corp, List<Info> infos) {
		Info info;
		Image image;
		if (infos != null) {
			for (Iterator<Info> it = infos.iterator(); it.hasNext(); ) {
				info = it.next();
				info.setCorpId(corp.getId());
				syllaDAO.addInfo(info);
				try {
					image = syllaDAO.getImage(info.getCode());
					if (image == null) {
						image = new Image();
						image.setCode(info.getCode());
						image.setStatus(Image.IS_INITIAL);
						syllaDAO.addImage(image);
					}
				} catch(Throwable tt) {}
			}
		}
		corp.setStatus(Corp.CS_DOWNLOADED);
		syllaDAO.updateCorp(corp);
	}

	public void verifyCorpAndInfos(Corp corp, List<Info> infos) {
		Info info;
		Image image;
		if (infos != null) {
			long infoCount = syllaDAO.getInfoCount(corp.getId());
			if (infoCount == 0) {
				for (Iterator<Info> it = infos.iterator(); it.hasNext(); ) {
					info = it.next();
					info.setCorpId(corp.getId());
					syllaDAO.addInfo(info);
					try {
						image = syllaDAO.getImage(info.getCode());
						if (image == null) {
							image = new Image();
							image.setCode(info.getCode());
							image.setStatus(Image.IS_INITIAL);
							syllaDAO.addImage(image);
						}
					} catch(Throwable tt) {}
				}
			}
		}
		corp.setStatus(Corp.CS_DOWNLOADED);
		syllaDAO.updateCorp(corp);
	}

	public List<Image> getImages(Long corpId) {
		return syllaDAO.getImages(corpId);
	}

	public List<Image> getImages(Info info) {
		return syllaDAO.getImages(info);
	}

	public List<Image> getImages(Long count, String[] ss, Long idx) {
		return syllaDAO.getImages(count, ss, idx);
	}

	public List<Image> getImages(Long[] idxs) {
		return syllaDAO.getImages(idxs);
	}

	public List<Image> getImages(Long beginIdx, Long endIdx) {
		return syllaDAO.getImages(beginIdx, endIdx);
	}

	public List<Image> getImages(Long count, String criteria, Long idx) {
		return syllaDAO.getImages(count, criteria, idx);
	}

	public Long getImageCount(Long beginIdx, Long endIdx) {
		return syllaDAO.getImageCount(beginIdx, endIdx);
	}

	public Image getNextImage(Long idx, String[] ss) {
		return syllaDAO.getNextImage(idx, ss);
	}

	public Image getImage(String code) {
		return syllaDAO.getImage(code);
	}

	public void addImage(Image image) {
		syllaDAO.addImage(image);
	}

	public void updateImage(Image image) {
		syllaDAO.updateImage(image);
	}

	public int updateImageStatus(Image image) {
		return syllaDAO.updateImageStatus(image);
	}

	public int updateImageText(Image image) {
		return syllaDAO.updateImageText(image);
	}

	public Long getInfoCount(Long corpId) {
		return syllaDAO.getInfoCount(corpId);
	}

	public List<Page> getPages(Long count, String[] ss) {
		return syllaDAO.getPages(count, ss);
	}

	public Page getNextPage(Long idx, String[] ss) {
		return syllaDAO.getNextPage(idx, ss);
	}

	public Page getPage(Long pageId, String type) {
		return getPage(pageId, type);
	}

	public void updatePage(Page page) {
		syllaDAO.updatePage(page);
	}

	public void savePageAndCorps(Page page, List<Corp> corps) {
		Corp corp;
		if (corps != null) {
			for (Iterator<Corp> it = corps.iterator(); it.hasNext(); ) {
				corp = it.next();
				corp.setPage(page.getPageId());
				corp.setStatus(Corp.CS_INITIAL);
				syllaDAO.addCorp(corp);
			}
		}
		page.setStatus(Page.PS_DOWNLOADED);
		syllaDAO.updatePage(page);
	}

	public void verifyPageAndCorps(Page page, List<Corp> corps) {
		Corp corp;
		if (corps != null) {
			for (Iterator<Corp> it = corps.iterator(); it.hasNext(); ) {
				corp = it.next();
				corp.setPage(page.getPageId());
				if (syllaDAO.updateCorpPage(corp) != 1) {
					corp.setStatus(Corp.CS_INITIAL);
					syllaDAO.addCorp(corp);
				}
			}
		}
		page.setStatus(Page.PS_VERIFIED);
		syllaDAO.updatePage(page);
	}

}
