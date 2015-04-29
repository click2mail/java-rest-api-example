package com.c2m.rest;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.junit.Before;
import org.junit.Test;

import com.c2m.rest.commons.Config;
import com.c2m.rest.commons.Utils;
import com.c2m.rest.model.AddressList;
import com.c2m.rest.model.Document;
import com.c2m.rest.model.Job;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

public class MolProRestApiTest {

	ClientConfig config;
	Client client;

	@Before
	public void init() {
		String username = (String) System.getProperty("C2M_USERNAME");
		String password = (String) System.getProperty("C2M_PASSWORD");
		if (username == null || password == null) {
			System.err
					.println("Environment lookup failed. Please set system properties named 'C2M_USERNAME' and 'C2M_PASSWORD'.");
			System.exit(1);
		}
		config = new DefaultClientConfig();
		client = Client.create(config);
		client.addFilter(new HTTPBasicAuthFilter(username, password));
		ClientResponse response = getResponse(
				Config.ACCOUNT_URL + "/authorize",
				MediaType.APPLICATION_FORM_URLENCODED_TYPE.toString(), false,
				null);
		if (response.getStatus() == 401) {
			System.err.println("Invalid credentials");
			System.exit(1);
		}

	}

	@Test
	public void submitJobTest() {
		try {
			/*
			 * To create job required to do following information 1) Account
			 * with Click2mail 2) Address List Id 3) Document Id
			 */
			// create address list - POST /addressLists
			String addressListId = createAddressList();

			// Create Document - POST /documents
			String documentId = createDocument();

			// Create Job now - POST /jobs
			String jobId = createJob(addressListId, documentId);// "37695";
			// System.out.println("Job Created Successfully. JobId is:"+ jobId);

			// Check the job cost - GET /jobs/{id}/cost
			double jobCost = getJobCost(jobId);
			System.out.println("Job(" + jobId + ") cost is:" + jobCost);

			// Submit job - POST /jobs/{id}/submit
			String paymentType = "Credit Card";//
			submitJob(jobId, paymentType);

			// Check Job Cost - GET /jobs/{id}
			String status = getJobStatus(jobId);
			System.out.println("Job(" + jobId + ") status is:" + status);

			// Request Job Information - GET /jobs/info/{id}
			Job jobInfo = getJobInfo(jobId);
			System.out.println("Job Info Details are:" + jobInfo);

		} catch (RuntimeException e) {
			System.err.println("Error message got from server :"
					+ e.getMessage());
		}
	}

