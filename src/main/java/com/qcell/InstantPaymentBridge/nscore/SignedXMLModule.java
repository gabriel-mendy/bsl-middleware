package com.qcell.InstantPaymentBridge.nscore;

import nu.xom.Builder;
import nu.xom.canonical.Canonicalizer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class SignedXMLModule {

	public String KeyInfoXML;
    public String KeyInfoReference;
    public String KeyInfoReferenceCanonicalized;

    public String SignedInfoXML;
    public String SignedInfoReference;
    public String SignedInfoReferenceCanonicalized;
    
    public String DocumentReference;
    public String DocumentReferenceCanonicalized;

    public String SignedInfoTAG;
    public String SignatureDigest;
    public String SignatureXML;

    public X509Certificate Certificate;
    public X509Certificate RemoteCertificate;
    
    private PrivateKey privateKey;
    private PrivateKey remotePrivateKey;

    private PublicKey publicKey;
    private PublicKey remotepublicKey;

    private String SignedDocumentXML;
//
//    public GeesoftSignedXMLxx1(String keystorePath, String keystorePassword, String keyAlias, String keyPassword)
//            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException {
//
//        KeyStore keystore = KeyStore.getInstance("JKS");
//        keystore.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());
//
//        // Get private key and certificate from keystore
//        privateKey = (PrivateKey) keystore.getKey(keyAlias, keyPassword.toCharArray());
//        Certificate = (X509Certificate) keystore.getCertificate(keyAlias);
//        publicKey = Certificate.getPublicKey();
//
//    }

    public SignedXMLModule(String keystorePath, String keystorePassword, String keyAlias, String keyPassword)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException,
            IOException, UnrecoverableKeyException {

        KeyStore keystore = KeyStore.getInstance("JKS");
        keystore.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());

        // Get private key and certificate from keystore
        privateKey = (PrivateKey) keystore.getKey(keyAlias, keyPassword.toCharArray());
        Certificate = (X509Certificate) keystore.getCertificate(keyAlias);
        publicKey = Certificate.getPublicKey();

    }



    public static String extractDocumentElement(String xmlString) throws Exception {
        int startPos;
        int endPos;
        String documentPart;

        startPos = xmlString.indexOf("<document:Document");
        endPos = xmlString.indexOf("</document:Document>") + 20 ;
        documentPart = xmlString.substring(startPos, endPos);

        return documentPart;
    }

    public String insertSignature(String unsignedDocument) {
        String signatureInsert = SignatureXML + "\n</header:AppHdr>";

       // System.out.println(signatureInsert);
        String signedDocument = unsignedDocument.replace("</header:AppHdr>", signatureInsert);
        SignedDocumentXML=signedDocument;
        return signedDocument;
    }

    public String SignDocument1(String lvEntireXML) throws Exception {
        try {
            //String lvDocumentXML = getXMLChildBlock(lvEntireXML, "<document:Document>");
            String signedDocument = "";
            String lvDocumentXML = extractDocumentElement(lvEntireXML);

           System.out.println(lvEntireXML);
            System.out.println("----------------------------------");
            System.out.println(lvDocumentXML);


            BuildKeyInfo();
            BuildSignedInfo();
            BuildDocumentReference(lvDocumentXML);
            BuildSignedInfo();
            BuildSignedInfoTag();
            BuildSignature();
            //return DocumentReference;
            signedDocument = insertSignature(lvEntireXML);
            //System.out.println(signedDocument);

            return signedDocument;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error Experienced";

        }

    }

    public String signDocument2(String lvEntireXML) throws Exception {
        try {
            //String lvDocumentXML = getXMLChildBlock(lvEntireXML, "<document:Document>");
            String signedDocument = "";
            String lvDocumentXML = extractDocumentElement(lvEntireXML);

            //  System.out.println(lvEntireXML);
            System.out.println("----------------------------------");
            //   System.out.println(lvDocumentXML);

            BuildKeyInfo();
            BuildSignedInfo();
            BuildDocumentReference2(lvDocumentXML);
            BuildSignedInfo();
            BuildSignedInfoTag();
            BuildSignature();
            //return DocumentReference;
            signedDocument = insertSignature(lvEntireXML);
            System.out.println(signedDocument);

            return signedDocument;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error Experienced";

        }

    }

    public String SignDocument(String lvDocumentXML) throws Exception {
        BuildKeyInfo();
        BuildSignedInfo();
        BuildDocumentReference(lvDocumentXML);
        BuildSignedInfo();
        BuildSignedInfoTag();
        BuildSignature();

        return SignatureXML;
    }
    
    public void BuildKeyInfo() throws IOException {
    	
    	String idKeyInfo = "_" + java.util.UUID.randomUUID();
    	
    	// KeyInfoXML = "<ds:KeyInfo Id=\"" + idKeyInfo + "\">" +
        //         "<ds:X509Data>" +
        //               "<ds:X509IssuerSerial>" +
        //                         "<ds:X509IssuerName>" + Certificate.getIssuerDN().toString() + "</ds:X509IssuerName>" +
        //                         "<ds:X509SerialNumber>" + Certificate.getSerialNumber().toString() + "</ds:X509SerialNumber>" +
        //                "</ds:X509IssuerSerial>" +
        //          "</ds:X509Data>" +
        //          "</ds:KeyInfo>";

        // Certificate.getIssuerDN().toString()
       // "2274676010400166394696670087864457092168417308"

        KeyInfoXML = "<ds:KeyInfo Id=\"" + idKeyInfo + "\">" +
                 "<ds:X509Data>" +
                       "<ds:X509IssuerSerial>" +
                                 "<ds:X509IssuerName>" + Certificate.getIssuerDN().toString() + "</ds:X509IssuerName>" +
                                 "<ds:X509SerialNumber>" + Certificate.getSerialNumber().toString() + "</ds:X509SerialNumber>" +
                        "</ds:X509IssuerSerial>" +
                  "</ds:X509Data>" +
                  "</ds:KeyInfo>";
        //System.out.println("key---details : "+KeyInfoXML);
    	//2274676010415352625709541311632067285332525056
    	// String canonicalizedKeyInfo = "<ds:KeyInfo xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"" + idKeyInfo + "\">" +
        //         "<ds:X509Data>" +
        //               "<ds:X509IssuerSerial>" +
        //                         "<ds:X509IssuerName>" + Certificate.getIssuerDN().toString() + "</ds:X509IssuerName>" +
        //                         "<ds:X509SerialNumber>" + Certificate.getSerialNumber().toString() + "</ds:X509SerialNumber>" +
        //                "</ds:X509IssuerSerial>" +
        //          "</ds:X509Data>" +
        //          "</ds:KeyInfo>";

        //"2274676010415352708811458411792717532387868703"
        String canonicalizedKeyInfo = "<ds:KeyInfo xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"" + idKeyInfo + "\">" +
                 "<ds:X509Data>" +
                       "<ds:X509IssuerSerial>" +
                                 "<ds:X509IssuerName>" + Certificate.getIssuerDN().toString() + "</ds:X509IssuerName>" +
                                 "<ds:X509SerialNumber>" + Certificate.getSerialNumber().toString() + "</ds:X509SerialNumber>" +
                        "</ds:X509IssuerSerial>" +
                  "</ds:X509Data>" +
                  "</ds:KeyInfo>";
        //System.out.println("key---canonldetails : "+canonicalizedKeyInfo);
    	byte[] hash = hash256(canonicalizedKeyInfo);
    	String lvDigestValue = Base64.getEncoder().encodeToString(hash);

       // System.out.println("hash---details : "+hash);
       // System.out.println("digest1---details : "+lvDigestValue);

    	String lvKeyInfoReference = "<ds:Reference URI=\"#" + idKeyInfo + "\">" +
                "<ds:Transforms>" +
                      "<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>" +
                "</ds:Transforms>" +
                "<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>" +
                "<ds:DigestValue>" + lvDigestValue + "</ds:DigestValue>" +
                "</ds:Reference>";
      //  System.out.println("keyRef---details : "+lvKeyInfoReference);
    	KeyInfoReferenceCanonicalized = "<ds:Reference URI=\"#" + idKeyInfo + "\">" +
                "<ds:Transforms>" +
                      "<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"></ds:Transform>" +
                "</ds:Transforms>" +
                "<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"></ds:DigestMethod>" +
                "<ds:DigestValue>" + lvDigestValue + "</ds:DigestValue>" +
                "</ds:Reference>";

     KeyInfoReference = lvKeyInfoReference;
   	
    }
    
    public void BuildSignedInfo() {
    	
    	String idSignedInfo = "_" + java.util.UUID.randomUUID() + "-signedprops";
    	
    	Date currentDate = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    	String timeStr = dateFormat. format(currentDate);		
    	//String timeStr = DateTime.Now.ToString("yyyy-MM-dd'T'HH:mm:ss'Z'");

        SignedInfoXML = "<ds:Object>" +
                "<xades:QualifyingProperties xmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\">" +

                    "<xades:SignedProperties Id=\"" + idSignedInfo + "\">" +
                        "<xades:SignedSignatureProperties>" +
                            "<xades:SigningTime>" + timeStr + "</xades:SigningTime>" +
                        "</xades:SignedSignatureProperties>" +
                    "</xades:SignedProperties>" +
                "</xades:QualifyingProperties>" +
                "</ds:Object>";
        
        String canonicalizedSignedInfo = "<xades:SignedProperties xmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\" Id=\"" + idSignedInfo + "\">" +
                "<xades:SignedSignatureProperties>" +
                   "<xades:SigningTime>" + timeStr + "</xades:SigningTime>" +
                "</xades:SignedSignatureProperties>" +
             "</xades:SignedProperties>";
        
        byte[] hash = hash256(canonicalizedSignedInfo);
    	String lvDigestValue = Base64.getEncoder().encodeToString(hash);
    	
    	String lvSignedInfoReference = "<ds:Reference Type=\"http://uri.etsi.org/01903/v1.3.2#SignedProperties\" URI=\"#" + idSignedInfo + "\">" +
                "<ds:Transforms>" +
                      "<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>" +
                "</ds:Transforms>" +
                "<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>" +
                "<ds:DigestValue>" + lvDigestValue + "</ds:DigestValue>" +
                "</ds:Reference>";
    	
    	SignedInfoReferenceCanonicalized = "<ds:Reference Type=\"http://uri.etsi.org/01903/v1.3.2#SignedProperties\" URI=\"#" + idSignedInfo + "\">" +
                "<ds:Transforms>" +
                      "<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"></ds:Transform>" +
                "</ds:Transforms>" +
                "<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"></ds:DigestMethod>" +
                "<ds:DigestValue>" + lvDigestValue + "</ds:DigestValue>" +
                "</ds:Reference>";
    	
    	SignedInfoReference = lvSignedInfoReference;
    	
    }
    
    public void BuildDocumentReference(String lvDocumentXML) {
    	
    	 //we add the namespace if it was not already in the document.
    	 String lvDocumentXMlWithNameSpace = lvDocumentXML.replace("<document:Document>", "<document:Document xmlns:document=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.10\">");
    	 //System.out.println(lvDocumentXMlWithNameSpace);
    	 //we replace windows CRLF (\r\n) with linux LF (\n)
    	 lvDocumentXMlWithNameSpace = lvDocumentXMlWithNameSpace.replace("\r\n", "\n");
    	 
    	 //we hash the xml document
    	 byte[] hash = hash256(lvDocumentXMlWithNameSpace);
     	 String lvDigestValue = Base64.getEncoder().encodeToString(hash);
     	 
     	 String lvDocumentInfoReference = "<ds:Reference>" +
                "<ds:Transforms>" +
                      "<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>" +
                "</ds:Transforms>" +
                "<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>" +
                "<ds:DigestValue>" + lvDigestValue + "</ds:DigestValue>" +
                "</ds:Reference>";
     	 
     	 DocumentReferenceCanonicalized = "<ds:Reference>" +
                "<ds:Transforms>" +
                      "<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"></ds:Transform>" +
                "</ds:Transforms>" +
                "<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"></ds:DigestMethod>" +
                "<ds:DigestValue>" + lvDigestValue + "</ds:DigestValue>" +
                "</ds:Reference>";

     	 DocumentReference = lvDocumentInfoReference;
        // System.out.println(DocumentReference);
    }

    public void BuildDocumentReference2(String lvDocumentXML) {
        System.out.println(lvDocumentXML);
        //System.out.println(lvDocumentXML.getBytes());
        //we add the namespace if it was not already in the document.
        String lvDocumentXMlWithNameSpace = lvDocumentXML.replace("<document:Document>", "<document:Document xmlns:document=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.12\">");
       // System.out.println(lvDocumentXMlWithNameSpace.getBytes());
        //we replace windows CRLF (\r\n) with linux LF (\n)
       // lvDocumentXMlWithNameSpace = lvDocumentXMlWithNameSpace.replace("\r\n", "\n");
        lvDocumentXMlWithNameSpace = lvDocumentXMlWithNameSpace.replace("\r\n", "\n");
        //System.err.println(lvDocumentXML.getBytes());
        //we hash the xml document
        byte[] hash = hash256(lvDocumentXMlWithNameSpace);
        //byte[] hash = hash256(lvDocumentXMlWithNameSpace);
        String lvDigestValue = Base64.getEncoder().encodeToString(hash);

        String lvDocumentInfoReference = "<ds:Reference>" +
                "<ds:Transforms>" +
                "<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>" +
                "</ds:Transforms>" +
                "<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>" +
                "<ds:DigestValue>" + lvDigestValue + "</ds:DigestValue>" +
                "</ds:Reference>";

        DocumentReferenceCanonicalized = "<ds:Reference>" +
                "<ds:Transforms>" +
                "<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"></ds:Transform>" +
                "</ds:Transforms>" +
                "<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"></ds:DigestMethod>" +
                "<ds:DigestValue>" + lvDigestValue + "</ds:DigestValue>" +
                "</ds:Reference>";

        DocumentReference = lvDocumentInfoReference;
        // System.out.println(DocumentReference);
    }
    
    public void BuildSignedInfoTag() throws Exception {
    	
    	String signedInfoTag = "<ds:SignedInfo>" +
                "<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>" +
                   "<ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>" +

                   KeyInfoReference +
                   SignedInfoReference +
                   DocumentReference +

                   "</ds:SignedInfo>";
    	
    	SignedInfoTAG = signedInfoTag;
    	
    	String canonicalizedSignedInfo = "<ds:SignedInfo xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">" +
                "<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"></ds:CanonicalizationMethod>" +
                           "<ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"></ds:SignatureMethod>" +

						   KeyInfoReferenceCanonicalized +
                           SignedInfoReferenceCanonicalized +
                           DocumentReferenceCanonicalized +

                 "</ds:SignedInfo>";
    	
    	//byte[] hash = hash256(canonicalizedSignedInfo);  	
    	
    	//String lvDigestValue = Base64.getEncoder().encodeToString(hash);
    	//String signatureDigest = signHash(hash);
    	String signatureDigest = signHash(canonicalizedSignedInfo.getBytes("UTF-8"));
    	SignatureDigest = signatureDigest;
    	
    }
    
    public void BuildSignature() {
        String lvSignature = "<document:Sgntr xmlns:document=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.03\">" +
              "<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">" +

              SignedInfoTAG +
              "<ds:SignatureValue>" + SignatureDigest + "</ds:SignatureValue>" +
              KeyInfoXML +
              SignedInfoXML +

              "</ds:Signature>" +
       "</document:Sgntr>";
        SignatureXML = lvSignature;
    }

    public String signHash(byte[] hash) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA1withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(hash);
        byte[] signature = privateSignature.sign();
        return Base64.getEncoder().encodeToString(signature);
    }

    public boolean verifySignature(String sourceXML, String certificateText) throws Exception {
        boolean signatureValid = false;

        try {

            String signedInfoXML = extractSignedInfo(sourceXML);
            String signatureValue = extractSignatureValue(sourceXML);

            // we first add back the namespace so that the signedInfo is namespace bound
            signedInfoXML = signedInfoXML.replace("<ds:SignedInfo>", "<ds:SignedInfo xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">");

            /* we canonicalize the string using nu.xom, which replaces the manual canonicalization
             * before that was not deleting unneeded characters*/
            Builder buildernu = new Builder();
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            Canonicalizer canonicalizer = new Canonicalizer(bytestream, Canonicalizer.EXCLUSIVE_XML_CANONICALIZATION);
            ByteArrayInputStream signedInfoXMLStream = new ByteArrayInputStream(signedInfoXML.getBytes("UTF-8"));

            nu.xom.Document input = buildernu.build(signedInfoXMLStream, null);
            canonicalizer.write(input);
            String canonicalizedString = bytestream.toString("UTF8");

            /* we strip whitespace from the canonicalized string */
            canonicalizedString = StripWhiteSpace(canonicalizedString);
            signatureValue= StripWhiteSpace(signatureValue);
            System.out.println("before"+certificateText);
            certificateText= StripWhiteSpace(certificateText);

            System.out.println("after"+certificateText);
            Signature publicSignature = Signature.getInstance("SHA1withRSA");
            //publicSignature.initVerify(publicKey);
            publicSignature.initVerify(extractCleanCertificate(certificateText));
            publicSignature.update(canonicalizedString.getBytes("UTF-8"));

            byte[] signatureBytes = Base64.getDecoder().decode(signatureValue);

            signatureValid = publicSignature.verify(signatureBytes);


        } catch (Exception ex) {
            signatureValid = false;
        }
        System.out.println("after"+signatureValid);
        return signatureValid;
    }
    
    private byte[] hash256(String input) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        final byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return hash;
    }
    
    private String extractSignedInfo(String inputXML) {
    	int positionstart = 0;
    	int positionend = 0;
    	int count = 0;
    	String outputText = "";
    	if (inputXML.contains("<ds:SignedInfo>")) {
    		positionstart = inputXML.indexOf("<ds:SignedInfo>");
    		positionend = inputXML.indexOf("</ds:SignedInfo>") + 16;
    		outputText = inputXML.substring(positionstart, positionend);
    	}
    	return outputText;  	
    }
    
    private String extractSignatureValue(String inputXML) {
    	int positionstart = 0;
    	int positionend = 0;
    	int count = 0;
    	String outputText = "";
    	if (inputXML.contains("<ds:SignatureValue>")) {
    		positionstart = inputXML.indexOf("<ds:SignatureValue>")+19;
    		positionend = inputXML.indexOf("</ds:SignatureValue>");
    		outputText = inputXML.substring(positionstart, positionend);
    	}
    	return outputText;  	
    }

    public PublicKey extractCleanCertificate(String certificateText) {

        /*CertificateFactory fact = CertificateFactory.getInstance("X.509");
        FileInputStream is = new FileInputStream (args[0]);
        X509Certificate cer = (X509Certificate) fact.generateCertificate(is);
        PublicKey key = cer.getPublicKey();
        is.close();*/
        try {
            String cleanCertificate = certificateText.replaceAll("-----BEGIN CERTIFICATE-----", "").replaceAll("-----END CERTIFICATE-----", "");
            cleanCertificate = cleanCertificate.replaceAll("\r\n", "").replaceAll("\n","");
            byte [] decoded = Base64.getDecoder().decode(cleanCertificate);
            RemoteCertificate=((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decoded)));
            System.out.println(RemoteCertificate.getPublicKey().getAlgorithm());
            return  RemoteCertificate.getPublicKey();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String StripWhiteSpace(String input) {
        BufferedReader reader = new BufferedReader(new StringReader(input));
        StringBuffer result = new StringBuffer();
        try {
            String line;
            while ( (line = reader.readLine() ) != null)
                result.append(line.trim());
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static GeesoftSignedXMLxx newInstance() throws UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException {
//
//        String keystoreFile = Optional.ofNullable(GeesoftSignedXMLxx.class.getResourceAsStream("/usr/local/estashcore/security/keystore.jks"))
//                .map(inputStream -> {
//                    try {
//                        File tempFile = Files.createTempFile("keystore.jks", null).toFile();
//                        Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//                        return tempFile.getAbsolutePath();
//                    } catch (IOException e) {
//                        throw new UncheckedIOException(e);
//                    }
//                }).orElse(null);
//
//        String keystoreType = "JKS";
//        String keystorePass = "qcellsl";
//        String privateKeyAlias = "qcellsl";
//        String privateKeyPass = "qcellsl";
//        String certificateAlias = "root";
//
//        return new GeesoftSignedXMLxx(keystoreFile, keystorePass,privateKeyAlias, privateKeyPass);
//    }



}
