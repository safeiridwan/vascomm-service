package com.vascomm.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestUtil extends PageRequest {
	private PageRequestUtil(int page, int size, Sort sort) {
		super(page, size, sort);
	}

	public static PageRequestUtil of(int page, int size, Sort sort) {
		return new PageRequestUtil(page, size, sort);
	}

	public static PageRequestUtil of(int page, int size) {
		return new PageRequestUtil(page, size, Sort.unsorted());
	}

	public static PageRequestUtil of(PageInput pageInput) {
		Sort sort;
		String orderBy = pageInput.getOrderBy();
		if (orderBy != null && !orderBy.isEmpty()) {
			String[] listOrderBy = orderBy.trim().split(",");
			String[] prop = new String[listOrderBy.length];
			Sort.Direction dir = Sort.Direction.ASC;

			int length = listOrderBy.length;
			if (length > 1) {
				prop = new String[listOrderBy.length - 1];
			}

			for (int i = 0; i < length; i++) {
				if (length > 1) {
					if (i < length - 1) {
						prop[i] = listOrderBy[i].trim();
					} else {
						if (listOrderBy[i].trim().equalsIgnoreCase("DESC")) {
							dir = Sort.Direction.DESC;
						} else if (listOrderBy[i].trim().equalsIgnoreCase("ASC")){
							dir = Sort.Direction.ASC;
						} else {
							prop[i] = listOrderBy[i].trim();
						}
					}
				} else {
					prop[i] = listOrderBy[i].trim();
				}
			}

			sort = Sort.by(dir, prop);
		} else {
			sort = Sort.unsorted();
		}

		double parsePage = (double) pageInput.getSkip() / pageInput.getTake();
		pageInput.setPage((int) Math.ceil(parsePage));
		pageInput.setSize(pageInput.getTake());
		return new PageRequestUtil(pageInput.getPage(), pageInput.getSize(), sort);
	}
}
