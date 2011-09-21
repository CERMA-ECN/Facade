package fr.irstv.kmeans;

import fr.irstv.dataModel.*;
import java.util.*;

public class CleaningDataGroups {
	
	public DataGroup[] clean(DataGroup[] groups) {
		int counter = 0;
		// counting the non null groups
		for (DataGroup group : groups) {
			if (group.getComponents().size() == 0) {
				counter++;
			}
		}
		// creating the cleaned groups
		DataGroup[] newGroups = new DataGroup[groups.length-counter];
		for (int i=0;i<groups.length;i++) {
			if (groups[i].getComponents().size() != 0) {
				newGroups[i] = new DataGroup(groups[i].fctDistance);
				newGroups[i].setCentroid(groups[i].getCentroid());
				newGroups[i].components = new ArrayList<MkDataPoint>(groups[i].getComponents());
			}
		}
		
		return newGroups;
	}

}
