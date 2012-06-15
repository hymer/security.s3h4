package com.epic.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * Bean Utility for PMS Application.<br/>
 * Copyright(c) 2011 China National Petroleum Corporation ,
 * http://www.cnpc.com.cn
 * 
 * @author Zhou Zaiqing
 * @since 2010/10/29
 */
public class BeanUtil {

	/**
	 * Format by src object type. java.util.date / number.
	 * 
	 * @param src
	 *            the src
	 * @param format
	 *            the format
	 * @return the string
	 */
	public static String formatByType(Object src, String format) {
		String result = null;
		if (src instanceof java.util.Date) {
			result = Formatters.formatDate((Date)src);
		} else if (src instanceof Number) {
			result = Formatters.formatNumber((Number) src);
		}
		return result;
	}

	/**
	 * Copy properties (copy and modify from apache commons lib).
	 * 
	 * @param dest
	 *            the dest
	 * @param orig
	 *            the orig
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	@SuppressWarnings("all")
	public static void copyProperties(Object dest, Object orig) throws IllegalAccessException,
		InvocationTargetException {

		// Validate existence of the specified beans
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}

		// Copy the properties, converting as necessary
		if (orig instanceof DynaBean) {
			DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
					Object value = ((DynaBean) orig).get(name);
					BeanUtils.copyProperty(dest, name, value);
				}
			}
		} else if (orig instanceof Map) {
			Iterator entries = ((Map) orig).entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				if (PropertyUtils.isWriteable(dest, name)) {
					BeanUtils.copyProperty(dest, name, entry.getValue());
				}
			}
		} else /* if (orig is a standard JavaBean) */{
			PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(orig);
			if (dest instanceof Map) {
				for (int i = 0; i < origDescriptors.length; i++) {
					String name = origDescriptors[i].getName();
					if ("class".equals(name)) {
						continue; // No point in trying to set an object's class
					}
					if (PropertyUtils.isReadable(orig, name)) {
						try {
							Object value = PropertyUtils.getSimpleProperty(orig, name);
							((Map) dest).put(name, value);
						} catch (NoSuchMethodException e) {
							// Should not happen
						}
					}
				}
			} else {
				for (int i = 0; i < origDescriptors.length; i++) {
					String name = origDescriptors[i].getName();
					if ("class".equals(name)) {
						continue; // No point in trying to set an object's class
					}
					if (origDescriptors[i].getPropertyType().equals(java.util.Date.class)
									|| origDescriptors[i].getPropertyType().equals(java.sql.Timestamp.class)
									|| origDescriptors[i].getPropertyType().equals(java.sql.Date.class)) {
						continue;
					}
					if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
						try {
							Object value = PropertyUtils.getSimpleProperty(orig, name);
							Class dtoClass = PropertyUtils.getPropertyType(dest, name);
//							if (PMSDTO.class.equals(dtoClass.getSuperclass())) {
//								continue;
//							}
							BeanUtils.copyProperty(dest, name, value);
						} catch (NoSuchMethodException e) {
							// Should not happen
						} catch (Exception e) {
							// Should not happen
						}
					}
				}
			}
		}
	}

}
