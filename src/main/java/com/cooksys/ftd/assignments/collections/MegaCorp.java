package com.cooksys.ftd.assignments.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	// Hierarchy
	private Set<Capitalist> listOfWorkers = new HashSet<Capitalist>();

	/**
	 * Adds a given element to the hierarchy.
	 * <p>
	 * If the given element is already present in the hierarchy, do not add it and
	 * return false
	 * <p>
	 * If the given element has a parent and the parent is not part of the
	 * hierarchy, add the parent and then add the given element
	 * <p>
	 * If the given element has no parent but is a Parent itself, add it to the
	 * hierarchy
	 * <p>
	 * If the given element has no parent and is not a Parent itself, do not add it
	 * and return false
	 *
	 * @param capitalist the element to add to the hierarchy
	 * @return true if the element was added successfully, false otherwise
	 */
	@Override
	public boolean add(Capitalist capitalist) {
		Capitalist c = capitalist;
		boolean added = false; // has the cap been added
		if (c == null) {
			// System.out.println(" Failed: Capitalist is null");System.out.flush();
			return false;
		}
		// System.out.println("Adding: " + c.getName());System.out.flush();

		//Is a fat cat
		if(c.getClass() == FatCat.class) {
			//Is the list doen't have this capitalist add it
			if(!listOfWorkers.contains(c)) {
				listOfWorkers.add(c);
				added = true;
			}
			while(c.hasParent()) {
				//If parent is not in list
				if(!listOfWorkers.contains(c.getParent())) {
					listOfWorkers.add(c.getParent());
					added = true;
				}
				c = c.getParent();
			}
		} else { //Is a wage slave
			//if slave has parent
			if(c.hasParent()) {
				if(!listOfWorkers.contains(c)) {
					listOfWorkers.add(c);
					added = true;
				}
				//While parent has parents
				while(c.hasParent()) {
					//If parent is not in list
					if(!listOfWorkers.contains(c.getParent())) {
						listOfWorkers.add(c.getParent());
						added = true;
					}
					c = c.getParent();
				}
			}
		}
		
		return added;
	}

	/**
	 * @param capitalist the element to search for
	 * @return true if the element has been added to the hierarchy, false otherwise
	 */
	@Override
	public boolean has(Capitalist capitalist) {
		return listOfWorkers.contains(capitalist);
	}

	/**
	 * @return all elements in the hierarchy, or an empty set if no elements have
	 *         been added to the hierarchy
	 */
	@Override
	public Set<Capitalist> getElements() {
		Set<Capitalist> elements = new HashSet<Capitalist>(listOfWorkers);

		return elements;
	}

	/**
	 * @return all parent elements in the hierarchy, or an empty set if no parents
	 *         have been added to the hierarchy
	 */
	@Override
	public Set<FatCat> getParents() {
		Set<FatCat> parents = new HashSet<FatCat>();
		
		for(Capitalist c : listOfWorkers) {
			if(c.getClass() == FatCat.class) {
				parents.add((FatCat) c);
			}
		}
		
		return (new HashSet<FatCat>(parents));
	}

	/**
	 * @param fatCat the parent whose children need to be returned
	 * @return all elements in the hierarchy that have the given parent as a direct
	 *         parent, or an empty set if the parent is not present in the hierarchy
	 *         or if there are no children for the given parent
	 */
	@Override
	public Set<Capitalist> getChildren(FatCat fatCat) {
		Set<Capitalist> children = new HashSet<Capitalist>();
		
		for(Capitalist child : listOfWorkers) {
			if(child.getParent() == fatCat) {
				children.add(child);
			}
		}
		
		return children;
	}

	/**
	 * @return a map in which the keys represent the parent elements in the
	 *         hierarchy, and the each value is a set of the direct children of the
	 *         associate parent, or an empty map if the hierarchy is empty.
	 */
	@Override
	public Map<FatCat, Set<Capitalist>> getHierarchy() {
		Map<FatCat, Set<Capitalist>> copy = new HashMap<FatCat, Set<Capitalist>>();

		//System.out.println("Start"); System.out.flush();
		for(Capitalist c : listOfWorkers) {
			//System.out.println(" c:" + c.getName()); System.out.flush();
			if(c.getClass() == FatCat.class) {
				if(!copy.containsKey(c)) {
					copy.put((FatCat) c, new HashSet<Capitalist>());
				}
				if(c.hasParent()) {
					//System.out.println("  Has parent " + c.getParent().getName()); System.out.flush();
					if(!copy.containsKey(c.getParent())) {
						//System.out.println("   Is not in list"); System.out.flush();
						copy.put(c.getParent(), new HashSet<Capitalist>());
					}
					copy.get(c.getParent()).add(c);
				}
			} else {
				if(c.hasParent()) {
					if(copy.containsKey(c.getParent())) {
						copy.get(c.getParent()).add(c);
					} else {
						copy.put(c.getParent(), new HashSet<Capitalist>());
						copy.get(c.getParent()).add(c);
					}
				}
			}
		}
		//System.out.println("End");
			
		return copy;
	}

	/**
	 * @param capitalist
	 * @return the parent chain of the given element, starting with its direct
	 *         parent, then its parent's parent, etc, or an empty list if the given
	 *         element has no parent or if its parent is not in the hierarchy
	 */
	@Override
	public List<FatCat> getParentChain(Capitalist capitalist) {
		List<FatCat> chain = new ArrayList<FatCat>();
		Capitalist c = capitalist;
		
		if(c == null || !listOfWorkers.contains(c)) {
			return chain;
		}
		
		while(c.hasParent()) {
			chain.add(c.getParent());
			c = c.getParent();
		}
		
		return chain;
	}
}
