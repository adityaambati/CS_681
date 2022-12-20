package edu.umb.cs681.nasa;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.*;

public class CallAPI {

  public static void main(String[] args) throws IOException {
    Map<String, List<String>> map = new HashMap<>();
	// [BOS]  42.37   71.03  Boston,MA
	// [LVS]  35.65  105.15  Las Vegas,NM
	// [BUF]  42.93   78.73  Buffalo,NY
	// [NYC]  40.77   73.98  New York,NY
	// [CHI]  41.90   87.65  Chicago,IL
	// [51Q]  37.75  122.68  San Francisco,CA
	// [IAD]  38.95   77.46  Washington/Dulles,DC
	// [HNL]  21.35  157.93  Honolulu Int,HI
	// [SAV]  32.13   81.20  Savannah Mun,GA
	// [PDX]  45.60  122.60  Portland,OR

    map.put("BOS", Arrays.asList("42.37", "71.03"));
    map.put("LVS", Arrays.asList("35.65", "105.15"));
    map.put("BUF", Arrays.asList("42.93", "78.73"));
    map.put("NYC", Arrays.asList("40.77", "73.98"));
    map.put("CHI", Arrays.asList("41.90", "87.65"));
    map.put("51Q", Arrays.asList("37.75", "122.68"));
    map.put("IAD", Arrays.asList("38.95", "77.46"));
    map.put("HNL", Arrays.asList("21.35", "157.93"));
    map.put("SAV", Arrays.asList("32.13", "81.20"));
    map.put("PDX", Arrays.asList("45.60", "122.60"));

    for (Map.Entry<String, List<String>> entry : map.entrySet()) {
      String key = entry.getKey();
      String fileName = key + ".csv";
      Path path = Paths.get(fileName);
      if (Files.exists(path)) {
        System.out.println("File " + fileName + " exists");
      } else {
      String url = "https://power.larc.nasa.gov/api/temporal/daily/point?start=20190101&end=20211231&latitude=" + entry.getValue().get(0) + "&longitude=" + entry.getValue().get(1) + "&community=ag&parameters=T2M%2CT2M_MIN%2CT2M_MAX%2CT2MDEW%2CPRECTOTCORR%2CRH2M%2CGWETPROF%2CGWETROOT%2CGWETTOP&format=csv&header=false&time-standard=lst";
      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("User-Agent", "Mozilla/5.0");
      int responseCode = con.getResponseCode();
      System.out.println("Sending 'GET' request to URL : " + url);
      System.out.println("Response Code : " + responseCode);
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
        response.append("\n");
      }
      in.close();
      FileWriter fileWriter = new FileWriter(fileName);
      fileWriter.write(response.toString());
      fileWriter.close();
    } 
  }
    List<List<String>> matrix = null;
    for (Map.Entry<String, List<String>> entry : map.entrySet()) {
        List<List<String>> matrixObj = null;
      String key = entry.getKey();
      Path path = Paths.get(key+".csv");
      try( Stream<String> lines = Files.lines(path) )
      {matrixObj = lines.map( line -> { return Stream.of( line.split(",") ) .map(value->value.substring(0)).collect( Collectors.toList() ); }) .collect( Collectors.toList() );
      } catch (IOException ex) {
        System.out.println(ex);
      }
      matrixObj.remove(0);
      if (matrix == null) {
        matrix = matrixObj;
      } else {
        matrix.addAll(matrixObj);
      }

    }
   
    List<Integer> date = matrix.stream().parallel().map(row -> Integer.parseInt(row.get(0))).collect(Collectors.toList());
    List<Double> doy = matrix.stream().parallel().map(row -> Double.parseDouble(row.get(1))).collect(Collectors.toList());
    List<Double> t2m = matrix.stream().parallel().map(row -> Double.parseDouble(row.get(2))).collect(Collectors.toList());
    List<Double> t2m_min = matrix.stream().parallel().map(row -> Double.parseDouble(row.get(3))).collect(Collectors.toList());
    List<Double> t2m_max = matrix.stream().parallel().map(row -> Double.parseDouble(row.get(4))).collect(Collectors.toList());
    List<Double> t2mdew = matrix.stream().parallel().map(row -> Double.parseDouble(row.get(5))).collect(Collectors.toList());
    List<Double> prectotcorr = matrix.stream().parallel().map(row -> Double.parseDouble(row.get(6))).collect(Collectors.toList());
    List<Double> rh2m = matrix.stream().parallel().map(row -> Double.parseDouble(row.get(7))).collect(Collectors.toList());
    List<Double> gwetprof = matrix.stream().parallel().map(row -> Double.parseDouble(row.get(8))).collect(Collectors.toList());
    List<Double> gwetroot = matrix.stream().parallel().map(row -> Double.parseDouble(row.get(9))).collect(Collectors.toList());
    List<Double> gwettop = matrix.stream().parallel().map(row -> Double.parseDouble(row.get(10))).collect(Collectors.toList());


