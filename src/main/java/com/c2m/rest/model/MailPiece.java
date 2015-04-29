package com.c2m.rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "mailPiece")
@XmlType(propOrder = { "uniqueId","barCode", "address", "status", "dateTime",
		"statusLocation", "mailPieceId" })
public class MailPiece {

	private String barCode;
	private String address;
	private String status;
	private String dateTime;
	private String statusLocation;
	private String uniqueId;
	private String mailPieceId;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getStatusLocation() {
		return statusLocation;
	}

	public void setStatusLocation(String statusLocation) {
		this.statusLocation = statusLocation;
	}

	public String getMailPieceId() {
		return mailPieceId;
	}

	public void setMailPieceId(String mailPieceId) {
		this.mailPieceId = mailPieceId;
	}

}