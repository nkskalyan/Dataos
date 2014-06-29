import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class SentimentClassifier {
	
	public static void main(String args[]) throws IOException
	{
		File fname = new File("D:/Hackathon/BestBuy/src/tweet1");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
		String[] line = new String[2];
		//int count = 0;
		while((line[0] = br.readLine()) != null)
		{	
			//count++;
			if(line[0] == "\n" || line[0] == "..")
				continue;
			SentiWordNet.main(line);
		}
	}
}