    int minDate = date.stream().parallel().mapToInt(v -> v).min().getAsInt();
    int maxDate = date.stream().parallel().mapToInt(v -> v).max().getAsInt();
    double minT2M = t2m.stream().parallel().mapToDouble(v -> v).min().getAsDouble();
    double maxT2M = t2m.stream().parallel().mapToDouble(v -> v).max().getAsDouble();
    double minT2MDEW = t2mdew.stream().parallel().mapToDouble(v -> v).min().getAsDouble();
    double maxT2MDEW = t2mdew.stream().parallel().mapToDouble(v -> v).max().getAsDouble();
    double minPRECTOTCORR = prectotcorr.stream().parallel().mapToDouble(v -> v).min().getAsDouble();
    double maxPRECTOTCORR = prectotcorr.stream().parallel().mapToDouble(v -> v).max().getAsDouble();
    double minRH2M = rh2m.stream().parallel().mapToDouble(v -> v).min().getAsDouble();
    double maxRH2M = rh2m.stream().parallel().mapToDouble(v -> v).max().getAsDouble();

    System.out.println("Min Date: " + minDate);
    System.out.println("Max Date: " + maxDate);
    System.out.println("Min T2M for 10 locations from 2019 to 2021: : " + minT2M);
    System.out.println("Max T2M for 10 locations from 2019 to 2021: : " + maxT2M);
    System.out.println("Min T2MDEW for 10 locations from 2019 to 2021: " + minT2MDEW);
    System.out.println("Max T2MDEW for 10 locations from 2019 to 2021: : " + maxT2MDEW);
    System.out.println("Min PRECTOTCORR for 10 locations from 2019 to 2021: : " + minPRECTOTCORR);
    System.out.println("Max PRECTOTCORR for 10 locations from 2019 to 2021: : " + maxPRECTOTCORR);
    System.out.println("Min RH2M for 10 locations from 2019 to 2021: : " + minRH2M);
    System.out.println("Max RH2M for 10 locations from 2019 to 2021: : " + maxRH2M);

    Double sumPrecTotCorr = prectotcorr.stream().parallel().mapToDouble(v -> v).sum();
    Double meanPrectotcorr = sumPrecTotCorr / prectotcorr.size();
    System.out.println("Mean PRECTOTCORR for 10 locations from 2019 to 2021: : " + meanPrectotcorr);

    Double sumRH2M = rh2m.stream().parallel().mapToDouble(v -> v).sum();
    Double meanRH2M = sumRH2M / rh2m.size();
    System.out.println("Mean RH2M for 10 locations from 2019 to 2021: : " + meanRH2M);

    Double sumGWETTOP = gwettop.stream().parallel().mapToDouble(v -> v).sum();
    Double meanGWETTOP = sumGWETTOP / gwettop.size();
    System.out.println("Mean GWETTOP for 10 locations from 2019 to 2021: : " + meanGWETTOP);

    Double sumGWETPROF = gwetprof.stream().parallel().mapToDouble(v -> v).sum();
    Double meanGWETPROF = sumGWETPROF / gwetprof.size();
    System.out.println("Mean GWETPROF for 10 locations from 2019 to 2021: : " + meanGWETPROF);
    
   
    distanceBetweenToLocations();

  }

  public static List<List<String>> getFileMatrix(String key) {
    List<List<String>> matrix = null;
    Path path = Paths.get(key+".csv");
    try( Stream<String> lines = Files.lines(path) )
    {matrix = lines.map( line -> { return Stream.of( line.split(",") ) .map(value->value.substring(0)).collect( Collectors.toList() ); }) .collect( Collectors.toList() );
    } catch (IOException ex) {
      System.out.println(ex);
    }
    matrix.remove(0);
    return matrix;
  }
  public static void distanceBetweenToLocations(){

    List<List<String>> matrix = getFileMatrix("BOS");
    List<List<String>> matrix2 = getFileMatrix("IAD");

   
    List<Double> prectotcorrLocBOS = matrix.stream().parallel().map(row -> Double.parseDouble(row.get(6))).collect(Collectors.toList());
    List<Double> prectotcorrLocIAD = matrix2.stream().parallel().map(row -> Double.parseDouble(row.get(6))).collect(Collectors.toList());

    List<Double> rh2m = matrix.stream().parallel().map(row -> Double.parseDouble(row.get(7))).collect(Collectors.toList());
    List<Double> rh2m2 = matrix2.stream().parallel().map(row -> Double.parseDouble(row.get(7))).collect(Collectors.toList());

    double distance = distance(prectotcorrLocBOS, prectotcorrLocIAD);
    System.out.println("Distance between BOS and IAD with respective to prectotcorr: " + distance);


  }

  public static double distance(List<Double> p1, List<Double> p2) {
		// TODO Error handling if p1.size() != p2.size()
		double sumOfSquared = 0;
		for(int i=0; i < p1.size(); i++) {
			sumOfSquared += Math.pow( p1.get(i)-p2.get(i), 2 );
		}
		return Math.sqrt(sumOfSquared);		
	}
}