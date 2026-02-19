package com.qcell.InstantPaymentBridge.nscore

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class SendPushPaymentRequest {

    companion object {

        // Receiver payment Request
        fun onGeneratePaymentRequest(
            dbtrSenderAcctAliasNumber: String,
            //="033636212",
            cdtrReceiverAcctAliasNumber: String,
            //"00101000019970",
            amount: Long,
            headerToBic: String
        ): String {

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
            val currDateTime = LocalDateTime.now().format(formatter)+randomNumber


            println("1. Current DateTime: $zonedFormattedDateTime")
            println("2. Current UTC DateTime: $isoFormattedDateTime")
            println("3. Current DateTime: $currDateTime")


            val headerFromBic= "QCELSLFR"

            val rs="<FPEnvelope xmlns=\"urn:iso:std:iso:20022:tech:xsd:payment_request\" xmlns:document=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.10\" xmlns:header=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.03\">\n" +
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
                    "\t\t\t\t\t\t<header:Id>${headerToBic}</header:Id>\n" +
                    "\t\t\t\t\t</header:Othr>\n" +
                    "\t\t\t\t</header:FinInstnId>\n" +
                    "\t\t\t</header:FIId>\n" +
                    "\t\t</header:To>\n" +
                    "\t\t<header:BizMsgIdr>${headerToBic}${currDateTime}</header:BizMsgIdr>\n" +
                    "\t\t<header:MsgDefIdr>pacs.008.001.10</header:MsgDefIdr>\n" +
                    "\t\t<header:CreDt>${isoFormattedDateTime}</header:CreDt>\n" +
                    "\t</header:AppHdr>\n" +
                    "<document:Document>\n" +
                    "\t<document:FIToFICstmrCdtTrf>\n" +
                    "\t\t<document:GrpHdr>\n" +
                    "\t\t\t<document:MsgId>${headerToBic}${currDateTime}</document:MsgId>\n" +
                    "\t\t\t<document:CreDtTm>${isoFormattedDateTime}</document:CreDtTm>\n" +
                    "\t\t\t<document:NbOfTxs>1</document:NbOfTxs>\n" +
                    "\t\t\t<document:SttlmInf>\n" +
                    "\t\t\t\t<document:SttlmMtd>CLRG</document:SttlmMtd>\n" +
                    "\t\t\t\t<document:ClrSys>\n" +
                    "\t\t\t\t\t<document:Prtry>${headerFromBic}</document:Prtry>\n" +
                    "\t\t\t\t</document:ClrSys>\n" +
                    "\t\t\t</document:SttlmInf>\n" +
                    "\t\t\t<document:PmtTpInf>\n" +
                    "\t\t\t\t<document:LclInstrm>\n" +
                    "\t\t\t\t\t<document:Prtry>P2P</document:Prtry>\n" +
                    "\t\t\t\t</document:LclInstrm>\n" +
                    "\t\t\t</document:PmtTpInf>\n" +
                    "\t\t\t<document:InstgAgt>\n" +
                    "\t\t\t\t<document:FinInstnId>\n" +
                    "\t\t\t\t\t<document:Othr>\n" +
                    "\t\t\t\t\t\t<document:Id>${headerFromBic}</document:Id>\n" +
                    "\t\t\t\t\t</document:Othr>\n" +
                    "\t\t\t\t</document:FinInstnId>\n" +
                    "\t\t\t</document:InstgAgt>\n" +
                    "\t\t\t<document:InstdAgt>\n" +
                    "\t\t\t\t<document:FinInstnId>\n" +
                    "\t\t\t\t\t<document:Othr>\n" +
                    "\t\t\t\t\t\t<document:Id>${headerToBic}</document:Id>\n" +
                    "\t\t\t\t\t</document:Othr>\n" +
                    "\t\t\t\t</document:FinInstnId>\n" +
                    "\t\t\t</document:InstdAgt>\n" +
                    "\t\t</document:GrpHdr>\n" +
                    "\t\t<document:CdtTrfTxInf>\n" +
                    "\t\t\t<document:PmtId>\n" +
                    "\t\t\t\t<document:EndToEndId>TNX.QCELL.${currDateTime}</document:EndToEndId>\n" +
                    "\t\t\t\t<document:TxId>TNX.QCELL.${currDateTime}</document:TxId>\n" +
                    "\t\t\t</document:PmtId>\n" +
                    "\t\t\t<document:IntrBkSttlmAmt Ccy=\"SLE\">${amount}</document:IntrBkSttlmAmt>\n" +
                    "\t\t\t<document:AccptncDtTm>${zonedFormattedDateTime}</document:AccptncDtTm>\n" +
                    "\t\t\t<document:InstdAmt Ccy=\"SLE\">${amount}</document:InstdAmt>\n" +
                    "\t\t\t<document:ChrgBr>SLEV</document:ChrgBr>\n" +
                    "\t\t\t<document:Dbtr>\n" +
                    "\t\t\t\t<document:Nm>MSCWT</document:Nm>\n" +
                    "\t\t\t\t<document:PstlAdr>\n" +
                    "\t\t\t\t\t<document:AdrLine>FREETOWN</document:AdrLine>\n" +
                    "\t\t\t\t</document:PstlAdr>\n" +
                    "\t\t\t</document:Dbtr>\n" +
                    "\t\t\t<document:DbtrAcct>\n" +
                    "\t\t\t\t<document:Id>\n" +
                    "\t\t\t\t\t<document:Othr>\n" +
                    "\t\t\t\t\t\t<document:Id>${dbtrSenderAcctAliasNumber}</document:Id>\n" +
                    "\t\t\t\t\t\t<document:SchmeNm>\n" +
                    "\t\t\t\t\t\t\t<document:Prtry>ACCT</document:Prtry>\n" +
                    "\t\t\t\t\t\t</document:SchmeNm>\n" +
                    "\t\t\t\t\t</document:Othr>\n" +
                    "\t\t\t\t</document:Id>\n" +
                    "\t\t\t</document:DbtrAcct>\n" +
                    "\t\t\t<document:DbtrAgt>\n" +
                    "\t\t\t\t<document:FinInstnId>\n" +
                    "\t\t\t\t\t<document:Othr>\n" +
                    "\t\t\t\t\t\t<document:Id>${headerFromBic}</document:Id>\n" +
                    "\t\t\t\t\t</document:Othr>\n" +
                    "\t\t\t\t</document:FinInstnId>\n" +
                    "\t\t\t</document:DbtrAgt>\n" +
                    "\t\t\t<document:CdtrAgt>\n" +
                    "\t\t\t\t<document:FinInstnId>\n" +
                    "\t\t\t\t\t<document:Othr>\n" +
                    "\t\t\t\t\t\t<document:Id>${headerToBic}</document:Id>\n" +
                    "\t\t\t\t\t</document:Othr>\n" +
                    "\t\t\t\t</document:FinInstnId>\n" +
                    "\t\t\t</document:CdtrAgt>\n" +
                    "\t\t\t<document:Cdtr>\n" +
                    "\t\t\t\t<document:Nm>test</document:Nm>\n" +
                    "\t\t\t</document:Cdtr>\n" +
                    "\t\t\t<document:CdtrAcct>\n" +
                    "\t\t\t\t<document:Id>\n" +
                    "\t\t\t\t\t<document:Othr>\n" +
                    "\t\t\t\t\t\t<document:Id>${cdtrReceiverAcctAliasNumber}</document:Id>\n" +
                    "\t\t\t\t\t\t<document:SchmeNm>\n" +
                    "\t\t\t\t\t\t\t<document:Prtry>ACCT</document:Prtry>\n" +
                    "\t\t\t\t\t\t</document:SchmeNm>\n" +
                    "\t\t\t\t\t</document:Othr>\n" +
                    "\t\t\t\t</document:Id>\n" +
                    "\t\t\t</document:CdtrAcct>\n" +
                    "\t\t\t<document:RmtInf>\n" +
                    "\t\t\t\t<document:Ustrd>${headerFromBic} ${dbtrSenderAcctAliasNumber} Transfer funds to ${headerToBic}${cdtrReceiverAcctAliasNumber}</document:Ustrd>\n" +
                    "\t\t\t</document:RmtInf>\n" +
                    "\t\t</document:CdtTrfTxInf>\n" +
                    "\t</document:FIToFICstmrCdtTrf>\n" +
                    "</document:Document>\n" +
                    "</FPEnvelope>"

           return rs
        }

        fun onErrorReceiverPaymentResponse(): String {
            return "error"
        }

    }
}
