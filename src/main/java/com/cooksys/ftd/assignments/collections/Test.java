package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

import java.util.Map;
import java.util.Set;

import com.cooksys.ftd.assignments.collections.model.Capitalist;

public class Test {
	public static void main(String[] args) {
		MegaCorp corp = new MegaCorp();
		
		FatCat f1 = new FatCat("Great Great Grandmother", 1);
		FatCat f2 = new FatCat("Great Grandmother", 2, f1);
		FatCat f3 = new FatCat("Grandmother", 3, f2);
		FatCat f4 = new FatCat("Mother", 4, f3);
		FatCat f5 = new FatCat("Sister", 5, f4);
		FatCat f6 = new FatCat("Daughter", 6, f5);
		FatCat f10 = new FatCat("Stranger", 3);
		
		//corp.add(f1);
		corp.add(f6);
		corp.add(f10);

    	System.out.println("-----Print-----");System.out.flush();
    	System.out.println(corp.has(f10));
    	System.out.println(corp.has(f1));
		
	
    	System.out.println("-----Print-----");System.out.flush();
		
		for (Map.Entry<FatCat, Set<Capitalist>> entry : corp.getHierarchy().entrySet()) {
            System.out.println("FatCat: " + entry.getKey().getName());
            for(Capitalist c : entry.getValue()) {
            	System.out.println("  c: " + c.getName());
            }
		}
		System.out.println("-----Print-----");System.out.flush();
		
		for (Capitalist entry : corp.getParents()) {
            System.out.println("FatCat: " + entry.getName());
            if(entry.getParent() != null) {
            	System.out.println("  c: " + entry.getParent().getName());
            } else {
            	System.out.println("  c: null");
            }
		}
	}
}
