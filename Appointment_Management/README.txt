Global Consulting Company
Appointment and Customer Management Application

Author:
Blake Sutton, Student ID #001109490
bsutt23@wgu.edu

Version Info:
1.1 as of 02/28/2021

Revisions for v 1.1 based on Attempt #1 Evaluation Report:
A3a: I added appointment ID and type to the delete confirmation alert.  Also, for consistency in the app, I added the customer name to the delete customer confirmation alert.
A3b: I was originally returning all appointments in the current month.  I changed the code to instead display all appointments between now and a month from now.
  B: I added lambda expressions to the weekly/monthly appointment radio button filter methods to reduce load times from several seconds each time the table view was updated to virtually instantly.
  D: I generated Javadocs, located in C195_Project/javadoc.  The index.html specifically referenced in the Evaluation Report is located at C195_Project/javadoc/index.html.  I also changed a setting in the project properties to add visibility for private entities.
  F: I reformatted README.txt and added more information.  Specifically, I added notes for the revisions in the new version and locations for each implementation of lambda expressions.

IDE:
Apache NetBeans IDE 11.0 (Build incubator-netbeans-release-404-on-20190319)
Java: 1.8.0_251; Java HotSpot(TM) 64-Bit Server VM 25.251-b08
Runtime: Java(TM) SE Runtime Environment 1.8.0_251-b08
System: Windows 10 version 10.0 running on amd64; Cp1252; en_US (nb)

Directions:

To get the application running, follow the instructions below:
1. Save Application to your machine, unzip it, and open in the Apache Netbeans IDE Version 11.0.
2. Click RUN -> Run 'C195_Project'
3. Login with {Username = 'test' password = 'test'} or {username = 'admin' password = 'admin'}

Additional Report:
I chose to have a report to show all appointments filtered by customer using a combobox.

Lambda locations:
AppointmentAddController: lines 199-217
AppointmentModifyController: lines 200-218
CustomerAddController: lines 151-168
CustomerModifyController: lines 133-150
MainController: lines 385-388 and 399-402