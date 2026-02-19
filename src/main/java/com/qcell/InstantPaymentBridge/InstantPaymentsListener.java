package com.qcell.InstantPaymentBridge;

import com.google.gson.Gson;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;
import com.qcell.InstantPaymentBridge.entity.*;
import com.qcell.InstantPaymentBridge.nscore.SendPushPaymentRequest;
import com.qcell.InstantPaymentBridge.nscore.SignedXMLModule;
import com.qcell.InstantPaymentBridge.payment.FromUser;
import com.qcell.InstantPaymentBridge.payment.Payment;
import com.qcell.InstantPaymentBridge.payment.QMoneyLoginRequest;
import com.qcell.InstantPaymentBridge.payment.QMoneyLoginRequestData;
import com.qcell.InstantPaymentBridge.payment.QMoneyLoginResponse;
import com.qcell.InstantPaymentBridge.payment.QMoneyPaymentRequest;
import com.qcell.InstantPaymentBridge.payment.QMoneyPaymentRequestData;
import com.qcell.InstantPaymentBridge.payment.QMoneyPaymentResponse;
import com.qcell.InstantPaymentBridge.payment.ToUser;
import com.qcell.InstantPaymentBridge.nscore.ReceiverPaymentResponse;
import com.qcell.InstantPaymentBridge.service.InstantCasApiClient;
import com.qcell.InstantPaymentBridge.service.InstantCertApiClient;
import com.qcell.InstantPaymentBridge.service.InstantPaymentApiClient;
import com.qcell.InstantPaymentBridge.service.InstantPaymentInterface;
import com.qcell.InstantPaymentBridge.service.QmoneyPaymentApiClient;
import com.qcell.InstantPaymentBridge.service.QmoneyPaymentInterface;

import okhttp3.Credentials;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.regexp.RE;

import retrofit2.Response;
import sun.security.validator.ValidatorException;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilderFactory;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

//InstantPaymentsListener
public class InstantPaymentsListener extends HttpServlet {

    static {
        System.setProperty("log4j.configurationFile", "/usr/local/estashcore/log4j2.xml");
    }

    private static final Logger logger = LogManager.getLogger(InstantPaymentsListener.class);
    /*
     * please add configuration options here so that they are read from a config
     * file
     */

    String keystorePath = "/usr/local/estashcore/security/keystore.jks";
    String publicKeyPath = "/usr/local/estashcore/security/";
    String logDestination = "/usr/local/estashcore/security";

    // String SwitchURL = "http://172.16.10.177:9001/v1/iso20022/incoming";
    String SwitchURL = "http://172.16.10.123:9001/v1/iso20022/incoming";

    String keystorePassword = "qcellsl";
    String keyAlias = "qcellsl";
    String keyPassword = "qcellsl";

     String CertificateURL = "http://localhost:9001/v1/cert";
    //String CertificateURL = "http://172.16.10.177:9001/v1/cert";

    String AccountAliasURL = "http://172.16.10.177:23473/api/fp/customer/accounts";

    /*
     * String QMoneyLoginURL = "http://localhost:23472/qmoney/login"; String
     * QMoneyB2WUrl = "http://localhost:23472/qmoney/sendmoney"; String
     * QMoneyUserName = "test"; String QMoneyPassword = "test";
     */
    //Adapter URL http://192.168.50.11:8101
    String QMoneyLoginURL = "http://192.168.50.11:8101/qmoney/login";
    String QMoneyB2WUrl = "http://192.168.50.11:8101/qmoney/sendmoney";
    String QMoneyUserName = "78778712";
    String QMoneyPassword = "IPS@2024";

    private static final long serialVersionUID = 1592128437444248901L;

