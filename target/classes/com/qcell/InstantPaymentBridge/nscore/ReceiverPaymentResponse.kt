package com.qcell.InstantPaymentBridge.nscore

import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory


class ReceiverPaymentResponse {

    companion object {

        // Receiver payment response
          fun onGenerateReceivePaymentResponse(inputXML: String, mVarTxSts: String): String {

            var headerFr = ""
            var headerTo = ""
            var headerBizMsgIdr = ""
            var headerCreDt = ""
            var documentMsgId = ""
            var documentCreDtTm = ""
            var documentInstgAgt = ""
            var documentInstdAgt = ""
            var documentEndToEndId = ""
            var documentTxId = ""
            var documentIntrBkSttlmAmt = ""
            var documentAccptncDtTm = ""
            var documentInstdAmt = ""
            var documentDbtrAcct = ""
            var documentCdtrAcct = ""

            // 1.  Get the current DateTime in UTC
            // Create a ZonedDateTime with a specific offset (e.g., +02:00)
            val zonedCurrentDateTimeOffset = ZonedDateTime.now(ZoneOffset.ofHours(1))

            // Format the DateTime with offset
            val zonedFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            val zonedFormattedDateTime = zonedFormatter.format(zonedCurrentDateTimeOffset)


            // Format the DateTime in ISO 8601 style with milliseconds and timezone
            // 2. Get the current UTC DateTime

            val currentInstant = Instant.now()
            val currentDateTime = currentInstant.atZone(ZoneOffset.UTC)
            // Format the DateTime in the ISO 8601 style like 2024-08-28T15:00:00Z

            // Format to ISO 8601 (without fractional seconds)
            val isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
            val isoFormattedDateTime = isoFormatter.format(currentDateTime)

            // 3. Get the current date and time

            // Format the DateTime in the same style as 202408280344289921
            val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
            val randomNumber = (1..9).random()
            val currDateTime = LocalDateTime.now().format(formatter) + randomNumber


            println("1. Current DateTime: $zonedFormattedDateTime")
            println("2. Current UTC DateTime: $isoFormattedDateTime")
            println("3. Current DateTime: $currDateTime")

            val headerFromBic = "QCELSLFR"

            try {

                val xmlInput: InputStream = ByteArrayInputStream(inputXML.encodeToByteArray())

                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc: Document = dBuilder.parse(xmlInput)
                doc.documentElement.normalize()

                headerFr = extractHeaderFr(doc)
                headerTo = extractHeaderTo(doc)
                headerBizMsgIdr = extractHeaderBizMsgIdr(doc)
                headerCreDt = extractHeaderCreDt(doc)
                documentMsgId = extractDocumentMsgId(doc)
                documentCreDtTm = extractDocumentCreDtTm(doc)
                documentInstgAgt = extractDocumentInstgAgt(doc)
                documentInstdAgt = extractDocumentInstdAgt(doc)
                documentEndToEndId = extractDocumentEndToEndId(doc)
                documentTxId = extractDocumentTxId(doc)
                documentIntrBkSttlmAmt = extractDocumentIntrBkSttlmAmt(doc)
                documentAccptncDtTm = extractDocumentAccptncDtTm(doc)
                documentInstdAmt = extractDocumentInstdAmt(doc)
                documentDbtrAcct = extractDocumentDbtrAcct(doc)
                documentCdtrAcct = extractDocumentCdtrAcct(doc)

                println("headerFr: " + headerFr)
                println("headerTo: " + headerTo)
                println("headerBizMsgIdr: " + headerBizMsgIdr)
                println("headerCreDt: " + headerCreDt)

                println("documentMsgId: " + documentMsgId)
                println("documentCreDtTm: " + documentCreDtTm)
                println("documentInstgAgt: " + documentInstgAgt)
                println("documentInstdAgt: " + documentInstdAgt)
                println("documentEndToEndId: " + documentEndToEndId)

                println("documentTxId: " + documentTxId)
                println("documentIntrBkSttlmAmt: " + documentIntrBkSttlmAmt)
                println("documentAccptncDtTm: " + documentAccptncDtTm)
                println("documentInstdAmt: " + documentInstdAmt)

                println("documentDbtrAcct: " + documentDbtrAcct)
                println("documentCdtrAcct: " + documentCdtrAcct)

            } catch (e: Exception) {
                e.printStackTrace();
            }


            val rs="<FPEnvelope xmlns=\"urn:iso:std:iso:20022:tech:xsd:payment_response\" xmlns:document=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.12\" xmlns:header=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.03\">\n" +
                    "\t<header:AppHdr>\n" +
                    "\t\t<header:Fr>\n" +
                    "\t\t\t<header:FIId>\n" +
                    "\t\t\t\t<header:FinInstnId>\n" +
                    "\t\t\t\t\t<header:Othr>\n" +
                    "\t\t\t\t\t\t<header:Id>${headerFromBic}</header:Id>\n" +
                    "\t\t\t\t\t</header:Othr>\n" +
                    "\t\t\t\t</header:FinInstnId>\n" +
                    "\t\t\t</header:FIId>\n" +
                    "\t\t</header:Fr>\n" +
                    "\t\t<header:To>\n" +
                    "\t\t\t<header:FIId>\n" +
                    "\t\t\t\t<header:FinInstnId>\n" +
                    "\t\t\t\t\t<header:Othr>\n" +
                    "\t\t\t\t\t\t<header:Id>${documentInstdAgt}</header:Id>\n" +
                    "\t\t\t\t\t</header:Othr>\n" +
                    "\t\t\t\t</header:FinInstnId>\n" +
                    "\t\t\t</header:FIId>\n" +
                    "\t\t</header:To>\n" +
                    "\t\t<header:BizMsgIdr>${headerBizMsgIdr}</header:BizMsgIdr>\n" +
                    "\t\t<header:MsgDefIdr>pacs.002.001.12</header:MsgDefIdr>\n" +
                    "\t\t<header:CreDt>${isoFormattedDateTime}</header:CreDt>\n" +
                    "\t\t<header:Rltd>\n" +
                    "\t\t\t<header:Fr>\n" +
                    "\t\t\t\t<header:FIId>\n" +
                    "\t\t\t\t\t<header:FinInstnId>\n" +
                    "\t\t\t\t\t\t<header:Othr>\n" +
                    "\t\t\t\t\t\t\t<header:Id>${headerFromBic}</header:Id>\n" +
                    "\t\t\t\t\t\t</header:Othr>\n" +
                    "\t\t\t\t\t</header:FinInstnId>\n" +
                    "\t\t\t\t</header:FIId>\n" +
                    "\t\t\t</header:Fr>\n" +
                    "\t\t\t<header:To>\n" +
                    "\t\t\t\t<header:FIId>\n" +
                    "\t\t\t\t\t<header:FinInstnId>\n" +
                    "\t\t\t\t\t\t<header:Othr>\n" +
                    "\t\t\t\t\t\t\t<header:Id>${documentInstdAgt}</header:Id>\n" +
                    "\t\t\t\t\t</header:Othr>\n" +
                    "\t\t\t\t\t</header:FinInstnId>\n" +
                    "\t\t\t\t</header:FIId>\n" +
                    "\t\t\t</header:To>\n" +
                    "\t\t\t<header:BizMsgIdr>${headerBizMsgIdr}</header:BizMsgIdr>\n" +
                    "\t\t\t<header:MsgDefIdr>pacs.002.001.12</header:MsgDefIdr>\n" +
                    "\t\t\t<header:CreDt>${isoFormattedDateTime}</header:CreDt>\n" +
                    "\t\t</header:Rltd>\n" +
                    "\t</header:AppHdr>\n" +
                    "<document:Document xmlns:document=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.12\">\n" +
                    "\t<document:FIToFIPmtStsRpt>\n" +
                    "\t\t<document:GrpHdr>\n" +
                    "\t\t\t<document:MsgId>${documentMsgId}</document:MsgId>\n" +
                    "\t\t\t<document:CreDtTm>${documentCreDtTm}</document:CreDtTm>\n" +
                    "\t\t\t\t<document:InstgAgt>\n" +
                    "\t\t\t\t\t<document:FinInstnId>\n" +
                    "\t\t\t\t\t\t<document:Othr>\n" +
                    "\t\t\t\t\t\t\t<document:Id>${documentInstgAgt}</document:Id>\n" +
                    "\t\t\t\t\t\t</document:Othr>\n" +
                    "\t\t\t\t\t</document:FinInstnId>\n" +
                    "\t\t\t\t</document:InstgAgt>\n" +
                    "\t\t\t\t<document:InstdAgt>\n" +
                    "\t\t\t\t\t<document:FinInstnId>\n" +
                    "\t\t\t\t\t\t<document:Othr>\n" +
                    "\t\t\t\t\t\t\t<document:Id>${documentInstdAgt}</document:Id>\n" +
                    "\t\t\t\t\t\t</document:Othr>\n" +
                    "\t\t\t\t\t</document:FinInstnId>\n" +
                    "\t\t\t\t</document:InstdAgt>\n" +
                    "\t\t\t</document:GrpHdr>\n" +
                    "\t\t\t<document:TxInfAndSts>\n" +
                    "\t\t\t\t<document:OrgnlGrpInf>\n" +
                    "\t\t\t\t\t<document:OrgnlMsgId>${documentMsgId}</document:OrgnlMsgId>\n" +
                    "\t\t\t\t\t<document:OrgnlMsgNmId>pacs.008.001.10</document:OrgnlMsgNmId>\n" +
                    "\t\t\t\t\t<document:OrgnlCreDtTm>${isoFormattedDateTime}</document:OrgnlCreDtTm>\n" +
                    "\t\t\t\t</document:OrgnlGrpInf>\n" +
                    "\t\t\t\t<document:OrgnlEndToEndId>${documentEndToEndId}</document:OrgnlEndToEndId>\n" +
                    "\t\t\t\t<document:OrgnlTxId>${documentTxId}</document:OrgnlTxId>\n" +
                    "\t\t\t\t<document:TxSts>${mVarTxSts}</document:TxSts>\n" +
                    "\t\t\t\t<document:AccptncDtTm>${documentAccptncDtTm}</document:AccptncDtTm>\n" +
                    "\t\t\t\t<document:OrgnlTxRef>\n" +
                    "\t\t\t\t\t<document:IntrBkSttlmAmt Ccy=\"SLE\">${documentIntrBkSttlmAmt}</document:IntrBkSttlmAmt>\n" +
                    "\t\t\t\t\t<document:Amt>\n" +
                    "\t\t\t\t\t\t<document:InstdAmt Ccy=\"SLE\">${documentInstdAmt}</document:InstdAmt>\n" +
                    "\t\t\t\t\t</document:Amt>\n" +
                    "\t\t\t\t\t<document:Dbtr>\n" +
                    "\t\t\t\t\t\t<document:Pty>\n" +
                    "\t\t\t\t\t\t\t<document:Nm>MSCWT</document:Nm>\n" +
                    "\t\t\t\t\t\t\t<document:PstlAdr>\n" +
                    "\t\t\t\t\t\t\t\t<document:AdrLine>FREETOWN</document:AdrLine>\n" +
                    "\t\t\t\t\t\t\t</document:PstlAdr>\n" +
                    "\t\t\t\t\t\t</document:Pty>\n" +
                    "\t\t\t\t\t</document:Dbtr>\n" +
                    "\t\t\t\t\t<document:DbtrAcct>\n" +
                    "\t\t\t\t\t\t<document:Id>\n" +
                    "\t\t\t\t\t\t\t<document:Othr>\n" +
                    "\t\t\t\t\t\t\t\t<document:Id>${documentDbtrAcct}</document:Id>\n" +
                    "\t\t\t\t\t\t\t\t<document:SchmeNm>\n" +
                    "\t\t\t\t\t\t\t\t\t<document:Prtry>ACCT</document:Prtry>\n" +
                    "\t\t\t\t\t\t\t\t</document:SchmeNm>\n" +
                    "\t\t\t\t\t\t\t</document:Othr>\n" +
                    "\t\t\t\t\t\t</document:Id>\n" +
                    "\t\t\t\t\t</document:DbtrAcct>\n" +
                    "\t\t\t\t\t<document:CdtrAcct>\n" +
                    "\t\t\t\t\t\t<document:Id>\n" +
                    "\t\t\t\t\t\t\t<document:Othr>\n" +
                    "\t\t\t\t\t\t\t\t<document:Id>${documentCdtrAcct}</document:Id>\n" +
                    "\t\t\t\t\t\t\t\t<document:SchmeNm>\n" +
                    "\t\t\t\t\t\t\t\t\t<document:Prtry>ACCT</document:Prtry>\n" +
                    "\t\t\t\t\t\t\t\t</document:SchmeNm>\n" +
                    "\t\t\t\t\t\t\t</document:Othr>\n" +
                    "\t\t\t\t\t\t</document:Id>\n" +
                    "\t\t\t\t\t</document:CdtrAcct>\n" +
                    "\t\t\t\t</document:OrgnlTxRef>\n" +
                    "\t\t\t</document:TxInfAndSts>\n" +
                    "\t\t</document:FIToFIPmtStsRpt>\n" +
                    "\t</document:Document>\n" +
                    "</FPEnvelope>"

            return rs
        }


        fun onErrorReceiverPaymentResponse(): String {
            return "error"
        }

        private fun extractHeaderFr(doc: Document): String {

            val nodeList1 = doc.getElementsByTagName("header:Fr")
            if (nodeList1.length > 0) {
                val node = nodeList1.item(0) as Element
                return node.textContent.trim()
            } else {
                return "No <header:Fr> element found"
            }

            //        val idValue1 = Regex("<document:Id>(.*?)</document:Id>").find(doc)?.groupValues?.get(1)
//        println("Content of <document:Id>: $idValue1")
        }

        private fun extractHeaderTo(doc: Document): String {
            val nodeList2 = doc.getElementsByTagName("header:To")
            if (nodeList2.length > 0) {
                val node = nodeList2.item(0) as Element
                return node.textContent.trim()
            } else {
                return "No <header:To> element found"
            }
        }

        private fun extractHeaderBizMsgIdr(doc: Document): String {

            val nodeList3 = doc.getElementsByTagName("header:BizMsgIdr")
            if (nodeList3.length > 0) {
                val node = nodeList3.item(0) as Element
                return node.textContent
            } else {
                return "No <header:BizMsgIdr> element found"
            }
        }

        private fun extractHeaderCreDt(doc: Document): String {

            val nodeList4 = doc.getElementsByTagName("header:CreDt")
            if (nodeList4.length > 0) {
                val node = nodeList4.item(0) as Element
                return node.textContent
            } else {
                return "No <header:CreDt> element found"
            }
        }

        private fun extractDocumentMsgId(doc: Document): String {
            val nodeList5 = doc.getElementsByTagName("document:MsgId")
            if (nodeList5.length > 0) {
                val node = nodeList5.item(0) as Element
                return node.textContent
            } else {
                return "No <document:MsgId> element found"
            }
        }

        private fun extractDocumentCreDtTm(doc: Document): String {
            val nodeList6 = doc.getElementsByTagName("document:CreDtTm")
            if (nodeList6.length > 0) {
                val node = nodeList6.item(0) as Element
                return node.textContent
            } else {
                return "No <document:CreDtTm> element found"
            }
        }

        private fun extractDocumentInstgAgt(doc: Document): String {

            val nodeList7 = doc.getElementsByTagName("document:InstgAgt")
            if (nodeList7.length > 0) {
                val node = nodeList7.item(0) as Element
                return node.textContent.trim()
            } else {
                return "No <document:InstgAgt> element found"
            }
        }

        private fun extractDocumentInstdAgt(doc: Document): String {
            val nodeList8 = doc.getElementsByTagName("document:InstdAgt")
            if (nodeList8.length > 0) {
                val node = nodeList8.item(0) as Element
                return node.textContent.trim()
            } else {
                return "No <document:InstdAgt> element found"
            }
        }

        private fun extractDocumentEndToEndId(doc: Document): String {
            val nodeList9 = doc.getElementsByTagName("document:EndToEndId")
            if (nodeList9.length > 0) {
                val node = nodeList9.item(0) as Element
                return node.textContent
            } else {
                return "No <document:EndToEndId> element found"
            }
        }

        fun extractDocumentOrgnlGrpInfAndSts(inputXML1: String): String {

            try{

                val xmlInput: InputStream = ByteArrayInputStream(inputXML1.encodeToByteArray())

                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc: Document = dBuilder.parse(xmlInput)
                doc.documentElement.normalize()

            
                val nodeList10 = doc.getElementsByTagName("document:OrgnlGrpInfAndSts")
                if (nodeList10.length > 0) {
                    val node = nodeList10.item(0) as Element
                    return node.textContent
                } else {

                    return "No_<document:document:OrgnlGrpInfAndSts>"
                }

            }catch (e: Exception) {
                e.printStackTrace();
            }
            return "ERROR"
        }

        fun paymentExtractDocumentTxSts(inputXML1: String): String {

            try{
                val xmlInput: InputStream = ByteArrayInputStream(inputXML1.encodeToByteArray())

                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc: Document = dBuilder.parse(xmlInput)
                doc.documentElement.normalize()

            
                val nodeList13 = doc.getElementsByTagName("document:TxSts")
                if (nodeList13.length > 0) {
                    val node = nodeList13.item(0) as Element
                    return node.textContent
                } else {
                    return "No <document:TxSts> element found"
                }

            }catch (e: Exception) {
                e.printStackTrace();
            }
             return "ERROR"
        }


        fun paymentExtractDocumentTxId(inputXML1: String): String {

            try{

                val xmlInput: InputStream = ByteArrayInputStream(inputXML1.encodeToByteArray())

                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc: Document = dBuilder.parse(xmlInput)
                doc.documentElement.normalize()

            //OrgnlTxId
                val nodeList10 = doc.getElementsByTagName("document:TxId")
                if (nodeList10.length > 0) {
                    val node = nodeList10.item(0) as Element
                    return node.textContent
                } else {
                    return "No <document:TxId> element found"
                }

            }catch (e: Exception) {
                e.printStackTrace();
            }
            return "ERROR"
        }

        private fun extractDocumentTxId(doc: Document): String {
            val nodeList10 = doc.getElementsByTagName("document:TxId")
            if (nodeList10.length > 0) {
                val node = nodeList10.item(0) as Element
                return node.textContent
            } else {
                return "No <document:TxId> element found"
            }
        }

        private fun extractDocumentIntrBkSttlmAmt(doc: Document): String {
            val nodeList11 = doc.getElementsByTagName("document:IntrBkSttlmAmt")
            if (nodeList11.length > 0) {
                val node = nodeList11.item(0) as Element
                return node.textContent
            } else {
                return "No <document:IntrBkSttlmAmt> element found"
            }
        }

        private fun extractDocumentAccptncDtTm(doc: Document): String {
            val nodeList12 = doc.getElementsByTagName("document:AccptncDtTm")
            if (nodeList12.length > 0) {
                val node = nodeList12.item(0) as Element
                return node.textContent
            } else {
                return "No <document:AccptncDtTm> element found"
            }
        }

        fun paymentExtractDocumentInstdAmt(inputXML1: String): String {

            try{
                val xmlInput: InputStream = ByteArrayInputStream(inputXML1.encodeToByteArray())

                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc: Document = dBuilder.parse(xmlInput)
                doc.documentElement.normalize()

            
                val nodeList13 = doc.getElementsByTagName("document:InstdAmt")
                if (nodeList13.length > 0) {
                    val node = nodeList13.item(0) as Element
                    return node.textContent
                } else {
                    return "No <document:InstdAmt> element found"
                }

            }catch (e: Exception) {
                e.printStackTrace();
            }
             return "ERROR"
        }

        private fun extractDocumentInstdAmt(doc: Document): String {
            val nodeList13 = doc.getElementsByTagName("document:InstdAmt")
            if (nodeList13.length > 0) {
                val node = nodeList13.item(0) as Element
                return node.textContent
            } else {
                return "No <document:InstdAmt> element found"
            }
        }

        fun paymentExtractDocumentDbtrAcct(inputXML1: String): String {

            try{
                val xmlInput: InputStream = ByteArrayInputStream(inputXML1.encodeToByteArray())

                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc: Document = dBuilder.parse(xmlInput)
                doc.documentElement.normalize()

            
                val nodeList14 = doc.getElementsByTagName("document:DbtrAcct")
                if (nodeList14.length > 0) {
                    val node = nodeList14.item(0) as Element
                    val str= node.textContent.substringBefore("ACCT")
                    val output = str.replace("\\s".toRegex(), "")
                    return output
                } else {
                    return "No <document:DbtrAcct> element found"
                }

            }catch (e: Exception) {
                e.printStackTrace();
            }

             return "ERROR"

        }

        private fun extractDocumentDbtrAcct(doc: Document): String {
            val nodeList14 = doc.getElementsByTagName("document:DbtrAcct")
            if (nodeList14.length > 0) {
                val node = nodeList14.item(0) as Element
                val str= node.textContent.substringBefore("ACCT")
                val output = str.replace("\\s".toRegex(), "")
                return output
            } else {
                return "No <document:DbtrAcct> element found"
            }
        }

        fun paymentExtractDocumentCdtrAcct(inputXML1: String): String {

            try{

                val xmlInput: InputStream = ByteArrayInputStream(inputXML1.encodeToByteArray())

                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc: Document = dBuilder.parse(xmlInput)
                doc.documentElement.normalize()
            
                val nodeList17 = doc.getElementsByTagName("document:CdtrAcct")
                if (nodeList17.length > 0) {
                    val node = nodeList17.item(0) as Element
                    val str=node.textContent.trim().substringBefore("ACCT")
                    val output = str.replace("\\s".toRegex(), "")

                    return output
                } else {
                    return "No <document:CdtrAcct> element found"
                }

            }catch (e: Exception) {
                e.printStackTrace();
            }

            return "ERROR"
        }

        fun extractDocumentCdtrAcct(doc: Document): String {
            val nodeList17 = doc.getElementsByTagName("document:CdtrAcct")
            if (nodeList17.length > 0) {
                val node = nodeList17.item(0) as Element
                val str=node.textContent.trim().substringBefore("ACCT")
                val output = str.replace("\\s".toRegex(), "")

                return output
            } else {
                return "No <document:CdtrAcct> element found"
            }
        }

        fun extractCertificateSerial(xmlString: String): String {
            val endPos: Int
            val certificateSerial: String

            val startPos: Int = xmlString.indexOf("<ds:X509SerialNumber>") + 21
            endPos = xmlString.indexOf("</ds:X509SerialNumber>")
            certificateSerial = xmlString.substring(startPos, endPos)
            //CertificateSerial = certificateSerial
            return certificateSerial
        }

        fun extractX509IssuerName(inputXML: String): String {
            var positionstart = 0
            var positionend = 0
            val count = 0
            var outputText = ""
            if (inputXML.contains("<ds:X509IssuerName>")) {
                positionstart = inputXML.indexOf("<ds:X509IssuerName>") + 19
                println(positionstart)
                positionend = inputXML.indexOf("</ds:X509IssuerName>")
                println(positionend)
                outputText = inputXML.substring(positionstart, positionend)
            }
            return outputText
        }

    }
}
