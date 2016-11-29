package snippet;

import java.util.*;

public class Main {
	public static void main(String[] args)
	{
		EntityResolution er = new EntityResolution();
		er.parseWWW();
		Set<Author> moreThanK = er.parseAllAuthors(70);
//		System.out.println("Authors with more than 20 publications: ");
//		for(Author a : moreThanK) 
//		{
//			System.out.println(a);
//			System.out.println("-------------------");
//		}
		
		System.out.println(er.ourMainMap.get("Craig Gentry"));
		System.out.println(er.ourMainMap.get("Craig Gentry").getNumOfPublications());
		for(String i : er.ourMainMap.get("Craig Gentry").getNames())
		{
			System.out.println(i);
		}
		System.out.println("-------------------------------");
		List<Publication> publ = er.parsePublicationByAuthor("Craig Gentry");
		
		System.out.println("\n\nResult for publications by the Author : Craig Gentry");
		int count = 0;
		for(Publication p : publ)
		{
			count += 1;
			System.out.println(count);
			System.out.println(p);
			System.out.println("-------------------");
		}
	
		Map<Integer, List<Publication> > publMap = er.parsePublicationByTitle("encrypted data");
		
		System.out.println("\n\nResult for publications with title tags encrypted data: ");
		for(Map.Entry<Integer, List<Publication> > entry: publMap.entrySet())
		{
			for(Publication p : entry.getValue())
			{
				System.out.println(p);
				System.out.println("-------------------");
			}
		}
	}
}
