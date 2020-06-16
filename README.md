# About this project:
OpenIMIS to Dhis2 adapter made in Java using Spring Framework. 


# Basic workflow of the program:
1) Fetch all the constant data and store them in a in-memory cache. This includes openIMIS Location,
and various Dhis2 ids of data element, program stage,etc.
2) Fetch openIMIS Patient bundle page. One-by-one adapt and post openIMIS Patient as Dhis2 Insuree. All the data is added to Dhis2 with HTTP POST and PUT requests, there are no database calls. 'Adapt' here refers to converting openIMIS data to a proper Dhis2 json.
Continue till there are no more pages of openIMIS Patient bundle.
3) Fetch openIMIS Coverage bundle page. One-by-one adapt and find the Dhis2 Insuree which this Coverage belongs to and post it as Dhis2 PolicyDetail. Continue till there are no more pages of openIMIS Coverage bundle.
4) Fetch openIMIS Claim bundle page and openIMIS ClaimResponse bundle page. Each Claim and ClaimResponse is together adapted into Dhis2 Claim and Dhis2 ClaimDetails, Claim-Items and Claim-Services. Continue till there are no more pages.

# Actual steps to add Dhis2 data is a little more complicated.
1) Add a TrackedEntityInstance, this is either the Insuree or Claim.
2) Then add an 'Enrollment' for this trackedEntityInstance.
3) Add multiple events to this enrollment. 
These events can be: 
	PolicyDetails for Insuree trackedEntityInstance. 
	ClaimDetails, Claim - Items, Claim - Services for Claim trackedEntityInstance.
4) Then add proper data to this events.


# How to run the program:
1) First step is to make .jar file.
You can do this by going into the project directory and typing the below command in the terminal.
mvn -Dmaven.test.skip=true clean compile package
2) The above step should create a .jar file in your project directory->target
You can copy this .jar file to any other directory or machine.
Using the terminal go to the directory with the .jar file and run it using:
java -Xms512m -Xmx2048m -jar JAR_FILE_NAME_HERE.jar &
3) After running the program, a 'logs' directory will be created in the same directory as your .jar file.
This will contain the logs.


# What do you need to run this .jar file:
1) Java 8 installed. 
2) Working internet connection.
3) At least 2GB of RAM is recommended. The size mostly depends on the constants fetched in the 1) point of the Basic workflow section.
For the current version the memory requirement is almost negligible, but that might change if we decide to fetch the openIMIS Patient or Claim data in bigger page-size and store them in-memory cache too.

# Work in progress:
1) Add Dhis2 relationship data for Insuree and Claim.
2) Add a cron job, to execute the basic workflow every 24 hours, fetching only the data that was updated on the last date. Currently it fetches data last updated after 2019-01-01, using the refDate param. This can changed in ParamsUtil class in util package.
3) Add a external application.properties file. This will contain the openIMIS url, Dhis2 url, their respective username and passwords. So changing these variables wont require making a new .jar file. Currently these can be changed in APIConfiguration class in util package.

