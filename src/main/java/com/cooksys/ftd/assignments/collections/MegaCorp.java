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
	// private Map<Capitalist, FatCat> workers = new HashMap<Capitalist, FatCat>();
	// private Map<FatCat, Set<Capitalist>> owners = new
	// HashMap<FatCat,Set<Capitalist>>(); //parents

	// Hierarchy
	private Map<FatCat, Set<Capitalist>> hierarchy = new HashMap<FatCat, Set<Capitalist>>();

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

		// Is it a fat cat?
		if (c.getClass() == FatCat.class) {
			// System.out.println(" Is FatCat");System.out.flush();
			// If it does not exist in the hierarchy
			if (!this.hierarchy.containsKey(c)) {
				// System.out.println(" Not in hierarchy");System.out.flush();
				this.hierarchy.put((FatCat) c, new HashSet<Capitalist>());
				// Does the c have a Parent?
				if (c.getParent() != null) {
					// While there are more parents
					while (c.getParent() != null) {
						// System.out.println(" Has Parent: " +
						// c.getParent().getName());System.out.flush();
						// If parent do not exist in hierarchy
						if (!this.hierarchy.containsKey(c.getParent())) {
							// System.out.println(" Parent NOT in Hierarchy");System.out.flush();
							// create new parent in hierarchy and add child
							this.hierarchy.put(c.getParent(), new HashSet<Capitalist>());
							added = true;
						} else {
							// System.out.println(" Parent IS in Hierarchy");System.out.flush();
						}
						this.hierarchy.get(c.getParent()).add(c); // because it's a set, it'll delete if there is
																	// duplicates
						c = c.getParent();
					}
					// System.out.println(" End Parent Loop: " + c.getParent() + " : " +
					// added);System.out.flush();
					return added;
				} else { // If there is a FatCat that does not exist without a parent
					// System.out.println(" Does NOT have parent");System.out.flush();
					this.hierarchy.put((FatCat) c, new HashSet<Capitalist>());
				}
			} else { // If is in hierarchy
				// System.out.println(" Is in hierarchy");System.out.flush();
				// While there are more parents
				while (c.getParent() != null) {
					// System.out.println(" Has Parent: " +
					// c.getParent().getName());System.out.flush();
					// If parent do not exist in hierarchy
					if (!this.hierarchy.containsKey(c.getParent())) {
						// System.out.println(" Parent NOT in Hierarchy");System.out.flush();
						// create new parent in hierarchy and add child
						this.hierarchy.put(c.getParent(), new HashSet<Capitalist>());
						added = true;
					} else {
						// System.out.println(" Parent IS in Hierarchy");System.out.flush();
					}
					this.hierarchy.get(c.getParent()).add(c); // because it's a set, it'll delete if there is duplicates
					c = c.getParent();
				}
				// System.out.println(" End Parent Loop: " + c.getParent());System.out.flush();
				return added;
			}
		} else { // if it is not a fat cat? (Is a wage slave);
			// System.out.println(" Is WageSlave");System.out.flush();
			// If WageSlave is not in hierarchy
			if (!has(c)) {
				// System.out.println(" slave not in Hierarchy");System.out.flush();
				// Does the c have a Parent?
				if (c.getParent() != null) {
					// While there are more parents
					while (c.getParent() != null) {
						// System.out.println(" Has Parent: " +
						// c.getParent().getName());System.out.flush();
						// If parent do not exist in hierarchy
						if (!this.hierarchy.containsKey(c.getParent())) {
							// System.out.println(" Parent NOT in Hierarchy");System.out.flush();
							// create new parent in hierarchy and add child
							this.hierarchy.put(c.getParent(), new HashSet<Capitalist>());
						} else {
							// System.out.println(" Parent IS in Hierarchy");System.out.flush();
						}
						this.hierarchy.get(c.getParent()).add(c); // because it's a set, it'll delete if there is
																	// duplicates
						c = c.getParent();
					}
					// System.out.println(" End Parent Loop: " + c.getParent());System.out.flush();
				} else { // If there is a wage slave without a parent
					// System.out.println(" Does NOT have Parent: " +
					// c.getParent().getName());System.out.flush();
					return false;
				}
			} else {
				// System.out.println(" slave is in Hierarchy");System.out.flush();
				return false;
			}
		}
		// System.out.println(" ---Added---");System.out.flush();
		return true;

		// ---Old method---
		// Is the element already in the hierarchy
		// if(this.workers.containsKey(c)) {
		// //System.out.println(" Failed: Already in the hierarchy");System.out.flush();
		// return false;
		// } else { //if element is not in the hierarchy
		// //System.out.println(" Not in the hierarchy");System.out.flush();
		// if(c.getClass() == FatCat.class) {
		// //add to hierarchy
		// this.workers.put(c, null);
		// this.owners.put((FatCat) c, new HashSet<Capitalist>());
		// //System.out.println(" A Fat Cat so added whether parentless or not");
		// }
		// //If element has a parent
		// if(c.hasParent()) {
		// //System.out.println(" Has Parent");System.out.flush();
		// //Loop for all parents
		// while(c.getParent() != null) {
		// //If element's parent is not part of hierarchy (aka parents have not existed
		// before);
		// if(!this.workers.containsKey(c.getParent())) {
		// //System.out.println(" Parent not in hierarchy: " + c.getParent().getName() +
		// " : " + c.getParent().getSalary());System.out.flush();
		// //Add parent
		// this.workers.put(c.getParent(), null);
		// this.owners.put(c.getParent(), new HashSet<Capitalist>());
		// this.owners.get(c.getParent()).add(c);
		// } else { //if parent is part of hierarchy
		// //System.out.println(" Parent in hierarchy: " +
		// c.getParent().getName());System.out.flush();
		// //Check if the set is null
		// if(this.owners.get(c.getParent()) == null) {
		// Set<Capitalist> s = this.owners.get(c.getParent());
		// s = new HashSet<Capitalist>();
		// this.owners.put(c.getParent(), s);
		// }
		// this.owners.get(c.getParent()).add(c);
		// }
		// this.workers.put(c, c.getParent());
		// c = c.getParent();
		// }
		// //System.out.println(" Test: c: " + c.getName() + " cp: " +
		// c.getParent());System.out.flush();
		// } else { //if element does not have a parent
		// System.out.println(" Failed: Parentless WageSlave");System.out.flush();
		// return (c.getClass() == FatCat.class);
		// }
		// }

		// System.out.println(" Added");System.out.flush();
		// return true;
	}

	/**
	 * @param capitalist the element to search for
	 * @return true if the element has been added to the hierarchy, false otherwise
	 */
	@Override
	public boolean has(Capitalist capitalist) {
		if (this.hierarchy.containsKey(capitalist)) {
			return true;
		} else {
			for (Map.Entry<FatCat, Set<Capitalist>> entry : this.hierarchy.entrySet()) {
				for (Capitalist c : entry.getValue()) {
					if (c == capitalist)
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return all elements in the hierarchy, or an empty set if no elements have
	 *         been added to the hierarchy
	 */
	@Override
	public Set<Capitalist> getElements() {
		Set<Capitalist> elements = new HashSet<Capitalist>();
		for (Map.Entry<FatCat, Set<Capitalist>> entry : this.hierarchy.entrySet()) {
			elements.add(entry.getKey());
			elements.addAll(entry.getValue());
		}

		return elements;
	}

	/**
	 * @return all parent elements in the hierarchy, or an empty set if no parents
	 *         have been added to the hierarchy
	 */
	@Override
	public Set<FatCat> getParents() {
		Set<FatCat> parents = this.hierarchy.keySet();
		if (parents == null) {
			return (new HashSet<FatCat>());
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
		Set<Capitalist> children = this.hierarchy.get(fatCat);
		if (children == null) {
			return (new HashSet<Capitalist>());
		}
		return (new HashSet<Capitalist>(children));
	}

	/**
	 * @return a map in which the keys represent the parent elements in the
	 *         hierarchy, and the each value is a set of the direct children of the
	 *         associate parent, or an empty map if the hierarchy is empty.
	 */
	@Override
	public Map<FatCat, Set<Capitalist>> getHierarchy() {
		Map<FatCat, Set<Capitalist>> copy = new HashMap<FatCat, Set<Capitalist>>();

		if (this.hierarchy == null) {
			return copy;
		}

		for (Map.Entry<FatCat, Set<Capitalist>> entry : this.hierarchy.entrySet()) {
			copy.put(entry.getKey(), new HashSet<Capitalist>(entry.getValue()));
		}

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
		boolean continueLoop = true;

		while (continueLoop) {
			continueLoop = false;
			for (Map.Entry<FatCat, Set<Capitalist>> entry : this.hierarchy.entrySet()) {
				if (entry.getValue().contains(c)) {
					chain.add(entry.getKey());
					c = entry.getKey();
					continueLoop = true;
				}
			}
		}
		return chain;
	}
}