    public void initialize() {

        if (!System.getProperty("os.name").contains("Windows")) {
            String keystorePath = "/usr/local/estashcore/security/keystore.jks";
            String logDestination = "/usr/local/estashcore";
        }

        String logFileDate = "";
        Date curDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        logFileDate = formatter.format(curDate);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String action = request.getParameter("action");
        String receiverAlias = request.getParameter("receiverAlias");
        String senderAlias = request.getParameter("senderAlias");
        String receiverBic = request.getParameter("receiverBic");
        String amount = request.getParameter("amount");

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        if ((action != null || !action.trim().isEmpty())) {

            if (action.equals("customerAlias") && receiverAlias != null && !receiverAlias.trim().isEmpty()) {

                logger.info("customer input:\n" + receiverAlias);

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");

                // get account details of msisdn
                InstantPaymentInterface apiService = InstantCasApiClient.getCasClient()
                        .create(InstantPaymentInterface.class);
                Response<NSCustomerAliasResponse> responseResponse = apiService
                        .getAccountByPhoneNumber(headers, new NSCustomerAliasRequest(receiverAlias)).execute();

                if (responseResponse.isSuccessful() && responseResponse.body() != null) {

                    String json = new Gson().toJson(responseResponse.body());
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);

                } else {
                    logger.info(
                            "NS_LOGS: Fetch Customer Receiver detail Fail" + responseResponse.errorBody().charStream());
                    System.out.print(
                            "NS_LOGS: Fetch Customer Receiver detail Fail" + responseResponse.errorBody().charStream());
                    response.getWriter().write("{ error: " + "NS_LOGS: Fetch Customer Receiver detail Fail"
                            + responseResponse.errorBody().charStream() + "}");
                }

            } else if (action.equals("pushPayment") && receiverAlias != null && !receiverAlias.isEmpty()
                    && senderAlias != null && !senderAlias.trim().isEmpty()
                    && receiverBic != null && !receiverBic.trim().isEmpty()
                    && amount != null && !amount.trim().isEmpty()) {

                try {
                    SignedXMLModule signedXML = new SignedXMLModule(keystorePath, keystorePassword, keyAlias,
                            keyPassword);
                    String paymentRequest = SendPushPaymentRequest.Companion.onGeneratePaymentRequest(senderAlias,
                            receiverAlias,
                            Long.parseLong(amount),
                            receiverBic);

                    // call Signedxml here ------
                    /*
                     * the SignDocument function will return the signature xml, which you can now
                     * insert in your envelope together with the rest of the data
                     */

                    String signedDocumentRequest = signedXML.SignDocument1(paymentRequest);

                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "text/xml");

                    InstantPaymentInterface apiService = InstantPaymentApiClient.getInstantPaymentClient()
                            .create(InstantPaymentInterface.class);
                    Response<String> paymentResponse = apiService.pushPayment(headers, signedDocumentRequest).execute();

                    if (paymentResponse.isSuccessful() && paymentResponse.body() != null) {

                        response.setContentType("text/xml");
                        response.getWriter().write(paymentResponse.body());

                    } else {
                        logger.info("NS_LOGS: Fetch Push payment Failed" + paymentResponse.errorBody().charStream());
                        System.out
                                .print("NS_LOGS: Fetch Push payment Failed" + paymentResponse.errorBody().charStream());
                        response.getWriter().write(
                                "{ error: " + "NS_LOGS: Fetch Push payment Failed" + paymentResponse.errorBody() + "}");
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            } else {

                response.getWriter().write("{ error: " + " \"Field_required\" " + "}");
            }

        } else {
            response.getWriter().write("{ error: " + " \"Field required\" " + "}");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Header Name - " + request.getContentLength());
        // initialize();
        response.setContentType("text/xml");
        try {

            String RequestMsg = null;
            String ResponseStr = null;

            /* we get the incoming string body */
            // -----------------------------------------------------------------------------------------------------------
            try {
                // reading the data from the incoming string
                Reader reader = null;
                try {
                    reader = new BufferedReader(request.getReader());
                } catch (IOException e) {
                    reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
                }

                StringBuffer sbuf = new StringBuffer();
                char[] cbuf = new char[4096];
                int count = 0;
                while ((count = reader.read(cbuf)) != -1) {
                    sbuf.append(cbuf, 0, count);
                }
                RequestMsg = sbuf.toString().replace("'", "\"");
                System.out.println("Request Received Start:\n" + RequestMsg);
                logger.info("Request Received Start:\n" + RequestMsg);
            } catch (IOException e2) {
                RequestMsg = "ERROR";
            }

            if (!RequestMsg.equals("ERROR")) {

                String certificateText = "";

                System.out.println("extractCertificateSerial Text - "
                        + ReceiverPaymentResponse.Companion.extractCertificateSerial(RequestMsg));
                String certificateFileName = publicKeyPath
                        + ReceiverPaymentResponse.Companion.extractCertificateSerial(RequestMsg) + ".pem";
                System.out.println("Certificate File Name - " + certificateFileName);
                File f = new File(certificateFileName);

                if (!f.exists()) {
                    System.out.println("Certificate file does not exist so getting from server");
                    // logger.info("Certificate file does not exist so getting from server");
                    /* getting the certificate from the certificate server */

                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");

                    InstantPaymentInterface apiService = InstantCertApiClient.getCertClient()
                            .create(InstantPaymentInterface.class);
                    String certificateResponse = null;

                    Response<NSCertificateResponse> responseResponse = apiService.getCertificate(headers,
                            new NSCertificateRequest(
                                    ReceiverPaymentResponse.Companion.extractX509IssuerName(RequestMsg),
                                    ReceiverPaymentResponse.Companion.extractCertificateSerial(RequestMsg), "",
                                    ""))
                            .execute();

                    if (responseResponse.isSuccessful() && responseResponse.body() != null) {
                        certificateResponse = responseResponse.body().getCertificate();
                        logger.info("Certificate file " + responseResponse.body());
                        System.out.print("Certificate file " + responseResponse.body());
                    } else {
                        logger.info("NS_LOGS: request Failed" +
                                responseResponse.errorBody().charStream());
                        System.out.print("NS_LOGS: request Failed" +
                                responseResponse.errorBody().charStream());
                    }

                    // CertificateMessage certificateRequest = new CertificateMessage();
                    // CertificateMessage certificateResponse =
                    // certificateRequest.getCertificate(CertificateURL,ReceiverPaymentResponse.Companion.extractCertificateSerial(RequestMsg));
                    /*
                     * we write the certificate text from the response to the file
                     */
                    // Files.write(Paths.get(certificateFileName),certificateResponse.certificate.getBytes());
                    Files.write(Paths.get(certificateFileName), certificateResponse.getBytes());
                }

                certificateText = new String(Files.readAllBytes(Paths.get(certificateFileName)),
                        StandardCharsets.UTF_8);
                SignedXMLModule signedXML = new SignedXMLModule(keystorePath, keystorePassword, keyAlias, keyPassword);

                boolean finalBoolean = signedXML.verifySignature(RequestMsg, certificateText);

                logger.info("validate response" + finalBoolean);
                // let encode and return to the participant
                PrintWriter pw = response.getWriter();
                String xmlResponse = "";

                if (finalBoolean) {
                    // Provides information on the original cancellation message, to which the
                    // resolution refers.
                    // Complete QCell payment instruction
                    if (RequestMsg.contains("<document:OrgnlGrpInfAndSts>")) {

                        // Check IPS cancellation message response status (TxSts)
                        String transStatus = ReceiverPaymentResponse.Companion.paymentExtractDocumentTxSts(RequestMsg);
                        logger.info("Final_PAY_LOGS: IPS Transaction status" + transStatus);
                        System.out.print("Final_PAY_LOGS: IPS Transaction status" + transStatus);
                        // Debit customer wallet Payment Gateway values for QMoney
                        //statusACSC
                        if(transStatus != null && "ACSC".equals(transStatus.trim())) {
                       // if (transStatus.equals("ACSC")) {

                            String transactionId = ReceiverPaymentResponse.Companion
                                    .paymentExtractDocumentTxId(RequestMsg);
                            String amount = ReceiverPaymentResponse.Companion
                                    .paymentExtractDocumentInstdAmt(RequestMsg);

                            logger.info("PAY_LOGS: Amount valux" + amount);
                            logger.info("PAY_LOGS: Transaction valux" + transactionId);
                            System.out.print("PAY_LOGS: Amount valux" + amount);
                            String fromUserAccount = ReceiverPaymentResponse.Companion
                                    .paymentExtractDocumentDbtrAcct(RequestMsg);
                            String toUserAccount = ReceiverPaymentResponse.Companion
                                    .paymentExtractDocumentCdtrAcct(RequestMsg);
                            String tranxStatus = "ACSC_" + fromUserAccount;
                            // int transactionPin=8888

                            QmoneyPaymentInterface apiService = QmoneyPaymentApiClient.getPaymentGwClient()
                                    .create(QmoneyPaymentInterface.class);

                            // Payment GW login
                            // Create credentials
                            String login = "IPS_ACCESS_CHANNEL";
                            String password = "RBC@Mobifin#123";

                            // Below code will create correct Base64 encoded Basic Auth credentials
                            String credentials = Credentials.basic(login, password);

                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json");
                            headers.put("Authorization", credentials);

                            String token = "";
                            QMoneyLoginRequestData loginDetails = new QMoneyLoginRequestData();
                            loginDetails.setGrantType("password");
                            loginDetails.setUsername("80000008");
                            loginDetails.setPassword("Vendor@2024");

                            QMoneyLoginRequest loginRequest = new QMoneyLoginRequest();
                            loginRequest.setData(loginDetails);

                            Response<QMoneyLoginResponse> loginResponse = apiService
                                    .getLoginToken(headers, loginRequest)
                                    .execute();

                            if (loginResponse.isSuccessful() && loginResponse.body() != null) {
                                token = loginResponse.body().getData().getAccessToken();
                                logger.info("PAY_LOGS: Login Success 1_" + token);
                                System.out.print("PAY_LOGS: Success-1" + token);

                            } else {
                                logger.info("PAY_LOGS: Login Failed" + loginResponse.errorBody().charStream());
                                System.out.print("PAY_LOGS: Login Failed" + loginResponse.errorBody().charStream());
                            }

                            System.out.print("PAY_LOGS: Success-1out0 " + token);
                            // Payment GW proccessing
                            if (token != null && !token.isEmpty()) {

                                System.out.print("PAY_LOGS: Success-1out1 " + token);
                                HashMap<String, String> headers1 = new HashMap<String, String>();
                                headers1.put("Content-Type", "application/json");
                                headers1.put("Authorization", "Bearer " + token);

                                System.out.print("PAY_LOGS: Success-1out2 " + token);
                                logger.info("PAY_LOGS: Success-1out2 " + token);

                                FromUser fromUser = new FromUser();
                                fromUser.setUserIdentifier("80000008");

                                ToUser toUser = new ToUser();
                                toUser.setUserIdentifier(toUserAccount);
                                toUser.setPouchId("EMONEY_POUCH");

                                List<Payment> arrayList = new ArrayList<Payment>();
                                Payment payment = new Payment();
                                payment.setAmount(amount);
                                arrayList.add(payment);
                                System.out.print("PAY_LOGS: Success-1out3" + token);
                                logger.info("PAY_LOGS: Success-1out3 " + token);

                                QMoneyPaymentRequestData qMoneyPaymentRequestData = new QMoneyPaymentRequestData();
                                qMoneyPaymentRequestData.setFromUser(fromUser);
                                qMoneyPaymentRequestData.setToUser(toUser);
                                qMoneyPaymentRequestData.setServiceId("RECEIVE_PAYMENTS");
                                qMoneyPaymentRequestData.setProductId("RECEIVE_PAYMENTS");
                                qMoneyPaymentRequestData.setSwitchtxnid(transactionId);
                                qMoneyPaymentRequestData.setTxnstatus(tranxStatus);
                                qMoneyPaymentRequestData.setRemarks("Send Money To Customer Account");
                                qMoneyPaymentRequestData.setPayment(arrayList);
                                qMoneyPaymentRequestData.setTransactionPin("8888");

                                QMoneyPaymentRequest qMoneyPaymentRequest = new QMoneyPaymentRequest();
                                qMoneyPaymentRequest.setData(qMoneyPaymentRequestData);

                                System.out.print("PAY_LOGS: Success-1out4" + token);
                                logger.info("PAY_LOGS: Success-1out4" + token);

                                Response<QMoneyPaymentResponse> paymentResponse = apiService
                                        .sendPayment(headers1, qMoneyPaymentRequest).execute();

                                logger.info("PAY_LOGS: Payment payload");

                                if (paymentResponse.isSuccessful()
                                        && paymentResponse.body() != null
                                        && paymentResponse.body().getResponseCode().equals("1")
                                        && paymentResponse.body().getResponseMessage().equals("Success")) {

                                    logger.info(
                                            "PAY_LOGS: Payment Successfull"
                                                    + paymentResponse.body().getResponseMessage());
                                    System.out.print(
                                            "PAY_LOGS: Payment Successfull"
                                                    + paymentResponse.body().getResponseMessage());

                                } else {
                                    logger.info("PAY_LOGS: Payment Failed" + paymentResponse.errorBody().charStream());
                                    System.out
                                            .print("PAY_LOGS: Payment Failed" + loginResponse.errorBody().charStream());

                      
                                }
                            } else {
                                logger.info("PAY_LOGS: Token Authenticate failed" + token);
                                System.out.print("PAY_LOGS: Token Authenticate failed" + token);
                            }
                        }

                        // Provide sign transaction response 
                        xmlResponse = signedXML.signDocument2(RequestMsg);
                        logger.info("xmlResponse cancellation msg:\n" + xmlResponse);

                    } else {

                        // First pass inbound request

                        String xmlReceiverPaymentsResponse = ReceiverPaymentResponse.Companion
                                .onGenerateReceivePaymentResponse(RequestMsg, "ACSC");

                        xmlResponse = signedXML.signDocument2(xmlReceiverPaymentsResponse);

                        logger.info("xmlResponse good:\n" + xmlResponse);

                    }

                } else {

                    String xmlReceiverPaymentsResponse = ReceiverPaymentResponse.Companion
                                            .onGenerateReceivePaymentResponse(RequestMsg, "RJCT");
                    xmlResponse = signedXML.signDocument2(xmlReceiverPaymentsResponse);

                    logger.info("xmlResponse bad:\n" + xmlResponse);

                  //  xmlResponse = "Error processing document - failed to validate";

                }

                logger.info("Request Received End:\n" + xmlResponse);

                pw.print(xmlResponse);
            }

        } catch (Exception ex) {

            System.out.println("Header Exception - " + ex.fillInStackTrace());

            response.setStatus(HttpServletResponse.SC_GATEWAY_TIMEOUT);
            String lvErrorResponse = "<html>" + "<head><title>" + ex.fillInStackTrace() + "</title></head>"
                    + "<body><h1>"
                    + ex.fillInStackTrace() + "</h1></body></html>";
            PrintWriter pw = response.getWriter();
            pw.print(lvErrorResponse);
            // return lvResponseStr1;
        }
    }

}