package Tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLParser{

    private ArrayList<String> notToUseTags; 
    private String xmlFileName;

    public XMLParser() {
    };
    
    
    // Onderstaande methoden voor toekomstig gebruik parser
    /** 
     * @return ArrayList
     */
    public ArrayList<String> getNotToUseTags() {
        return this.notToUseTags;
    }

    /** 
     * @param notToUseTags
     * @return HashMap<String, String>
     */

    public void setNotToUseTags(ArrayList<String> notToUseTags) {
        this.notToUseTags = notToUseTags;
    }

    
    /** 
     * @return String
     */
    public String getXmlFileName() {
        return this.xmlFileName;
    }

    
    /** 
     * @param xmlFileName
     */
    public void setXmlFileName(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }
    
    /** 
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof XMLParser)) {
            return false;
        }
        XMLParser xMLParser = (XMLParser) o;
        return Objects.equals(notToUseTags, xMLParser.notToUseTags) && Objects.equals(xmlFileName, xMLParser.xmlFileName);
    }

    
    /** 
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(notToUseTags, xmlFileName);
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "{" +
            " notToUseTags='" + getNotToUseTags() + "'" +
            ", xmlFileName='" + getXmlFileName() + "'" +
            "}";
    }

    public XMLParser(String xmlfile){
        this.xmlFileName = xmlfile;
    }

    
    /** 
     * @return HashMap<String, String>
     */
    public HashMap<String, String> parse(String file) {
        if (!file.equals("")) {
            this.xmlFileName = file;
        }
        final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        BufferedReader br = null;
        String regel;
        InputStream is = classloader.getResourceAsStream(this.xmlFileName);
        HashMap<String, String> hm = new HashMap<>(); 
        

        try {
            br = new BufferedReader(new InputStreamReader(is, "UTF-8")); // De reader word geinistieerd met een fileReader van het CSV bestand
            while ((regel = br.readLine()) != null) { // Wanneer er nog een nieuwe regel is
                regel = regel.trim();
                /** NOTICE: The code below is quick and dirty. Needs improvement */
                if (!notToUseTags.contains(regel) && !regel.startsWith("<?") && !regel.startsWith("<!--")){
                    System.out.println("Not Contains regel and not <?");
                    //"<pre>(.*?)</pre>"
                    Pattern patternValue = Pattern.compile(">(.*?)</");
                    Pattern patternKey = Pattern.compile("</(.*?)>");

                    Matcher mtchVal = patternValue.matcher(regel);
                    Matcher mtchKey = patternKey.matcher(regel);

                    if (mtchVal.find() && mtchKey.find()){
                        System.out.println("IF find");
                        hm.put(mtchKey.group(1),mtchVal.group(1));
                    }
                } else {
                    System.out.println("IS VERSION OF NOT OT USE");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } 
        return hm;
    }

    /**
     * @return HashMap<String, String>
     */
    public ArrayList<HashMap<String, String>> parseStream(String whatTagToParse, String theStream) {
        ArrayList<HashMap<String, String>> extractedData = new ArrayList<>();

        Pattern extractedSubStream = Pattern.compile("<" + whatTagToParse + ">(.*?)</" + whatTagToParse + ">");
        Matcher subMatch = extractedSubStream.matcher(theStream);

        while (subMatch.find()) {
            HashMap<String, String> hm = new HashMap<>();
            Pattern patternValue = Pattern.compile(">(.*?)</");
            Pattern patternKey = Pattern.compile("</(.*?)>");

            String subm = subMatch.group(1);
            //System.out.println(subm);

            Matcher mtchVal = patternValue.matcher(subm);
            Matcher mtchKey = patternKey.matcher(subm);

            while (mtchVal.find() && mtchKey.find()) {
                String val = mtchVal.group(1);
                if (val.contains(">")) {
                    int start = val.indexOf(">");
                    val = val.substring(start + 1);
                }
                hm.put(mtchKey.group(1), val);
            }
            extractedData.add(hm);
        }
        return extractedData;
    }
}