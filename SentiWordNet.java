import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class SentiWordNet 
{
	private Map<String, Double> dictionary;

	public SentiWordNet(String pathToSWN) throws IOException 
	{
		// main dictionary representation
		dictionary = new HashMap<String, Double>();

		// From String to list of doubles.
		HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();

		BufferedReader csv = null;
		try
		{
			csv = new BufferedReader(new FileReader(pathToSWN));
			int lineNumber = 0;

			String line;
			while ((line = csv.readLine()) != null) 
			{
				lineNumber++;

				// If it's a comment, skip this line.
				if (!line.trim().startsWith("#")) 
				{
					// We use tab separation
					String[] data = line.split("\t");
					String wordTypeMarker = data[0];

					// Is it a valid line? Otherwise, through exception.
					if (data.length != 6)
					{
						throw new IllegalArgumentException(
								"Incorrect tabulation format in file, line: "
										+ lineNumber);
					}

					// Calculate synset score as score = PosS - NegS
					Double synsetScore = Double.parseDouble(data[2])
							- Double.parseDouble(data[3]);

					// Get all Synset terms
					String[] synTermsSplit = data[4].split(" ");

					// Go through all terms of current synset.
					for (String synTermSplit : synTermsSplit) 
					{
						// Get synterm and synterm rank
						String[] synTermAndRank = synTermSplit.split("#");
						String synTerm = synTermAndRank[0] + "#"
								+ wordTypeMarker;
						//System.out.println("synTerm:"+synTerm);
						int synTermRank = Integer.parseInt(synTermAndRank[1]);
						//  a map of the type:
						// term -> {score of synset#1, score of synset#2...}

						// Add map to term if it doesn't have one
						if (!tempDictionary.containsKey(synTerm)) 
						{
							tempDictionary.put(synTerm,
									new HashMap<Integer, Double>());
						}

						// Add synset link to synterm
						tempDictionary.get(synTerm).put(synTermRank,
								synsetScore);
					}
				}
			}

			// Go through all the terms.
			for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary
					.entrySet()) 
			{
				String word = entry.getKey();
				Map<Integer, Double> synSetScoreMap = entry.getValue();
				

				// Calculate weighted average. Weigh the synsets according to
				// their rank.
				// Score= 1/2*first + 1/3*second + 1/4*third ..... etc.
				// Sum = 1/1 + 1/2 + 1/3 ...
				double score = 0.0;
				double sum = 0.0;
				for (Map.Entry<Integer, Double> setScore : synSetScoreMap
						.entrySet()) 
				{
					score += setScore.getValue() / (double) setScore.getKey();
					sum += 1.0 / (double) setScore.getKey();
				}
				score /= sum;

				dictionary.put(word, score);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			if (csv != null) 
			{
				csv.close();
			}
		}
	}

	public double extract(String word, String pos) 
	{
		return dictionary.get(word + "#" + pos);
	}
	
	public static void main(String [] args) throws IOException 
	{
		String pathToSWN = "D:/Hackathon/BestBuy/src/SentiWordNet_3.0.0_20130122.txt";
		SentiWordNet sentiwordnet = new SentiWordNet(pathToSWN);
		
		/*System.out.println("good#a "+sentiwordnet.extract("good", "a"));
		System.out.println("bad#a "+sentiwordnet.extract("bad", "a"));
		System.out.println("blue#a "+sentiwordnet.extract("blue", "a"));
		System.out.println("blue#n "+sentiwordnet.extract("blue", "n"));*/
		
		// the tweet whose polarity is to be determined
		String text = args[0];
		text = text.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
        text = text.replaceAll("http://www.[a-zA-Z0-9_]+.[a-z]+.[a-z]/[a-zA-Z0-9_]","");
        text = text.replaceAll("http://[a-zA-Z0-9]+.[a-z]+.[a-z]/[a-zA-Z0-9_]","");
        text = text.replaceAll("@[a-zA-Z0-9._]+", "");
        text = text.replaceAll("#[a-zA-Z0-9._]+","");
		text = text.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
		text = text.replaceAll("\\ba\\b","");
		text = text.replaceAll("\\babout\\b","");
		text = text.replaceAll("\\ban\\b","");
		text = text.replaceAll("\\bare\\b","");
		text = text.replaceAll("\\bas\\b","");
		text = text.replaceAll("\\bat\\b","");
		text = text.replaceAll("\\bbe\\b","");
		text = text.replaceAll("\\bby\\b","");
		text = text.replaceAll("\\bcom\\b","");
		text = text.replaceAll("\\bfor\\b","");
		text = text.replaceAll("\\bfrom\\b","");
		text = text.replaceAll("\\bhow\\b","");
		text = text.replaceAll("\\bin\\b","");
		text = text.replaceAll("\\bis\\b","");
		text = text.replaceAll("\\bit\\b","");
		text = text.replaceAll("\\bi\\b","");
		text = text.replaceAll("\\bof\\b","");
		text = text.replaceAll("\\bbn\\b","");
		text = text.replaceAll("\\bor\\b","");
		text = text.replaceAll("\\bthat\\b","");
		text = text.replaceAll("\\bthe\\b","");
		text = text.replaceAll("\\bthis\\b","");
		text = text.replaceAll("\\bto\\b","");
		text = text.replaceAll("\\bwas\\b","");
		text = text.replaceAll("\\bwhat\\b","");
		text = text.replaceAll("\\bwhen\\b","");
		text = text.replaceAll("\\bwhere\\b","");
		text = text.replaceAll("\\bwho\\b","");
		text = text.replaceAll("\\bwill\\b","");
		text = text.replaceAll("\\bwith\\b","");
		
		
		//Using POS Tagger to find parts of speech
		MaxentTagger tagger = new MaxentTagger("D:/Hackathon/BestBuy/src/taggers/wsj-0-18-bidirectional-nodistsim.tagger");
    	String tagged = tagger.tagString(text);
    	//System.out.println(tagged);
    	
    	
    	Map<String, String> map = new HashMap<String, String>();
    	String [] posInformation = tagged.split("\\s");
    	
    	Double temp = new Double(0.0);
    	double totalScore = 0;
    	
    	
    	for(String word : posInformation)
	    {
    		if(word.contains("_PRP$"))
    		{
    			word = word.replace("_PRP$","");
    			map.put(word,"n");
    		}
    		if(word.contains("_PRP"))
    		{
    			word = word.replace("_PRP","");
    			map.put(word,"n");
    		}
    		if(word.contains("_IN"))
    		{
    			word = word.replace("_IN","");
    			map.put(word,"n");
    		}
    		if(word.contains("_CC"))
    		{
    			word = word.replace("_CC","");
    			map.put(word,"n");
    		}
    		if(word.contains("_DT"))
    		{
    			word = word.replace("_DT","");
    			map.put(word,"n");
    		}
    		if(word.contains("_EX"))
    		{
    			word = word.replace("_EX","");
    			map.put(word,"n");
    		}
    		if(word.contains("_PDT"))
    		{
    			word = word.replace("_PDT","");
    			map.put(word,"n");
    		}
    		if(word.contains("_NNPS"))
    		{
    			word = word.replace("_NNPS","");
    			map.put(word,"n");
    		}
    		if(word.contains("_NNP"))
    		{
    			word = word.replace("_NNP","");
    			map.put(word,"n");
    		}
    		else if(word.contains("_FW"))
			{
				word = word.replace("_FW","");
				map.put(word,"n");
			}
    		if(word.contains("_POS"))
    		{
    			word = word.replace("_POS","");
    			map.put(word,"n");
    		}
    		if(word.contains("_UH"))
    		{
    			word = word.replace("_UH","");
    			map.put(word,"n");
    		}
    		if(word.contains("_TO"))
    		{
    			word = word.replace("_TO","");
    			map.put(word,"n");
    		}
    		if(word.contains("_PRP"))
    		{
    			word = word.replace("_PRP","");
    			map.put(word,"n");
    		}
    		if(word.contains("_NNS"))
    		{
    			word = word.replace("_NNS","");
    			map.put(word,"n");
    		}
    		if(word.contains("_NN"))
    		{
    			word = word.replace("_NN","");
    			map.put(word,"n");
    		}
    		if(word.contains("_WDT"))
    		{
    			word = word.replace("_WDT","");
    			map.put(word,"n");
    		}
    		if(word.contains("_WP$"))
    		{
    			word = word.replace("_WP$","");
    			map.put(word,"n");
    		}
    		if(word.contains("_WP"))
    		{
    			word = word.replace("_WP","");
    			map.put(word,"n");
    		}
    		if(word.contains("_MD"))
    		{
    			word = word.replace("_MD","");
    			map.put(word,"n");
    		}
    		if(word.contains("_VBP"))
    		{
    			word = word.replace("_VBP","");
    			map.put(word,"v");
    		}
    		if(word.contains("_VBG"))
    		{
    			word = word.replace("_VBG","");
    			map.put(word,"v");
    		}
    		if(word.contains("_VBN"))
    		{
    			word = word.replace("_VBN","");
    			map.put(word,"v");
    		}
    		if(word.contains("_VBD"))
    		{
    			word = word.replace("_VBD","");
    			map.put(word,"v");
    		}
    		if(word.contains("_VBZ"))
    		{
    			word = word.replace("_VBZ","");
    			map.put(word,"v");
    		}
    		if(word.contains("_VB"))
    		{
    			word = word.replace("_VB","");
    			map.put(word,"v");
    		}
    		if(word.contains("_JJS"))
    		{
    			word = word.replace("_JJS","");
    			map.put(word,"a");
    		}
    		if(word.contains("_JJR"))
    		{
    			word = word.replace("_JJR","");
    			map.put(word,"a");
    		}
    		if(word.contains("_JJ"))
    		{
    			word = word.replace("_JJ","");
    			map.put(word,"a");
    		}
    		if(word.contains("_RBR"))
    		{
    			word = word.replace("_RBR","");
    			map.put(word,"r");
    		}
    		if(word.contains("_RBS"))
    		{
    			word = word.replace("_RBS","");
    			map.put(word,"r");
    		}
    		if(word.contains("_RB"))
    		{
    			word = word.replace("_RB","");
    			map.put(word,"r");
    		}
    		if(word.contains("_WRB"))
    		{
    			word = word.replace("_WRB","");
    			map.put(word,"r");
    		}
    		else if(word.contains("_CD"))
			{
				word = word.replace("_CD","");
			}
	    } //end of for
    	
    	

		//System.out.println(word);
		//word = word.replaceAll("([^a-zA-Z\\s])", "");
		for (Map.Entry<String, String> entry : map.entrySet()) 
		{
			try
			{
				String key = entry.getKey();
				String value = entry.getValue();
				if( (temp = sentiwordnet.extract(key,value)) == null )
					continue;
				totalScore += sentiwordnet.extract(key,value);
			}
			catch(NullPointerException e)
			{
				continue;
			}
		}
    		
		
		PrintWriter pospw = new PrintWriter(new FileWriter("PositiveTweets.txt", true),true);
		PrintWriter negpw = new PrintWriter(new FileWriter("NegativeTweets.txt", true),true);
		if(totalScore > 0)
        	pospw.println( args[0] );
        else if(totalScore < 0)
        	negpw.println( args[0] );
        pospw.close();
        negpw.close();
	}
}