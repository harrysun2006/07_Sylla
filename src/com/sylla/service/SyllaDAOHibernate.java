package com.sylla.service;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sylla.Corp;
import com.sylla.Image;
import com.sylla.Info;
import com.sylla.InfoPK;
import com.sylla.Page;
import com.sylla.PagePK;

class SyllaDAOHibernate extends HibernateTemplate {

	public String getDatabaseLang() {
		Query q = getSession().getNamedQuery("DATABASE_LANG");
		return (String) q.uniqueResult();
	}

	public List<Corp> getCorps(Long count, String[] ss) {
		StringBuffer sb = new StringBuffer();
		sb.append("select c from Corp c where 0 = 1");
		if (ss != null) {
			for (int i = 0; i < ss.length; i++) sb.append(" or c.status = :s" + i);
		}
		Query q = getSession().createQuery(sb.toString());
		if (ss != null) {
			for (int i = 0; i < ss.length; i++) q.setParameter("s" + i, ss[i]);
		}
		if (count != null && count != 0) q.setMaxResults(count.intValue());
		return q.list();
	}

	public List<Corp> getVoidCorps(Long count) {
		Query q = getSession().createQuery("select c from Corp c where c.page > 0 and not exists (select info.id.corpId from Info info where info.id.corpId = c.id)");
		if (count != null && count != 0) q.setMaxResults(count.intValue());
		return q.list();
	}

	public Corp getNextCorp(Long idx, String[] ss) {
		StringBuffer sb = new StringBuffer();
		sb.append("select c from Corp c where c.index > :idx and (0 = 1");
		if (ss != null) {
			for (int i = 0; i < ss.length; i++) sb.append(" or c.status = :s" + i);
		}
		sb.append(") order by c.index asc");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("idx", idx);
		if (ss != null) {
			for (int i = 0; i < ss.length; i++) q.setParameter("s" + i, ss[i]);
		}
		q.setMaxResults(1);
		List list = q.list();
		return (list.size() > 0) ? (Corp) list.get(0) : null;
	}

	public Corp getVoidCorp(Long idx) {
		Query q = getSession().createQuery("select c from Corp c where c.index > :idx and not exists (select info.id.corpId from Info info where info.id.corpId = c.id) order by c.index asc");
		q.setParameter("idx", idx);
		q.setMaxResults(1);
		List list = q.list();
		return (list.size() > 0) ? (Corp) list.get(0) : null;
	}

	public Corp getCorp(Long id) {
		return (Corp) getSession().get(Corp.class, id);
	}

	public void addCorp(Corp corp) {
		getSession().save(corp);
	}

	public void saveCorp(Corp corp) {
		getSession().saveOrUpdate(corp);
	}

	public void updateCorp(Corp corp) {
		getSession().update(corp);
	}

	public int updateCorpPage(Corp corp) {
		return 
			getSession()
				.createSQLQuery("UPDATE CORP SET CORP_Page = :page, CORP_PageIdx = :idx WHERE CORP_ID = :id")
				.setParameter("page", corp.getPage())
				.setParameter("idx", corp.getPageIndex())
				.setParameter("id", corp.getId())
				.executeUpdate();
	}

	public List<Image> getImages(Long corpId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select im from Image im, Info info, Corp c ")
			.append("where im.code = info.code and info.corpId = c.corpId and c.corpId = :corpId");
		Query q = getSession()
			.createQuery(sb.toString())
			.setParameter("corpId", corpId);
		return q.list();
	}

	public List<Image> getImages(Info info) {
		StringBuffer sb = new StringBuffer();
		sb.append("select im from Image im, Info info where im.code = info.code");
		if (info != null) {
			if (info.getCorpId() != null && info.getCorpId() != 0) sb.append(" and info.id.corpId = :corpId");
			if (info.getType() != null) sb.append(" and info.id.type = :type");
			if (info.getCode() != null) sb.append(" and info.code = :code");
			if (info.getStatus() != null) sb.append(" and im.status = :status");
		}
		Query q = getSession().createQuery(sb.toString());
		if (info != null) {
			if (info.getCorpId() != null && info.getCorpId() != 0) q.setParameter("corpId", info.getCorpId());
			if (info.getType() != null) q.setParameter("type", info.getType());
			if (info.getCode() != null) q.setParameter("code", info.getCode());
			if (info.getStatus() != null) q.setParameter("status", info.getStatus());
		}
		return q.list();
	}

	public List<Image> getImages(Long count, String[] ss, Long idx) {
		StringBuffer sb = new StringBuffer();
		sb.append("select im from Image im where (0 = 1");
		if (ss != null) {
			for (int i = 0; i < ss.length; i++) sb.append(" or im.status = :s" + i);
		}
		sb.append(")");
		if (idx != null) sb.append(" and im.index > :idx");
		sb.append(" order by im.index asc");
		Query q = getSession().createQuery(sb.toString());
		if (ss != null) {
			for (int i = 0; i < ss.length; i++) q.setParameter("s" + i, ss[i]);
		}
		if (idx != null) q.setParameter("idx", idx);
		if (count != null && count != 0) q.setMaxResults(count.intValue());
		return q.list();
	}

	public List<Image> getImages(Long[] idxs) {
		StringBuffer sb = new StringBuffer();
		sb.append("select im from Image im where im.index in (0");
		if (idxs != null) {
			for (int i = 0; i < idxs.length; i++) sb.append(", :i" + i);
		}
		sb.append(")");
		sb.append(" order by im.index asc");
		Query q = getSession().createQuery(sb.toString());
		if (idxs != null) {
			for (int i = 0; i < idxs.length; i++) q.setParameter("i" + i, idxs[i]);
		}
		return q.list();
	}

