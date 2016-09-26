/*
{
        "_id" : ObjectId("4f16fc97d1e2d32371003f02"),
        "body" : "COURTYARD\n\nMESQUITE\n2300 HWY 67\nMESQUITE, TX  75150\ntel: 972-681-3300\nfax: 972-681-3324\n\nHotel Information: http://courtyard.com/DALCM\n\n\nARRIVAL CONFIRMATION:\n Confirmation Number:84029698\nGuests in Room: 2\nNAME: MR ERIC  BASS \nGuest Phone: 7138530977\nNumber of Rooms:1\nArrive: Oct 6 2001\nDepart: Oct 7 2001\nRoom Type: ROOM - QUALITY\nGuarantee Method:\n Credit card guarantee\nCANCELLATION PERMITTED-BEFORE 1800 DAY OF ARRIVAL\n\nRATE INFORMATION:\nRate(s) Quoted in: US DOLLAR\nArrival Date: Oct 6 2001\nRoom Rate: 62.10  per night. Plus tax when applicable\nRate Program: AAA AMERICAN AUTO ASSN\n\nSPECIAL REQUEST:\n NON-SMOKING ROOM, GUARANTEED\n   \n\n\nPLEASE DO NOT REPLY TO THIS EMAIL \nAny Inquiries Please call 1-800-321-2211 or your local\ninternational toll free number.\n \nConfirmation Sent: Mon Jul 30 18:19:39 2001\n\nLegal Disclaimer:\nThis confirmation notice has been transmitted to you by electronic\nmail for your convenience. Marriott's record of this confirmation\nnotice is the official record of this reservation. Subsequent\nalterations to this electronic message after its transmission\nwill be disregarded.\n\nMarriott is pleased to announce that High Speed Internet Access is\nbeing rolled out in all Marriott hotel brands around the world.\nTo learn more or to find out whether your hotel has the service\navailable, please visit Marriott.com.\n\nEarn points toward free vacations, or frequent flyer miles\nfor every stay you make!  Just provide your Marriott Rewards\nmembership number at check in.  Not yet a member?  Join for free at\nhttps://member.marriottrewards.com/Enrollments/enroll.asp?source=MCRE\n\n",
        "filename" : "2.",
        "headers" : {
                "Content-Transfer-Encoding" : "7bit",
                "Content-Type" : "text/plain; charset=us-ascii",
                "Date" : ISODate("2001-07-30T22:19:40Z"),
                "From" : "reservations@marriott.com",
                "Message-ID" : "<32788362.1075840323896.JavaMail.evans@thyme>",
                "Mime-Version" : "1.0",
                "Subject" : "84029698 Marriott  Reservation Confirmation Number",
                "To" : [
                        "ebass@enron.com"
                ],
                "X-FileName" : "eric bass 6-25-02.PST",
                "X-Folder" : "\\ExMerge - Bass, Eric\\Personal",
                "X-From" : "Reservations@Marriott.com",
                "X-Origin" : "BASS-E",
                "X-To" : "EBASS@ENRON.COM",
                "X-bcc" : "",
                "X-cc" : ""
        },
        "mailbox" : "bass-e",
        "subFolder" : "personal"
}
*/
var result = db.messages.aggregate([
        {'$unwind': '$headers.To'}
        ,{'$group': {
                '_id': {
                        'objId': '$headers.Message-ID'
                        ,'from': '$headers.From'
                }
                ,'recipients': { '$addToSet': '$headers.To'}
        }}
        ,{'$unwind': '$recipients'}
        ,{'$group': {
                '_id': {
                        'sender': '$_id.from'
                        ,'recipient': '$recipients'
                }
                ,'num_messages': {'$sum': 1}
         }}
         ,{'$sort': {
                'num_messages': -1
         }}
])

printjson(result._batch)