package com.cooksys.ftd.assignments.collections;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

public class Test {
	public static void main(String[] args) {
		MegaCorp corp = new MegaCorp();
		FatCat f1 = new FatCat("Great GrandMother", 1);
		FatCat f2 = new FatCat("GrandMother", 1, f1);
		FatCat f3 = new FatCat("Mother", 1, f2);
		FatCat f4 = new FatCat("Sister", 1, f3);
		WageSlave f5 = new WageSlave("Daughter", 1, f4);
		FatCat f6 = new FatCat("Stranger", 1);
		FatCat f7 = new FatCat("Stranger's son", 1, f6);
		WageSlave f8 = new WageSlave("Brother", 1, f3);
		
		corp.add(f5);
		corp.add(f7);
		corp.add(f8);
		
		Map<FatCat, Set<Capitalist>> map = corp.getHierarchy();
		
		System.out.println("Children");
        for (FatCat parent : corp.getParents()) {
        	System.out.println("P: " + parent.getName());
        	for(Capitalist child : corp.getChildren(parent)) {
        		System.out.println(" c: " + child.getName());
        	}
        }
		System.out.println("Children");
        for (FatCat parent : corp.getParents()) {
        	System.out.println("P: " + parent.getName());
        	for(Capitalist child : map.get(parent)) {
        		System.out.println(" c: " + child.getName());
        	}
        }
	}
}