	public List<Image> getImages(Long beginIdx, Long endIdx) {
		return (List<Image>) getSession()
			.createQuery("select im from Image im where im.index >= :beginIdx and im.index < :endIdx")
			.setParameter("beginIdx", beginIdx)
			.setParameter("endIdx", endIdx)
			.list();
	}

	public List<Image> getImages(Long count, String criteria, Long idx) {
		StringBuffer sb = new StringBuffer();
		sb.append("select im from Image im where im.accurate < 1000 ");
		if (criteria != null) {
			sb.append(" and ").append(criteria);
		}
		if (idx != null) sb.append(" and im.index > :idx");
		sb.append(" order by im.index asc");
		Query q = getSession().createQuery(sb.toString());
		if (idx != null) q.setParameter("idx", idx);
		if (count != null && count != 0) q.setMaxResults(count.intValue());
		return q.list();
	}

	public Long getImageCount(Long beginIdx, Long endIdx) {
		return (Long) getSession()
			.createQuery("select count(*) from Image im where im.index >= :beginIdx and im.index < :endIdx")
			.setParameter("beginIdx", beginIdx)
			.setParameter("endIdx", endIdx)
			.uniqueResult();
	}

	public Image getNextImage(Long idx, String[] ss) {
		StringBuffer sb = new StringBuffer();
		sb.append("select im from Image im where im.index > :idx and (0 = 1");
		if (ss != null) {
			for (int i = 0; i < ss.length; i++) sb.append(" or im.status = :s" + i);
		}
		sb.append(") order by im.index asc");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("idx", idx);
		if (ss != null) {
			for (int i = 0; i < ss.length; i++) q.setParameter("s" + i, ss[i]);
		}
		q.setMaxResults(1);
		List list = q.list();
		return (list.size() > 0) ? (Image) list.get(0) : null;
	}

	public Image getImage(String code) {
		return (Image) getSession().get(Image.class, code);
	}

	public void addImage(Image image) {
		getSession().save(image);
	}

	public void saveImage(Image image) {
		getSession().saveOrUpdate(image);
	}

	public void updateImage(Image image) {
		getSession().update(image);
	}

	public int updateImageStatus(Image image) {
		return getSession()
			.createSQLQuery("UPDATE IMG SET IMG_Status = :status WHERE IMG_Code = :code")
			.setParameter("status", image.getStatus())
			.setParameter("code", image.getCode())
			.executeUpdate();
	}

	public int updateImageText(Image image) {
		return getSession()
			.createSQLQuery("UPDATE IMG SET IMG_Text = :text, IMG_Accurate = :accurate, IMG_Status = :status WHERE IMG_Code = :code")
			.setParameter("text", image.getText())
			.setParameter("accurate", image.getAccurate())
			.setParameter("status", image.getStatus())
			.setParameter("code", image.getCode())
			.executeUpdate();
	}

	public Info getInfo(InfoPK pk) {
		return (Info) getSession().get(Info.class, pk);
	}

	public Long getInfoCount(Long corpId) {
		return 
			(Long) getSession()
				.createQuery("select count(*) from Info info where info.id.corpId = :corpId")
				.setParameter("corpId", corpId)
				.uniqueResult();
	}

	public void addInfo(Info info) {
		getSession().save(info);
	}

	public void saveInfo(Info info) {
		getSession().saveOrUpdate(info);
	}

	public void updateInfo(Info info) {
		getSession().update(info);
	}

	public List<Page> getPages(Long count, String[] ss) {
		StringBuffer sb = new StringBuffer();
		sb.append("select p from Page p where 0 = 1");
		if (ss != null) {
			for (int i = 0; i < ss.length; i++) sb.append(" or p.status = :s" + i);
		}
		sb.append(" order by p.index asc");
		Query q = getSession().createQuery(sb.toString());
		if (ss != null) {
			for (int i = 0; i < ss.length; i++) q.setParameter("s" + i, ss[i]);
		}
		if (count != null && count != 0) q.setMaxResults(count.intValue());
		return q.list();
	}

	public Page getNextPage(Long idx, String[] ss) {
		StringBuffer sb = new StringBuffer();
		sb.append("select p from Page p where p.index > :idx and (0 = 1");
		if (ss != null) {
			for (int i = 0; i < ss.length; i++) sb.append(" or p.status = :s" + i);
		}
		sb.append(") order by p.index asc");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("idx", idx);
		if (ss != null) {
			for (int i = 0; i < ss.length; i++) q.setParameter("s" + i, ss[i]);
		}
		q.setMaxResults(1);
		List list = q.list();
		return (list.size() > 0) ? (Page) list.get(0) : null;
	}

	public Page getPage(PagePK pk) {
		return (Page) getSession().get(Page.class, pk);
	}

	/**
	 * LockMode can not lock the record for update
	 * @param pk
	 * @param lock
	 * @return
	 */
	public Page getPage(PagePK pk, boolean lock) {
		if (lock) return (Page) getSession().get(Page.class, pk, LockMode.UPGRADE);
		else return (Page) getSession().get(Page.class, pk);
	}

	public void addPage(Page page) {
		getSession().save(page);
	}

	public void savePage(Page page) {
		getSession().saveOrUpdate(page);
	}

	public void updatePage(Page page) {
		getSession().update(page);
	}

}
