package snippet;

import java.awt.EventQueue;
import java.util.*;

import javax.swing.JFrame;

public class Main {
	private EntityResolution er;
	
	public Main()
	{
		er = new EntityResolution();
		er.parseWWW();
		er.parseAllAuthors();
	}
	
	public Set<Author> getMoreThanK(int k)
	{
		Set<Author> KAuthors = new TreeSet<Author>();
		for(Map.Entry<String, Author> entry : er.ourMainMap.entrySet())
		{
			if(entry.getValue().getNumOfPublications() > k)
			{
				KAuthors.add(entry.getValue());
			}
		}
		return KAuthors;
	}
	
	public List<Publication> getPublicationByAuthor(String authorName)
	{
		return er.parsePublicationByAuthor(authorName);
	}
	
	public void sortPublicationByDate(List<Publication> publ)
	{
		Collections.sort(publ, new Comparator<Publication>()
				{
					public int compare(Publication a, Publication b)
					{
						return Integer.valueOf(b.getYear()).compareTo(a.getYear());
					}
				});
	}

	public int binarySearch(List<Publication> publ, int searchYear)
	{
		int l = 0;
		int h = publ.size();
		while(l < h )
		{
			int mid = (l + h)/2;
			if(publ.get(mid).getYear() < searchYear)
				h = mid;
			else
				l = mid + 1;
		}
		return l;
	}
	
	public Map<Integer, List<Publication> > getPublicatonByTitle(String title)
	{
		String[] splitTitle = title.split(" ");
		List<String> listTitle = Arrays.asList(splitTitle);
		Iterator<String> listIterator = listTitle.iterator();
		while(listIterator.hasNext())
		{
			String word = listIterator.next();
			if(word.equalsIgnoreCase("is") | word.equalsIgnoreCase("a") | word.equalsIgnoreCase("an") | word.equalsIgnoreCase("the") | word.equalsIgnoreCase("on") | word.equalsIgnoreCase("for") | word.equalsIgnoreCase("of"))
			{
				listIterator.remove();
			}
		}
		Map<Integer, List<Publication> > publ = er.parsePublicationByTitle(listTitle);
		return publ;
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
        {
           public void run()
           {
        	   EntityResolution er =  new EntityResolution();
              DBLP_GUI frame = new DBLP_GUI();
              frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              frame.setVisible(true);
           }
        });
	}
}