	private Job getJobInfo(String jobId) {
		Job output = new Job();
		try {
			String jobCostURL = Config.JOB_URL + "/info/" + jobId;
			ClientResponse response = getResponse(jobCostURL, null, true, null);
			if (response.getStatus() == 500) {
				throw new RuntimeException(
						"Exception in Get JobInfo rest api call. Error message : "
								+ response.getEntity(String.class));
			}
			output = response.getEntity(Job.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	private String getJobStatus(String jobId) {
		String jobStatus = null;
		try {
			String jobCostURL = Config.JOB_URL + "/" + jobId;
			ClientResponse response = getResponse(jobCostURL, null, true, null);
			if (response.getStatus() == 500) {
				throw new RuntimeException(
						"Exception in Get JobStatus rest api call. Error message : "
								+ response.getEntity(String.class));
			}
			Job output = response.getEntity(Job.class);
			jobStatus = output.getDescription();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobStatus;
	}

	public double getJobCost(String jobId) {
		Double jobCost = new Double(0);
		try {
			String jobCostURL = Config.JOB_URL + "/" + jobId + "/cost";
			ClientResponse response = getResponse(jobCostURL, null, true, null);
			if (response.getStatus() == 500) {
				throw new RuntimeException(
						"Exception in Get JobCost rest api call. Error message : "
								+ response.getEntity(String.class));
			}

			Job output = response.getEntity(Job.class);
			if (output.getStatus().equals("9")) {
				throw new RuntimeException(
						"Exception in Get JobCost rest api call. Error message : "
								+ output.getDescription());
			} else {
				jobCost = output.getCost();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobCost;
	}

	// In 'src/test/resources/xmls/addressList.xml' addressListName should be
	// unique
	public String createAddressList() {
		String addressListId = null;
		try {
			String xml = "<addressList> "
					+ "	<addressMappingId>2</addressMappingId> "
					+ "	<addresses> " + "		<address> "
					+ "			<first_name>john</first_name> "
					+ "			<last_name>smith</last_name> "
					+ "			<organization>my business</organization> "
					+ "			<address1>PO Box 100729</address1> "
					+ "			<address2></address2> " + "			<address3></address3> "
					+ "			<city>Arlington</city> " + "			<state>va</state> "
					+ "			<zip>22210-3729</zip> "
					+ "			<country_non-us></country_non-us> " + "		</address> "
					+ "	</addresses> " + "</addressList> ";

			ClientResponse response = getResponse(Config.ADDRESS_LIST_URL,
					MediaType.APPLICATION_XML, false, xml);
			if (response.getStatus() == 400) {// Throwing error because some
												// times due to misdata there is
												// possible of exceptions
				throw new RuntimeException(
						"Exception in Create AddressList rest api call. Error message : "
								+ response.getEntity(String.class));
			}
			AddressList output = response.getEntity(AddressList.class);
			if (output.getStatus().equals("9")) {
				throw new RuntimeException(
						"Exception in Create AddressList rest api call. Error message : "
								+ output.getDescription());
			} else {
				addressListId = output.getId().toString();
				System.out
						.println("Address List uploaded successfully and the Address List Id is:"
								+ addressListId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addressListId;
	}

	public String createDocument() {
		String documentId = null;
		try {
			FileDataBodyPart fdp = new FileDataBodyPart("file", new File(
					"src/test/resources/docs/Postcard_5x8-Double_Sided.pdf"));
			FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
			formDataMultiPart.field("documentClass", "Postcard 5 X 8");
			formDataMultiPart.field("documentFormat", "PDF");
			formDataMultiPart.bodyPart(fdp);
			ClientResponse response = getResponse(Config.DOCUMENT_URL,
					MediaType.MULTIPART_FORM_DATA, false, formDataMultiPart);

			if (response.getStatus() == 500 || response.getStatus() == 400) {
				throw new RuntimeException(
						"Exception in Create Document rest api call. Error message : "
								+ response.getEntity(String.class));
			}
			Document output = response.getEntity(Document.class);
			if (!output.getStatus().equals("0")) {
				throw new RuntimeException(
						"Exception in Create Document rest api call. Error message : "
								+ output.getDescription());
			} else {
				documentId = output.getId().toString();
				System.out
						.println("Document uploaded successfully and the document Id is:"
								+ documentId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return documentId;
	}

	public String createJob(String addressListId, String documentId) {
		String jobId = null;
		try {
			Map<String, String> mapDetails = new HashMap<String, String>();
			mapDetails.put("documentClass", "Postcard 5 x 8");
			mapDetails.put("layout", "Double Sided Postcard");
			mapDetails.put("productionTime", "Next Day");
			mapDetails.put("envelope", " ");
			mapDetails.put("color", "Full Color");
			mapDetails.put("paperType", "White Matte with Gloss UV Finish");
			mapDetails.put("printOption", "Printing both sides");
			mapDetails.put("mailClass", "First Class");
			mapDetails.put("documentId", documentId);
			mapDetails.put("addressId", addressListId);
			ClientResponse response = getResponse(Config.JOB_URL,
					MediaType.APPLICATION_FORM_URLENCODED_TYPE.toString(),
					false, Utils.getMutliValuedMap(mapDetails));
			if (response.getStatus() == 500 || response.getStatus() == 400) {
				String errorMessage = null;
				try {
					errorMessage = response.getEntity(String.class);
				} catch (Exception e) {
					Job job = response.getEntity(Job.class);
					errorMessage = job.getDescription();
				}

				throw new RuntimeException(
						"Exception in Create Job rest api call. Error message : "
								+ errorMessage);

			}
			Job job = response.getEntity(Job.class);
			if (!job.getStatus().equals("0")) {
				throw new RuntimeException(
						"Exception in Create Job rest api call. Error message : "
								+ job.getDescription());
			} else {
				jobId = job.getId().toString();
				System.out
						.println("Job Created Successfully and the Job Id is:"
								+ jobId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobId;
	}

	public void submitJob(String jobId, String paymentType) {
		Map<String, String> submitJobDetails = new HashMap<String, String>();
		submitJobDetails.put("billingType", paymentType);

		if (paymentType.equalsIgnoreCase("credit card")) {
			submitJobDetails.put("billingName", "Click 2 mail");
			submitJobDetails.put("billingCompany", "Click2mail");
			submitJobDetails.put("billingAddress1", "3103 10th St N");
			submitJobDetails.put("billingAddress2", "Suite 201");
			submitJobDetails.put("billingAddress3", "testing");
			submitJobDetails.put("billingCity", "Arlington");
			submitJobDetails.put("billingState", "VA");
			submitJobDetails.put("billingZip", "22201-2191");
			submitJobDetails.put("billingCcType", "VI");
			submitJobDetails.put("billingNumber", "4111111111111111");
			submitJobDetails.put("billingMonth", "08");
			submitJobDetails.put("billingYear", "2018");
			submitJobDetails.put("billingCvv", "123");
		}

		// If payment type credit card then you have pass the billing details in
		// the map

		String submitJobURl = Config.JOB_URL + "/" + jobId + "/submit";
		ClientResponse submitJobresponse = getResponse(submitJobURl,
				MediaType.APPLICATION_FORM_URLENCODED_TYPE.toString(), false,
				Utils.getMutliValuedMap(submitJobDetails));

		// for the bad request checking - 400
		if (submitJobresponse.getStatus() == 400) {
			throw new RuntimeException(
					"Exception in Submit Job rest api call. Error message : "
							+ submitJobresponse.getEntity(String.class));
		}
		Job submitJob = submitJobresponse.getEntity(Job.class);
		// check for other status. If there then throw error
		if (!"0".equals(submitJob.getStatus())) {
			throw new RuntimeException(
					"Exception in Submit Job rest api call. Error message : "
							+ submitJob.getDescription());
		}
		System.out.println("Job Submited successfully.");
	}

	public ClientResponse getResponse(String url, String contentType,
			boolean isGetMethod, Object params) {
		try {
			WebResource resource = client.resource(UriBuilder.fromUri(url)
					.build());
			ClientResponse response = null;
			if (isGetMethod) {
				response = resource.get(ClientResponse.class);
			} else {
				response = resource.type(contentType).post(
						ClientResponse.class, params);
			}
			return response;
		} catch (Exception e) {
			throw new RuntimeException(
					"Exception in Submit Job rest api call. Error message : "
							+ e.getMessage());
		}

	}

	public String getId(ClientResponse response, String rootTag)
			throws ClientHandlerException, UniformInterfaceException, Exception {
		Map<String, Object> responseMap = Utils.getMapObjFromXMLString(response
				.getEntity(String.class));
		System.out.println("Response for " + rootTag + " :" + responseMap);
		LinkedHashMap<String, Object> documentResponse = (LinkedHashMap<String, Object>) responseMap
				.get(rootTag);
		if (documentResponse.get("status") != null
				&& ((Integer) documentResponse.get("status")) == 0) {
			return (String) documentResponse.get("id");
		} else {
			throw new RuntimeException(
					(String) documentResponse.get("description"));
		}
	}
}
