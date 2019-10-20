package Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Utility {


    public static String homeRoute = "/home";
    public static String defaultLink = "http://localhost:5000";
    private static String accessToken = null;
    private static String homeLinkForChecking = "http://localhost:5000/home";

    private static ArrayList<RouteData> allDataFromServer = new ArrayList<>();
    public static ArrayList<Record> recordsDatabase = new ArrayList<>();

    /**
     * Function get access token from the server
     * @return token that will be used in header for getting access to all pages
     */
    public static void getAccessToken(){
        String registerRoute = "/register";
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(defaultLink + registerRoute);

        try {
            HttpResponse response = client.execute(request);

            ResponseHandler<String> handler = new BasicResponseHandler();
            String text = handler.handleResponse(response);
            accessToken = text.split("\"")[3];
        } catch (IOException e) {
            System.out.println("Error in getting access token. Check your connection!\n\n" + e);
        }
    }

    /**
     * Function gets all required data from JSON-formatted text
     * @param data JSON formatted data that needs to be analyzed to get all info from it
     * @return list of records from JSON
     */
    private static List<Record> getDataFromJson (RouteData data) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        ArrayList<Record> dataJson = null;
//        try {
//            dataJson = objectMapper.readValue(data.getData().replaceAll(",]", "]"),
//                    new TypeReference<ArrayList<Record>>(){});
//        } catch (JsonProcessingException e) {
//            System.out.println("Error in reading from JSON file. Check integrity or format of file.\n" + e);
//        }
//
//        return dataJson;

        try{
            return new ObjectMapper().readValue(data.getData().replaceAll(",]", "]"), new TypeReference<ArrayList<Record>>(){});
        } catch (JsonProcessingException e) {
            System.err.println("Error in reading from JSON file, check integrity or format of file.\n" + e);
        }

        return null;
    }

    /**
     * Function gets all required data from YAML-formatted text
     * @param data YAML formatted data that needs to be analyzed to get all info from it
     * @return list of records from YAML
     */
    private static List<Record> getDataFromYaml (RouteData data) {
//        YAMLMapper yamlMapper = new YAMLMapper();
//        ArrayList<Record> dataYaml = null;
//        try {
//            dataYaml = yamlMapper.readValue(data.getData(), new TypeReference<ArrayList<Record>>(){});
//        } catch (IOException e) {
//            System.out.println("Error in reading from YAML file. Check integrity or format of file.\n" + e);
//        }
//
//        return dataYaml;

        try{
            return new YAMLMapper().readValue(data.getData().replaceAll(",]", "]"), new TypeReference<ArrayList<Record>>(){});
        } catch (JsonProcessingException e) {
            System.err.println("Error in reading from YAML file, check integrity or format of file.\n" + e);
        }

        return null;
    }

    /**
     * Function gets all required data from XML-formatted text
     * @param data XML formatted data that needs to be analyzed to get all info from it
     * @return list of records from XML
     */
    private static List<Record> getDataFromXml (RouteData data) {
//        XmlMapper xmlMapper = new XmlMapper();
//        ArrayList<Record> dataXml = null;
//        try {
//            dataXml = xmlMapper.readValue(data.getData(), new TypeReference<ArrayList<Record>>(){});
//        } catch (IOException e) {
//            System.out.println("Error in reading from XML file. Check integrity or format of file.\n" + e);
//        }
//
//        return dataXml;

        try{
            return new XmlMapper().readValue(data.getData().replaceAll(",]", "]"), new TypeReference<ArrayList<Record>>(){});
        } catch (JsonProcessingException e) {
            System.err.println("Error in reading from XML file, check integrity or format of file.\n" + e);
        }

        return null;
    }

    /**
     * Function gets all required data from CSV-formatted text
     * @param data CSV formatted data that needs to be analyzed to get all info from it
     * @return list of records from CSV
     */
    private static List<Record> getDataFromCsv (RouteData data) {
        List<String> contentOfCsv = Arrays.asList(data.getData().split("\n"));
        String allContent = data.getData();
        int amountOfRecords = contentOfCsv.size() - 1;
        List<Record> recordsList = new ArrayList<>();

        if(allContent.contains("username")){
            for(int i = 0; i < amountOfRecords; i++) {
                String[] currentRecord = contentOfCsv.get(i+1).split(",");
                recordsList.add(new Record());

                recordsList.get(i).setId(currentRecord[0]);

                recordsList.get(i).setUsername(currentRecord[1]);

                recordsList.get(i).setEmail(currentRecord[2]);

                recordsList.get(i).setCreated_account_data(currentRecord[3]);
            }
        }
        else {
            for(int i = 0; i < amountOfRecords; i++) {
                String[] currentRecord = contentOfCsv.get(i+1).split(",");
                recordsList.add(new Record());

                recordsList.get(i).setId(currentRecord[0]);

                recordsList.get(i).setFirst_name(currentRecord[1]);

                recordsList.get(i).setLast_name(currentRecord[2]);

                recordsList.get(i).setEmail(currentRecord[3]);

                recordsList.get(i).setGender(currentRecord[4]);

                recordsList.get(i).setIp_address(currentRecord[5]);
            }
        }
        return recordsList;
    }

    public static void addDataFromServer(){
        if(allDataFromServer != null && allDataFromServer.size() > 0) {
            System.out.println(recordsDatabase);
            for(int i = 0; i < allDataFromServer.size(); i++) {
                if(allDataFromServer.get(i).getMime_type() == null)
                    recordsDatabase.addAll(Objects.requireNonNull(Utility.getDataFromJson(allDataFromServer.get(i))));
                else if(allDataFromServer.get(i).getMime_type().equals("application/xml"))
                    recordsDatabase.addAll(Objects.requireNonNull(Utility.getDataFromXml(allDataFromServer.get(i))));
                else if(allDataFromServer.get(i).getMime_type().equals("application/x-yaml"))
                    recordsDatabase.addAll(Objects.requireNonNull(Utility.getDataFromYaml(allDataFromServer.get(i))));
                else if(allDataFromServer.get(i).getMime_type().equals("text/csv"))
                    recordsDatabase.addAll(Utility.getDataFromCsv(allDataFromServer.get(i)));
                else
                    System.err.println("Unknown type of data: " + allDataFromServer.get(i).getMime_type());
            }
        }
    }

    /**
    * Recursive function that searches over all links to find new routes on server.
    * @param availableRoutes list of routes that are accessible at the moment.
     */
    public static void getLinksFromServer(List<String> availableRoutes) {
        if(availableRoutes != null && availableRoutes.size() > 0) {
            for(int i = 0; i < availableRoutes.size(); i++){
                //considering that lambdas require work with final variables, was used this field
                final int finalI = i;
                new Thread(() -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String currentRoute = availableRoutes.get(finalI);
                    try {
                        RouteData routeData = objectMapper.readValue(Utility.getDataFromPage(currentRoute), RouteData.class);
                        if(!homeLinkForChecking.equals(currentRoute)){
                            allDataFromServer.add(routeData);
                        }
                        if(routeData.getLink() != null && routeData.getLink().size() > 0){
                            List<String> innerList = new ArrayList<>(routeData.getLink().values());
                            getLinksFromServer(innerList.stream().map(route ->
                                    route = defaultLink + route)
                                    .collect(Collectors.toList()));
                        }
                    } catch (IOException e) {
                        System.err.println("Error reading links from server, shutting program down.\n" + e);
                    }
                }).start();
            }
        } else System.err.println("No routes, check your connection or server");
    }

    /**
     * Function that gets all content from the page or file on server
     * @param link link to the page or file that needs to be analyzed
     * @return content that is on written link
     */
    private static String getDataFromPage(String link) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(link);
        request.addHeader("X-Access-Token", accessToken);
        String contentOfPage = null;

        try{
            HttpResponse response = client.execute(request);
            ResponseHandler<String> handler = new BasicResponseHandler();
            contentOfPage = handler.handleResponse(response);
            client.close();
        } catch (IOException e) { System.err.println("Error reading content of page, shutting program down.\n" + e); }

        return contentOfPage;
    }

    /**
     * Function that returns record if there is such one with written ID
     * @param id ID of the record that you want to get
     * @return Record with such ID or null value if there is no such record
     */
    private static Record getRecordById(String id) {
        for (Record record : recordsDatabase) {
            if(record.getId() != null) {
                if(record.getId().equalsIgnoreCase(id))
                    return record;
            }
        }
        System.out.println("Error: No such ID in system");
        return null;
    }

    /**
     * Returns record with identical first name in system.
     * @param firstName Name that needs to be found
     * @return Record with name from argument
     */
    private static Record getRecordByFirstName(String firstName){
        for (Record record : recordsDatabase) {
            if(record.getFirst_name() != null) {
                if(record.getFirst_name().equalsIgnoreCase(firstName))
                    return record;
            }
        }
        System.out.println("Error: No such first name in system");
        return null;
    }

    /**
     * Returns record with identical last name in system
     * @param lastName Last name that needs to be found in system
     * @return Record with last name from argument
     */
    private static Record getRecordByLastName(String lastName){
        for (Record record : recordsDatabase) {
            if(record.getLast_name() != null) {
                if(record.getLast_name().equalsIgnoreCase(lastName))
                    return record;
            }
        }
        System.out.println("Error: No such last name in system");
        return null;
    }

    /**
     * Returns record with identical bitcoin address in system
     * @param bitcoinAddress Bitcoin address that needs to be found in system
     * @return Record with bitcoin address from argument
     */
    private static Record getRecordByBitcoinAddress(String bitcoinAddress){
        for (Record record : recordsDatabase) {
            if(record.getBitcoin_address() != null) {
                if(record.getBitcoin_address().equalsIgnoreCase(bitcoinAddress))
                    return record;
            }
        }
        System.out.println("Error: No such bitcoin address in system");
        return null;
    }

    /**
     * Returns record with identical Email in system
     * @param email Email that needs to be found in system
     * @return Record with Email from argument
     */
    private static Record getRecordByEmail(String email){
        for (Record record : recordsDatabase) {
            if(record.getEmail() != null) {
                if(record.getEmail().equalsIgnoreCase(email))
                    return record;
            }
        }
        System.out.println("Error: No such Email in system");
        return null;
    }

    /**
     * Returns record with identical IP address in system
     * @param ipAddress IP address that needs to be found in system
     * @return Record with IP address from argument
     */
    private static Record getRecordByIp(String ipAddress) {
        for (Record record : recordsDatabase) {
            if(record.getIp_address() != null) {
                if(record.getIp_address().equalsIgnoreCase(ipAddress))
                    return record;
            }
        }
        System.out.println("Error: No such IP address in system");
        return null;
    }

    /**
     * Returns record with identical card number in system
     * @param cardNumber Card number that needs to be found in system
     * @return Record with card number from argument
     */
    private static Record getRecordByCardNumber(String cardNumber){
        for (Record record : recordsDatabase) {
            if(record.getCard_number() != null) {
                if(record.getCard_number().equalsIgnoreCase(cardNumber))
                    return record;
            }
        }
        System.out.println("Error: No such card number in system");
        return null;
    }

    /**
     * Returns record with identical full name in system
     * @param fullName Full name that needs to be found in system
     * @return Record with full name from argument
     */
    private static Record getRecordByFullName(String fullName){
        for (Record record : recordsDatabase) {
            if(record.getFull_name() != null) {
                if(record.getFull_name().equalsIgnoreCase(fullName))
                    return record;
            }
        }
        System.out.println("Error: No such full name in system");
        return null;
    }

    /**
     * Returns record with identical employee ID in system
     * @param employeeId Employee ID that needs to be found in system
     * @return Record with employee ID from argument
     */
    private static Record getRecordByEmployeeId(String employeeId){
        for (Record record : recordsDatabase) {
            if(record.getEmployee_id() != null) {
                if(record.getEmployee_id().equalsIgnoreCase(employeeId))
                    return record;
            }
        }
        System.out.println("Error: No such employee ID in system");
        return null;
    }

    /**
     * Returns record with identical username in system
     * @param username Username that needs to be found in system
     * @return Record with username from argument
     */
    private static Record getRecordByUsername(String username){
        for (Record record : recordsDatabase) {
            if(record.getUsername() != null) {
                if(record.getUsername().equalsIgnoreCase(username))
                    return record;
            }
        }
        System.out.println("Error: No such username in system");
        return null;
    }

    /**
     * Returns record with identical created account data in system
     * @param accountData Account data that needs to be found in system
     * @return Record with account data from argument
     */
    private static Record getRecordByAccountData(String accountData){
        for (Record record : recordsDatabase) {
            if(record.getCreated_account_data() != null) {
                if(record.getCreated_account_data().equalsIgnoreCase(accountData))
                    return record;
            }
        }
        System.out.println("Error: No such account data in system");
        return null;
    }

    /**
     * Read command from user and user data conform input
     * @param command Input from user that is recognized as command "select"
     * @return Required data by user
     */
    public static Record readCommandFromUser(String[] command) {
        switch(command[1]){
            case "id":
                return getRecordById(command[2]);
            case "first_name":
                return getRecordByFirstName(command[2]);
            case "last_name":
                return getRecordByLastName(command[2]);
            case "bitcoin_address":
                return getRecordByBitcoinAddress(command[2]);
            case "email":
                return getRecordByEmail(command[2]);
            case "ip_address":
                return getRecordByIp(command[2]);
            case "card_number":
                return getRecordByCardNumber(command[2]);
            case "full_name":
                return getRecordByFullName(command[2]);
            case "employee_id":
                return getRecordByEmployeeId(command[2]);
            case "username":
                return getRecordByUsername(command[2]);
            case "created_account_data":
                return getRecordByAccountData(command[2]);
            default:
                System.out.println("Error: no such column in database");
                return null;
        }
    }

    /**
     * Get random record from server
     * @return Random record from server
     */
    public static Record getRandomRecord() {
        return recordsDatabase.get(new Random().nextInt(recordsDatabase.size() - 1));
    }

    /**
     * Get all records from server that have ID
     * @return All records with not null ID
     */
    public static List<Record> getAllNotNullIdRecords() {
        List<Record> listOfNotNullIdRecords = new ArrayList<>();

        for(Record record : recordsDatabase) {
            if(record.getId() != null)
                listOfNotNullIdRecords.add(record);
        }
        return listOfNotNullIdRecords;
    }
}