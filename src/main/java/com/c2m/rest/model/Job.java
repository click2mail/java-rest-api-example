package com.c2m.rest.model;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "job")
public class Job extends BasicModel {

	private double cost;
	private TrackingJob tracking;

	private String invoice;
	private String jobStatus;
	private String dateUpdated;
	private String product;
	private String document;
	private String addressList;
	private String documentClass;
	private String layout;
	private String productionTime;
	private String envelope;
	private String color;
	private String paperType;
	private String printOption;
	private String mailClass;

	private Long documentId;
	private Long addressListId;
	private Long addressMappingId;

	private Map<Integer, String> mappingFields;

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public Long getAddressListId() {
		return addressListId;
	}

	public void setAddressListId(Long addressListId) {
		this.addressListId = addressListId;
	}

	public Long getAddressMappingId() {
		return addressMappingId;
	}

	public void setAddressMappingId(Long addressMappingId) {
		this.addressMappingId = addressMappingId;
	}

	public Map<Integer, String> getMappingFields() {
		return mappingFields;
	}

	public void setMappingFields(Map<Integer, String> mappingFields) {
		this.mappingFields = mappingFields;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(String dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getAddressList() {
		return addressList;
	}

	public void setAddressList(String addressList) {
		this.addressList = addressList;
	}

	public String getDocumentClass() {
		return documentClass;
	}

	public void setDocumentClass(String documentClass) {
		this.documentClass = documentClass;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getProductionTime() {
		return productionTime;
	}

	public void setProductionTime(String productionTime) {
		this.productionTime = productionTime;
	}

	public String getEnvelope() {
		return envelope;
	}

	public void setEnvelope(String envelope) {
		this.envelope = envelope;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPaperType() {
		return paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	public String getPrintOption() {
		return printOption;
	}

	public void setPrintOption(String printOption) {
		this.printOption = printOption;
	}

	public String getMailClass() {
		return mailClass;
	}

	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public TrackingJob getTracking() {
		return tracking;
	}

	public void setTracking(TrackingJob tracking) {
		this.tracking = tracking;
	}

	@Override
	public String toString() {
		// TODO - Want to print more details, need to be concatenate fields
		// here.
		return "addressList=" + addressList + ", document=" + document
				+ ", addressMappingId=" + addressMappingId + ", invoice="
				+ invoice + ", status=" + jobStatus;
	}

}
