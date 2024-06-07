import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Utilities {

    public static Set<String> toTokenSet(String text){
        Set<String> textTokens = new HashSet<>();
        
        String cleanText = text.replaceAll("[^a-zA-Z ]", " ");

        String spacedText = cleanText.toUpperCase().replaceAll("  ", " ");

        String[] tokens = spacedText.split(" ");

        for(String token: tokens){
            textTokens.add(token);
        }

        return textTokens;        
    }

    public static String toFilename(String text){
        LinkedHashSet<String> textTokens = new LinkedHashSet<>();
        
        String cleanText = text.replaceAll("[^a-zA-Z0-9]", " ");

        String spacedText = cleanText.toLowerCase().replaceAll("  ", " ");

        String[] tokens = spacedText.split(" ");

        for(int tokenNumber = 0; tokenNumber < tokens.length; tokenNumber++){
            String token = tokens[tokenNumber];
            
            if(tokenNumber == 0){
                textTokens.add(token);    
            } else {
                char firstChar = token.charAt(0);
                char upperCase = Character.toUpperCase(firstChar);
    
                textTokens.add(upperCase + token.substring(1));
            }
        }

        String name = " ";
        for(String token: textTokens){
            name += token;
        }
        String filename = name + ".txt"; 
        
        return filename.trim();
    }

    public static int matchTitles(String textA, String textB){
        Set<String> textSetA = toTokenSet(textA);
        Set<String> textSetB = toTokenSet(textB);

        Set<String> longer = null;
        Set<String> shorter = null;

        if(textSetA.equals(textSetB)){
            return 1;
        } else {
            if(textSetA.size() > textSetB.size()) {
                longer = textSetA;
                shorter = textSetB;
            } else {
                longer = textSetB;
                shorter = textSetA;
            }

            longer.retainAll(shorter);

            if(!longer.isEmpty()){
                return 2;
            } else {
                return 0;
            }
        }
    }

    public static Set<String> stringCleaning(String text){
        Set<String> textTokens = new HashSet<>();
        
        String cleanText = text.replaceAll("[^a-zA-Z ]", " ");

        String spacedText = cleanText.toUpperCase().replaceAll("  ", " ");

        String[] tokens = spacedText.split(" ");

        for(String token: tokens){
            textTokens.add(token);
        }
        return textTokens;
    } 
}
