package com.c2m.rest.commons;

public class Config {
	// Stage URL - https://stage-rest.click2mail.com/molpro/
	// Production URL - https://rest.click2mail.com/molpro/

	public final static String BASE_URL = "https://stage-rest.click2mail.com/molpro/";
	// Addresslist rest api base url
	public static String ADDRESS_LIST_URL = BASE_URL + "addressLists";
	// Address correction rest api base url
	public static String ADDRESS_CORRECTION_URL = BASE_URL + "addressLists";
	// Document rest api base url
	public static String DOCUMENT_URL = BASE_URL + "documents";
	// Job rest api base url
	public static String JOB_URL = BASE_URL + "jobs";
	// Account rest api base url
	public static String ACCOUNT_URL = BASE_URL + "account";

}
