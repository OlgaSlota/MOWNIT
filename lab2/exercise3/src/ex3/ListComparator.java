package zad3;

import java.util.Comparator;
import java.util.List;

public class ListComparator implements Comparator<List>{
	@Override
	public int compare(List l1, List l2) {
		return l1.size()- l2.size();

	}


}
