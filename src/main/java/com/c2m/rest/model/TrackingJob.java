package com.c2m.rest.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "tracking")
public class TrackingJob {
	List<MailPiece> mailPiece = new ArrayList<MailPiece>();
	
	
	public List<MailPiece> getMailPiece() {
		return mailPiece;
	}

	public void setMailPiece(List<MailPiece> mailPiece) {
		this.mailPiece = mailPiece;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (MailPiece piece : mailPiece) {
			sb.append("----------------------------Start--------------------------------");
			if(piece.getUniqueId() != null){
				sb.append("\n");
				sb.append("<uniqueId>" + piece.getUniqueId() + "</uniqueId>");
			}
			sb.append("\n");
			sb.append("<barcode>" + piece.getBarCode() + "</barcode>");
			sb.append("\n");
			sb.append("<address>" + piece.getAddress() + "</address>");
			sb.append("\n");
			sb.append("<status>" + piece.getStatus() + "</status>");
			sb.append("\n");
			sb.append("<dateTime>" + piece.getDateTime() + "</dateTime>");
			sb.append("\n");
			sb.append("<statusLocation>" + piece.getStatusLocation()
					+ "</statusLocation>");
			sb.append("\n");
			sb.append("----------------------------END--------------------------------");
		}
		return sb.toString();
	}

}